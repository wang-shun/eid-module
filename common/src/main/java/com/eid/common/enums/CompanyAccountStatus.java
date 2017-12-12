package com.eid.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 商户账户状态
 * Created by:ruben Date:2017/1/17 Time:下午5:31
 */
@Getter
@AllArgsConstructor
public enum CompanyAccountStatus {

    NORMAL(1, "正常"),
    AUDIT(2, "冻结"),
    FROZEN_YEAR(3, "销户");

    /**
     * 编码
     */
    private Integer code;

    /**
     * 编码描述
     */
    private String desc;
}
