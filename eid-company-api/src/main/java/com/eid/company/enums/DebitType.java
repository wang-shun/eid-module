package com.eid.company.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 扣费类型
 * Created by:ruben Date:2017/1/17 Time:下午5:31
 */
@Getter
@AllArgsConstructor
public enum DebitType {

    RECHARGE(1, "充值"),
    YEAR(2, "年费"),
    AUTHENTICATION(3, "认证费"),
    WITHDRAWALS(3, "提现费");

    /**
     * 编码
     */
    private Integer code;

    /**
     * 编码描述
     */
    private String desc;
}
