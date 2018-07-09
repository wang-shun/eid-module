package com.eid.anonymous.service;

import com.eid.common.model.Response;

public interface AuthSchlechteFacade {

    Response<Object> authentication(String name, String idNum, String apId);

}
