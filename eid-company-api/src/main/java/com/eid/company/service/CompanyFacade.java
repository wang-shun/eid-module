package com.eid.company.service;

import com.eid.common.model.Response;
import com.eid.company.model.CompanyAppInfoDTO;
import com.eid.company.model.CompanyInfoDTO;

/**
 * 商户服务
 * Created by:ruben Date:2017/1/17 Time:下午5:40
 */
public interface CompanyFacade {

    /**
     * 校验商户是否有效、商户余额是否充足
     *
     * @param companyId
     * @return
     */
    Response<CompanyInfoDTO> available(String companyId);

    /**
     * 校验商户是否有效、商户余额是否充足
     *
     * @param apId
     * @return
     */
    Response<CompanyInfoDTO> availableByApId(String apId);

    /**
     * 校验商户app是否有效
     *
     * @param appId
     * @return
     */
    Response<CompanyAppInfoDTO> availableByAppId(String appId);

    /**
     * 获取应用信息
     *
     * @param appId
     * @return
     */
    Response<CompanyAppInfoDTO> getAppInfo(String appId);

    /**
     * 获取商户app信息
     *
     * @param appId
     * @return
     */
//    Response<CompanyAppInfoDTO> getAppInfo(String appId);

    /**
     * 获取apkeyfactor
     * @param apId
     * @return
     */
    Response<String> getApkeyFactor(String apId);

}
