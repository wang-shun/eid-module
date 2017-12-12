package com.eid.company.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/1/22 Time:下午2:27
 */
@Data
public class CompanyAuthenticationDTO implements Serializable {

    private static final long serialVersionUID = 8922734978669896519L;

    private String appEidCode;
    private String resultMessage;
    private String resultDetail;
    private String resultTime;
    private String userIdInfo;
    private Integer status;
    private String bizTime;
    private String bizType;
    private String bizSequenceId;
    private String attach;
    private String accessToken;
    private String appId;

}
