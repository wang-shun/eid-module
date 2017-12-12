package com.eid.connect.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务类型
 * Created by:ruben Date:2017/2/8 Time:下午3:35
 */
@Getter
@AllArgsConstructor
public enum InterfaceType {

    REAL_NAME_SIGN_VERIFY_PKI("0101001", "PKI实名认证"),
    REAL_NAME_SIGN_VERIFY_HMAC("0102001", "HMAC实名认证"),
    BIZ_SIGN_VERIFY_PKI("0201001", "PKI签名验签"),
    BIZ_SIGN_VERIFY_HMAC("0202001", "HMAC签名验签"),
    BIZ_DIRECT_LOGIN_PKI("0201002", "PKI匿名认证"),
    BIZ_DIRECT_LOGIN_HMAC("0202002", "HMAC匿名认证"),
    APP_REG("0000000", "应用申请"),
    APP_REG_UPDATE("0000001", "应用信息更换"),
    APP_KEY_UPDATE("0000002", "应用秘钥信息更换");

    private String code;
    private String desc;

    public static InterfaceType parse(String code) {
        for (InterfaceType interfaceType : InterfaceType.values()) {
            if (interfaceType.code.equals(code)) {
                return interfaceType;
            }
        }
        return null;
    }
}
