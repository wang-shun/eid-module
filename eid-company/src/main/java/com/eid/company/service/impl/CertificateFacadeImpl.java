package com.eid.company.service.impl;

import com.eid.common.enums.ErrorCode;
import com.eid.common.exception.FacadeException;
import com.eid.common.model.Response;
import com.eid.common.model.param.res.CertificateResDTO;
import com.eid.company.biz.CertificateBiz;
import com.eid.company.service.CertificateFacade;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/7/21 Time:下午5:17
 */
@Slf4j
@Service
public class CertificateFacadeImpl implements CertificateFacade {

    @Autowired
    private CertificateBiz certificateBiz;

    @Override
    public Response<String> get(String companyId) {
        log.info("Call CertificateFacade.get request:{};", companyId);
        Response<String> response = new Response<>();
        try {
            if (Strings.isNullOrEmpty(companyId))
                throw new FacadeException(ErrorCode.PARAM_ERR);

            response.setResult(certificateBiz.get(companyId));
        } catch (FacadeException fe) {
            log.error("Failed to CertificateFacade.get request:{};CAUSE:{};", companyId, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to CertificateFacade.get request:{};CAUSE:{};", companyId, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("Call CertificateFacade.get request:{};result:{};", companyId, response);
        return response;
    }

    @Override
    public Response<CertificateResDTO> validate(String id, String name, String certificateNo, String certificatePassword, String attach) {
        log.info("Call CertificateFacade.validate request:{};", id);
        Response<CertificateResDTO> response = new Response<>();
        try {
            if (Strings.isNullOrEmpty(id) || Strings.isNullOrEmpty(certificateNo))
                throw new FacadeException(ErrorCode.PARAM_ERR);

            response.setResult(certificateBiz.match(id, name, certificateNo, certificatePassword, attach));
        } catch (FacadeException fe) {
            log.error("Failed to CertificateFacade.validate request:{};CAUSE:{};", id, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to CertificateFacade.validate request:{};CAUSE:{};", id, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("Call CertificateFacade.validate request:{};result:{};", id, response);
        return response;
    }

}
