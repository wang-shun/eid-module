package com.eid.company.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.eid.common.enums.ErrorCode;
import com.eid.common.exception.FacadeException;
import com.eid.common.model.Response;
import com.eid.common.model.param.request.management.EidAppRegParam;
import com.eid.common.model.param.request.management.EidAppkeyUpdateParam;
import com.eid.company.biz.CompanyAppBiz;
import com.eid.company.service.CompanyAppFacade;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
public class CompanyAppFacadeImpl implements CompanyAppFacade {

    @Autowired
    private CompanyAppBiz companyAppBiz;

    /**
     * app应用注册
     * @param eidAppRegParam
     * @return
     */
    @Override
    public Response<Boolean> apply(EidAppRegParam eidAppRegParam) {
        log.info("call CompanyAppFacade.apply request:{};", eidAppRegParam);
        Response<Boolean> response = new Response<>();
        try {
            if (Objects.equal(eidAppRegParam, null) || Strings.isNullOrEmpty(eidAppRegParam.getCompanyId()) || Strings.isNullOrEmpty(eidAppRegParam.getRelatedAppid()))
                throw new FacadeException(ErrorCode.PARAM_ERR);

            response.setResult(companyAppBiz.get(eidAppRegParam));
        } catch (FacadeException fe) {
            log.error("Failed to CompanyAppFacade.apply request:{};CAUSE:{};", eidAppRegParam, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to CompanyAppFacade.apply request:{};CAUSE:{};", eidAppRegParam, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("call CompanyAppFacade.apply request:{};result:{};", eidAppRegParam, response);
        return response;
    }

    @Override
    public Response<Boolean> applyAppInfo(EidAppRegParam eidAppRegParam) {
        log.info("call CompanyAppFacade.applyAppInfo request:{};", eidAppRegParam);
        Response<Boolean> response = new Response<>();
        try {
            if (Objects.equal(eidAppRegParam, null) || Strings.isNullOrEmpty(eidAppRegParam.getCompanyId()))
                throw new FacadeException(ErrorCode.PARAM_ERR);

            response.setResult(companyAppBiz.getAppId(eidAppRegParam));
        } catch (FacadeException fe) {
            log.error("Failed to CompanyAppFacade.applyAppInfo request:{};CAUSE:{};", eidAppRegParam, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to CompanyAppFacade.applyAppInfo request:{};CAUSE:{};", eidAppRegParam, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("call CompanyAppFacade.applyAppInfo request:{};result:{};", eidAppRegParam, response);
        return response;
    }

    @Override
    public Response<Boolean> resetAppKey(EidAppkeyUpdateParam eidAppkeyUpdateParam) {
        log.info("call CompanyAppFacade.resetAppKey request:{};", eidAppkeyUpdateParam);
        Response<Boolean> response = new Response<>();
        try {
            if (Objects.equal(eidAppkeyUpdateParam, null) || Strings.isNullOrEmpty(eidAppkeyUpdateParam.getAppid()))
                throw new FacadeException(ErrorCode.PARAM_ERR);

            response.setResult(companyAppBiz.resetAppKey(eidAppkeyUpdateParam));
        } catch (FacadeException fe) {
            log.error("Failed to CompanyAppFacade.resetAppKey request:{};CAUSE:{};", eidAppkeyUpdateParam, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to CompanyAppFacade.resetAppKey request:{};CAUSE:{};", eidAppkeyUpdateParam, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("call CompanyAppFacade.resetAppKey request:{};result:{};", eidAppkeyUpdateParam, response);
        return response;
    }

    @Override
    public Response<Boolean> resetApKey(EidAppkeyUpdateParam eidAppkeyUpdateParam) {
        log.info("call CompanyAppFacade.resetApKey request:{};", eidAppkeyUpdateParam);
        Response<Boolean> response = new Response<>();
        try {
            if (Objects.equal(eidAppkeyUpdateParam, null) || Strings.isNullOrEmpty(eidAppkeyUpdateParam.getAppid()))
                throw new FacadeException(ErrorCode.PARAM_ERR);

            response.setResult(companyAppBiz.resetApKey(eidAppkeyUpdateParam));
        } catch (FacadeException fe) {
            log.error("Failed to CompanyAppFacade.resetApKey request:{};CAUSE:{};", eidAppkeyUpdateParam, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to CompanyAppFacade.resetApKey request:{};CAUSE:{};", eidAppkeyUpdateParam, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("call CompanyAppFacade.resetApKey request:{};result:{};", eidAppkeyUpdateParam, response);
        return response;
    }

    /**
     * 初始化app应用注册的id和key
     * @param requestData idso回调数据
     * @return response
     */
    @Override
    public Response<Boolean> initAppInfoIdAndKey(JSONObject requestData) {
        log.info("call CompanyAppFacade.initAppInfoIdAndKey request:{};", requestData);
        Response<Boolean> response = new Response<>();
        try {
            if (Objects.equal(requestData, null) || ObjectUtils.isEmpty(requestData))
                throw new FacadeException(ErrorCode.PARAM_ERR);

            response.setResult(companyAppBiz.initIdAndKey(requestData));

        } catch (FacadeException fe) {
            log.error("Failed to CompanyAppFacade.initAppInfoIdAndKey request:{};CAUSE:{};", requestData, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to CompanyAppFacade.initAppInfoIdAndKey request:{};CAUSE:{};", requestData, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("call CompanyAppFacade.initAppInfoIdAndKey request:{};result:{};", requestData, response);

        return response;
    }

}
