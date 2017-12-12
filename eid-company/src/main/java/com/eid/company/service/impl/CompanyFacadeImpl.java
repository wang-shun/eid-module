package com.eid.company.service.impl;

import com.eid.common.enums.ErrorCode;
import com.eid.common.exception.FacadeException;
import com.eid.common.model.Response;
import com.eid.company.biz.CompanyBiz;
import com.eid.company.model.CompanyAppInfoDTO;
import com.eid.company.model.CompanyInfoDTO;
import com.eid.company.service.CompanyFacade;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/1/17 Time:下午5:10
 */
@Slf4j
@Service
public class CompanyFacadeImpl implements CompanyFacade {

    @Autowired
    private CompanyBiz companyBiz;

    @Override
    public Response<CompanyInfoDTO> available(String companyId) {
        log.info("call CompanyInfoFacade.available request:{};", companyId);
        Response<CompanyInfoDTO> response = new Response<>();
        try {
            if (Strings.isNullOrEmpty(companyId))
                throw new FacadeException(ErrorCode.PARAM_ERR);

            response.setResult(companyBiz.validate(companyId));
        } catch (FacadeException fe) {
            log.error("Failed to CompanyInfoFacade.available request:{};CAUSE:{}", companyId, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to CompanyInfoFacade.available request:{};CAUSE:{}", companyId, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("call CompanyInfoFacade.available request:{};result:{};", companyId, response);
        return response;
    }

    @Override
    public Response<CompanyInfoDTO> availableByApId(String apId) {
        log.info("call CompanyInfoFacade.availableByApId request:{};", apId);
        Response<CompanyInfoDTO> response = new Response<>();
        try {
            if (Strings.isNullOrEmpty(apId))
                throw new FacadeException(ErrorCode.PARAM_ERR);

            response.setResult(companyBiz.validateByApId(apId));
        } catch (FacadeException fe) {
            log.error("Failed to CompanyInfoFacade.availableByApId request:{};CAUSE:{}", apId, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to CompanyInfoFacade.availableByApId request:{};CAUSE:{}", apId, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("call CompanyInfoFacade.availableByApId request:{};result:{};", apId, response);
        return response;
    }

    @Override
    public Response<CompanyAppInfoDTO> availableByAppId(String appId) {
        log.info("Call CompanyInfoFacade.availableByAppId request:{};", appId);
        Response<CompanyAppInfoDTO> response = new Response<>();
        try {
            if (Strings.isNullOrEmpty(appId))
                throw new FacadeException(ErrorCode.PARAM_ERR);

            response.setResult(companyBiz.validateByAppId(appId));
        } catch (FacadeException fe) {
            log.error("Failed to CompanyInfoFacade.availableByAppId request:{};CAUSE:{}", appId, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to CompanyInfoFacade.availableByAppId request:{};CAUSE:{}", appId, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("call CompanyInfoFacade.availableByAppId request:{};result:{};", appId, response);
        return response;
    }

    @Override
    public Response<CompanyAppInfoDTO> getAppInfo(String appId) {
        log.info("Call CompanyInfoFacade.getAppName request:{};", appId);
        Response<CompanyAppInfoDTO> response = new Response<>();
        try {
            if (Strings.isNullOrEmpty(appId))
                throw new FacadeException(ErrorCode.PARAM_ERR);

            response.setResult(companyBiz.queryCompanyAppInfo(appId));
        } catch (FacadeException fe) {
            log.error("Failed to CompanyInfoFacade.getAppName request:{};CAUSE:{}", appId, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to CompanyInfoFacade.getAppName request:{};CAUSE:{}", appId, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("call CompanyInfoFacade.getAppName request:{};result:{};", appId, response);
        return response;
    }
}
