package com.eid.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务类型
 * Created by:ruben Date:2017/2/8 Time:下午3:35
 */
@Getter
@AllArgsConstructor
public enum BizType {

    REALNAME_PRO_HMAC("REALNAME_PRO_HMAC", "增强实名认证 HMAC"),
    REALNAME_PRO_PKI("REALNAME_PRO_PKI", "增强实名认证 PKI"),
    IDENTITY_PKI("REALNAME_PKI", "实名认证 PKI"),
    IDENTITY_HMAC("REALNAME_HMAC", "实名认证 HMAC"),
    VERIFY_PKI("VERIFY_PKI", "签名验签 PKI"),
    VERIFY_HMAC("VERIFY_HMAC", "签名验签 HMAC"),
    ANONYMOUS_PKI("ANONYMOUS_PKI", "匿名认证 PKI"),
    ANONYMOUS_HMAC("ANONYMOUS_HMAC", "匿名认证 HMAC"),
    IDENTITY_PKI_ASYNC("REALNAME_PKI_ASYNC", "实名认证 异步 PKI"),
    IDENTITY_HMAC_ASYNC("REALNAME_HMAC_ASYNC", "实名认证 异步 HMAC"),
    VERIFY_PKI_ASYNC("VERIFY_PKI_ASYNC", "签名验签 异步 PKI"),
    VERIFY_HMAC_ASYNC("VERIFY_HMAC_ASYNC", "签名验签 异步 HMAC"),
    ANONYMOUS_PKI_ASYNC("ANONYMOUS_PKI_ASYNC", "匿名认证 异步 PKI"),
    CERTIFICATE("CERTIFICATE", "证件认证"),
    ANONYMOUS_HMAC_ASYNC("ANONYMOUS_HMAC_ASYNC", "匿名认证 异步 HMAC"),
    APP_REG("0000000", "应用申请"),
    APP_REG_UPDATE("0000001", "应用信息更换"),
    APP_KEY_UPDATE("0000002", "应用秘钥信息更换"),
    IDENTITY_SIM_ASYNC("DENTITY_SIM_ASYNC","SIM身份识别异步"),
    AUTHEN_SIM_ASYNC("AUTHEN_SIM_ASYNC","SIM签名验签异步"),
    AUTHORIZE_SIM_ASYNC("AUTHORIZE_SIM_ASYNC","SIM身份授权异步");

    public static BizType parse(String code) {
        for (BizType bizType : BizType.values()) {
            if (bizType.code.equals(code)) {
                return bizType;
            }
        }
        return null;
    }

    private String code;
    private String desc;
}
