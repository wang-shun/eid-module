package com.eid.connect.service;

import com.eid.common.model.Response;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.result.EidBaseResult;

/**
 * 发送认证消息服务
 * Created by:ruben Date:2017/2/22 Time:下午2:41
 */
public interface SendFacade {

    /**
     * 请求op认证
     *
     * @param eidBaseParam
     * @return
     */
    Response<EidBaseResult> request(EidBaseParam eidBaseParam);
}
