package com.eid.connect.service.impl;

import com.eid.common.enums.ErrorCode;
import com.eid.common.exception.FacadeException;
import com.eid.common.model.Response;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.result.EidBaseResult;
import com.eid.connect.biz.SendBiz;
import com.eid.connect.service.SendFacade;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.aiav.astoopsdk.util.EncryptionMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SendFacadeImpl implements SendFacade {

    @Autowired
    private SendBiz sendBiz;

    // 发送eID 认证请求
    @Override
    public Response<EidBaseResult> request(EidBaseParam eidBaseParam) {
        Response<EidBaseResult> response = new Response<>();
        try {
            if (Strings.isNullOrEmpty(eidBaseParam.getBizType()))
                throw new FacadeException(ErrorCode.PARAM_ERR);

            response.setResult(sendBiz.send(eidBaseParam));
        } catch (FacadeException fe) {
            log.error("Failed to SendFacade.request request:{};CAUSE:{};", eidBaseParam, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to SendFacade.request request:{};CAUSE:{};", eidBaseParam, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("Call SendFacade.request request:{};result:{};", eidBaseParam, response);
        return response;
    }

}
