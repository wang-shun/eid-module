package com.eid.anonymous.biz.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.eid.anonymous.biz.AnonymousBiz;
import com.eid.common.enums.AuthenticationStatus;
import com.eid.common.enums.ErrorCode;
import com.eid.common.enums.ResCode;
import com.eid.common.exception.FacadeException;
import com.eid.common.model.Response;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.result.EidBaseResult;
import com.eid.common.util.BeanMapperUtil;
import com.eid.connect.service.SendFacade;
import com.eid.dal.dao.CompanyAuthenticationDao;
import com.eid.dal.entity.CompanyAuthenticationEntity;
import com.google.common.base.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Component
public class AnonymousBizImpl implements AnonymousBiz {

    @Autowired
    private CompanyAuthenticationDao companyAuthenticationDao;

    @Autowired(required = false)
    private SendFacade sendFacade;

    @Override
    public CompanyAuthenticationEntity save(EidBaseParam eidBaseParam, String companyId) {
        CompanyAuthenticationEntity authenticationEntity = new CompanyAuthenticationEntity();
        BeanMapperUtil.copy(eidBaseParam, authenticationEntity);
        authenticationEntity.setBizTime(eidBaseParam.getBizTime());
        authenticationEntity.setCompanyId(companyId);
        authenticationEntity.setCreatedAt(new Date());
        return companyAuthenticationDao.save(authenticationEntity);
    }

    @Override
    public EidBaseResult send(EidBaseParam eidBaseParam) {
        Response<EidBaseResult> resultResponse = sendFacade.request(eidBaseParam);
        log.info("call sendFacade.request response:{};", resultResponse);
        if (!resultResponse.isSuccess() || Objects.equal(resultResponse.getResult(), null))
            throw new FacadeException(resultResponse.getErrorCode(), resultResponse.getErrorMsg());

        EidBaseResult eidBaseResult = resultResponse.getResult();
        AuthenticationStatus authenticationStatus = AuthenticationStatus.FAILED;
        if (Objects.equal(eidBaseResult.getResultDetail(), ResCode.EID_0000000.getCode()))
            authenticationStatus = AuthenticationStatus.SUCCESS;
        eidBaseResult.setStatus(authenticationStatus.getCode());

        return eidBaseResult;
    }

    @Override
    public Boolean update(EidBaseResult eidBaseResult, Long id) {
        Integer resultRow = companyAuthenticationDao.updateRes(eidBaseResult.getResultDetail(), eidBaseResult.getResult(), new Date(), eidBaseResult.getResult(), null, eidBaseResult.getStatus(), new Date(), id);
        if (resultRow != 1)
            throw new FacadeException(ErrorCode.DATA_ERR);

        return true;
    }
}
