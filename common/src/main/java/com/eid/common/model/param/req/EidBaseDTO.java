package com.eid.common.model.param.req;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/17 Time:下午12:06
 */
@Data
public class EidBaseDTO implements Serializable {

    private static final long serialVersionUID = 8710928086961420191L;

    private String apId;
    private String appId;
    private String attach;
    private String returnUrl;
    private String bizType;
    private String eidSign;
    private String eidSignAlgorithm;
    private String dataToSign;
    private String companyId;
    private String accessToken;

}
