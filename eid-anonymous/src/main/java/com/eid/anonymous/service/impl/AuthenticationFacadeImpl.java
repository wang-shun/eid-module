package com.eid.anonymous.service.impl;

import com.eid.anonymous.biz.AuthenticationBiz;
import com.eid.anonymous.service.AuthenticationFacade;
import com.eid.common.enums.ErrorCode;
import com.eid.common.exception.FacadeException;
import com.eid.common.model.Response;
import com.eid.common.model.param.req.EidBaseDTO;
import com.eid.common.model.param.res.EidBaseResDTO;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    @Autowired
    private AuthenticationBiz authenticationBiz;

    // eid认证处理服务
    @Override
    public Response<EidBaseResDTO> authentication(EidBaseDTO eidBaseDTO) {
        log.info("eID认证服务，方法名：authentication，请求参数:{};", eidBaseDTO);
        Response<EidBaseResDTO> response = new Response<>();
        try {
            response.setResult(authenticationBiz.authentication(eidBaseDTO));
        } catch (FacadeException fe) {
            log.error("Failed to AuthenticationFacade.authentication request:{};CAUSE:{};", eidBaseDTO, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to AuthenticationFacade.authentication request:{};CAUSE:{};", eidBaseDTO, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("call AuthenticationFacade.authentication request:{};result:{};", eidBaseDTO, response);
        return response;
    }

}
