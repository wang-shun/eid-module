package com.eid.verification.service.impl;

import com.eid.common.enums.ErrorCode;
import com.eid.common.exception.FacadeException;
import com.eid.common.model.Response;
import com.eid.common.model.param.request.hmac.EidHmacVerifyParam;
import com.eid.common.model.param.result.hmac.EidHmacVerifyResult;
import com.eid.company.model.CompanyInfoDTO;
import com.eid.company.service.CompanyFacade;
import com.eid.dal.entity.CompanyAuthenticationEntity;
import com.eid.dal.manager.DispatchCmdManager;
import com.eid.verification.biz.VerifyBiz;
import com.eid.verification.service.VerifyHmacFacade;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VerifyHmacFacadeImpl implements VerifyHmacFacade {

    @Autowired(required = false)
    private CompanyFacade companyFacade;

    @Autowired
    private VerifyBiz verifyBiz;

    @Autowired
    private DispatchCmdManager dispatchCmdManager;

    @Override
    public Response<EidHmacVerifyResult> authentication(EidHmacVerifyParam eidHmacVerifyParam) {
        log.info("call VerifyHmacFacade.authentication request:{};", eidHmacVerifyParam);
        Response<EidHmacVerifyResult> response = new Response<>();
        try {
            // 1. 校验商户信息
            Response<CompanyInfoDTO> companyResponse = companyFacade.availableByApId(eidHmacVerifyParam.getAppId());
//            Response<CompanyInfoDTO> companyResponse = companyFacade.get(eidHmacVerifyParam.getAppId());
            if (!companyResponse.isSuccess() || Objects.equal(companyResponse.getResult(), null))
                throw new FacadeException(companyResponse.getErrorCode(), companyResponse.getErrorMsg());

            CompanyInfoDTO companyInfoDTO = companyResponse.getResult();
            String companyId = companyInfoDTO.getCompanyId();
//            Response<Boolean> availableResponse = companyFacade.isAvailable(companyId);
//            if (!availableResponse.isSuccess() || !availableResponse.getResult())
//                throw new FacadeException(availableResponse.getErrorCode(), availableResponse.getErrorMsg());

            // 2. 身份认证记录入库
            CompanyAuthenticationEntity companyAuthenticationEntity = verifyBiz.save(eidHmacVerifyParam, companyId);

            // 3. 请求op pki身份认证接口 并响应结果
            EidHmacVerifyResult eidHmacVerifyResult = (EidHmacVerifyResult) verifyBiz.send(eidHmacVerifyParam);

            // 4. 更新身份认证记录结果
            verifyBiz.update(eidHmacVerifyResult, companyAuthenticationEntity.getId());

            // 5. 计费
            dispatchCmdManager.feeCommand(companyAuthenticationEntity.getId() + "");

            response.setResult(eidHmacVerifyResult);
        } catch (FacadeException fe) {
            log.error("Failed to VerifyHmacFacade.authentication request:{};CAUSE:{};", eidHmacVerifyParam, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to VerifyHmacFacade.authentication request:{};CAUSE:{};", eidHmacVerifyParam, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("call VerifyHmacFacade.authentication request:{};result:{};", eidHmacVerifyParam, response);
        return response;
    }
}
