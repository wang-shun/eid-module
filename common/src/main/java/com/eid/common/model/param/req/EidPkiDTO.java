package com.eid.common.model.param.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/17 Time:下午12:21
 */
@Getter
@Setter
@ToString(callSuper = true)
public class EidPkiDTO extends EidBaseDTO {

    private static final long serialVersionUID = 6179882443788342473L;

    private String eidIssuer;
    private String eidIssuerSn;
    private String eidSn;
}
