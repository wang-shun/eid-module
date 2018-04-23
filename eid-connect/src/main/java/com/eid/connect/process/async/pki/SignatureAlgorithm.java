/**************************************************************
 * Copyright © 2015-2020 www.eidlink.com All rights reserved.
 *
 * 系统名称：EidLink_SDK
 * 
 **************************************************************/
package com.eid.connect.process.async.pki;

import java.util.HashMap;
import java.util.Map;

/**
 * 签名算法
 * 
 * 消息摘要算法，对报文进行hamc的类型 目前支持4中（SHA/SHA256/MD5/SM3）
 * 
 * @author Administrator
 * @date 2016/03/23
 */
public enum SignatureAlgorithm {
	
	SHA1("1", "HMAC_SHA1"),

	SHA256("2", "HMAC_SHA256"),

	MD5("3", "HMAC_MD5"),

	SM3("4", "HMAC_SM3");

	private String code;
	private String meaning;

	private SignatureAlgorithm(String code, String meaning) {
		this.code = code;
		this.meaning = meaning;
	}

	// Implementing a fromString method on an enum type
	private static final Map<String, SignatureAlgorithm> stringToEnum =
			new HashMap<String, SignatureAlgorithm>();

	static {
		for (SignatureAlgorithm sa : values()) {
			stringToEnum.put(sa.toString(), sa);
		}
	}

	/**
	 * 
	 * Returns SignatureAlgorithm for string, or null if string is invalid
	 *
	 * @param symbol
	 * @return
	 */
	public static SignatureAlgorithm fromString(String symbol) {
		return stringToEnum.get(symbol);
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the meaning
	 */
	public String getMeaning() {
		return meaning;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.code;
	}

}
