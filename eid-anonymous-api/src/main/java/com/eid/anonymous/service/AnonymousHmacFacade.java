package com.eid.anonymous.service;

import com.eid.common.model.Response;
import com.eid.common.model.param.request.hmac.EidHmacAnonymousParam;
import com.eid.common.model.param.result.hmac.EidHmacAnonymousResult;

/**
 * 匿名认证服务
 * Created by:ruben Date:2017/2/8 Time:下午4:29
 */
public interface AnonymousHmacFacade {

    /**
     * 匿名认证 hmac
     *
     * @param eidHmacAnonymousParam
     * @return
     */
    Response<EidHmacAnonymousResult> authentication(EidHmacAnonymousParam eidHmacAnonymousParam);
}
