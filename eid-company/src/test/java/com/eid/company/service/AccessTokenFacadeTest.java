package com.eid.company.service;

import com.eid.common.model.Response;
import com.eid.company.BaseServiceTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/1/17 Time:下午5:41
 */
@Slf4j
public class AccessTokenFacadeTest extends BaseServiceTest {

    @Autowired
    private AccessTokenFacade accessTokenFacade;

    @Test
    public void tokenTest() {
        Response<String> response = accessTokenFacade.token("test", "{fdafdafas:fdafdafas}");
        log.info("result:{};", response);
    }

    @Test
    public void tokenValidateTest() {
        Response<String> response = accessTokenFacade.validate("094E28D3605A75F76DC6351777E7C2EC");
        log.info("result:{};", response);
    }
}
