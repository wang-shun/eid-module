package com.eid.common.model.param.request.pki;

import com.eid.common.model.param.request.EidBizBaseParam;
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
public class EidPkiRealNameSIMParam extends EidPkiBaseParam {

    private static final long serialVersionUID = -6308392513851968515L;

    private String userIdInfo;
//    private String dataToSign;
    private String msisdn;
    private String dataToBeDisplayed;
    private int info;

}
