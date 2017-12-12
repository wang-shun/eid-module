package com.eid.connect.process.management;

import com.eid.common.enums.BizType;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.request.management.EidAppRegUpdateParam;
import com.eid.common.model.param.result.EidBaseResult;
import com.eid.common.model.param.result.management.EidAppRegUpdateResult;
import com.eid.common.util.BeanMapperUtil;
import com.eid.connect.annotations.InterfaceImpl;
import com.eid.connect.process.SendProcessor;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.aiav.astoopsdk.constants.*;
import org.aiav.astoopsdk.service.dataprotection.sign.impl.SHmacSha1Service;
import org.aiav.astoopsdk.service.eidservice.manage.AppRegUpdateService;
import org.aiav.astoopsdk.service.eidservice.params.request.manage.AppRegUpdateParams;
import org.aiav.astoopsdk.service.eidservice.params.result.manage.AppRegUpdateResult;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 应用信息更换
 * Created by:ruben Date:2017/2/8 Time:下午4:26
 */
@Slf4j
@Component("appRegUpdateProcessorImpl")
@InterfaceImpl(value = {BizType.APP_REG_UPDATE})
public class AppRegUpdateProcessorImpl extends SendProcessor {

    @Override
    public EidBaseResult send(EidBaseParam eidBaseParam) {
        EidAppRegUpdateParam eidAppRegUpdateParam = (EidAppRegUpdateParam) eidBaseParam;
        AppRegUpdateParams params = new AppRegUpdateParams();
        params.setReturnUrl(eidAppRegUpdateParam.getReturnUrl());
        params.setBizSequenceId(eidAppRegUpdateParam.getBizSequenceId());
        params.setEncryptType(EEncryptType.getEnum(eidAppRegUpdateParam.getEncryptType()));
        params.setSignType(ESignType.getEnum(eidAppRegUpdateParam.getSignType()));
        params.setSecurityType(ESecurityType.getEnum(eidAppRegUpdateParam.getSecurityType()));
        params.setAsid(asId);
        params.setAttach(eidAppRegUpdateParam.getAttach());
        params.setAppid(eidAppRegUpdateParam.getAppid());
        params.setAppInfo(eidAppRegUpdateParam.getAppInfo());
        params.setAppName(eidAppRegUpdateParam.getAppName());
        params.setCmpName(eidAppRegUpdateParam.getCmpName());
        params.setDomainName(eidAppRegUpdateParam.getDomainName());
        params.setIpAddr(eidAppRegUpdateParam.getIpAddr());
        params.setDefaultSecurityType(ESecurityType.getEnum(eidAppRegUpdateParam.getDefaultSecurityType()));
        params.setAppIcon(eidAppRegUpdateParam.getAppIcon());
        Map<EBizType, String> bizsMap = Maps.newHashMap();
        bizsMap.put(EBizType.BIZ_SIGN_VERIFY_HMAC, eidAppRegUpdateParam.getBizs().get(EBizType.BIZ_SIGN_VERIFY_HMAC.getIndex()));
        bizsMap.put(EBizType.BIZ_SIGN_VERIFY_PKI, eidAppRegUpdateParam.getBizs().get(EBizType.BIZ_SIGN_VERIFY_PKI.getIndex()));
        bizsMap.put(EBizType.REAL_NAME_SIGN_VERIFY_HMAC, eidAppRegUpdateParam.getBizs().get(EBizType.REAL_NAME_SIGN_VERIFY_HMAC.getIndex()));
        bizsMap.put(EBizType.REAL_NAME_SIGN_VERIFY_PKI, eidAppRegUpdateParam.getBizs().get(EBizType.REAL_NAME_SIGN_VERIFY_PKI.getIndex()));
        bizsMap.put(EBizType.BIZ_DIRECT_LOGIN_PKI, eidAppRegUpdateParam.getBizs().get(EBizType.BIZ_DIRECT_LOGIN_PKI.getIndex()));
        bizsMap.put(EBizType.BIZ_DIRECT_LOGIN_HMAC, eidAppRegUpdateParam.getBizs().get(EBizType.BIZ_DIRECT_LOGIN_HMAC.getIndex()));
        params.setBizs(bizsMap);
        params.setProvince(eidAppRegUpdateParam.getProvince());
        params.setCity(eidAppRegUpdateParam.getCity());
        params.setOrgType(EOrgType.getEnum(eidAppRegUpdateParam.getOrgType()));
        params.setContact1(eidAppRegUpdateParam.getContact1());
        params.setContact1Tel(eidAppRegUpdateParam.getContact1Tel());
        params.setContact1Email(eidAppRegUpdateParam.getContact1Email());
        params.setContact2(eidAppRegUpdateParam.getContact2());
        params.setContact2Tel(eidAppRegUpdateParam.getContact2Tel());
        params.setContact2Email(eidAppRegUpdateParam.getContact2Email());
        params.setRemark(eidAppRegUpdateParam.getRemark());
        params.setAppSalt(eidAppRegUpdateParam.getAppSalt());

        AppRegUpdateService service = new AppRegUpdateService(new SHmacSha1Service(asKey), opAddress + params.getAsid());
        AppRegUpdateResult arur = service.doRequestSyn(params);
        EidAppRegUpdateResult eidAppRegUpdateResult = new EidAppRegUpdateResult();
        BeanMapperUtil.copy(arur, eidAppRegUpdateResult);
        return eidAppRegUpdateResult;
    }
}
