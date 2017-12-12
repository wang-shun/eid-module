package com.eid.common.model.param.request.pki;

import com.eid.common.model.param.request.EidSignBaseParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Eid pki方式参数
 * Created by:ruben Date:2017/2/17 Time:下午3:10
 */
@Getter
@Setter
@ToString(callSuper = true)
public class EidPkiBaseParam extends EidSignBaseParam {

    private static final long serialVersionUID = 7620210099934470472L;

    private String eidIssuer;
    private String eidIssuerSn;
    private String eidSn;
}
