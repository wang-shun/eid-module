package org.aiav.astoopsdk.service.dataprotection.sign.impl;

import com.as.dev.DeviceService;
import com.eid.dev.constant.EDeviceSignType;
import com.eid.dev.constant.RC;
import com.eid.dev.pojo.sign.rk2.AskeySignParameters;
import com.eid.dev.pojo.sign.rk2.AskeySignResult;
import com.eid.dev.util.Bytes;
import org.aiav.astoopsdk.service.dataprotection.SBaseCryptoService;
import org.aiav.astoopsdk.service.dataprotection.sign.ISignService;
import org.aiav.astoopsdk.util.EncryptionMachine;
import org.aiav.astoopsdk.util.FuncUtil;
import org.aiav.crypto.EidCryptoUtils;
import org.aiav.crypto.util.CryptoFuncUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class SHmacSha1Service extends SBaseCryptoService implements
		ISignService {
	private static final Logger log = Logger.getLogger(SHmacSha1Service.class);

	public SHmacSha1Service(String key) {
		super(key);
	}

//	/**
//	 * 自建非加密机模式
//	 * @param args
//	 * @throws Exception
//	 */
//	@Override
//	public String createSign(String strToSign, String signFactor) {
//
//		log.debug("strToSign:" + strToSign + ";signFactor:" + signFactor
//				+ ";key:" + this.getKey());
//		String sign = null;
//		byte[] result = EidCryptoUtils.createHMACSHA1(
//				CryptoFuncUtil.getStringBytes(strToSign),
//				buildSM4Key(signFactor));
//		if (!FuncUtil.isEmpty(result)) {
//			sign = CryptoFuncUtil.encodeBytesToBase64(result);
//		}
//
//		log.debug("sign:" + sign);
//		return sign;
//	}

//	/**
//	 * 	自建非加密机模式
//	 *
//	 * @param sign
//	 * @param strToSign
//	 * @param signFactor
//	 * @param askeyFactor
//	 * @return
//	 */
//	@Override
//	public boolean verifySign(String sign, String strToSign, String signFactor) {
//		log.debug("sign:" + sign + ";strToSign:" + strToSign + ";signFactor:"
//				+ signFactor + ";key:" + this.getKey());
//
//		return EidCryptoUtils.verifyHMACSHA1(
//				CryptoFuncUtil.getStringBytes(strToSign),
//				CryptoFuncUtil.decodeBase64ToBytes(sign),
//				buildSM4Key(signFactor));
//	}

//	// as到op加密机测试方法
//	public static void main(String[] args) throws Exception
//	{
//		String sign = null;
//		DeviceService ds = new DeviceService();
//
//		AskeySignParameters params = new AskeySignParameters();
//		params.setAskeyFactor("C1FF76ED0F904446A6B0F1CE624E6A38");
//		params.setSignFactor("BDBA9DC40C0640E7");
//		params.setSignType(EDeviceSignType.HMAC_SHA1);
//		params.setData("彭代忠1111".getBytes("UTF-8"));
//
//		AskeySignResult dr = ds.signByAskey(params);
//		if (RC.SUCCESS.equals(dr.getResult()))
//			sign = new String(Base64.encodeBase64(dr.getSign()),"UTF-8");
//
//		System.out.println(" as 使用加密机签名值："+sign);
//	}

	/**
	 * 自建加密机模式 HMAC_SHA1签名
	 * @param strToSign		待签原文
	 * @param signFactor	签名因子
	 * @return sign
	 */
	@Override
	public String createSign(String strToSign, String signFactor) {

		try
		{
			String sign = new EncryptionMachine().asToOpSign(strToSign,signFactor,this.getKey(),EDeviceSignType.HMAC_SHA1);

			log.debug("签名：加密机HMAC_SHA1签名值："+sign);

			return sign;

		} catch (Exception e)
		{
		    e.printStackTrace();
		}
		return null;

	}

	/**
	 * 	自建加密机模式，验签
	 *
	 * @param sign			签名值
	 * @param strToSign		待签原文
	 * @param signFactor	签名因子
	 * @return
	 */
	@Override
	public boolean verifySign(String sign, String strToSign, String signFactor) {

		try
		{
			String newSign = new EncryptionMachine().asToOpSign(strToSign,signFactor,this.getKey(),EDeviceSignType.HMAC_SHA1);

			log.info("IDSO回调通知验签：strToSign："+strToSign);
			log.info("IDSO回调通知验签：原始签名值："+sign);
			log.info("IDSO回调通知验签：加密机HMAC_SHA1签名值："+newSign);

			if(!StringUtils.isBlank(newSign) && sign.equals(newSign))
				return true;

		} catch (Exception e)
		{
		    e.printStackTrace();
		}

		return false;

	}

}
