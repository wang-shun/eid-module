package com.eid.connect.process.pki;

import com.alibaba.fastjson.JSONObject;
import com.eid.common.enums.BizType;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.request.pki.EidPkiAnonymousParam;
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
import org.aiav.aptoassdk.service.eidservice.biz.pki.PkiBizDirectLoginService;
import org.aiav.aptoassdk.util.ServiceUtil;
import org.aiav.astoopsdk.constants.EBizType;
import org.aiav.astoopsdk.service.eidservice.params.result.biz.pki.PkiBizDirectLoginResult;
import org.springframework.stereotype.Component;

/**
 * 匿名登陆pki方式
 * Created by:ruben Date:2017/2/8 Time:下午4:26
 */
@Slf4j
@Component("pkiDirectLoginProcessorImpl")
@InterfaceImpl(value = {BizType.ANONYMOUS_PKI})
public class DirectLoginProcessorImpl extends SendProcessor {

    @Override
    public EidBaseResult send(EidBaseParam eidBaseParam) {
        EidPkiAnonymousParam eidPkiAnonymousParam = (EidPkiAnonymousParam) eidBaseParam;
        org.aiav.aptoassdk.service.eidservice.params.request.biz.pki.PkiBizDirectLoginParams parameters = new org.aiav.aptoassdk.service.eidservice.params.request.biz.pki.PkiBizDirectLoginParams();
        parameters.setAppId(eidPkiAnonymousParam.getAppId());
        parameters.setAttach(eidPkiAnonymousParam.getAttach());
        parameters.setDataToSign(eidPkiAnonymousParam.getDataToSign());
        parameters.setEidSign(eidPkiAnonymousParam.getEidSign());
        parameters.setSecurityType(org.aiav.aptoassdk.constants.ESecurityType.SKEY);
        parameters.setSignType(org.aiav.aptoassdk.constants.ESignType.HMAC_SHA1);
        parameters.setEncryptType(org.aiav.aptoassdk.constants.EEncryptType.TRIPLE_DES_ECB_PKCS5PADDING);
        parameters.setEidSignAlgorithm(org.aiav.aptoassdk.constants.EEidSignA.getEnum(eidPkiAnonymousParam.getEidSignAlgorithm()));
        parameters.setEncryptFactor(ServiceUtil.genHexString(8));

        PkiBizDirectLoginService service = new PkiBizDirectLoginService(new SHmacSha1Service(eidPkiAnonymousParam.getAppKey()), new SDesedeService(eidPkiAnonymousParam.getAppKey()), "");
        String requestStr = service.doRequestSyn(parameters);
        JSONObject jsonObject = JSONObject.parseObject(requestStr);

        org.aiav.astoopsdk.service.eidservice.params.request.biz.pki.PkiBizDirectLoginParams params = new org.aiav.astoopsdk.service.eidservice.params.request.biz.pki.PkiBizDirectLoginParams();

        params.setAsid(asId);
        params.setAppid(parameters.getAppId());
        params.setReturnUrl(parameters.getReturnUrl());
        params.setAttach(parameters.getAttach());

        params.setBizSequenceId(jsonObject.getString("biz_sequence_id"));
        params.setBizType(EBizType.BIZ_DIRECT_LOGIN_PKI);
        params.setSecurityType(org.aiav.astoopsdk.constants.ESecurityType.getEnum(jsonObject.getString("security_type")));
        params.setSignType(org.aiav.astoopsdk.constants.ESignType.getEnum(jsonObject.getString("sign_type")));
        params.setEncryptType(org.aiav.astoopsdk.constants.EEncryptType.getEnum(jsonObject.getString("encrypt_type")));
        params.setEidSignAlgorithm(org.aiav.astoopsdk.constants.EEidSignA.getEnum(jsonObject.getString("eid_sign_algorithm")));
        params.setEncryptFactor(parameters.getEncryptFactor());

        params.setDataToSign(jsonObject.getString("data_to_sign"));
        params.setEidSign(jsonObject.getString("eid_sign"));
        params.setEidIssuerSn(eidPkiAnonymousParam.getEidIssuerSn());
        params.setEidIssuer(eidPkiAnonymousParam.getEidIssuer());
        params.setEidSn(eidPkiAnonymousParam.getEidSn());

        EidPkiAnonymousResult eidPkiAnonymousResult = new EidPkiAnonymousResult();
        String op = opAddress + "/pki/biz/directlogin/sync/" + params.getAsid();
        try {
            org.aiav.astoopsdk.service.eidservice.biz.pki.PkiBizDirectLoginService services = new org.aiav.astoopsdk.service.eidservice.biz.pki.PkiBizDirectLoginService(new org.aiav.astoopsdk.service.dataprotection.sign.impl.SHmacSha1Service(asKey), op); // sync request
            PkiBizDirectLoginResult result = services.doRequestSyn(params);
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
