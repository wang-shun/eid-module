package com.eid.dispatch.util;

import com.eid.common.enums.ErrorCode;
import com.eid.common.exception.ControllerException;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加解密算法公用类
 * AES/ECB/PKCS5Padding (128)
 *
 * @author pdz 2017-03-09
 */
@Slf4j
public class AES {

    public static String encrypt(String strIn, String strKey) throws Exception {
        try {
            SecretKeySpec skeySpec = getKey(strKey);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(strIn.getBytes());
            return new AESBase64().encode(encrypted);
        } catch (Exception e) {
            log.info("Failed to decrypt! message:{}", Throwables.getStackTraceAsString(e));
            throw new ControllerException(ErrorCode.ENCRYPT_ERROR);
        }
    }

    public static String decrypt(String strIn, String strKey) throws Exception {
        try {
            SecretKeySpec skeySpec = getKey(strKey);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new AESBase64().decode(strIn);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original);
        } catch (Exception e) {
            log.info("Failed to decrypt! message:{}", Throwables.getStackTraceAsString(e));
            throw new ControllerException(ErrorCode.ENCRYPT_ERROR);
        }
    }

    private static SecretKeySpec getKey(String strKey) throws Exception {
        byte[] arrBTmp = strKey.getBytes();
        byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");
        return skeySpec;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(AES.encrypt("jSHj0pwi6nz0baSk/cO/JBEfPy8EyGlqL2q6+HOUCNIxMDAw", "FE855CD3655D316EE20E894C7AD45432"));
    }
}
