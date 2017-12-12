package com.eid.identity.service.impl;

import com.eid.common.enums.ErrorCode;
import com.eid.common.exception.FacadeException;
import com.eid.common.model.Response;
import com.eid.common.model.param.request.pki.EidPkiRealNameParam;
import com.eid.common.model.param.result.pki.EidPkiRealNameResult;
import com.eid.company.model.CompanyInfoDTO;
import com.eid.company.service.CompanyFacade;
import com.eid.connect.enums.InterfaceType;
import com.eid.dal.entity.CompanyAuthenticationEntity;
import com.eid.dal.manager.DispatchCmdManager;
import com.eid.identity.biz.IdentityBiz;
import com.eid.identity.service.IdentityPkiFacade;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class IdentityPkiFacadeImpl implements IdentityPkiFacade {

    @Autowired(required = false)
    private CompanyFacade companyFacade;

    @Autowired
    private IdentityBiz identityBiz;

    @Autowired
    private DispatchCmdManager dispatchCmdManager;


    @Override
    public Response<EidPkiRealNameResult> authentication(EidPkiRealNameParam eidPkiRealNameParam) {
        log.info("call IdentityPkiFacade.authentication request:{};", eidPkiRealNameParam);
        Response<EidPkiRealNameResult> response = new Response<>();
        try {
            // 1. 校验商户信息
            Response<CompanyInfoDTO> companyResponse = companyFacade.availableByApId(eidPkiRealNameParam.getAppId());
            if (!companyResponse.isSuccess() || Objects.equal(companyResponse.getResult(), null))
                throw new FacadeException(companyResponse.getErrorCode(), companyResponse.getErrorMsg());

            CompanyInfoDTO companyInfoDTO = companyResponse.getResult();
            String companyId = companyInfoDTO.getCompanyId();
//            Response<Boolean> availableResponse = companyFacade.isAvailable(companyId);
//            if (!availableResponse.isSuccess() || !availableResponse.getResult())
//                throw new FacadeException(availableResponse.getErrorCode(), availableResponse.getErrorMsg());

            // 2. 身份认证记录入库
            CompanyAuthenticationEntity companyAuthenticationEntity = identityBiz.save(eidPkiRealNameParam, companyId);

            // 3. 请求op pki身份认证接口 并响应结果
            EidPkiRealNameResult eidPkiRealNameResult = (EidPkiRealNameResult) identityBiz.send(eidPkiRealNameParam);

            // 4. 更新身份认证记录结果
            identityBiz.update(eidPkiRealNameResult, companyAuthenticationEntity.getId());

            // 5. 计费
            dispatchCmdManager.feeCommand(companyAuthenticationEntity.getId() + "");

            response.setResult(eidPkiRealNameResult);
        } catch (FacadeException fe) {
            log.error("Failed to IdentityPkiFacade.authentication request:{};CAUSE:{};", eidPkiRealNameParam, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to IdentityPkiFacade.authentication request:{};CAUSE:{};", eidPkiRealNameParam, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("call IdentityPkiFacade.authentication request:{};result:{};", eidPkiRealNameParam, response);
        return response;
    }
}
