package com.eid.company.service.impl;

import com.eid.common.enums.ErrorCode;
import com.eid.common.exception.FacadeException;
import com.eid.common.model.Response;
import com.eid.company.biz.CompanyAccountBiz;
import com.eid.company.model.DebitInfo;
import com.eid.company.service.CompanyAccountFacade;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class CompanyAccountFacadeImpl implements CompanyAccountFacade {

    @Autowired
    private CompanyAccountBiz companyAccountBiz;

    @Override
    public Response<Boolean> deduction(DebitInfo debitInfo) {
        log.info("call CompanyAccountFacade.deduction request:{};", debitInfo);
        Response<Boolean> response = new Response<>();
        try {
            if (Objects.equals(debitInfo, null) || Strings.isNullOrEmpty(debitInfo.getCompanyId())
                    || Objects.equals(debitInfo.getMoney(), null) || Objects.equals(debitInfo.getDebitType(), null))
                throw new FacadeException(ErrorCode.PARAM_ERR);

            response.setResult(companyAccountBiz.debit(debitInfo));
        } catch (FacadeException fe) {
            log.error("Failed to CompanyAccountFacade.deduction request:{};CAUSE:{};", debitInfo, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to CompanyAccountFacade.deduction request:{};CAUSE:{};", debitInfo, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("call CompanyAccountFacade.deduction request:{};result:{};", debitInfo, response);
        return response;
    }


}
