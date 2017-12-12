package com.eid.anonymous.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/2/8 Time:下午4:15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EAnonymousHmacRes implements Serializable {

    private static final long serialVersionUID = 269337194293986860L;

    private String dataToSign;
    private String eidSign;
    private String eidSignAlgorithm;
    private String idCarrier;
    private String syncType;
    private String returnUrl;
    private String attach;
    private String appId;
    private String appKey;
    private String version;
    private String encryptType;
    private String securityType;
    private String signType;
    private String bizSequenceId;
    private String bizTime;
    private String reqType;
    private String accessToken;

    private String appEidCode;
    private String resultDetail;
    private String errorMsg;
}
