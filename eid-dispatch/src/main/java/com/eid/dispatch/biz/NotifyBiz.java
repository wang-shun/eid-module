package com.eid.dispatch.biz;

import com.eid.dal.entity.CompanyAuthenticationEntity;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/22 Time:上午11:38
 */
public interface NotifyBiz {

    /**
     * 通知认证结果至商户
     *
     * @param companyAuthenticationEntity
     * @throws Exception
     */
    void notify(CompanyAuthenticationEntity companyAuthenticationEntity) throws Exception;
}
