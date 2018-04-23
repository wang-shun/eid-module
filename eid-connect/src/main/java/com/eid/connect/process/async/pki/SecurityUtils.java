/**************************************************************
 * Copyright © 2015-2020 www.eidlink.com All rights reserved.
 *
 * 系统名称：EidLink_SDK
 * 
 **************************************************************/
package com.eid.connect.process.async.pki;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;


/**
 * 加解密的工具类
 * 
 * @author Administrator
 * @date 2016/03/23
 */
public class SecurityUtils {
	
	private static final byte bf = (byte) 0xff;
	private static final Log log = LogFactory.getLog(SecurityUtils.class);

	/**
	 * 做hmac运算
	 * 
	 * @param appKey 8字节
	 * @param hmac_factor 8字节
	 * @param data 原文
	 * @param hmactype 哈希算法的类型的枚举类 {@link SignatureAlgorithm}
	 * @return
	 */
	public static String doSofHamc(String appKey, String hmac_factor, String data, SignatureAlgorithm hmactype) {

		try {
			// 获取hmac_factor取反后 的拼接值
			byte[] factorData = getFactorData(hmac_factor);
			// 获取密钥 密钥的填充方式为：NOPadding
			byte[] key = SM4Utils.encrypt(factorData, hex2Byte(appKey), 2);
			// fixUp调整key的奇偶校验位，然后进行hmac运算
			byte[] hmacValue = null;

			switch (hmactype) {
			case SHA1:
				hmacValue = hmacSha1(data.getBytes("UTF-8"), key);
				break;
			case SHA256:
				hmacValue = hmacSha256(data.getBytes("UTF-8"), key);
				break;
			case MD5:
				hmacValue = hmacMD5(data.getBytes("UTF-8"), key);
				break;
			case SM3:
				hmacValue = hmacSM3(data.getBytes("UTF-8"), key);
				break;
			}

			return new String(Base64.encode(hmacValue));
		} catch (Exception e) {
			log.error("appKey:" + appKey + "\nhmac_factor" + hmac_factor + "\ndata" + data + "\nhmactype"
					+ hmactype, e);
			throw new RuntimeException("创建参数hmac的值时计算出错", e);
		}
	}


	/**
	 * 做3des加密运算
	 * 
	 * @param data 加密原文
	 * @param appKey 16字节长度
	 * @param encryptFactor 8字节长度
	 * @return base64编码的密文
	 */
	public static String do3desEncrypt(String data, String appKey, String encryptFactor) {
		
		try {
			
			log.debug("data:" + data + "\nappKey:" + appKey + "\nencryptFactor:" + encryptFactor);
			// 获取hmac_factor取反后 的拼接值
			byte[] factorData = getFactorData(encryptFactor);
			// 获取密钥
			byte[] key = SM4Utils.encrypt(factorData, hex2Byte(appKey), 2);
			// 调整key的奇偶校验位，然后进行3des运算
			byte[] encryptData =
					encryptWith3desEcb(data.getBytes("UTF-8"), key, "DESede/ECB/PKCS5Padding");

			return new String(Base64.encode(encryptData));
		} catch (Exception e) {
			log.error("data:" + data + "\nappKey:" + appKey + "\nencryptFactor" + encryptFactor, e);
			throw new RuntimeException("对参数的数据进行加密时出错", e);
		}
	}


	/**
	 * 做3des解密运算
	 * 
	 * @param data 密文（Base64编码）
	 * @param appKey 16字节长度
	 * @param encryptFactor 8字节长度
	 * @return 明文（utf-8编码的字符串）
	 */
	public static String do3desDecrypt(String data, String appKey, String encryptFactor) {
		
		try {
			log.debug("data:" + data + "\nappKey:" + appKey + "\nencryptFactor" + encryptFactor);
			// 获取hmac_factor取反后 的拼接值
			byte[] factorData = getFactorData(encryptFactor);
			// 获取密钥
			byte[] key = SM4Utils.encrypt(factorData, hex2Byte(appKey), 2);
			// 调整key的奇偶校验位，然后进行3des运算

			byte[] encryptData = decryptWith3desEcb(Base64.decode(data), key);

			return new String(encryptData, "UTF-8");
		} catch (Exception e) {
			log.error("data:" + data + "\nappKey:" + appKey + "\nencryptFactor" + encryptFactor, e);
			throw new RuntimeException("对密文数据进行解密时出错", e);
		}
	}


	/**
	 * 做hmacSM3运算
	 * 
	 * @param data
	 * @param key
	 * @return
	 */
	private static byte[] hmacSM3(byte[] data, byte[] key) {
		String algorithm = "HmacSM3";
		throw new RuntimeException("暂不支持" + algorithm + "算法");
	}

	/**
	 * 做hmacMD5运算
	 * 
	 * @param data
	 * @param key
	 * @return
	 */
	private static byte[] hmacMD5(byte[] data, byte[] key) {
		String algorithm = "HmacMD5";
		try {
			Mac mac = Mac.getInstance(algorithm);
			SecretKeySpec sks = new SecretKeySpec(key, algorithm);
			mac.init(sks);
			byte[] byteHMAC = mac.doFinal(data);
			return byteHMAC;
		} catch (Exception e) {
			log.error("data:" + new String(data) + "\nappKey:" + new String(key), e);
			throw new RuntimeException("计算" + algorithm + "时出错", e);
		}
	}

