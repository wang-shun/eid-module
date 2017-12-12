package com.eid.anonymous.biz;

import com.eid.common.model.param.req.EidBaseDTO;
import com.eid.common.model.param.res.EidBaseResDTO;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/17 Time:下午12:43
 */
public interface AuthenticationBiz {

    EidBaseResDTO authentication(EidBaseDTO eidBaseDTO);
}
