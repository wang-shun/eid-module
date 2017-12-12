package com.eid.dispatch.exception;

import com.eid.dispatch.common.ErrorCode;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/8 Time:上午9:59
 */
public enum DispatchErrorCode implements ErrorCode {
    /**
     * 这种情况一般是捕获了没有单独进行catch处理的异常然后设定的错误码
     */
    UNEXPECTED_ERROR("UNEXPECTED_ERROR", "非预期的系统错误"),
    /**
     * 不支持的取值
     */
    VALUE_NOT_SUPPORT("VALUE_NOT_SUPPORT", "不支持的取值"),
    /**
     * 该错误码一般是在系统内部处理过程中出现不需要外部系统明确了解（如模型转化错误，非对外服务参数校验不同，内部BUG）时设定的错误码
     */
    SYSTEM_INNER_ERROR("SYSTEM_INNER_ERROR", "系统内部错误"),
    /**
     * 执行数据库操作发生异常,具体查看数据库返回的异常信息
     */
    DATA_BASE_ERROR("DATA_BASE_ERROR", "数据库操作异常"),
    /**
     * 参数为null（不含空字符串）是非法的
     */
    NULL_IS_ILLEGAL_PARAM("NULL_IS_ILLEGAL_PARAM", "参数为null是非法的"),;
    /**
     * 处理结果码
     */
    private final String code;

    /**
     * 处理结果描述
     */
    private final String desc;

    /**
     * 构造函数
     *
     * @param code
     * @param desc
     */
    DispatchErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 通过枚举<code>code</code>获得枚举
     *
     * @param code 如果为null，返回null
     * @return 不匹配返回<code>null</code>
     */
    public static DispatchErrorCode getByCode(String code) {
        if (code != null) {
            for (DispatchErrorCode result : values()) {
                if (result.getCode().equals(code)) {
                    return result;
                }
            }
        }
        return UNEXPECTED_ERROR;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}