package com.eid.common.model.param.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Eid基础参数
 * Created by:ruben Date:2017/2/17 Time:下午3:01
 */
@Getter
@Setter
@ToString
public class EidBaseParam implements Serializable {

    private static final long serialVersionUID = -401699741958721380L;

    private String appKey;
    private String version;
    private String bizTime;
    private String bizSequenceId;
    private String signFactor;
    private String securityType;
    private String signType;
    private String encryptType;
    private String attach;
    private String returnUrl;
    private String accessToken;
    private String bizType;
    private String eidIdentification;
}
