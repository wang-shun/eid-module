package com.eid.company.biz;

/**
 * token业务层
 * Created by:ruben Date:2017/1/19 Time:下午4:44
 */
public interface AccessTokenBiz {

    /**
     * 生成token
     *
     * @param appId
     * @param message
     * @return
     */
    String generateToken(String appId, String message);

    /**
     * 生成查询token
     *
     * @param message
     * @return
     */
    String generateToken(String message);

    /**
     * 校验token是否有效
     *
     * @param token
     * @return
     */
    String validateToken(String token);

    /**
     * 校验token是否有效，让token时间超时
     *
     * @param token
     * @return
     */
    String validateTokenNotDel(String token);
}
