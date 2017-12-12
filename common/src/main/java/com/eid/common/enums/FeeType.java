package com.eid.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 计费类型
 * Created by:ruben Date:2017/1/17 Time:下午5:31
 */
@Getter
@AllArgsConstructor
public enum FeeType {

//    YEAR(1, "年费"),
//    AUTHENTICATION(2, "认证服务费"),
//    CERTIFICATE(3, "证书颁发费"),
//    TRADE(4, "交易服务费"),
//    AGREEMENT(5, "协议签名费"),

    SINGLE(1, "单笔计费"),
    MONTH(2, "月结"),
    LADDER(3, "阶梯计费");

    public static FeeType getEnum(Integer code) {
        for (FeeType at : FeeType.values()) {
            if (at.code.equals(code)) {
                return at;
            }
        }
        return null;
    }

    /**
     * 编码
     */
    private Integer code;

    /**
     * 编码描述
     */
    private String desc;
}
