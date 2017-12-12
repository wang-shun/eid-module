package com.eid.common.model.param.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/2/17 Time:下午3:51
 */
@Getter
@Setter
@ToString(callSuper = true)
public class EidBizBaseResult extends EidBaseResult {

    private static final long serialVersionUID = -3857128521661245758L;

    private String userInfo;
    private String encryptType;
    private String encryptFactor;

}
