package com.eid.common.model.param.request.management;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * EID app信息注册
 * Created by:ruben Date:2017/2/20 Time:下午3:31
 */
@Getter
@Setter
@ToString(callSuper = true)
public class EidAppRegParam extends EidAppRegBaseParam {

    private static final long serialVersionUID = -4642843004996842545L;

    private String relatedAppid;
    private String companyId;
}
