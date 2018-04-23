package com.eid.connect.service;

import com.alibaba.fastjson.JSONObject;

/**
 * idso回调通知服务暴露接口
 */
public interface IdsoCallBackFacade {

    JSONObject idsoCallBackVerifySign(String requestData);
    String doEncrypt3DesECBPKCS5(String appKey, String data, String factor);
    String doEncryptSSm4(String appKey, String data, String factor);
    String createSign(String data, String signFacotr);

}
