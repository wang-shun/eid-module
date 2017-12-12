package com.eid.identity.service;

import com.eid.common.model.Response;
import com.eid.common.model.param.request.hmac.EidHmacRealNameParam;
import com.eid.common.model.param.result.hmac.EidHmacRealNameResult;

/**
 * 身份识别服务
 * Created by:ruben Date:2017/2/22 Time:下午12:26
 */
public interface IdentityHmacFacade {

    /**
     * 身份识别 hmac
     *
     * @param eidHmacRealNameParam
     * @return
     */
    Response<EidHmacRealNameResult> authentication(EidHmacRealNameParam eidHmacRealNameParam);
}
