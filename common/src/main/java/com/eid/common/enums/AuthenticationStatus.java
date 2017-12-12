package com.eid.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 认证状态
 * Created by:ruben Date:2017/1/17 Time:下午5:31
 */
@Getter
@AllArgsConstructor
public enum AuthenticationStatus {

    SUCCESS(0, "成功"),
    FAILED(1, "失败"),
    PROCESSING(2, "处理中");

    /**
     * 编码
     */
    private Integer code;

    /**
     * 编码描述
     */
    private String desc;
}
