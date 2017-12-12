package com.eid.common.model.param.result.management;

import com.eid.common.model.param.result.EidBaseResult;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/2/20 Time:下午3:36
 */
@Setter
@Getter
@ToString(callSuper = true)
public class EidAppRegResult extends EidBaseResult {

    private static final long serialVersionUID = 6390841132299303798L;

    private String appid;
    private String appkeyFactor;

}
