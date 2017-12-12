package com.eid.common.model.param.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/17 Time:下午12:23
 */
@Getter
@Setter
@ToString(callSuper = true)
public class EidIdentityPkiDTO extends EidPkiDTO {

    private static final long serialVersionUID = -6053135302475692400L;

    private String userIdInfo;

}
