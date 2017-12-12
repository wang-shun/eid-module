package com.eid.dal.manager;

import com.eid.dal.entity.CompanyAuthenticationEntity;

public interface AuthenticationManager {

    /**
     * 认证失败
     *
     * @param id
     * @param errorCode
     * @param errorMessage
     */
    void failed(Long id, String errorCode, String errorMessage);

    /**
     * 认证成功
     *
     * @param id
     * @param resultDetail
     * @param resultMessage
     * @param appEidCode
     */
    void success(Long id, String resultDetail, String resultMessage, String appEidCode);

    /**
     * 认证处理中
     *
     * @param id
     */
    void processing(Long id);

    /**
     * 认证记录入库
     *
     * @param companyAuthenticationEntity
     * @return
     */
    CompanyAuthenticationEntity insert(CompanyAuthenticationEntity companyAuthenticationEntity);
}
