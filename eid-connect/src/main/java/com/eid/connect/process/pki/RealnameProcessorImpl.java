package com.eid.connect.process.pki;

import com.alibaba.fastjson.JSONObject;
import com.eid.common.enums.BizType;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.request.pki.EidPkiRealNameParam;
import com.eid.common.model.param.result.EidBaseResult;
import com.eid.common.model.param.result.pki.EidPkiRealNameResult;
import com.eid.common.util.BeanMapperUtil;
import com.eid.connect.annotations.InterfaceImpl;
import com.eid.connect.process.SendProcessor;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.aiav.aptoassdk.service.dataprotection.secresy.impl.SDesedeService;
import org.aiav.aptoassdk.service.dataprotection.sign.impl.SHmacSha1Service;
import org.aiav.aptoassdk.service.eidservice.biz.pki.PkiRNVService;
import org.aiav.aptoassdk.util.ServiceUtil;
import org.aiav.astoopsdk.constants.EBizType;
import org.aiav.astoopsdk.service.eidservice.params.result.biz.pki.PkiRNVResult;
import org.springframework.stereotype.Component;

/**
 * 身份识别pki方式
 * Created by:ruben Date:2017/2/8 Time:下午4:26
 */
@Slf4j
@Component("pkiRealnameProcessorImpl")
@InterfaceImpl(value = {BizType.IDENTITY_PKI, BizType.REALNAME_PRO_PKI})
public class RealnameProcessorImpl extends SendProcessor {

    @Override
    public EidBaseResult send(EidBaseParam eidBaseParam) {
        EidPkiRealNameParam eidPkiRealNameParam = (EidPkiRealNameParam) eidBaseParam;
        org.aiav.aptoassdk.service.eidservice.params.request.biz.pki.PkiRNVParams parameters = new org.aiav.aptoassdk.service.eidservice.params.request.biz.pki.PkiRNVParams();
        parameters.setAppId(eidPkiRealNameParam.getAppId());
        parameters.setAttach("");
        parameters.setDataToSign(eidPkiRealNameParam.getDataToSign());
        parameters.setEidSign(eidPkiRealNameParam.getEidSign());
        parameters.setSecurityType(org.aiav.aptoassdk.constants.ESecurityType.SKEY);
        parameters.setSignType(org.aiav.aptoassdk.constants.ESignType.HMAC_SHA1);
        parameters.setEncryptType(org.aiav.aptoassdk.constants.EEncryptType.TRIPLE_DES_ECB_PKCS5PADDING);
        parameters.setEidSignAlgorithm(org.aiav.aptoassdk.constants.EEidSignA.getEnum(eidPkiRealNameParam.getEidSignAlgorithm()));
        parameters.setEncryptFactor(ServiceUtil.genHexString(8));
        parameters.setUser_id_info(eidPkiRealNameParam.getUserIdInfo());

        PkiRNVService service = new PkiRNVService(new SHmacSha1Service(eidPkiRealNameParam.getAppKey()), new SDesedeService(eidPkiRealNameParam.getAppKey()), "");
        String requestStr = service.doRequestSyn(parameters);
        JSONObject jsonObject = JSONObject.parseObject(requestStr);
        System.out.println(jsonObject);

        org.aiav.astoopsdk.service.eidservice.params.request.biz.pki.PkiRNVParams params = new org.aiav.astoopsdk.service.eidservice.params.request.biz.pki.PkiRNVParams();

        params.setAsid(asId);
        params.setAppid(parameters.getAppId());
        params.setReturnUrl(parameters.getReturnUrl());
        params.setAttach(parameters.getAttach());

        params.setBizSequenceId(jsonObject.getString("biz_sequence_id"));
        params.setBizType(EBizType.REAL_NAME_SIGN_VERIFY_PKI);
        params.setSecurityType(org.aiav.astoopsdk.constants.ESecurityType.getEnum(jsonObject.getString("security_type")));
        params.setSignType(org.aiav.astoopsdk.constants.ESignType.getEnum(jsonObject.getString("sign_type")));
        params.setEncryptType(org.aiav.astoopsdk.constants.EEncryptType.getEnum(jsonObject.getString("encrypt_type")));
        params.setEidSignAlgorithm(org.aiav.astoopsdk.constants.EEidSignA.getEnum(jsonObject.getString("eid_sign_algorithm")));
        params.setEncryptFactor(parameters.getEncryptFactor());

        params.setUserIdInfo(jsonObject.getString("user_id_info"));
        params.setDataToSign(jsonObject.getString("data_to_sign"));
        params.setEidSign(jsonObject.getString("eid_sign"));
        params.setEidIssuerSn(eidPkiRealNameParam.getEidIssuerSn());
        params.setEidIssuer(eidPkiRealNameParam.getEidIssuer());
        params.setEidSn(eidPkiRealNameParam.getEidSn());

        EidPkiRealNameResult eidPkiRealNameResult = new EidPkiRealNameResult();
        String op = opAddress + "/active/realname/signverify/sync/" + params.getAsid();
        try {
            org.aiav.astoopsdk.service.eidservice.biz.pki.PkiRNVService services = new org.aiav.astoopsdk.service.eidservice.biz.pki.PkiRNVService(new org.aiav.astoopsdk.service.dataprotection.sign.impl.SHmacSha1Service(asKey), op); // sync request
            PkiRNVResult result = services.doRequestSyn(params);

            BeanMapperUtil.copy(result, eidPkiRealNameResult);
            if (Objects.equal("00", result.getResult())) {
                JSONObject resultJson = JSONObject.parseObject(service.doDecrypt(result.getUserInfo(), result.getEncryptFactor()));
                eidPkiRealNameResult.setResult(resultJson.getString("appeidcode"));
            }
            return eidPkiRealNameResult;
        } catch (Exception e) {
            log.info("Failed to send op! message:{}", Throwables.getStackTraceAsString(e));
            return null;
        }
    }
}