	/**
	 * hmacSha256 运算
	 * 
	 * @param data
	 * @param key
	 * @return
	 */
	private static byte[] hmacSha256(byte[] data, byte[] key) {
		String algorithm = "HmacSHA256";
		try {
			Mac mac = Mac.getInstance(algorithm);
			SecretKeySpec sks = new SecretKeySpec(key, algorithm);
			mac.init(sks);
			byte[] byteHMAC = mac.doFinal(data);
			return byteHMAC;
		} catch (Exception e) {
			log.error("data:" + new String(data) + "\nappKey:" + new String(key), e);
			throw new RuntimeException("计算" + algorithm + "时出错", e);
		}
	}

	/**
	 * hmacSha1 运算
	 * 
	 * @param data
	 * @param key
	 * @return
	 */
	private static byte[] hmacSha1(byte[] data, byte[] key) {
		String algorithm = "HmacSHA1";
		try {
			Mac mac = Mac.getInstance(algorithm);
			SecretKeySpec sks = new SecretKeySpec(key, algorithm);
			mac.init(sks);
			byte[] byteHMAC = mac.doFinal(data);
			return byteHMAC;
		} catch (Exception e) {
			log.error("data:" + new String(data) + "\nappKey:" + new String(key), e);
			throw new RuntimeException("计算" + algorithm + "时出错", e);
		}
	}

	/**
	 * 补位方法 对8位值与ff异或 添加到8位后面
	 * 
	 * @param factor
	 * @return
	 */
	private static byte[] getFactorData(String factor) {
		byte[] appkey_factor = hex2Byte(factor);

		byte[] retby = new byte[16];
		for (int i = 0; i < appkey_factor.length; i++) {
			retby[i] = appkey_factor[i];
			retby[i + appkey_factor.length] = (byte) (appkey_factor[i] ^ bf);
		}
		return retby;
	}


	/**
	 * 进行3des加密
	 * 
	 * @param data 原文
	 * @param key 密钥，16字节长度，HEX编码
	 * @return
	 */
	public static byte[] encryptWith3desEcb(byte[] data, byte[] key, String transformation) {
		try {
			SecretKey desKey = new SecretKeySpec(key, "DESede");
			BouncyCastleProvider provider = new BouncyCastleProvider();
			Cipher cipher = Cipher.getInstance(transformation, provider);
			cipher.init(Cipher.ENCRYPT_MODE, desKey);
			byte[] encryptData = cipher.doFinal(data);
			return encryptData;
		} catch (Exception e) {
			log.error("data:" + new String(data) + "\nappKey:" + new String(key) + "\ntransformation"
					+ transformation, e);
			throw new RuntimeException("使用3des算法加密数据失败");
		}
	}

	/**
	 * 进行3des解密
	 * 
	 * @param edata
	 * @param key
	 * @return
	 */
	private static byte[] decryptWith3desEcb(byte[] edata, byte[] key) {
		try {
			SecretKey desKey = new SecretKeySpec(key, "DESede");
			BouncyCastleProvider provider = new BouncyCastleProvider();
			String transformation = "DESede/ECB/PKCS5Padding";
			Cipher cipher = Cipher.getInstance(transformation, provider);
			cipher.init(Cipher.DECRYPT_MODE, desKey);
			byte[] decryptData = cipher.doFinal(edata);
			return decryptData;
		} catch (Exception e) {
			log.error("data:" + new String(edata) + "\nappKey:" + new String(key), e);
			throw new RuntimeException("使用3des算法解密数据失败");
		}
	}


	/**
	 * 调整奇偶校验位 原理：看这个字节的2进制里面的1是不是偶数个，是的话就把最后以为取反
	 * 
	 * @param key
	 * @return
	 */
	public static byte[] fixUp(byte[] key) {
		int b;
		for (int i = 0; i < key.length; i++) {
			b = key[i];
			if (((b & 0x1) ^ ((b >> 1) & 0x1) ^ ((b >> 2) & 0x1) ^ ((b >> 3) & 0x1) ^ ((b >> 4) & 0x1)
					^ ((b >> 5) & 0x1) ^ ((b >> 6) & 0x1) ^ ((b >> 7) & 0x1)) == 0) {
				key[i] ^= 0x01;
			}
		}
		return key;
	}

	/**
	 * 将给定的数值两位放一起转化为16进制数组（长度必须为偶数 目前只支持0-9 以及A-F,a-f混搭） "12345678" [18, 52, 86, 120]
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] hex2Byte(String str) {
		if (str == null)
			return null;
		str = str.trim();
		int len = str.length();
		if (len == 0 || (len>0 && len % 2 == 1))
			return null;
		byte[] b = new byte[len / 2];
		try {
			for (int i = 0; i < str.length(); i += 2) {
				b[i / 2] = (byte) Integer.decode("0x" + str.substring(i, i + 2)).intValue();
			}
			return b;
		} catch (Exception e) {
			log.error("str:" + str, e);
			return null;
		}
	}
}
