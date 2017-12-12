package com.eid.company.service;

import com.eid.company.BaseServiceTest;
import com.eid.company.enums.DebitType;
import com.eid.company.model.DebitInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/4/25 Time:下午2:41
 */
@Slf4j
public class CompanyAccountFacadeTest extends BaseServiceTest {
    @Autowired
    private CompanyAccountFacade companyAccountFacade;

    @Test
    public void debitTest() {
//        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
//        cachedThreadPool.execute(new Runnable() {



//            public void run() {
                DebitInfo debitInfo = new DebitInfo();
                debitInfo.setContent("Test");
                debitInfo.setMoney(10000L);
                debitInfo.setDebitType(DebitType.AUTHENTICATION);
                debitInfo.setCompanyId("170476039019165238835001");

                companyAccountFacade.deduction(debitInfo);

//            }
//        });
    }
}
