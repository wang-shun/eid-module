package com.eid.connect.service.identity;

import com.alibaba.fastjson.JSONObject;
import com.eid.connect.BaseServiceTest;
import com.eid.connect.config.OP;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.aiav.aptoassdk.constants.*;
import org.aiav.aptoassdk.service.dataprotection.secresy.impl.SDesedeService;
import org.aiav.aptoassdk.service.dataprotection.sign.impl.SHmacSha1Service;
import org.aiav.aptoassdk.service.eidservice.biz.pki.PkiBizDirectLoginService;
import org.aiav.aptoassdk.service.eidservice.biz.pki.PkiRNVService;
import org.aiav.aptoassdk.service.eidservice.params.request.biz.pki.PkiBizDirectLoginParams;
import org.aiav.aptoassdk.service.eidservice.params.request.biz.pki.PkiRNVParams;
import org.aiav.aptoassdk.util.ServiceUtil;
import org.aiav.astoopsdk.constants.EBizType;
import org.junit.Test;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/6/7 Time:上午10:38
 */
@Slf4j
public class IdentityPkiTest extends BaseServiceTest {

    @Test
    public void identityPkiTest() {
        String dataToSign = "MjAxNzEwMjAxNjU5MzU6MjAxNzEwMjAxNjU5MzU5NTc5NzMxMDc6ODUxMjkz";
        String eidSign = "KCNwobHQFyhkUlmR4XImdWRK74FIm87PCql1MiINNSKp2K3PLhi8q/WY7iEKlY7C+NoH6aieR3s/mgEgrGTKNg==";

        PkiRNVParams parameters = new PkiRNVParams();
        parameters.setAppId(OP.app_id);
        parameters.setAttach("");
        parameters.setDataToSign(dataToSign);
        parameters.setEidSign(eidSign);
        parameters.setSecurityType(ESecurityType.SKEY);
        parameters.setSignType(ESignType.HMAC_SHA1);
        parameters.setEncryptType(EEncryptType.TRIPLE_DES_ECB_PKCS5PADDING);
        parameters.setEidSignAlgorithm(EEidSignA.SM2_WITH_SM3);
        parameters.setEncryptFactor(ServiceUtil.genHexString(8));
        parameters.setIdtype(EIdType.ID_CARD);
        parameters.setName(OP.name);
        parameters.setIdnum(OP.id_num);

        PkiRNVService service = new PkiRNVService(new SHmacSha1Service(OP.app_key), new SDesedeService(OP.app_key), "");
        String requestStr = service.doRequestSyn(parameters);
        JSONObject jsonObject = JSONObject.parseObject(requestStr);
        System.out.println(jsonObject);

        org.aiav.astoopsdk.service.eidservice.params.request.biz.pki.PkiRNVParams params = new org.aiav.astoopsdk.service.eidservice.params.request.biz.pki.PkiRNVParams();

        params.setAsid(OP.as_id);
        params.setAppid(parameters.getAppId());
        params.setReturnUrl(parameters.getReturnUrl());
        params.setAttach(parameters.getAttach());

        params.setBizSequenceId(jsonObject.getString("biz_sequence_id"));
        params.setBizType(EBizType.getEnum(jsonObject.getString("biz_type")));
        params.setSecurityType(org.aiav.astoopsdk.constants.ESecurityType.getEnum(jsonObject.getString("security_type")));
        params.setSignType(org.aiav.astoopsdk.constants.ESignType.getEnum(jsonObject.getString("sign_type")));
        params.setEncryptType(org.aiav.astoopsdk.constants.EEncryptType.getEnum(jsonObject.getString("encrypt_type")));
        params.setEidSignAlgorithm(org.aiav.astoopsdk.constants.EEidSignA.getEnum(jsonObject.getString("eid_sign_algorithm")));
        params.setEncryptFactor(parameters.getEncryptFactor());

        params.setUserIdInfo(jsonObject.getString("user_id_info"));
        params.setDataToSign(jsonObject.getString("data_to_sign"));
        params.setEidSign(jsonObject.getString("eid_sign"));
        params.setEidIssuerSn(OP.eid_issuer_sn);
        params.setEidIssuer(OP.eid_issuer);
        params.setEidSn(OP.eid_sn);

        String op = OP.address + "/active/realname/signverify/sync/" + params.getAsid();
        try {
            org.aiav.astoopsdk.service.eidservice.biz.pki.PkiRNVService services = new org.aiav.astoopsdk.service.eidservice.biz.pki.PkiRNVService(new org.aiav.astoopsdk.service.dataprotection.sign.impl.SHmacSha1Service(OP.as_key), op); // sync request
            services.doRequestSyn(params);
        } catch (Exception e) {
            log.info("Failed to send op! message:{}", Throwables.getStackTraceAsString(e));
        }

    }
}
