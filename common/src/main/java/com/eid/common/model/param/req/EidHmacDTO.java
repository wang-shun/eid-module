package com.eid.common.model.param.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/17 Time:下午12:21
 */
@Getter
@Setter
@ToString(callSuper = true)
public class EidHmacDTO extends EidBaseDTO {

    private static final long serialVersionUID = -5108216424864537602L;

    private String idCarrier;
}
