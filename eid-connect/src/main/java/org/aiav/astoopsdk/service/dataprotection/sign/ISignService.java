package org.aiav.astoopsdk.service.dataprotection.sign;

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
//	public String createSign(String strToSign, String signFactor, String askeyFactor);

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
//	public boolean verifySign(String sign, String strToSign, String signFactor, String askeyFactor);
}
