package com.eid.dal.manager;

import com.eid.dal.entity.CompanyInfoEntity;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/21 Time:下午3:42
 */
public interface CompanyInfoManager {

    /**
     * 根据companyId查询商户信息
     *
     * @param companyId
     * @return
     */
    CompanyInfoEntity queryCompanyInfoById(String companyId);

    /**
     * 根据apId查询商户信息
     *
     * @param apId
     * @return
     */
    CompanyInfoEntity queryCompanyInfoByApId(String apId);

    /**
     * 更新ap信息
     *
     * @param apId
     * @param apKey
     * @param id
     * @return
     */
//    Integer updateApInfo(String apId, String apKey, Long id);

    /**
     * 重置商户信息
     *
     * @param companyId
     */
    void delCompanyInfo(String companyId);
}
