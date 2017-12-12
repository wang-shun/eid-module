package com.eid.dal.manager.impl;

import com.alibaba.fastjson.JSON;
import com.eid.common.util.RedisUtil;
import com.eid.dal.dao.CompanyAppDao;
import com.eid.dal.entity.CompanyAppEntity;
import com.eid.dal.manager.CompanyAppManager;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/21 Time:下午3:43
 */
@Component
public class CompanyAppManagerImpl implements CompanyAppManager {

    @Autowired(required = false)
    private JedisCluster jedisCluster;

    @Autowired
    private CompanyAppDao companyAppDao;

    @Override
    public CompanyAppEntity queryAppInfoByApp(String appId) {
//        String key = RedisUtil.getAppInfoKey(appId);
//        // 从redis获取app信息
//        String appInfo = jedisCluster.get(key);
//        if (!Strings.isNullOrEmpty(appInfo))
//            return JSON.parseObject(appInfo, CompanyAppEntity.class);

        CompanyAppEntity companyAppEntity = companyAppDao.findByAppId(appId);
//        if (!Objects.equal(companyAppEntity, null))
//            jedisCluster.set(key, JSON.toJSONString(companyAppEntity));

        return companyAppEntity;
    }

    @Override
    public CompanyAppEntity queryAppInfoById(Long id) {
        return companyAppDao.findOne(id);
    }

    @Override
    public Integer updateKey(String appId, String appKey, Long id) {
        return companyAppDao.appKey(appId, appKey, id);
    }

    @Override
    public void delAppInfo(String id) {
        String key = RedisUtil.getAppInfoKey(id);
        // 从redis删除app信息
        jedisCluster.del(key);
    }
}
