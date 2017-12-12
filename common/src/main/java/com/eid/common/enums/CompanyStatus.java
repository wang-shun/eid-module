package com.eid.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 商户状态
 * Created by:ruben Date:2017/1/17 Time:下午5:31
 */
@Getter
@AllArgsConstructor
public enum CompanyStatus {

    NORMAL(1, "正常"),
    AUDIT(2, "注册待审核"),
    FROZEN_YEAR(3, "欠费年费（冻结）"),
    FROZEN_AUTH(4, "欠认证费（冻结）"),
    FAIL(5, "审核未通过（重新提交资料）"),
    UPLOAD(6, "提交待上传协议");

    /**
     * 编码
     */
    private Integer code;

    /**
     * 编码描述
     */
    private String desc;
}
