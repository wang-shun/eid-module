package com.eid.identity.biz;

import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.result.EidBaseResult;
import com.eid.dal.entity.CompanyAuthenticationEntity;

/**
 * 实名认证业务流程
 * Created by:ruben Date:2017/2/20 Time:下午4:32
 */
public interface IdentityBiz {

    /**
     * 认证记录入库
     *
     * @param eidBaseParam
     * @param companyId
     * @return
     */
    CompanyAuthenticationEntity save(EidBaseParam eidBaseParam, String companyId);

    /**
     * 请求实名认证
     *
     * @param eidBaseParam
     * @return
     */
    EidBaseResult send(EidBaseParam eidBaseParam);

    /**
     * 更新认证结果
     *
     * @param eidBaseResult
     * @param id
     * @return
     */
    Boolean update(EidBaseResult eidBaseResult, Long id);

}
