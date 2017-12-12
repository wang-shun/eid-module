package org.aiav.aptoassdk.service.dataprotection.sign;

public interface ISignService {
	/**
	 * <p>
	 * create sign of hmac_sm3
	 * </p>
	 * 
	 * @param strToSign
	 * @param signFactor
	 * @param key
	 * @return
	 */
	public String createSign(String strToSign, String signFactor);

	/**
	 * <p>
	 * verify sign of hmac_sm3
	 * </p>
	 * 
	 * @param sign
	 * @param strTosign
	 * @param signFactor
	 * @param key
	 * @return
	 */
	public boolean verifySign(String sign, String strToSign, String signFactor);
}
