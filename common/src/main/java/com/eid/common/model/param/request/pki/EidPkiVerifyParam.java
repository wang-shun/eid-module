package com.eid.common.model.param.request.pki;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Eid pki签名认证方式参数
 * Created by:ruben Date:2017/2/17 Time:下午3:10
 */
@Getter
@Setter
@ToString(callSuper = true)
public class EidPkiVerifyParam extends EidPkiBaseParam {

    private static final long serialVersionUID = 3841865007285684314L;

}
