package com.eid.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 缓存key
 * Created by:ruben Date:2017/2/7 Time:下午4:00
 */
@Getter
@AllArgsConstructor
public enum Redis {

    ACCESS_TOKE_CODE("ACCESS_TOKE_CODE_", "token值");

    /**
     * 编码
     */
    private String code;

    /**
     * 编码描述
     */
    private String desc;
}
