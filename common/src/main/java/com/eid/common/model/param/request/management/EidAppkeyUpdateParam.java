package com.eid.common.model.param.request.management;

import com.eid.common.model.param.request.EidBaseParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/2/20 Time:下午3:39
 */
@Getter
@Setter
@ToString(callSuper = true)
public class EidAppkeyUpdateParam extends EidBaseParam {

    private static final long serialVersionUID = 3653952983129409348L;

    private String appid;
}
