package com.eid.common.model.param.result.management;

import com.eid.common.model.param.result.EidBaseResult;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/2/20 Time:下午3:41
 */
@Setter
@Getter
@ToString(callSuper = true)
public class EidAppkeyUpdateResult extends EidBaseResult {

    private static final long serialVersionUID = 8486879617634492769L;

    private String appkeyFactor;
}
