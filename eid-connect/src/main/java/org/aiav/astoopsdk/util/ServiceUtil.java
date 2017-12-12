package org.aiav.astoopsdk.util;

import java.beans.Encoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.github.kevinsawicki.http.HttpRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.aiav.astoopsdk.constants.Constant;
import org.aiav.crypto.EidCryptoUtils;
import org.aiav.crypto.util.CryptoFuncUtil;
import org.apache.log4j.Logger;

public class ServiceUtil {
	private static final Logger log = Logger.getLogger(ServiceUtil.class);

	public static String genHexString(int byteLen) {
		if (byteLen < 1) {
			return null;
		}
		int n = 1;
		if (byteLen % 16 == 0) {
			n = byteLen / 16;
		} else {
			n = byteLen / 16 + 1;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < n; i++) {
			sb.append(UUID.randomUUID().toString().replace("-", "")
					.toUpperCase());
		}
		return sb.toString().substring(0, byteLen * 2);
	}

	public static String buildSoftIdhash(String userName, String userId,
			String salt) {
		if (FuncUtil.isEmpty(userName) && FuncUtil.isEmpty(userId)
				&& FuncUtil.isEmpty(salt)) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(null == userName ? "" : userName);
		sb.append(null == userId ? "" : userId);
		sb.append(null == salt ? "" : salt);
		byte[] sm3 = EidCryptoUtils.createSM3(CryptoFuncUtil.getStringBytes(sb
				.toString()));
		if (!FuncUtil.isEmpty(sm3)) {
			return CryptoFuncUtil.encodeBytesToBase64(sm3);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static String createStrToSign(JSONObject json) {

		if (FuncUtil.isEmpty(json)) {
			return "";
		}
		StringBuffer returnSb = new StringBuffer();
		List<String> keys = new ArrayList<String>(json.keySet());
		Collections.sort(keys);
		int i = 0;
		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
			String key = iterator.next();
			Object obj = json.get(key);
			String value = "";
			if (Constant.SIGN.equalsIgnoreCase(key) || FuncUtil.isEmpty(obj)) {
				continue;
			}

			if (obj instanceof JSONArray) {
				List<String> tmpList = new ArrayList<String>();
				String tmpKey = null;
				StringBuffer tmpSb = new StringBuffer();

				Iterator<JSONObject> iter = ((JSONArray) obj).iterator();
				while (iter.hasNext()) {
					JSONObject tmpJson = iter.next();
					Iterator<String> iter2 = tmpJson.keySet().iterator();
					while (iter2.hasNext()) {
						tmpKey = iter2.next();
						if (!FuncUtil.isEmpty(tmpKey)
								&& !FuncUtil.isEmpty(tmpJson.get(tmpKey))) {
							tmpList.add(tmpKey + Constant.EQUALITY_SIGN
									+ tmpJson.getString(tmpKey));
						}
					}
				}

				Collections.sort(tmpList);

				for (int j = 0; j < tmpList.size(); j++) {
					if (j == (tmpList.size() - 1)) {
						tmpSb.append(tmpList.get(j));
					} else {
						tmpSb.append(tmpList.get(j)).append(Constant.AND);
					}
				}
				value = tmpSb.toString();
			} else if (obj instanceof JSONObject) {
				value = createStrToSign((JSONObject) obj);
			} else if (obj instanceof String) {
				value = (String) obj;
			} else {
				throw new IllegalArgumentException("unsupported param type");
			}

			if (value != null && !"".equals(value)) {
				if (i != 0) {
					returnSb.append(Constant.AND);
				}
				returnSb.append(key).append(Constant.EQUALITY_SIGN)
						.append(value);
				i++;
			}

		}
		return returnSb.toString();

	}

	public static String doRequest(String requestStr, String requestUrl) {
		log.info(" request JSON string: " + requestStr);
		String resultStr = null;
		try {
//			resultStr = HttpRequest.post(requestUrl).send(requestStr).body();
			resultStr = HttpClientUtil.httpclientRequest(
					HttpClientUtil.JSON_TYPE, requestUrl, requestStr);
			if (FuncUtil.isEmpty(resultStr)) {
				log.error(" respnose string null");
				return null;
			}
			log.info(" result JSON string: " + resultStr);
			return resultStr;
		} catch (Exception e) {
			log.error(" exception");
			return null;
		}
	}
}
