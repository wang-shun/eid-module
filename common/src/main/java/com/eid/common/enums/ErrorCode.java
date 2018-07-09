package com.eid.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误码
 * Created by:ruben Date:2016/12/15 Time:下午3:40
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

    SYS_ERR("SYS_ERR", "系统异常，请稍后再试"),
    DATA_ERR("DATA_ERR", "数据库更新异常"),
    SIGN_ERROR("SIGN_ERROR", "签名校验错误"),
    ENCRYPT_ERROR("ENCRYPT_ERROR", "解密校验错误"),
    SEND_ERROR("SEND_ERROR", "发送失败，请检查"),

    SERVICE_EXPIRY("SERVICE_EXPIRY", "服务到期，请缴费"),

    TOKEN_EXPIRY("TOKEN_EXPIRY", "Token过期，请重新生成"),

    NON_EXISTENT("NON_EXISTENT", "信息不存在，请检查"),
    COMPANY_INFO_NOTFAND("COMPANY_INFO_NOTFAND", "apId不合法"),
    FEE_NON_EXISTENT("FEE_NON_EXISTENT", "计费信息不存在，请联系运营方"),
    STATUS_UN_NORMAL("STATUS_UN_NORMAL", "信息状态不正常"),
    BALANCE_NOT_ENOUGH("BALANCE_NOT_ENOUGH", "余额不够，请充值"),

    STATUS_ERROR("STATUS_ERROR", "证件认证 证件状态不可用！"),
    PASSWORD_ERROR("PASSWORD_ERROR", "证件认证 密码错误！"),
    UNABLE_AUTHENTICATION("UNABLE_AUTHENTICATION", "证件认证 无法判断认证结果！"),

    LOGIN_ERR("LOGIN_ERR", "登陆失败，无此用户"),
    BIZ_SEQUENCE_ID_ERR("BIZ_SEQUENCE_ID_ERR", "认证无效"),
    PARAM_ERR("PARAM_ERR", "参数错误，请检查");

    private String code;
    private String desc;
}
