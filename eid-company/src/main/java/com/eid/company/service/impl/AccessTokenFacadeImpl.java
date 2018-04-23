package com.eid.company.service.impl;

import com.eid.common.enums.ErrorCode;
import com.eid.common.exception.FacadeException;
import com.eid.common.model.Response;
import com.eid.company.biz.AccessTokenBiz;
import com.eid.company.service.AccessTokenFacade;
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
public class AccessTokenFacadeImpl implements AccessTokenFacade {

    @Autowired
    private AccessTokenBiz accessTokenBiz;

    @Override
    public Response<String> token(String mark, String message) {
        log.info("获取accesstoken服务，方法名：token，请求参数：{};", message);
        Response<String> response = new Response<>();
        try {
            if (Strings.isNullOrEmpty(mark) || Strings.isNullOrEmpty(message))
                throw new FacadeException(ErrorCode.PARAM_ERR);

            response.setResult(accessTokenBiz.generateToken(mark, message));
        } catch (FacadeException fe) {
            log.error("Failed to AccessTokenFacade.token request:{};CAUSE:{};", message, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to AccessTokenFacade.token request:{};CAUSE:{};", message, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("call AccessTokenFacade.token request:{};result:{};", message, response);
        return response;
    }

        @Override
    public Response<String> token(String message) {
        log.info("call AccessTokenFacade.token request:{};", message);
        Response<String> response = new Response<>();
        try {
            if (Strings.isNullOrEmpty(message))
                throw new FacadeException(ErrorCode.PARAM_ERR);

            response.setResult(accessTokenBiz.generateToken(message));
        } catch (FacadeException fe) {
            log.error("Failed to AccessTokenFacade.token request:{};CAUSE:{};", message, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to AccessTokenFacade.token request:{};CAUSE:{};", message, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("call AccessTokenFacade.token request:{};result:{};", message, response);
        return response;
    }

    @Override
    public Response<String> validate(String token) {
        log.info("call AccessTokenFacade.validate request:{};", token);
        Response<String> response = new Response<>();
        try {
            if (Strings.isNullOrEmpty(token))
                throw new FacadeException(ErrorCode.PARAM_ERR);

            response.setResult(accessTokenBiz.validateToken(token));
        } catch (FacadeException fe) {
            log.error("Failed to AccessTokenFacade.validate request:{};CAUSE:{};", token, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to AccessTokenFacade.validate request:{};CAUSE:{};", token, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("call AccessTokenFacade.validate request:{};result:{};", token, response);
        return response;
    }

    @Override
    public Response<String> validateToken(String token) {
        log.info("call AccessTokenFacade.validateToken request:{};", token);
        Response<String> response = new Response<>();
        try {
            if (Strings.isNullOrEmpty(token))
                throw new FacadeException(ErrorCode.PARAM_ERR);

            response.setResult(accessTokenBiz.validateTokenNotDel(token));
        } catch (FacadeException fe) {
            log.error("Failed to AccessTokenFacade.validateToken request:{};CAUSE:{};", token, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to AccessTokenFacade.validateToken request:{};CAUSE:{};", token, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("call AccessTokenFacade.validateToken request:{};result:{};", token, response);
        return response;
    }

}
