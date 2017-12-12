package com.eid.common.model.param.request.management;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/2/20 Time:下午3:48
 */
@Getter
@Setter
@ToString(callSuper = true)
public class EidAppRegUpdateParam extends EidAppRegBaseParam {

    private static final long serialVersionUID = 1307010650491317634L;

    private String appid;
}
