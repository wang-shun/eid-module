package com.eid.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResCode {

    EID_0000000("0000000", "成功"),
    EID_0000001("0000001", "提交成功，异步通知结果"),
    EID_0001002("0001002", "挂起"),
    EID_0001003("0001003", "过期"),
    EID_0001004("0001004", "已注销"),
    EID_0001005("0001005", "未开通"),
    EID_0201003("0201003", "不在有效期范围内"),
    EID_0201008("0201008", "重复请求"),
    EID_0201015("0201015", "请求地址无效"),
    EID_0301000("0301000", "证书已注销"),
    EID_0302001("0302001", "证书已过期"),
    EID_0301001("0301001", "证书已冻结"),
    EID_0301002("0301002", "证书无效"),
    EID_0301005("0301005", "载体已注销"),
    EID_0301006("0301006", "载体已冻结"),
    EID_0301007("0301007", "载体无效"),
    EID_0302000("0302000", "姓名身份证不匹配"),
    EID_0401000("0401000", "服务器异常"),
    EID_0401001("0401001", "认证请求失败");

    private String code;

    private String desc;

}
