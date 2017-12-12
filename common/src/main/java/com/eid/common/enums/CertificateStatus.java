package com.eid.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CertificateStatus {

    SUCCESS(0, "成功"),

    FAILURE(1, "失败"),

    UNABLE(2, "无法认证");


    /**
     * 编码
     */
    private Integer code;

    /**
     * 编码描述
     */
    private String desc;

}
