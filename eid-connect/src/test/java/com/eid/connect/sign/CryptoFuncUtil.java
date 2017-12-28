package com.eid.connect.sign;

import java.io.UnsupportedEncodingException;

/**
*
* CryptoFuncUtil AS-SDK抽离
*
* @author pdz 2017-12-18 下午 2:44
*
**/
public class CryptoFuncUtil 
{

    public static byte[] getStringBytes(String text) {
        try {
            return text.getBytes("UTF-8");
        } catch (UnsupportedEncodingException var2) {
            throw new RuntimeException("Illegal charset : UTF-8");
        }
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString.length() < 1) {
            return null;
        } else if (hexString.length() % 2 != 0) {
            return null;
        } else {
            byte[] result = new byte[hexString.length() / 2];

            for(int i = 0; i < hexString.length() / 2; ++i) {
                int high = Integer.parseInt(hexString.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(hexString.substring(i * 2 + 1, i * 2 + 2), 16);
                result[i] = (byte)(high * 16 + low);
            }

            return result;
        }
    }

    public static byte[] rebuildFactor(String securityFactor) {
        byte[] factorHex = hexStringToBytes(securityFactor);
        byte[] ret = new byte[16];
        for (int i = 0; i < factorHex.length; i++) {
            ret[i] = factorHex[i];
            ret[i + factorHex.length] = (byte) (factorHex[i] ^ 0xFFFFFFFF);
        }
        return ret;
    }

}
