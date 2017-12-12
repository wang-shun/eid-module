package com.eid.dispatch.biz.impl;

import com.alibaba.fastjson.JSON;
import com.eid.common.enums.ErrorCode;
import com.eid.common.exception.FacadeException;
import com.eid.common.model.Response;
import com.eid.common.util.BeanMapperUtil;
import com.eid.company.model.CompanyInfoDTO;
import com.eid.company.service.CompanyFacade;
import com.eid.dal.entity.CompanyAuthenticationEntity;
import com.eid.dispatch.biz.NotifyBiz;
import com.eid.dispatch.model.AuthenticationNotifyDTO;
import com.eid.dispatch.util.AES;
import com.eid.dispatch.util.SignUtil;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/22 Time:上午11:38
 */
@Slf4j
@Component
public class NotifyBizImpl implements NotifyBiz {

    @Autowired(required = false)
    private CompanyFacade companyFacade;

    @Override
    public void notify(CompanyAuthenticationEntity companyAuthenticationEntity) throws Exception {
        String returnUrl = companyAuthenticationEntity.getReturnUrl();
        if (Strings.isNullOrEmpty(returnUrl))
            return;

        AuthenticationNotifyDTO authenticationNotifyDTO = new AuthenticationNotifyDTO();
        BeanMapperUtil.copy(companyAuthenticationEntity, authenticationNotifyDTO);

        Response<CompanyInfoDTO> apResponse = companyFacade.availableByApId(companyAuthenticationEntity.getApId());
        log.info("Call companyFacade.availableByApId request:{};response:{};", companyAuthenticationEntity.getApId(), apResponse);
        if (!apResponse.isSuccess() || Objects.equal(apResponse.getResult(), null))
            return;

        String apKey = apResponse.getResult().getApKey();
        authenticationNotifyDTO.setSignType("SHA");
        authenticationNotifyDTO.setEncryptType("AES");

        if (!Strings.isNullOrEmpty(authenticationNotifyDTO.getAppEidCode()))
            authenticationNotifyDTO.setAppEidCode(AES.encrypt(authenticationNotifyDTO.getAppEidCode(), apKey));

        Map<String, String> requestParam = BeanMapperUtil.objConvert(authenticationNotifyDTO, HashMap.class);
        authenticationNotifyDTO.setSign(SignUtil.generateSign(requestParam, apKey));
        String requestData = JSON.toJSONString(authenticationNotifyDTO);
        HttpRequest httpRequest = HttpRequest.post(returnUrl, true, "requestData", requestData).connectTimeout(5000).readTimeout(10000);
        log.info("Call HttpRequest.post request:{};", requestData);
        int responseCode = httpRequest.code();
        log.info("Call httpRequest.code responseCode:{};", responseCode);
        if (responseCode != 200)
            throw new FacadeException(ErrorCode.SEND_ERROR);

        String response = httpRequest.body();
        log.info("Call httpRequest.body response:{};", response);
        if (Strings.isNullOrEmpty(response) || Objects.equal(response, "SUCCESS"))
            throw new FacadeException(ErrorCode.SEND_ERROR);
    }

}
