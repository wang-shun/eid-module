package com.eid.company.biz;

import com.eid.company.BaseServiceTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/2/9 Time:上午11:01
 */
@Slf4j
public class AccessTokenBizTest extends BaseServiceTest {

    @Autowired
    private AccessTokenBiz accessTokenBiz;

    @Test
    public void tokenTest() {
        String response = accessTokenBiz.generateToken("test", "{fdafdafas:fdafdafas}");
        log.info("result:{};", response);
    }

}
