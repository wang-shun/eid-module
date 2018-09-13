package com.eid.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServiceType {

    EID_IMAGE_CONTRAST("IMAGE_CONTRAST","两照比对"),
    EID_OCR_MORE("OCR_MORE","OCR多项"),
    EID_OCR_SIMPLE("OCR_SIMPLE","OCR简项"),
    EID_GAIN_BANK_INFO("GAIN_BANK_INFO","银行卡基本信息"),
    EID_BANK_INFO("BANK_INFO","银行卡信息核查"),
    EID_USER_PHOTO("USER_PHOTO","人像对比"),
    EID_USERS_INFO("USERS_INFO","多项身份信息核查"),
    EID_USER_INFO("USER_INFO","简项身份信息核查"),
    EID_CAR_INFO("CAR_INFO","车辆信息"),
    EID_USER_INFO_IMAGE("USER_INFO_IMAGE","返照身份核验服务"),
    EID_LICENSE("LICENSE","行驶证信息核查服务"),
    EID_IDNUM("IDNUM","身份证风险核验服务"),
    EID_PHONE("PHONE","手机号实名认证服务"),
    EID_SCHLECHTE("SCHLECHTE","不良身份信息服务");

    private String code;

    private String desc;

}
