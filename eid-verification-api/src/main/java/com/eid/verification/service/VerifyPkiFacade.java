package com.eid.verification.service;

import com.eid.common.model.Response;
import com.eid.common.model.param.request.pki.EidPkiVerifyParam;
import com.eid.common.model.param.result.pki.EidPkiVerifyResult;

/**
 * 签名验签服务
 * Created by:ruben Date:2017/2/22 Time:下午3:18
 */
public interface VerifyPkiFacade {

    /**
     * 签名验签 pki
     *
     * @param eidPkiVerifyParam
     * @return
     */
    Response<EidPkiVerifyResult> authentication(EidPkiVerifyParam eidPkiVerifyParam);
}
