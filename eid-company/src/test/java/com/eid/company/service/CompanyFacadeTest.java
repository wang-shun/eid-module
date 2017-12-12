package com.eid.company.service;

import com.eid.common.model.Response;
import com.eid.company.BaseServiceTest;
import com.eid.company.model.CompanyInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/1/17 Time:下午5:41
 */
@Slf4j
public class CompanyFacadeTest extends BaseServiceTest {

    @Autowired
    private CompanyFacade companyFacade;

    @Test
    public void createUserTest() {
        String apId = "3333";
        Response<CompanyInfoDTO> response = companyFacade.availableByApId(apId);
        log.info("request:{};result:{};", apId, response);
    }

}
