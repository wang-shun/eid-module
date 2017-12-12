package com.eid.common.model.param.request.hmac;

import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.request.EidSignBaseParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Eid Hmac方式参数
 * Created by:ruben Date:2017/2/17 Time:下午3:10
 */
@Getter
@Setter
@ToString(callSuper = true)
public class EidHmacBaseParam extends EidSignBaseParam {

    private static final long serialVersionUID = 5219155646793314520L;

    private String idCarrier;
}
