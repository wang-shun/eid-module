package org.aiav.aptoassdk.service.dataprotection.secresy;

public interface IEncryptService {
	/**
	 * <p>
	 * encrypt strToEncrypt using sm3/ecb/pkcs5padding
	 * </p>
	 * 
	 * @param strToEncrypt
	 * @param encryptFactor
	 * @return
	 */
	public String doEncrypt(String strToEncrypt, String encryptFactor);

	/**
	 * <p>
	 * decrypt strToDecrypt using sm3/ecb/pkcs5padding
	 * </p>
	 * 
	 * @param strToDecrypt
	 * @param encryptFactor
	 * @return
	 */
	public String doDecrypt(String strToDecrypt, String encryptFactor);
}
