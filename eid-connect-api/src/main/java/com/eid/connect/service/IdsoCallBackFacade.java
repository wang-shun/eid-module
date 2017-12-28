package com.eid.connect.service;

import com.alibaba.fastjson.JSONObject;

/**
 * idso回调通知服务暴露接口
 */
public interface IdsoCallBackFacade {

    JSONObject idsoCallBackVerifySign(String requestData);

}
