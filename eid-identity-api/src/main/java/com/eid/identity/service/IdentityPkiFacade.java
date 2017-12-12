package com.eid.identity.service;

import com.eid.common.model.Response;
import com.eid.common.model.param.request.pki.EidPkiRealNameParam;
import com.eid.common.model.param.result.pki.EidPkiRealNameResult;

/**
 * 身份识别服务
 * Created by:ruben Date:2017/2/22 Time:下午12:26
 */
public interface IdentityPkiFacade {

    /**
     * 身份识别 pki
     *
     * @param eidPkiRealNameParam
     * @return
     */
    Response<EidPkiRealNameResult> authentication(EidPkiRealNameParam eidPkiRealNameParam);
}
