package com.eid.common.model.param.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/2/17 Time:下午3:51
 */
@Setter
@Getter
@ToString
public class EidBaseResult implements Serializable {

    private static final long serialVersionUID = 8243080757945218450L;

    private String version;
    private Integer status;
    private String result;
    private String resultDetail;
    private String resultTime;
    private String bizType;
    private String accessToken;
    private String bizSequenceId;
    private String securityType;
    private String attach;
}
