package com.eid.anonymous.service;

import com.alibaba.fastjson.JSONObject;
import com.eid.common.model.Response;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/17 Time:下午4:44
 */
public interface AuthorizeSIMFacade {

    /**
     * 认证
     *
     * @param jsonObject
     * @return
     */
    Response<JSONObject> eIDSIMCallBackHandle(JSONObject jsonObject);
}
