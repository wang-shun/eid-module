package com.eid.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 身份证省
 * Created by:ruben Date:2017/2/8 Time:下午3:35
 */
@Getter
@AllArgsConstructor
public enum Province {

    BEIJING("11", "北京"),
    TIANJIN("12", "天津"),
    HEBEI("13", "河北"),
    SHANXI("14", "山西"),
    NEIMENG("15", "内蒙"),
    LIAONING("21", "辽宁"),
    JILIN("22", "吉林"),
    HEILONGJIANG("23", "黑龙江"),
    SHANGHAI("31", "上海"),
    JIANGSU("32", "江苏"),
    ZHEJIANG("33", "浙江"),
    ANHUI("34", "安徽"),
    FUJIAN("35", "福建"),
    JIANGXI("36", "江西"),
    SHANDONG("37", "山东"),
    HENAN("41", "河南"),
    HUBEI("42", "湖北"),
    HUNAN("43", "湖南"),
    GUANGDONG("44", "广东"),
    GUANGXI("45", "广西"),
    HAINAN("46", "海南"),
    CHONGQIN("50", "重庆"),
    SICHUAN("51", "四川"),
    GUIZHOU("52", "贵州"),
    YUNNAN("53", "云南"),
    XIZANG("54", "西藏"),
    SHANGXI("61", "陕西"),
    GANSU("62", "甘肃"),
    QINGHAI("63", "青海"),
    NINGXIA("64", "宁夏"),
    XINJIANG("65", "新疆"),
    XINJIANGBINGTUAN("66", "新疆兵团"),
    TAIWAN("71", "台湾"),
    XIANGGANG("81", "香港"),
    AOMEN("91", "澳门");

    public static Province parse(String code) {
        for (Province bizType : Province.values()) {
            if (bizType.code.equals(code)) {
                return bizType;
            }
        }
        return null;
    }

    private String code;
    private String desc;
}
