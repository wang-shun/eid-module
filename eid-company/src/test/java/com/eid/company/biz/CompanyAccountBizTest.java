package com.eid.company.biz;

import com.eid.company.BaseServiceTest;
import com.eid.company.enums.DebitType;
import com.eid.company.model.DebitInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/1/22 Time:上午11:56
 */
@Slf4j
public class CompanyAccountBizTest extends BaseServiceTest {

    @Autowired
    private CompanyAccountBiz companyAccountBiz;

    @Test
    public void debitTest() {
        DebitInfo debitInfo = new DebitInfo();
        debitInfo.setContent("Test");
        debitInfo.setMoney(10000L);
        debitInfo.setDebitType(DebitType.AUTHENTICATION);
        debitInfo.setCompanyId("170179989718171933103000");
        Boolean response = companyAccountBiz.debit(debitInfo);
        log.info("request:{};result:{};", debitInfo, response);
    }
}
