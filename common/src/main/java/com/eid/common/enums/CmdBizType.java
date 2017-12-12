package com.eid.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 任务类型
 * Created by:ruben Date:2017/1/17 Time:下午5:31
 */
@Getter
@AllArgsConstructor
public enum CmdBizType {

    ASYNC_FEE("ASYNC_FEE", "异步计费"),
    ASYNC_NOTIFY("ASYNC_NOTIFY", "异步通知"),
    ASYNC_RESULT("ASYNC_RESULT", "异步模拟op通知");

    /**
     * 编码
     */
    private String code;

    /**
     * 编码描述
     */
    private String desc;

}
