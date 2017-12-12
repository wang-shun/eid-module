package com.eid.anonymous.service.impl;

import com.eid.anonymous.biz.AnonymousBiz;
import com.eid.anonymous.service.AnonymousPkiFacade;
import com.eid.common.enums.ErrorCode;
import com.eid.common.exception.FacadeException;
import com.eid.common.model.Response;
import com.eid.common.model.param.request.pki.EidPkiAnonymousParam;
import com.eid.common.model.param.result.pki.EidPkiAnonymousResult;
import com.eid.company.model.CompanyInfoDTO;
import com.eid.company.service.CompanyFacade;
import com.eid.connect.enums.InterfaceType;
import com.eid.dal.entity.CompanyAuthenticationEntity;
import com.eid.dal.manager.DispatchCmdManager;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class AnonymousPkiFacadeImpl implements AnonymousPkiFacade {

    @Autowired(required = false)
    private CompanyFacade companyFacade;

    @Autowired
    private AnonymousBiz anonymousBiz;

    @Autowired
    private DispatchCmdManager dispatchCmdManager;

    public Response<EidPkiAnonymousResult> authentication(EidPkiAnonymousParam eidPkiAnonymousParam) {
        log.info("call AnonymousPkiFacade.authentication request:{};", eidPkiAnonymousParam);
        Response<EidPkiAnonymousResult> response = new Response<>();
        try {
            // 1. 校验商户信息
            Response<CompanyInfoDTO> companyResponse = companyFacade.availableByApId(eidPkiAnonymousParam.getAppId());
            if (!companyResponse.isSuccess() || Objects.equal(companyResponse.getResult(), null))
                throw new FacadeException(companyResponse.getErrorCode(), companyResponse.getErrorMsg());

            CompanyInfoDTO companyInfoDTO = companyResponse.getResult();
            String companyId = companyInfoDTO.getCompanyId();
//            Response<Boolean> availableResponse = companyFacade.isAvailable(companyId);
//            if (!availableResponse.isSuccess() || !availableResponse.getResult())
//                throw new FacadeException(availableResponse.getErrorCode(), availableResponse.getErrorMsg());

            // 2. 身份认证记录入库
            CompanyAuthenticationEntity companyAuthenticationEntity = anonymousBiz.save(eidPkiAnonymousParam, companyId);

            // 3. 请求op pki身份认证接口 并响应结果
            EidPkiAnonymousResult eidPkiAnonymousResult = (EidPkiAnonymousResult) anonymousBiz.send(eidPkiAnonymousParam);

            // 4. 更新身份认证记录结果
            anonymousBiz.update(eidPkiAnonymousResult, companyAuthenticationEntity.getId());

            // 5. 计费
            dispatchCmdManager.feeCommand(companyAuthenticationEntity.getId() + "");

            response.setResult(eidPkiAnonymousResult);
        } catch (FacadeException fe) {
            log.error("Failed to AnonymousPkiFacade.authentication request:{};CAUSE:{};", eidPkiAnonymousParam, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to AnonymousPkiFacade.authentication request:{};CAUSE:{};", eidPkiAnonymousParam, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("call AnonymousPkiFacade.authentication request:{};result:{};", eidPkiAnonymousParam, response);
        return response;
    }
}
