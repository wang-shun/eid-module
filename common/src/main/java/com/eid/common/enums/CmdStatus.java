package com.eid.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum CmdStatus {

    Success("Success", "成功"),

    Failure("Failure", "失败"),

    Initial("Initial", "初始化"),

    Wait("Wait", "待处理"),

    Processing("Processing", "处理中"),;

    public static Map<String, CmdStatus> codeMap = new HashMap<String, CmdStatus>();

    static {
        for (CmdStatus enums : values()) {
            codeMap.put(enums.getCode(), enums);
        }
    }

    public static CmdStatus getEnumsByCode(String code) {
        return codeMap.get(code);
    }

    private String code;

    private String desc;

}
