package com.eid.connect.service;

import com.eid.common.model.Response;

public interface EncryptionMachineFacade {

    Response<String> getAppkey(String appid, String appkeyFactor);

    Response<String> apToAsSign(String appid, String appkeyFactor, String signFactor, String data, String signType);
}
