package com.eid.dispatch.process.month;

import com.eid.common.enums.FeeType;
import com.eid.dal.entity.CompanyAgreementEntity;
import com.eid.dal.entity.CompanyAuthenticationEntity;
import com.eid.dispatch.annotations.FeeTypeImpl;
import com.eid.dispatch.process.AsyncFeeProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/13 Time:下午6:23
 */
@Slf4j
@Component("monthAsyncFeeProcessorImpl")
@FeeTypeImpl(value = {FeeType.MONTH})
public class AsyncFeeProcessorImpl extends AsyncFeeProcessor {

    @Override
    protected Long costCompanyFee(CompanyAgreementEntity companyAgreementEntity, CompanyAuthenticationEntity companyAuthenticationEntity) {
        return 0L;
    }
}
