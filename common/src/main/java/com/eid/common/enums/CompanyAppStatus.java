package com.eid.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 商户状态
 * Created by:ruben Date:2017/1/17 Time:下午5:31
 */
@Getter
@AllArgsConstructor
public enum CompanyAppStatus {

    //    NORMAL(2, "正常"),
//    UN_AVAILABLE(3, "不可用"),
//DELETED(4, "已删除"),

    AUDIT(1, "审核中"),
    AUDIT_YES(2, "初审通过"),
    TRIAL_NO(3, "审核未通过"),
    SHELF(4, "下架"),
    MANAGE_SHELF(5, "管理下架"),

    TRIAL_YES(6, "审核通过");

    /**
     * 编码
     */
    private Integer code;

    /**
     * 编码描述
     */
    private String desc;
}
