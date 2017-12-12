package com.eid.common.model.param.request.hmac;

import com.eid.common.model.param.request.EidSignBaseParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Eid hmac实名认证方式参数
 * Created by:ruben Date:2017/2/17 Time:下午3:10
 */
@Getter
@Setter
@ToString(callSuper = true)
public class EidHmacRealNameParam extends EidHmacBaseParam {

    private static final long serialVersionUID = -3554679442132300617L;

    private String userIdInfo;

}
