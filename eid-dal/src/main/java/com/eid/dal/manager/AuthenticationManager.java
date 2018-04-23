package com.eid.dal.manager;

import com.eid.dal.entity.CompanyAppEntity;
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
     * 认证成功(SIM 身份授权)
     *
     * @param id
     * @param resultDetail
     * @param resultMessage
     * @param appEidCode
     */
    void success(Long id, String resultDetail, String resultMessage, String appEidCode, String userInfo);

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

    /**
     * 根据认证流水号查询认证记录
     * @param bizSequenceId
     * @return
     */
    CompanyAuthenticationEntity findByBizSequenceId(String bizSequenceId);


    /**
     * 根据apid查询app信息
     * @param apId
     * @return
     */
    CompanyAppEntity findByApId(String apId);
}
