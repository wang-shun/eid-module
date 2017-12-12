package com.eid.common.model.param.request.pki;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Eid pki实名认证方式参数
 * Created by:ruben Date:2017/2/17 Time:下午3:10
 */
@Getter
@Setter
@ToString(callSuper = true)
public class EidPkiRealNameParam extends EidPkiBaseParam {

    private static final long serialVersionUID = -6308392513851968514L;

    private String userIdInfo;

}
