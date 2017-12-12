package com.eid.dal.manager.impl;

import com.alibaba.fastjson.JSON;
import com.eid.common.util.RedisUtil;
import com.eid.dal.dao.CompanyInfoDao;
import com.eid.dal.entity.CompanyInfoEntity;
import com.eid.dal.manager.CompanyInfoManager;
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
public class CompanyInfoManagerImpl implements CompanyInfoManager {

    @Autowired(required = false)
    private JedisCluster jedisCluster;

    @Autowired
    private CompanyInfoDao companyInfoDao;

    @Override
    public CompanyInfoEntity queryCompanyInfoById(String companyId) {
        String key = RedisUtil.getCompanyInfoKey(companyId);
        // 从redis获取app信息
        String companyInfo = jedisCluster.get(key);
        if (!Strings.isNullOrEmpty(companyInfo))
            return JSON.parseObject(companyInfo, CompanyInfoEntity.class);

        CompanyInfoEntity companyInfoEntity = companyInfoDao.findByCompanyId(companyId);

        // 将查询出来的商户信息放入缓存中
        if (!Objects.equal(companyInfoEntity, null)) {
            jedisCluster.set(key, JSON.toJSONString(companyInfoEntity));
            jedisCluster.expire(key, RedisUtil.CACHE_INFO_SECONDS);
        }

        return companyInfoEntity;
    }

    @Override
    public CompanyInfoEntity queryCompanyInfoByApId(String apId) {
//        String key = RedisUtil.getCompanyInfoKey(apId);
//        // 从redis获取app信息
//        String companyId = jedisCluster.get(key);
//        if (!Strings.isNullOrEmpty(companyId))
//            return queryCompanyInfoById(companyId);

        CompanyInfoEntity companyInfoEntity = companyInfoDao.findByApId(apId);

//        // 将查询出来的商户信息放入缓存中
//        if (!Objects.equal(companyInfoEntity, null)) {
//            jedisCluster.set(key, companyInfoEntity.getCompanyId());
//            jedisCluster.expire(key, RedisUtil.CACHE_INFO_SECONDS);
//            return queryCompanyInfoById(companyInfoEntity.getCompanyId());
//        }

        return companyInfoEntity;
    }

    @Override
    public Integer updateApInfo(String apId, String apKey, Long id) {
        return companyInfoDao.updateApInfo(apId, apKey, id);
    }

    @Override
    public void delCompanyInfo(String companyId) {
        String key = RedisUtil.getCompanyInfoKey(companyId);
        // 从redis删除商户信息
        jedisCluster.del(key);
    }

}
