package com.eid.common.model.param.result.management;

import com.eid.common.model.param.result.EidBaseResult;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/2/20 Time:下午3:53
 */
@Setter
@Getter
@ToString(callSuper = true)
public class EidAppRegUpdateResult extends EidBaseResult {

    private static final long serialVersionUID = 3335092312949036939L;

    private String appkeyFactor;
}
