package com.eid.verification.service.impl;

import com.eid.common.enums.ErrorCode;
import com.eid.common.exception.FacadeException;
import com.eid.common.model.Response;
import com.eid.common.model.param.request.pki.EidPkiVerifyParam;
import com.eid.common.model.param.result.pki.EidPkiVerifyResult;
import com.eid.company.model.CompanyInfoDTO;
import com.eid.company.service.CompanyFacade;
import com.eid.dal.entity.CompanyAuthenticationEntity;
import com.eid.dal.manager.DispatchCmdManager;
import com.eid.verification.biz.VerifyBiz;
import com.eid.verification.service.VerifyPkiFacade;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/2/22 Time:下午3:17
 */
@Slf4j
@Service
public class VerifyPkiFacadeImpl implements VerifyPkiFacade {

    @Autowired(required = false)
    private CompanyFacade companyFacade;

    @Autowired
    private VerifyBiz verifyBiz;

    @Autowired
    private DispatchCmdManager dispatchCmdManager;

    @Override
    public Response<EidPkiVerifyResult> authentication(EidPkiVerifyParam eidPkiVerifyParam) {
        log.info("call VerifyPkiFacade.authentication request:{};", eidPkiVerifyParam);
        Response<EidPkiVerifyResult> response = new Response<>();
        try {
            // 1. 校验商户信息
            Response<CompanyInfoDTO> companyResponse = companyFacade.availableByApId(eidPkiVerifyParam.getAppId());
            if (!companyResponse.isSuccess() || Objects.equal(companyResponse.getResult(), null))
                throw new FacadeException(companyResponse.getErrorCode(), companyResponse.getErrorMsg());

            CompanyInfoDTO companyInfoDTO = companyResponse.getResult();
            String companyId = companyInfoDTO.getCompanyId();
//            Response<Boolean> availableResponse = companyFacade.isAvailable(companyId);
//            if (!availableResponse.isSuccess() || !availableResponse.getResult())
//                throw new FacadeException(availableResponse.getErrorCode(), availableResponse.getErrorMsg());

            // 2. 身份认证记录入库
            CompanyAuthenticationEntity companyAuthenticationEntity = verifyBiz.save(eidPkiVerifyParam, companyId);

            // 3. 请求op pki身份认证接口 并响应结果
            EidPkiVerifyResult eidPkiVerifyResult = (EidPkiVerifyResult) verifyBiz.send(eidPkiVerifyParam);

            // 4. 更新身份认证记录结果
            verifyBiz.update(eidPkiVerifyResult, companyAuthenticationEntity.getId());

            // 5. 计费
            dispatchCmdManager.feeCommand(companyAuthenticationEntity.getId() + "");

            response.setResult(eidPkiVerifyResult);
        } catch (FacadeException fe) {
            log.error("Failed to VerifyPkiFacade.authentication request:{};CAUSE:{};", eidPkiVerifyParam, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to VerifyPkiFacade.authentication request:{};CAUSE:{};", eidPkiVerifyParam, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("call VerifyPkiFacade.authentication request:{};result:{};", eidPkiVerifyParam, response);
        return response;
    }

}
