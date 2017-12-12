package com.eid.connect.process.pki;

import com.alibaba.fastjson.JSONObject;
import com.eid.common.enums.BizType;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.request.pki.EidPkiVerifyParam;
import com.eid.common.model.param.result.EidBaseResult;
import com.eid.common.model.param.result.pki.EidPkiAnonymousResult;
import com.eid.common.util.BeanMapperUtil;
import com.eid.connect.annotations.InterfaceImpl;
import com.eid.connect.process.SendProcessor;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.aiav.aptoassdk.service.dataprotection.secresy.impl.SDesedeService;
import org.aiav.aptoassdk.service.dataprotection.sign.impl.SHmacSha1Service;
import org.aiav.aptoassdk.service.eidservice.biz.pki.PkiBizService;
import org.aiav.aptoassdk.util.ServiceUtil;
import org.aiav.astoopsdk.constants.EBizType;
import org.aiav.astoopsdk.service.eidservice.params.result.biz.pki.PkiBizResult;
import org.springframework.stereotype.Component;

/**
 * 签名验签pki方式
 * Created by:ruben Date:2017/2/8 Time:下午4:26
 */
@Slf4j
@Component("pkiSignVerifyProcessorImpl")
@InterfaceImpl(value = {BizType.VERIFY_PKI})
public class SignVerifyProcessorImpl extends SendProcessor {

    @Override
    public EidBaseResult send(EidBaseParam eidBaseParam) {
        EidPkiVerifyParam eidPkiVerifyParam = (EidPkiVerifyParam) eidBaseParam;
        org.aiav.aptoassdk.service.eidservice.params.request.biz.pki.PkiBizParams parameters = new org.aiav.aptoassdk.service.eidservice.params.request.biz.pki.PkiBizParams();
        parameters.setAppId(eidPkiVerifyParam.getAppId());
        parameters.setAttach(eidPkiVerifyParam.getAttach());
        parameters.setDataToSign(eidPkiVerifyParam.getDataToSign());
        parameters.setEidSign(eidPkiVerifyParam.getEidSign());
        parameters.setSecurityType(org.aiav.aptoassdk.constants.ESecurityType.SKEY);
        parameters.setSignType(org.aiav.aptoassdk.constants.ESignType.HMAC_SHA1);
        parameters.setEncryptType(org.aiav.aptoassdk.constants.EEncryptType.TRIPLE_DES_ECB_PKCS5PADDING);
        parameters.setEidSignAlgorithm(org.aiav.aptoassdk.constants.EEidSignA.getEnum(eidPkiVerifyParam.getEidSignAlgorithm()));
        parameters.setEncryptFactor(ServiceUtil.genHexString(8));

        PkiBizService service = new PkiBizService(new SHmacSha1Service(eidPkiVerifyParam.getAppKey()), new SDesedeService(eidPkiVerifyParam.getAppKey()), "");
        String requestStr = service.doRequestSyn(parameters);
        JSONObject jsonObject = JSONObject.parseObject(requestStr);

        org.aiav.astoopsdk.service.eidservice.params.request.biz.pki.PkiBizParams params = new org.aiav.astoopsdk.service.eidservice.params.request.biz.pki.PkiBizParams();

        params.setAsid(asId);
        params.setAppid(parameters.getAppId());
        params.setReturnUrl(parameters.getReturnUrl());
        params.setAttach(parameters.getAttach());

        params.setBizSequenceId(jsonObject.getString("biz_sequence_id"));
        params.setBizType(EBizType.BIZ_SIGN_VERIFY_PKI);
        params.setSecurityType(org.aiav.astoopsdk.constants.ESecurityType.getEnum(jsonObject.getString("security_type")));
        params.setSignType(org.aiav.astoopsdk.constants.ESignType.getEnum(jsonObject.getString("sign_type")));
        params.setEncryptType(org.aiav.astoopsdk.constants.EEncryptType.getEnum(jsonObject.getString("encrypt_type")));
        params.setEidSignAlgorithm(org.aiav.astoopsdk.constants.EEidSignA.getEnum(jsonObject.getString("eid_sign_algorithm")));
        params.setEncryptFactor(parameters.getEncryptFactor());

        params.setDataToSign(jsonObject.getString("data_to_sign"));
        params.setEidSign(jsonObject.getString("eid_sign"));
        params.setEidIssuerSn(eidPkiVerifyParam.getEidIssuerSn());
        params.setEidIssuer(eidPkiVerifyParam.getEidIssuer());
        params.setEidSn(eidPkiVerifyParam.getEidSn());

        EidPkiAnonymousResult eidPkiAnonymousResult = new EidPkiAnonymousResult();
        String op = opAddress + "/active/biz/signverify/sync/" + params.getAsid();
        try {
            org.aiav.astoopsdk.service.eidservice.biz.pki.PkiBizService services = new org.aiav.astoopsdk.service.eidservice.biz.pki.PkiBizService(new org.aiav.astoopsdk.service.dataprotection.sign.impl.SHmacSha1Service(asKey), op); // sync request
            PkiBizResult result = services.doRequestSyn(params);
            BeanMapperUtil.copy(result, eidPkiAnonymousResult);
            if (Objects.equal("00", result.getResult())) {
                JSONObject resultJson = JSONObject.parseObject(service.doDecrypt(result.getUserInfo(), result.getEncryptFactor()));
                eidPkiAnonymousResult.setResult(resultJson.getString("appeidcode"));
            }

            return eidPkiAnonymousResult;
        } catch (Exception e) {
            log.info("Failed to send op! message:{}", Throwables.getStackTraceAsString(e));
            return null;
        }

    }
}
