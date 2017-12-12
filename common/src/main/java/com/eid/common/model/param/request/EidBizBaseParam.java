package com.eid.common.model.param.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/2/17 Time:下午3:28
 */
@Getter
@Setter
@ToString(callSuper = true)
public class EidBizBaseParam extends EidBaseParam {

    private static final long serialVersionUID = -5372116768951219280L;

    private String appId;
    private String encryptFactor;

}
