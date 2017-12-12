package com.eid.connect.process.async.hmac;

import com.eid.common.enums.AuthenticationStatus;
import com.eid.common.enums.BizType;
import com.eid.common.enums.ResCode;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.request.hmac.EidHmacAnonymousParam;
import com.eid.common.model.param.result.EidBaseResult;
import com.eid.common.model.param.result.hmac.EidHmacAnonymousResult;
import com.eid.common.util.BeanMapperUtil;
import com.eid.connect.annotations.InterfaceImpl;
import com.eid.connect.process.SendProcessor;
import lombok.extern.slf4j.Slf4j;
import org.aiav.astoopsdk.constants.*;
import org.aiav.astoopsdk.service.eidservice.params.request.biz.hmac.HmacBizDirectLoginParams;
import org.springframework.stereotype.Component;

/**
 * 匿名登陆hmac方式
 * Created by:ruben Date:2017/2/8 Time:下午4:26
 */
@Slf4j
@Component("hmacAsyncDirectLoginProcessorImpl")
@InterfaceImpl(value = {BizType.ANONYMOUS_HMAC_ASYNC})
public class DirectLoginProcessorImpl extends SendProcessor {

    @Override
    public EidBaseResult send(EidBaseParam eidBaseParam) {
        EidHmacAnonymousParam eidHmacAnonymousParam = (EidHmacAnonymousParam) eidBaseParam;
        HmacBizDirectLoginParams parameters = new HmacBizDirectLoginParams();
        parameters.setAsid(asId);
        parameters.setAppid(eidHmacAnonymousParam.getAppId());
        parameters.setAttach(eidHmacAnonymousParam.getAttach());
        parameters.setBizSequenceId(eidHmacAnonymousParam.getBizSequenceId());
        parameters.setBizType(EBizType.getEnum(eidHmacAnonymousParam.getBizType()));
        parameters.setSecurityType(ESecurityType.getEnum(eidHmacAnonymousParam.getSecurityType()));
        parameters.setSignType(ESignType.getEnum(eidHmacAnonymousParam.getSignType()));
        parameters.setEncryptFactor(eidHmacAnonymousParam.getEncryptFactor());
        parameters.setSignFactor(eidHmacAnonymousParam.getSignFactor());
        parameters.setEncryptType(EEncryptType.getEnum(eidHmacAnonymousParam.getEncryptType()));
        parameters.setReturnUrl(returnUrl);
        parameters.setDataToSign(eidHmacAnonymousParam.getDataToSign());
        parameters.setEidSign(eidHmacAnonymousParam.getEidSign());
        parameters.setEidSignAlgorithm(EEidSignA.getEnum(eidHmacAnonymousParam.getEidSignAlgorithm()));
        parameters.setIdcarrier(eidHmacAnonymousParam.getIdCarrier());

        EidHmacAnonymousResult eidHmacAnonymousResult = new EidHmacAnonymousResult();
//        try {
//            HmacBizDirectLoginService service = new HmacBizDirectLoginService(new SHmacSha1Service(asKey), opAddress + parameters.getAsid()); // sync request
//            JSONObject jsonObject = service.doRequestAsyn(parameters);
//            String received = jsonObject.getString("received");
//            log.info("Call HmacBizDirectLoginService.doRequestAsync request:{};response:{};", parameters, received);
//            BeanMapperUtil.copy(parameters, eidHmacAnonymousResult);
//            eidHmacAnonymousResult.setStatus(AuthenticationStatus.PROCESSING.getCode());
//            return eidHmacAnonymousResult;
//        } catch (Exception e) {
//            log.info("Failed to send op! message:{}", Throwables.getStackTraceAsString(e));
//            throw new FacadeException(ErrorCode.SEND_ERROR);
//        }

        BeanMapperUtil.copy(eidHmacAnonymousParam, eidHmacAnonymousResult);
        eidHmacAnonymousResult.setStatus(AuthenticationStatus.PROCESSING.getCode());
        eidHmacAnonymousResult.setResultDetail(ResCode.EID_0000001.getCode());
        return eidHmacAnonymousResult;
    }
}
