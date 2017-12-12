package com.eid.dispatch.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/1/22 Time:下午2:27
 */
@Data
public class AuthenticationNotifyDTO implements Serializable {

    private static final long serialVersionUID = 143496655958184105L;

    private String appEidCode;
    private String resultMessage;
    private String resultDetail;
    private String resultTime;
    private String status;
    private String bizTime;
    private String bizType;
    private String bizSequenceId;
    private String attach;
    private String accessToken;
    private String sign;
    private String signType;
    private String encryptType;

}
