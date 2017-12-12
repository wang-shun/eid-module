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
public class EidSignBaseParam extends EidBizBaseParam {

    private static final long serialVersionUID = 2558571528158977863L;

    private String eidSign;
    private String eidSignAlgorithm;
    private String dataToSign;

}
