package com.eid.connect.process.hmac;

import com.alibaba.fastjson.JSONObject;
import com.eid.common.enums.BizType;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.request.hmac.EidHmacAnonymousParam;
import com.eid.common.model.param.result.EidBaseResult;
import com.eid.common.model.param.result.hmac.EidHmacAnonymousResult;
import com.eid.common.util.BeanMapperUtil;
import com.eid.connect.annotations.InterfaceImpl;
import com.eid.connect.process.SendProcessor;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.aiav.aptoassdk.service.dataprotection.secresy.impl.SDesedeService;
import org.aiav.aptoassdk.service.dataprotection.sign.impl.SHmacSha1Service;
import org.aiav.aptoassdk.service.eidservice.biz.hmac.HmacBizDirectLoginService;
import org.aiav.aptoassdk.util.ServiceUtil;
import org.aiav.astoopsdk.constants.EBizType;
import org.aiav.crypto.util.CryptoFuncUtil;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

/**
 * 匿名登陆hmac方式
 * Created by:ruben Date:2017/2/8 Time:下午4:26
 */
@Slf4j
@Component("hmacDirectLoginProcessorImpl")
@InterfaceImpl(value = {BizType.ANONYMOUS_HMAC})
public class DirectLoginProcessorImpl extends SendProcessor {

    @Override
    public EidBaseResult send(EidBaseParam eidBaseParam) {
        EidHmacAnonymousParam eidHmacAnonymousParam = (EidHmacAnonymousParam) eidBaseParam;
        log.info("eidHmacAnonymousParam:{};", eidHmacAnonymousParam);
        // hmac实名认证
        org.aiav.aptoassdk.service.eidservice.params.request.biz.hmac.HmacBizDirectLoginParams parameters = new org.aiav.aptoassdk.service.eidservice.params.request.biz.hmac.HmacBizDirectLoginParams();
        parameters.setAppId(eidHmacAnonymousParam.getAppId());
        parameters.setAttach(eidHmacAnonymousParam.getAttach());
        parameters.setEncryptFactor(ServiceUtil.genHexString(8));

        parameters.setSecurityType(org.aiav.aptoassdk.constants.ESecurityType.SKEY);
        parameters.setSignType(org.aiav.aptoassdk.constants.ESignType.HMAC_SHA1);
        parameters.setEncryptType(org.aiav.aptoassdk.constants.EEncryptType.TRIPLE_DES_ECB_PKCS5PADDING);
        parameters.setEidSignAlgorithm(org.aiav.aptoassdk.constants.EEidSignA.getEnum(eidHmacAnonymousParam.getEidSignAlgorithm()));

        parameters.setDataToSign(eidHmacAnonymousParam.getDataToSign());
        parameters.setEidSign(eidHmacAnonymousParam.getEidSign());
        parameters.setIdcarrier(eidHmacAnonymousParam.getIdCarrier());

        HmacBizDirectLoginService service = new HmacBizDirectLoginService(new SHmacSha1Service(eidHmacAnonymousParam.getAppKey()), new SDesedeService(eidHmacAnonymousParam.getAppKey()), "");
        String requestStr = service.doRequestSyn(parameters);
        JSONObject jsonObject = JSONObject.parseObject(requestStr);
        log.info("ap json result:{};", jsonObject.toJSONString());

        org.aiav.astoopsdk.service.eidservice.params.request.biz.hmac.HmacBizDirectLoginParams params = new org.aiav.astoopsdk.service.eidservice.params.request.biz.hmac.HmacBizDirectLoginParams();
        params.setAsid(asId);
        params.setAppid(parameters.getAppId());
        params.setReturnUrl(returnUrl);
        params.setAttach(parameters.getAttach());
        params.setBizSequenceId(jsonObject.getString("biz_sequence_id"));
        params.setBizType(EBizType.BIZ_DIRECT_LOGIN_HMAC);
        params.setSecurityType(org.aiav.astoopsdk.constants.ESecurityType.getEnum(jsonObject.getString("security_type")));
        params.setSignType(org.aiav.astoopsdk.constants.ESignType.getEnum(jsonObject.getString("sign_type")));
        params.setEncryptType(org.aiav.astoopsdk.constants.EEncryptType.getEnum(jsonObject.getString("encrypt_type")));
        params.setEidSignAlgorithm(org.aiav.astoopsdk.constants.EEidSignA.getEnum(jsonObject.getString("eid_sign_algorithm")));
        params.setEncryptFactor(parameters.getEncryptFactor());

        params.setDataToSign(jsonObject.getString("data_to_sign"));
        params.setEidSign(jsonObject.getString("eid_sign"));
        params.setIdcarrier(jsonObject.getString("idcarrier"));

        EidHmacAnonymousResult eidHmacAnonymousResult = new EidHmacAnonymousResult();
        String op = opAddress + "/hmac/biz/directlogin/sync/" + params.getAsid();
        try {
            org.aiav.astoopsdk.service.eidservice.biz.hmac.HmacBizDirectLoginService services = new org.aiav.astoopsdk.service.eidservice.biz.hmac.HmacBizDirectLoginService(new org.aiav.astoopsdk.service.dataprotection.sign.impl.SHmacSha1Service(asKey), op); // sync request
            org.aiav.astoopsdk.service.eidservice.params.result.biz.hmac.HmacBizDirectLoginResult result = services.doRequestSyn(params);
            BeanMapperUtil.copy(result, eidHmacAnonymousResult);
            if (Objects.equal("00", result.getResult())) {
                JSONObject resultJson = JSONObject.parseObject(service.doDecrypt(result.getUserInfo(), result.getEncryptFactor()));
                eidHmacAnonymousResult.setResult(resultJson.getString("appeidcode"));
            }
            return eidHmacAnonymousResult;

        } catch (Exception e) {
            log.info("Failed to send op! message:{}", Throwables.getStackTraceAsString(e));
            return null;
        }
    }
}
