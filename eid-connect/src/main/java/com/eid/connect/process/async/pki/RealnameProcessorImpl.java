package com.eid.connect.process.async.pki;

import com.eid.common.enums.AuthenticationStatus;
import com.eid.common.enums.BizType;
import com.eid.common.enums.ResCode;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.request.pki.EidPkiRealNameParam;
import com.eid.common.model.param.result.EidBaseResult;
import com.eid.common.model.param.result.pki.EidPkiRealNameResult;
import com.eid.common.util.BeanMapperUtil;
import com.eid.connect.annotations.InterfaceImpl;
import com.eid.connect.process.SendProcessor;
import lombok.extern.slf4j.Slf4j;
import org.aiav.astoopsdk.constants.*;
import org.aiav.astoopsdk.service.eidservice.params.request.biz.pki.PkiRNVParams;
import org.springframework.stereotype.Component;

/**
 * 身份识别pki方式
 * Created by:ruben Date:2017/2/8 Time:下午4:26
 */
@Slf4j
@Component("pkiAsyncRealnameProcessorImpl")
@InterfaceImpl(value = {BizType.IDENTITY_PKI_ASYNC})
public class RealnameProcessorImpl extends SendProcessor {

    @Override
    public EidBaseResult send(EidBaseParam eidBaseParam) {
        EidPkiRealNameParam eidPkiRealNameParam = (EidPkiRealNameParam) eidBaseParam;

        PkiRNVParams parameters = new PkiRNVParams();
        parameters.setAsid(asId);
        parameters.setAppid(eidPkiRealNameParam.getAppId());
        parameters.setAttach(eidPkiRealNameParam.getAttach());
        parameters.setBizSequenceId(eidPkiRealNameParam.getBizSequenceId());
        parameters.setBizType(EBizType.getEnum(eidPkiRealNameParam.getBizType()));
        parameters.setSecurityType(ESecurityType.getEnum(eidPkiRealNameParam.getSecurityType()));
        parameters.setSignType(ESignType.getEnum(eidPkiRealNameParam.getSignType()));
        parameters.setEncryptFactor(eidPkiRealNameParam.getEncryptFactor());
        parameters.setSignFactor(eidPkiRealNameParam.getSignFactor());
        parameters.setEncryptType(EEncryptType.getEnum(eidPkiRealNameParam.getEncryptType()));
        parameters.setReturnUrl(returnUrl);
        parameters.setDataToSign(eidPkiRealNameParam.getDataToSign());
        parameters.setUserIdInfo(eidPkiRealNameParam.getUserIdInfo());
        parameters.setEidSign(eidPkiRealNameParam.getEidSign());
        parameters.setEidSignAlgorithm(EEidSignA.getEnum(eidPkiRealNameParam.getEidSignAlgorithm()));
        parameters.setEidIssuer(eidPkiRealNameParam.getEidIssuer());
        parameters.setEidIssuerSn(eidPkiRealNameParam.getEidIssuerSn());
        parameters.setEidSn(eidPkiRealNameParam.getEidSn());

        EidPkiRealNameResult eidPkiRealNameResult = new EidPkiRealNameResult();
//        try {
//            PkiRNVService service = new PkiRNVService(new SHmacSm3Service(asKey), opAddress + parameters.getAsid()); // sync request
//            JSONObject jsonObject = service.doRequestAsyn(parameters);
//            String received = jsonObject.getString("received");
//            log.info("Call PkiRNVService.doRequestAsync request:{};response:{};", parameters, received);
//            BeanMapperUtil.copy(parameters, eidPkiRealNameResult);
//            eidPkiRealNameResult.setStatus(AuthenticationStatus.PROCESSING.getCode());
//            return eidPkiRealNameResult;
//        } catch (Exception e) {
//            log.info("Failed to send op! message:{}", Throwables.getStackTraceAsString(e));
//            throw new FacadeException(ErrorCode.SEND_ERROR);
//        }


        BeanMapperUtil.copy(eidPkiRealNameParam, eidPkiRealNameResult);
        eidPkiRealNameResult.setStatus(AuthenticationStatus.PROCESSING.getCode());
        eidPkiRealNameResult.setResultDetail(ResCode.EID_0000001.getCode());
        return eidPkiRealNameResult;
    }
}
