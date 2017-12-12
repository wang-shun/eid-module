package com.eid.dal.manager;

import com.eid.dal.entity.CompanyAppEntity;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/21 Time:下午3:42
 */
public interface CompanyAppManager {

    /**
     * 根据app_id查询app信息
     *
     * @param appId
     * @return
     */
    CompanyAppEntity queryAppInfoByApp(String appId);

    /**
     * 根据id查询app信息
     *
     * @param id
     * @return
     */
    CompanyAppEntity queryAppInfoById(Long id);

    /**
     * 重置app_id
     *
     * @param appId
     * @param appKey
     * @param id
     * @return
     */
    Integer updateKey(String appId, String appKey, Long id);

    /**
     * 根据app_id删除app信息
     *
     * @param appId
     */
    void delAppInfo(String appId);
}
