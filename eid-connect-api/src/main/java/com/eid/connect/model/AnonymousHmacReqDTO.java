package com.eid.connect.model;

import lombok.Data;

import java.io.Serializable;

/**
 * hmac匿名认证
 * Created by:ruben Date:2017/2/17 Time:下午2:28
 */
@Data
public class AnonymousHmacReqDTO implements Serializable {

    private static final long serialVersionUID = -5840025035635080178L;

    private String appId;
    private String attach;
    private String bizSequenceId;
    private String bizType;
    private String securityType;
    private String signType;
    private String encryptFactor;
    private String signFactor;
    private String encryptType;
    private String returnUrl;
    private String dataToSign;
    private String eidSign;
    private String eidSignAlgorithm;
    private String idCarrier;

}
