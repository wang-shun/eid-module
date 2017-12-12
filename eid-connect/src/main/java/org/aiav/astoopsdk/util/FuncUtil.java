package org.aiav.astoopsdk.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.aiav.astoopsdk.constants.Constant;

public class FuncUtil {

	/**
	 * <p>
	 * Create strToSign to CryptoService
	 * </p>
	 *
	 * @param obj
	 * @return String
	 */
	public static String strToSign(Object obj) {
		if (obj == null)
			return "";
		else
			try {
				return createLinkString(paramFilter(obj2MapToDoSign(obj)));
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
	}

	/**
	 * <p>
	 * Trim blanks at start or end of a string.
	 * </p>
	 *
	 * @param toBeTrim
	 * @return String
	 */
	public static String trimStr(String toBeTrim) {
		String returnValue = toBeTrim;
		if (toBeTrim != null && !"".equals(toBeTrim)) {
			returnValue = returnValue.trim();

			for (; returnValue != null
					&& !"".equals(returnValue)
					&& (returnValue.startsWith("　") || returnValue
					.startsWith(" "));) {
				if (returnValue.startsWith("　"))
					returnValue = returnValue.substring(1);
				if (returnValue.startsWith(" "))
					returnValue = returnValue.trim();
			}

			for (; returnValue != null && !"".equals(returnValue)
					&& (returnValue.endsWith("　") || returnValue.endsWith(" "));) {
				if (returnValue.endsWith("　"))
					returnValue = returnValue.substring(0,
							returnValue.length() - 1);
				if (returnValue.endsWith(" "))
					returnValue = returnValue.trim();
			}

		}
		return returnValue;
	}

	/**
	 * <p>
	 * Check a string whether it is empty.
	 * </p>
	 *
	 * @param value
	 * @return boolean<br>
	 *         true: value is empty<br>
	 *         false: value is not empty
	 */
	public static boolean isEmpty(String value) {
		if (value == null || "".equals(value))
			return true;
		return false;
	}

	/**
	 * <p>
	 * Check a string or a JSON object whether it is empty.
	 * </p>
	 *
	 * @param value
	 * @return boolean<br>
	 *         true: value is empty<br>
	 *         false: value is not empty
	 */
	public static boolean isEmpty(Object value) {
		if (value == null || value.equals(null))
			return true;
		else {
			if ((value instanceof String) && isEmpty((String) value)) {
				return true;
			} else if ((value instanceof JSONObject)
					&& ((JSONObject) value).isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <p>
	 * Check strings whether they are empty.
	 * </p>
	 *
	 * @param value
	 * @return boolean<br>
	 *         true: value is empty<br>
	 *         false: value is not empty
	 */
	public static boolean isEmpty(String... values) {
		for (String value : values) {
			if (value == null || "".equals(value))
				return true;
		}
		return false;
	}

	// /**
	// * <p>
	// * Check a string whether it is empty after trimmed blanks at start or end
	// * of it.
	// * </p>
	// *
	// * @param value
	// * @return boolean<br>
	// * true: value is empty<br>
	// * false: value is not empty
	// */
	// public static boolean isEmptyAfterTrim(String value) {
	// String val = trimStr(value);
	// return isEmpty(val);
	// }
	//
	// /**
	// * <p>
	// * Check a string after trimmed blanks at start or end of it or a JSON
	// * object whether it is empty.
	// * </p>
	// *
	// * @param value
	// * @return boolean<br>
	// * true: value is empty<br>
	// * false: value is not empty
	// */
	// public static boolean isEmptyAfterTrim(Object value) {
	// if (value == null || value.equals(null))
	// return true;
	// else {
	// if ((value instanceof String)) {
	// String val = trimStr((String) value);
	// if (isEmpty(val))
	// return true;
	// } else if ((value instanceof JSONObject)
	// && ((JSONObject) value).isEmpty()) {
	// return true;
	// }
	// }
	// return false;
	// }
	//
	// /**
	// * <p>
	// * Check strings whether they are empty after trimmed blanks at start or
	// end
	// * of them.
	// * </p>
	// *
	// * @param value
	// * @return boolean<br>
	// * true: value is empty<br>
	// * false: value is not empty
	// */
	// public static boolean isEmptyAfterTrim(String... values) {
	// for (String value : values) {
	// String val = trimStr(value);
	// if (isEmpty(val))
	// return true;
	// }
	// return false;
	// }

	/**
	 * <p>
	 * Check the version whether it is correct.
	 * </p>
	 *
	 * @param version
	 * @return boolean <br>
	 *         true: the version is correct.<br>
	 *         false: the version is not correct.
	 */
	public static boolean isCorrectVersionFormat(String version) {
		return regMatch("^[0-9]{1,}(\\.)[0-9]{1,}(\\.)[0-9]{1,}", version);
	}

	/**
	 * <p>
	 * Check the IP format whether it matched "xxx.xxx.xxx.xxx".
	 * </p>
	 *
	 * @param ip
	 * @return boolean<br>
	 *         true: ip is correct.<br>
	 *         false: ip is not correct.
	 */
	public static boolean isCorrectIpFormat(String ip) {
		return regMatch(
				"(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])(\\.)(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])(\\.)(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])(\\.)(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])",
				ip);
	}

	/**
	 * <p>
	 * Check if the argument is a BASE64 string.<br>
	 * </p>
	 *
	 * @param str
	 * @return boolean<br>
	 *         true: is a BASE64 string.<br>
	 *         false: not a BASE64 string.
	 */
	public static boolean isBase64Str(String str) {
		return regMatch("[A-Za-z0-9=\\/\\+]+", str);
	}

	/**
	 * <p>
	 * Check the url whether is started with 'http://' or 'https://'.
	 * </p>
	 *
	 * @param url
	 * @return boolean<br>
	 *         true: url is correct.<br>
	 *         false: url is not correct.
	 */
	public static boolean isHttpUrl(String url) {
		return regMatch("(https|http)://([A-Za-z0-9-_%&?/=~+:]|(\\.))+", url);
	}

	public static boolean isValidFactor(String factor) {
		return regMatch("[A-Fa-f0-9]{16}", factor);
	}

	/**
	 * <p>
	 * Get exception message of last throwable.
	 * </p>
	 *
	 * @param t
	 *            (Throwable)
	 * @return
	 */
	public static String showTraces(Throwable t) {
		Throwable next = t.getCause();
		if (next == null) {
			return t.getMessage();
		} else {
			return showTraces(next);
		}
	}

	/**
	 * <p>
	 * Check the bytes of the string is less than or equals to the length.
	 * </p>
	 *
	 * @param value
	 * @param length
	 * @return boolean<br>
	 *         true: is less than or equals to the length;<br>
	 *         false: is more than the length;
	 */
	public static boolean leLengthOfBytes(String value, int length) {
		try {
			if (value.getBytes("UTF-8").length <= length)
				return true;
		} catch (UnsupportedEncodingException e) {
		}
		return false;
	}

	// ===============================================================================

	/**
	 * <p>
	 * The private function for doing regular expression check.
	 * </p>
	 *
	 * @param regex
	 * @param arg
	 * @return boolean<br>
	 *         true: the arg matches regex.<br>
	 *         false: the arg does not match regex.
	 */
	private static boolean regMatch(String regex, String arg) {
		Pattern formatPattern = Pattern.compile(regex);
		Matcher matcher = formatPattern.matcher(arg);
		return matcher.matches();
	}

	/**
	 * <p>
	 * in the return map: <br>
	 * key = "a"; value = "abc";<br>
	 * key = "b"; value = "c=c&d=d";
	 * </p>
	 *
	 * @param obj
	 * @return Map<String, String>
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> obj2MapToDoSign(Object obj)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Map<String, String> map = null;

		if (!isEmpty(obj)) {
			map = new HashMap<String, String>();
			Field[] fs = obj.getClass().getDeclaredFields();
			Field f = null;

			for (int i = 0; i < fs.length; i++) {
				f = fs[i];
				f.setAccessible(true);

				if (!Constant.SERIAL_VERSION_UID.equals(f.getName())) {
					if (FuncUtil.isEmpty(f.get(obj))) {
						map.put(f.getName(), null);
					} else {
						if (f.get(obj) instanceof String) {
							map.put(f.getName(), (String) f.get(obj));
						} else if (f.get(obj) instanceof JSONArray) {
							JSONArray tmpA = (JSONArray) f.get(obj);
							List<String> tmpL = new ArrayList<String>();
							String key = null;
							StringBuffer sb = new StringBuffer();

							Iterator<JSONObject> iter = tmpA.iterator();
							while (iter.hasNext()) {
								JSONObject tmeJ = iter.next();
								Iterator<String> iter2 = tmeJ.keySet()
										.iterator();
								while (iter2.hasNext()) {
									key = iter2.next().toString();
									if (!isEmpty(key)
											&& !isEmpty(tmeJ.get(key))) {
										tmpL.add(key + Constant.EQUALITY_SIGN
												+ tmeJ.getString(key));
									}
								}
							}

							Collections.sort(tmpL);

							for (int j = 0; j < tmpL.size(); j++) {
								if (j == (tmpL.size() - 1)) {
									sb.append(tmpL.get(j));
								} else {
									sb.append(tmpL.get(j)).append(Constant.AND);
								}
							}

							map.put(f.getName(), sb.toString());
						} else if (f.get(obj) instanceof JSONObject) {
							Map<String, String> tmpM = JsonUtil
									.jsonObjToMap((JSONObject) f.get(obj));
							if (tmpM != null && tmpM.size() > 0) {
								map.put(f.getName(),
										createLinkString(paramFilter(tmpM)));
							} else {
								map.put(f.getName(), null);
							}
						} else {
							// TODO
						}
					}
				}
			}
		}

		return map;
	}

	/**
	 * <p>
	 * Create a string from a map sorted by ASCII of the keys in it.<br>
	 * The format of the string is as 'key1=value1&key2=value2...keyN=valueN'.
	 * </p>
	 *
	 * @param params
	 * @return String
	 */
	private static String createLinkString(Map<String, String> params) {
		if (params == null || params.size() <= 0)
			return "";
		else {
			List<String> keys = new ArrayList<String>(params.keySet());
			StringBuffer sb = new StringBuffer();
			String key = null;
			String value = null;

			Collections.sort(keys);

			for (int i = 0; i < keys.size(); i++) {
				key = keys.get(i);
				value = params.get(key);
				if (i == keys.size() - 1) {
					sb.append(key).append(Constant.EQUALITY_SIGN).append(value);
				} else {
					sb.append(key).append(Constant.EQUALITY_SIGN).append(value)
							.append(Constant.AND);
				}
			}

			return sb.toString();
		}
	}

	/**
	 * <p>
	 * Filter out elements which have empty values or key of 'sign' in a map.
	 * </p>
	 *
	 * @param params
	 * @return Map<String, String>
	 */
	private static Map<String, String> paramFilter(Map<String, String> params) {
		if (params == null || params.size() <= 0)
			return null;
		else {
			Map<String, String> result = new HashMap<String, String>();
			String value = null;

			for (String key : params.keySet()) {
				value = params.get(key);
				if (FuncUtil.isEmpty(value)
						|| key.equalsIgnoreCase(Constant.SIGN)) {
					continue;
				}

				result.put(key, value);
			}

			return result;
		}
	}

}
