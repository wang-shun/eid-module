package com.eid.connect.service.anonymous;

import com.alibaba.fastjson.JSONObject;
import com.eid.connect.BaseServiceTest;
import com.eid.connect.config.OP;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.aiav.aptoassdk.constants.EEidSignA;
import org.aiav.aptoassdk.constants.EEncryptType;
import org.aiav.aptoassdk.constants.ESecurityType;
import org.aiav.aptoassdk.constants.ESignType;
import org.aiav.aptoassdk.service.dataprotection.secresy.impl.SDesedeService;
import org.aiav.aptoassdk.service.dataprotection.sign.impl.SHmacSha1Service;
import org.aiav.aptoassdk.service.eidservice.biz.pki.PkiBizDirectLoginService;
import org.aiav.aptoassdk.service.eidservice.params.request.biz.pki.PkiBizDirectLoginParams;
import org.aiav.aptoassdk.util.ServiceUtil;
import org.aiav.astoopsdk.constants.EBizType;
import org.aiav.astoopsdk.service.eidservice.params.result.biz.pki.PkiBizDirectLoginResult;
import org.junit.Test;

import java.util.UUID;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/6/7 Time:上午10:38
 */
@Slf4j
public class AnonymousPkiTest extends BaseServiceTest {

    @Test
    public void anonymousPkiTest() {
        String dataToSign = "MjAxNzEwMjYxMTI3Mzg6MjAxNzEwMjYxMTI3MzgzMTY1MDUwNzA6NDY4OTIx";
        String eidSign = "p8atEgnuE0/4JkBP72ErQrILbpuGC0xlVk0EXCBFT+TYO8PuFDe4Q94j4gFd/KPUSnn898G/k6JpnB1POw+FTQ==";

        PkiBizDirectLoginParams parameters = new PkiBizDirectLoginParams();
        parameters.setAppId(OP.app_id);
        parameters.setAttach("");
        parameters.setDataToSign(dataToSign);
        parameters.setEidSign(eidSign);
        parameters.setSecurityType(ESecurityType.SKEY);
        parameters.setSignType(ESignType.HMAC_SHA1);
        parameters.setEncryptType(EEncryptType.TRIPLE_DES_ECB_PKCS5PADDING);
        parameters.setEidSignAlgorithm(EEidSignA.SM2_WITH_SM3);
        parameters.setEncryptFactor(ServiceUtil.genHexString(8));

        PkiBizDirectLoginService service = new PkiBizDirectLoginService(new SHmacSha1Service(OP.app_key), new SDesedeService(OP.app_key), "");
        String requestStr = service.doRequestSyn(parameters);
        JSONObject jsonObject = JSONObject.parseObject(requestStr);
        System.out.println(jsonObject);

        org.aiav.astoopsdk.service.eidservice.params.request.biz.pki.PkiBizDirectLoginParams params = new org.aiav.astoopsdk.service.eidservice.params.request.biz.pki.PkiBizDirectLoginParams();

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

        params.setDataToSign(jsonObject.getString("data_to_sign"));
        params.setEidSign(jsonObject.getString("eid_sign"));
        params.setEidIssuerSn(OP.eid_issuer_sn);
        params.setEidIssuer(OP.eid_issuer);
        params.setEidSn(OP.eid_sn);

        String op = OP.address + "/pki/biz/directlogin/sync/" + params.getAsid();
        try {
            org.aiav.astoopsdk.service.eidservice.biz.pki.PkiBizDirectLoginService services = new org.aiav.astoopsdk.service.eidservice.biz.pki.PkiBizDirectLoginService(new org.aiav.astoopsdk.service.dataprotection.sign.impl.SHmacSha1Service(OP.as_key), op); // sync request
            PkiBizDirectLoginResult pkiBizDirectLoginResult = services.doRequestSyn(params);
            String userInfo = service.doDecrypt(pkiBizDirectLoginResult.getUserInfo(), pkiBizDirectLoginResult.getEncryptFactor());
            System.out.println(userInfo);

        } catch (Exception e) {
            log.info("Failed to send op! message:{}", Throwables.getStackTraceAsString(e));
        }

    }
}
