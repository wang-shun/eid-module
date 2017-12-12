package com.eid.company.service.impl;

import com.eid.common.enums.ErrorCode;
import com.eid.common.exception.FacadeException;
import com.eid.common.model.Response;
import com.eid.company.biz.CompanyAuthenticationBiz;
import com.eid.company.model.CompanyAuthenticationDTO;
import com.eid.company.service.CompanyAuthenticationFacade;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/2/10.
 */
@Slf4j
@Service
public class CompanyAuthenticationFacadeImpl implements CompanyAuthenticationFacade {

    @Autowired
    private CompanyAuthenticationBiz companyAuthenticationBiz;

    @Override
    public Response<CompanyAuthenticationDTO> getAuthenticationResult(String apId, String accessToken) {
        log.info("Call CompanyAuthenticationFacade.getAuthenticationResult request:{};accessToken:{};", apId, accessToken);
        Response<CompanyAuthenticationDTO> response = new Response<>();
        try {
            if (Strings.isNullOrEmpty(apId) || Strings.isNullOrEmpty(accessToken))
                throw new FacadeException(ErrorCode.PARAM_ERR);

            response.setResult(companyAuthenticationBiz.getResult(apId, accessToken));
        } catch (FacadeException fe) {
            log.error("Failed to CompanyAuthenticationFacade.getAuthenticationResult request:{};accessToken:{};CAUSE:{};", apId, accessToken, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to CompanyAuthenticationFacade.getAuthenticationResult request:{};accessToken:{};CAUSE:{};", apId, accessToken, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("Call CompanyAuthenticationFacade.getAuthenticationResult request:{};accessToken:{};response:{};", apId, accessToken, response);
        return response;
    }

    @Override
    public Response<List<CompanyAuthenticationDTO>> getRecordPage(String appEidCode, String startDate, String endDate, Integer page, Integer pageSize) {
        log.info("Call CompanyAuthenticationFacade.getRecordList request:{};", appEidCode);
        Response<List<CompanyAuthenticationDTO>> response = new Response<>();
        try {
            if (Strings.isNullOrEmpty(appEidCode))
                throw new FacadeException(ErrorCode.PARAM_ERR);

            response.setResult(companyAuthenticationBiz.getRecordList(appEidCode, startDate, endDate, page, pageSize));
        } catch (FacadeException fe) {
            log.error("Failed to CompanyAuthenticationFacade.getRecordList request:{};CAUSE:{};", appEidCode, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to CompanyAuthenticationFacade.getRecordList request:{};CAUSE:{};", appEidCode, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("Call CompanyAuthenticationFacade.getRecordList request:{};response:{};", appEidCode, response);
        return response;
    }
}
