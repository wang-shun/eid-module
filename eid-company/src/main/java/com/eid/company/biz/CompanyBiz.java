package com.eid.company.biz;

import com.eid.company.model.CompanyAppInfoDTO;
import com.eid.company.model.CompanyInfoDTO;

/**
 * 商户业务层
 * Created by:ruben Date:2017/1/19 Time:下午4:44
 */
public interface CompanyBiz {

    /**
     * 校验商户是否有效、商户余额是否充足
     *
     * @param companyId
     * @return
     */
    CompanyInfoDTO validate(String companyId);

    /**
     * 校验商户是否有效、商户余额是否充足
     *
     * @param apId
     * @return
     */
    CompanyInfoDTO validateByApId(String apId);

    /**
     * 校验商户app信息是否有效
     *
     * @param appId
     * @return
     */
    CompanyAppInfoDTO validateByAppId(String appId);

    /**
     * 根据apid获取商户信息
     *
     * @param apId
     * @return
     */
    CompanyInfoDTO queryCompanyInfoByApId(String apId);

    /**
     * 根据companyId获取商户信息
     *
     * @param companyId
     * @return
     */
    CompanyInfoDTO queryCompanyInfo(String companyId);

    /**
     * 根据appid获取app信息
     *
     * @param appId
     * @return
     */
    CompanyAppInfoDTO queryCompanyAppInfo(String appId);
}
