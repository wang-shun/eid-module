package com.eid.anonymous.service;

import com.eid.common.model.Response;
import com.eid.common.model.param.request.pki.EidPkiAnonymousParam;
import com.eid.common.model.param.result.pki.EidPkiAnonymousResult;

/**
 * 匿名认证服务
 * Created by:ruben Date:2017/2/8 Time:下午4:29
 */
public interface AnonymousPkiFacade {

    /**
     * 匿名认证 pki
     *
     * @param eidPkiAnonymousParam
     * @return
     */
    Response<EidPkiAnonymousResult> authentication(EidPkiAnonymousParam eidPkiAnonymousParam);
}
