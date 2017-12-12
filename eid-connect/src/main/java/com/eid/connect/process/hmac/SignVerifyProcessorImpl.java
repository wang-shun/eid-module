package com.eid.connect.process.hmac;

import com.alibaba.fastjson.JSONObject;
import com.eid.common.enums.BizType;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.request.hmac.EidHmacVerifyParam;
import com.eid.common.model.param.result.EidBaseResult;
import com.eid.common.model.param.result.hmac.EidHmacVerifyResult;
import com.eid.common.util.BeanMapperUtil;
import com.eid.connect.annotations.InterfaceImpl;
import com.eid.connect.process.SendProcessor;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.aiav.aptoassdk.service.dataprotection.secresy.impl.SDesedeService;
import org.aiav.aptoassdk.service.dataprotection.sign.impl.SHmacSha1Service;
import org.aiav.aptoassdk.service.eidservice.biz.hmac.HmacBizService;
import org.aiav.aptoassdk.util.ServiceUtil;
import org.aiav.astoopsdk.constants.EBizType;
import org.aiav.astoopsdk.service.eidservice.params.result.biz.hmac.HmacRNVResult;
import org.springframework.stereotype.Component;

/**
 * 签名验签hmac方式
 * Created by:ruben Date:2017/2/8 Time:下午4:26
 */
@Slf4j
@Component("hmacSignVerifyProcessorImpl")
@InterfaceImpl(value = {BizType.VERIFY_HMAC})
public class SignVerifyProcessorImpl extends SendProcessor {

    @Override
    public EidBaseResult send(EidBaseParam eidBaseParam) {
        EidHmacVerifyParam eidHmacVerifyParam = (EidHmacVerifyParam) eidBaseParam;
        org.aiav.aptoassdk.service.eidservice.params.request.biz.hmac.HmacBizParams parameters = new org.aiav.aptoassdk.service.eidservice.params.request.biz.hmac.HmacBizParams();

        parameters.setAppId(eidHmacVerifyParam.getAppId());
        parameters.setAttach(eidHmacVerifyParam.getAttach());
        parameters.setEncryptFactor(ServiceUtil.genHexString(8));

        parameters.setSecurityType(org.aiav.aptoassdk.constants.ESecurityType.SKEY);
        parameters.setSignType(org.aiav.aptoassdk.constants.ESignType.HMAC_SHA1);
        parameters.setEncryptType(org.aiav.aptoassdk.constants.EEncryptType.TRIPLE_DES_ECB_PKCS5PADDING);
        parameters.setEidSignAlgorithm(org.aiav.aptoassdk.constants.EEidSignA.getEnum(eidHmacVerifyParam.getEidSignAlgorithm()));

        parameters.setDataToSign(eidHmacVerifyParam.getDataToSign());
        parameters.setEidSign(eidHmacVerifyParam.getEidSign());
        parameters.setIdcarrier(eidHmacVerifyParam.getIdCarrier());

        HmacBizService service = new HmacBizService(new SHmacSha1Service(eidHmacVerifyParam.getAppKey()), new SDesedeService(eidHmacVerifyParam.getAppKey()), "");
        String requestStr = service.doRequestSyn(parameters);
        JSONObject jsonObject = JSONObject.parseObject(requestStr);

        org.aiav.astoopsdk.service.eidservice.params.request.biz.hmac.HmacRNVParams params = new org.aiav.astoopsdk.service.eidservice.params.request.biz.hmac.HmacRNVParams();

        params.setAsid(asId);
        params.setAppid(parameters.getAppId());
        params.setReturnUrl(returnUrl);
        params.setAttach(parameters.getAttach());
        params.setBizSequenceId(jsonObject.getString("biz_sequence_id"));
        params.setBizType(EBizType.BIZ_SIGN_VERIFY_HMAC);
        params.setSecurityType(org.aiav.astoopsdk.constants.ESecurityType.getEnum(jsonObject.getString("security_type")));
        params.setSignType(org.aiav.astoopsdk.constants.ESignType.getEnum(jsonObject.getString("sign_type")));
        params.setEncryptType(org.aiav.astoopsdk.constants.EEncryptType.getEnum(jsonObject.getString("encrypt_type")));
        params.setEidSignAlgorithm(org.aiav.astoopsdk.constants.EEidSignA.getEnum(jsonObject.getString("eid_sign_algorithm")));
        params.setEncryptFactor(parameters.getEncryptFactor());

        params.setDataToSign(jsonObject.getString("data_to_sign"));
        params.setEidSign(jsonObject.getString("eid_sign"));
        params.setIdcarrier(jsonObject.getString("idcarrier"));

        EidHmacVerifyResult eidHmacVerifyResult = new EidHmacVerifyResult();
        String op = opAddress + "/hmac/biz/signverify/sync/" + params.getAsid();
        try {
            org.aiav.astoopsdk.service.eidservice.biz.hmac.HmacRNVService services = new org.aiav.astoopsdk.service.eidservice.biz.hmac.HmacRNVService(new org.aiav.astoopsdk.service.dataprotection.sign.impl.SHmacSha1Service(asKey), op); // sync request
            HmacRNVResult result = services.doRequestSyn(params);
            BeanMapperUtil.copy(result, eidHmacVerifyResult);
            if (Objects.equal("00", result.getResult())) {
                JSONObject resultJson = JSONObject.parseObject(service.doDecrypt(result.getUserInfo(), result.getEncryptFactor()));
                eidHmacVerifyResult.setResult(resultJson.getString("appeidcode"));
            }
            return eidHmacVerifyResult;
        } catch (Exception e) {
            log.info("Failed to send op! message:{}", Throwables.getStackTraceAsString(e));
            return null;
        }
    }
}
