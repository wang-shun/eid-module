package com.eid.connect.process.async.hmac;

import com.eid.common.enums.AuthenticationStatus;
import com.eid.common.enums.BizType;
import com.eid.common.enums.ResCode;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.request.hmac.EidHmacRealNameParam;
import com.eid.common.model.param.result.EidBaseResult;
import com.eid.common.model.param.result.hmac.EidHmacRealNameResult;
import com.eid.common.util.BeanMapperUtil;
import com.eid.connect.annotations.InterfaceImpl;
import com.eid.connect.process.SendProcessor;
import lombok.extern.slf4j.Slf4j;
import org.aiav.astoopsdk.constants.*;
import org.aiav.astoopsdk.service.eidservice.params.request.biz.hmac.HmacRNVParams;
import org.springframework.stereotype.Component;

/**
 * 身份识别hmac方式
 * Created by:ruben Date:2017/2/8 Time:下午4:26
 */
@Slf4j
@Component("hmacAsyncRealnameProcessorImpl")
@InterfaceImpl(value = {BizType.IDENTITY_HMAC_ASYNC})
public class RealnameProcessorImpl extends SendProcessor {

    @Override
    public EidBaseResult send(EidBaseParam eidBaseParam) {
        EidHmacRealNameParam eidHmacRealNameParam = (EidHmacRealNameParam) eidBaseParam;
        HmacRNVParams parameters = new HmacRNVParams();
        parameters.setAsid(asId);
        parameters.setAppid(eidHmacRealNameParam.getAppId());
        parameters.setAttach(eidHmacRealNameParam.getAttach());
        parameters.setBizSequenceId(eidHmacRealNameParam.getBizSequenceId());
        parameters.setBizType(EBizType.getEnum(eidHmacRealNameParam.getBizType()));
        parameters.setSecurityType(ESecurityType.getEnum(eidHmacRealNameParam.getSecurityType()));
        parameters.setSignType(ESignType.getEnum(eidHmacRealNameParam.getSignType()));
        parameters.setEncryptFactor(eidHmacRealNameParam.getEncryptFactor());
        parameters.setSignFactor(eidHmacRealNameParam.getSignFactor());
        parameters.setEncryptType(EEncryptType.getEnum(eidHmacRealNameParam.getEncryptType()));
        parameters.setReturnUrl(returnUrl);
        parameters.setDataToSign(eidHmacRealNameParam.getDataToSign());
        parameters.setUserIdInfo(eidHmacRealNameParam.getUserIdInfo());
        parameters.setEidSign(eidHmacRealNameParam.getEidSign());
        parameters.setEidSignAlgorithm(EEidSignA.getEnum(eidHmacRealNameParam.getEidSignAlgorithm()));
        parameters.setIdcarrier(eidHmacRealNameParam.getIdCarrier());


        EidHmacRealNameResult eidHmacRealNameResult = new EidHmacRealNameResult();
//        try {
//            HmacRNVService service = new HmacRNVService(new SHmacSha1Service(asKey), opAddress + parameters.getAsid()); // sync request
//            JSONObject jsonObject = service.doRequestAsyn(parameters);
//            String received = jsonObject.getString("received");
//            log.info("Call HmacRNVService.doRequestAsync request:{};response:{};", parameters, received);
//            BeanMapperUtil.copy(parameters, eidHmacRealNameResult);
//            eidHmacRealNameResult.setStatus(AuthenticationStatus.PROCESSING.getCode());
//            return eidHmacRealNameResult;
//        } catch (Exception e) {
//            log.info("Failed to send op! message:{}", Throwables.getStackTraceAsString(e));
//            throw new FacadeException(ErrorCode.SEND_ERROR);
//        }


        BeanMapperUtil.copy(eidHmacRealNameParam, eidHmacRealNameResult);
        eidHmacRealNameResult.setStatus(AuthenticationStatus.PROCESSING.getCode());
        eidHmacRealNameResult.setResultDetail(ResCode.EID_0000001.getCode());
        return eidHmacRealNameResult;
    }
}
