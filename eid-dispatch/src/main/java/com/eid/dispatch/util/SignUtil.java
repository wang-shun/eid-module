package com.eid.dispatch.util;

import com.eid.common.enums.ErrorCode;
import com.eid.common.exception.ControllerException;
import com.google.common.base.Objects;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.util.*;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/7 Time:上午11:45
 */
@Slf4j
public class SignUtil {

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params, String secretKey) {

        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        prestr += "&secretKey=" + secretKey;

        return prestr;
    }

    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("signType")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    private static final String SHA = "SHA";
    private static final String CHARSET = "UTF-8";

    private static String sign(String inStr) throws Exception {
        MessageDigest sha = MessageDigest.getInstance(SHA);
        byte[] byteArray = inStr.getBytes(CHARSET);
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public static Boolean verifySign(String sign, String signType, String key, Map<String, String> strToSign) throws Exception {
        Map<String, String> param = paraFilter(strToSign);
        String preSignStr = createLinkString(param, key);
        String signResult = sign(preSignStr);
        log.info("待签原文：{};原始签名值：{};签名值：{};", preSignStr, sign, signResult);
        if (!Objects.equal(sign, signResult))
            throw new ControllerException(ErrorCode.SIGN_ERROR);

        return true;
    }

    public static String generateSign(Map<String, String> strToSign, String key) throws Exception {
        Map<String, String> param = paraFilter(strToSign);
        String preSignStr = createLinkString(param, key);
        String signResult = sign(preSignStr);
        log.info("待签原文：{};签名值：{};", preSignStr, signResult);
        return signResult;
    }
}
