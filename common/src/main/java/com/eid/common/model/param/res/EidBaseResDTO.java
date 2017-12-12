package com.eid.common.model.param.res;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/17 Time:下午2:45
 */
@Data
public class EidBaseResDTO implements Serializable {

    private static final long serialVersionUID = -3055746271998283874L;

    private String appEidCode;
    private String resultMessage;
    private String resultDetail;
    private String resultTime;
    private Integer status;
    private String bizTime;
    private String bizType;
    private String bizSequenceId;
    private String attach;
    private String accessToken;
    private String userIdInfo;
}
