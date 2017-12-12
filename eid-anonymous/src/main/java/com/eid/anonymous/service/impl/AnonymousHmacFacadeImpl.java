package com.eid.anonymous.service.impl;

import com.eid.anonymous.biz.AnonymousBiz;
import com.eid.anonymous.service.AnonymousHmacFacade;
import com.eid.common.enums.ErrorCode;
import com.eid.common.exception.FacadeException;
import com.eid.common.model.Response;
import com.eid.common.model.param.request.hmac.EidHmacAnonymousParam;
import com.eid.common.model.param.result.hmac.EidHmacAnonymousResult;
import com.eid.company.model.CompanyInfoDTO;
import com.eid.company.service.CompanyFacade;
import com.eid.connect.enums.InterfaceType;
import com.eid.dal.entity.CompanyAuthenticationEntity;
import com.eid.dal.manager.AuthenticationManager;
import com.eid.dal.manager.DispatchCmdManager;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class AnonymousHmacFacadeImpl implements AnonymousHmacFacade {

    @Autowired(required = false)
    private CompanyFacade companyFacade;

    @Autowired
    private AnonymousBiz anonymousBiz;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DispatchCmdManager dispatchCmdManager;

    @Override
    public Response<EidHmacAnonymousResult> authentication(EidHmacAnonymousParam eidHmacAnonymousParam) {
        log.info("call AnonymousHmacFacade.authentication request:{};", eidHmacAnonymousParam);
        Response<EidHmacAnonymousResult> response = new Response<>();
        CompanyAuthenticationEntity companyAuthenticationEntity = new CompanyAuthenticationEntity();
        try {
            // 1. 校验商户信息
            Response<CompanyInfoDTO> companyResponse = companyFacade.availableByApId(eidHmacAnonymousParam.getAppId());
            if (!companyResponse.isSuccess() || Objects.equal(companyResponse.getResult(), null))
                throw new FacadeException(companyResponse.getErrorCode(), companyResponse.getErrorMsg());

            CompanyInfoDTO companyInfoDTO = companyResponse.getResult();
            String companyId = companyInfoDTO.getCompanyId();

            // 2. 身份认证记录入库
            companyAuthenticationEntity = anonymousBiz.save(eidHmacAnonymousParam, companyId);

//            Response<Boolean> availableResponse = companyFacade.isAvailable(companyId);
//            if (!availableResponse.isSuccess() || !availableResponse.getResult())
//                throw new FacadeException(availableResponse.getErrorCode(), availableResponse.getErrorMsg());

            // 3. 请求op pki身份认证接口 并响应结果
            EidHmacAnonymousResult eidHmacAnonymousResult = (EidHmacAnonymousResult) anonymousBiz.send(eidHmacAnonymousParam);

            // 5. 计费
            dispatchCmdManager.feeCommand(companyAuthenticationEntity.getId() + "");

            response.setResult(eidHmacAnonymousResult);
        } catch (FacadeException fe) {
            log.error("Failed to AnonymousHmacFacade.authentication request:{};CAUSE:{};", eidHmacAnonymousParam, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
            // 更新身份认证记录结果
            authenticationManager.failed(companyAuthenticationEntity.getId(), fe.getCode(), fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to AnonymousHmacFacade.authentication request:{};CAUSE:{};", eidHmacAnonymousParam, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
            authenticationManager.failed(companyAuthenticationEntity.getId(), ErrorCode.SYS_ERR.getCode(), e.getMessage());
        }

        log.info("call AnonymousHmacFacade.authentication request:{};result:{};", eidHmacAnonymousParam, response);
        return response;
    }
}
