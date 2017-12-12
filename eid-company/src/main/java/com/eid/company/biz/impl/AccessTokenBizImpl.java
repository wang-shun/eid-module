package com.eid.company.biz.impl;

import com.eid.common.enums.ErrorCode;
import com.eid.common.exception.FacadeException;
import com.eid.common.util.DateUtil;
import com.eid.common.util.MD5Encrypt;
import com.eid.company.biz.AccessTokenBiz;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.UUID;

@Slf4j
@Component
public class AccessTokenBizImpl implements AccessTokenBiz {

    @Autowired(required = false)
    private JedisCluster jedisCluster;

    // 认证过期时间
    private int seconds = 5 * 60;
    // 查询过期时间
    private int querySeconds = 10 * 60;

    @Override
    public String generateToken(String mark, String message) {
        String salt = mark + DateUtil.getCurrent();// mark、时间戳 当md5 盐
        MD5Encrypt encoderMd5 = new MD5Encrypt(salt);
        UUID uuid = UUID.randomUUID();
        String token = encoderMd5.encode(uuid.toString());// 使用uuid、盐 md5 生成token
        log.info("token:{};", token);

        jedisCluster.set(token, message);// 放入redis缓存，时效为3分钟
        jedisCluster.expire(token, seconds);
        return token;
    }

    @Override
    public String generateToken(String message) {
        MD5Encrypt encoderMd5 = new MD5Encrypt(DateUtil.getCurrent());
        UUID uuid = UUID.randomUUID();
        String token = encoderMd5.encode(uuid.toString());// 使用uuid、盐 md5 生成token
        log.info("token:{};", token);

        jedisCluster.set(token, message);// 放入redis缓存，时效为3分钟
        jedisCluster.expire(token, querySeconds);
        return token;
    }

    @Override
    public String validateToken(String token) {
        String value = jedisCluster.get(token);// 根据token获取值。取不到值则已过期，或者未生成token
        log.info("token:{};value:{};", token, value);
        if (Strings.isNullOrEmpty(value))
            throw new FacadeException(ErrorCode.TOKEN_EXPIRY);

        jedisCluster.del(token);
        return value;
    }

    @Override
    public String validateTokenNotDel(String token) {
        String value = jedisCluster.get(token);
        log.info("token:{};value:{};", token, value);
        if (Strings.isNullOrEmpty(value))
            throw new FacadeException(ErrorCode.TOKEN_EXPIRY);

        return value;
    }
}
