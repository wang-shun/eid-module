package com.eid.company.service;

import com.eid.common.model.Response;
import com.eid.common.model.param.request.management.EidAppRegParam;
import com.eid.common.model.param.request.management.EidAppkeyUpdateParam;

/**
 * 公司信息服务
 * Created by:ruben Date:2017/2/21 Time:下午3:21
 */
public interface CompanyAppFacade {

    /**
     * 申请apid、apkey
     *
     * @param eidAppRegParam
     * @return
     */
    Response<Boolean> apply(EidAppRegParam eidAppRegParam);

    /**
     * 申请appid、appkey
     *
     * @param eidAppRegParam
     * @return
     */
    Response<Boolean> applyAppInfo(EidAppRegParam eidAppRegParam);

    /**
     * 重置appid、appkey
     *
     * @param eidAppkeyUpdateParam
     * @return
     */
    Response<Boolean> resetAppKey(EidAppkeyUpdateParam eidAppkeyUpdateParam);

    /**
     * 重置apid、apkey
     *
     * @param eidAppkeyUpdateParam
     * @return
     */
    Response<Boolean> resetApKey(EidAppkeyUpdateParam eidAppkeyUpdateParam);
}
