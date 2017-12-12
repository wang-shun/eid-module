package com.eid.company.service;

import com.eid.common.model.Response;

/**
 * token服务
 * Created by:ruben Date:2017/2/8 Time:上午10:31
 */
public interface AccessTokenFacade {

    /**
     * 生成accessToken
     *
     * @param mark
     * @param message
     * @return
     */
    Response<String> token(String mark, String message);

    /**
     * 生成查询token
     *
     * @param message
     * @return
     */
    Response<String> token(String message);

    /**
     * 校验accessToken是否超时 以及是否有效
     *
     * @param token
     * @return
     */
    Response<String> validate(String token);

    /**
     * 校验token是否超时，及有效
     *
     * @param token
     * @return
     */
    Response<String> validateToken(String token);
}
