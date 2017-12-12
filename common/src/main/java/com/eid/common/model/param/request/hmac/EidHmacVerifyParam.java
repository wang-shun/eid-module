package com.eid.common.model.param.request.hmac;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Eid hmac签名认证方式参数
 * Created by:ruben Date:2017/2/17 Time:下午3:10
 */
@Getter
@Setter
@ToString(callSuper = true)
public class EidHmacVerifyParam extends EidHmacBaseParam {

    private static final long serialVersionUID = -7979354174589656570L;
}
