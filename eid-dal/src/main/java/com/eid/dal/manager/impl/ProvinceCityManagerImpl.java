package com.eid.dal.manager.impl;

import com.alibaba.fastjson.JSON;
import com.eid.common.util.RedisUtil;
import com.eid.dal.dao.ProvinceCityDao;
import com.eid.dal.entity.ProvinceCityEntity;
import com.eid.dal.manager.ProvinceCityManager;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/4/6 Time:下午5:01
 */
@Component
public class ProvinceCityManagerImpl implements ProvinceCityManager {

    @Autowired(required = false)
    private JedisCluster jedisCluster;

    @Autowired
    private ProvinceCityDao provinceCityDao;

    @Override
    public ProvinceCityEntity queryCity(Integer code) {
        String key = RedisUtil.getProvinceCodeKey(code);
        // 从redis获取app信息
        String info = jedisCluster.get(key);
        if (!Strings.isNullOrEmpty(info))
            return JSON.parseObject(info, ProvinceCityEntity.class);

        ProvinceCityEntity provinceCityEntity = provinceCityDao.findByCode(code);
        if (!Objects.equal(provinceCityEntity, null))
            jedisCluster.set(key, JSON.toJSONString(provinceCityEntity));

        return provinceCityEntity;
    }
}
