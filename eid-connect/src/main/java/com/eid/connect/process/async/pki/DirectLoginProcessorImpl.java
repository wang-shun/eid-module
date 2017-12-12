package com.eid.connect.process.async.pki;

import com.eid.common.enums.AuthenticationStatus;
import com.eid.common.enums.BizType;
import com.eid.common.enums.ResCode;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.request.pki.EidPkiAnonymousParam;
import com.eid.common.model.param.result.EidBaseResult;
import com.eid.common.model.param.result.pki.EidPkiAnonymousResult;
import com.eid.common.util.BeanMapperUtil;
import com.eid.connect.annotations.InterfaceImpl;
import com.eid.connect.process.SendProcessor;
import lombok.extern.slf4j.Slf4j;
import org.aiav.astoopsdk.constants.*;
import org.aiav.astoopsdk.service.eidservice.params.request.biz.pki.PkiBizDirectLoginParams;
import org.springframework.stereotype.Component;

/**
 * 匿名登陆pki方式
 * Created by:ruben Date:2017/2/8 Time:下午4:26
 */
@Slf4j
@Component("pkiAsyncDirectLoginProcessorImpl")
@InterfaceImpl(value = {BizType.ANONYMOUS_PKI_ASYNC})
public class DirectLoginProcessorImpl extends SendProcessor {

    @Override
    public EidBaseResult send(EidBaseParam eidBaseParam) {
        EidPkiAnonymousParam eidPkiAnonymousParam = (EidPkiAnonymousParam) eidBaseParam;
        PkiBizDirectLoginParams parameters = new PkiBizDirectLoginParams();
        parameters.setAsid(asId);
        parameters.setAppid(eidPkiAnonymousParam.getAppId());
        parameters.setAttach(eidPkiAnonymousParam.getAttach());
        parameters.setBizSequenceId(eidPkiAnonymousParam.getBizSequenceId());
        parameters.setBizType(EBizType.getEnum(eidPkiAnonymousParam.getBizType()));
        parameters.setSecurityType(ESecurityType.getEnum(eidPkiAnonymousParam.getSecurityType()));
        parameters.setSignType(ESignType.getEnum(eidPkiAnonymousParam.getSignType()));
        parameters.setEncryptFactor(eidPkiAnonymousParam.getEncryptFactor());
        parameters.setSignFactor(eidPkiAnonymousParam.getSignFactor());
        parameters.setEncryptType(EEncryptType.getEnum(eidPkiAnonymousParam.getEncryptType()));
        parameters.setReturnUrl(returnUrl);
        parameters.setDataToSign(eidPkiAnonymousParam.getDataToSign());
        parameters.setEidSign(eidPkiAnonymousParam.getEidSign());
        parameters.setEidSignAlgorithm(EEidSignA.getEnum(eidPkiAnonymousParam.getEidSignAlgorithm()));
        parameters.setEidIssuer(eidPkiAnonymousParam.getEidIssuer());
        parameters.setEidIssuerSn(eidPkiAnonymousParam.getEidIssuerSn());
        parameters.setEidSn(eidPkiAnonymousParam.getEidSn());

        EidPkiAnonymousResult eidPkiAnonymousResult = new EidPkiAnonymousResult();
//        try {
//            PkiBizDirectLoginService service = new PkiBizDirectLoginService(new SHmacSha1Service(asKey), opAddress + parameters.getAsid()); // sync request
//            JSONObject jsonObject = service.doRequestAsyn(parameters);
//            String received = jsonObject.getString("received");
//            log.info("Call PkiBizDirectLoginService.doRequestAsync request:{};response:{};", parameters, received);
//            BeanMapperUtil.copy(parameters, eidPkiAnonymousResult);
//            eidPkiAnonymousResult.setStatus(AuthenticationStatus.PROCESSING.getCode());
//            return eidPkiAnonymousResult;
//        } catch (Exception e) {
//            log.info("Failed to send op! message:{}", Throwables.getStackTraceAsString(e));
//            throw new FacadeException(ErrorCode.SEND_ERROR);
//        }


        BeanMapperUtil.copy(eidPkiAnonymousParam, eidPkiAnonymousResult);
        eidPkiAnonymousResult.setStatus(AuthenticationStatus.PROCESSING.getCode());
        eidPkiAnonymousResult.setResultDetail(ResCode.EID_0000001.getCode());
        return eidPkiAnonymousResult;
    }
}
