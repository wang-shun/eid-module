package com.eid.verification.service;

import com.eid.common.model.Response;
import com.eid.common.model.param.request.hmac.EidHmacVerifyParam;
import com.eid.common.model.param.result.hmac.EidHmacVerifyResult;

/**
 * 签名验签服务
 * Created by:ruben Date:2017/2/22 Time:下午3:18
 */
public interface VerifyHmacFacade {

    /**
     * 签名验签 hmac
     *
     * @param eidHmacVerifyParam
     * @return
     */
    Response<EidHmacVerifyResult> authentication(EidHmacVerifyParam eidHmacVerifyParam);
}
