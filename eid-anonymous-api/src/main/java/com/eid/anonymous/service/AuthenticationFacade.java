package com.eid.anonymous.service;

import com.eid.common.model.Response;
import com.eid.common.model.param.req.EidBaseDTO;
import com.eid.common.model.param.res.EidBaseResDTO;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/17 Time:下午4:44
 */
public interface AuthenticationFacade {

    /**
     * 认证
     *
     * @param eidBaseDTO
     * @return
     */
    Response<EidBaseResDTO> authentication(EidBaseDTO eidBaseDTO);

}
