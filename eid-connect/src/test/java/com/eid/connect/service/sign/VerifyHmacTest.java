package com.eid.connect.service.sign;

import com.alibaba.fastjson.JSONObject;
import com.eid.connect.BaseServiceTest;
import com.eid.connect.config.OP;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.aiav.aptoassdk.constants.*;
import org.aiav.aptoassdk.service.dataprotection.secresy.impl.SDesedeService;
import org.aiav.aptoassdk.service.dataprotection.sign.impl.SHmacSha1Service;
import org.aiav.aptoassdk.service.eidservice.biz.hmac.HmacBizService;
import org.aiav.aptoassdk.service.eidservice.biz.hmac.HmacRNVService;
import org.aiav.aptoassdk.service.eidservice.params.request.biz.hmac.HmacBizParams;
import org.aiav.aptoassdk.service.eidservice.params.request.biz.hmac.HmacRNVParams;
import org.aiav.astoopsdk.constants.EBizType;
import org.junit.Test;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/4/12 Time:上午9:54
 */
@Slf4j
public class VerifyHmacTest extends BaseServiceTest {

    @Test
    public void verifyHmacTest() {
        String dataToSign = "MjAxNzEwMTkxMTMwNDc6bWQMF0YKVD16XilHMVRwQTpEMjU5RDgxRDYzRDA0MDIzQThCMzYzMkVCODY0OThEOA==";
        String eidSign = "KtM2MvrF3gZGI7KkWPlddidsiPM=";
        String idCarrier = "EAwAAAH3U/4=";

        HmacBizParams parameters = new HmacBizParams();

        parameters.setAppId(OP.app_id);
        parameters.setAttach("");

        parameters.setSecurityType(ESecurityType.SKEY);
        parameters.setSignType(ESignType.HMAC_SHA1);
        parameters.setEncryptType(EEncryptType.TRIPLE_DES_ECB_PKCS5PADDING);
        parameters.setEidSignAlgorithm(EEidSignA.SHA1);

        parameters.setDataToSign(dataToSign);
        parameters.setEidSign(eidSign);
        parameters.setIdcarrier(idCarrier);

        HmacBizService service = new HmacBizService(new SHmacSha1Service(OP.app_key), new SDesedeService(OP.app_key), "");
        String requestStr = service.doRequestSyn(parameters);
        JSONObject jsonObject = JSONObject.parseObject(requestStr);

        org.aiav.astoopsdk.service.eidservice.params.request.biz.hmac.HmacRNVParams params = new org.aiav.astoopsdk.service.eidservice.params.request.biz.hmac.HmacRNVParams();

        params.setAsid(OP.as_id);
        params.setAppid(parameters.getAppId());
        params.setAttach(parameters.getAttach());
        params.setBizSequenceId(jsonObject.getString("biz_sequence_id"));
        params.setBizType(EBizType.BIZ_SIGN_VERIFY_HMAC);
        params.setSecurityType(org.aiav.astoopsdk.constants.ESecurityType.getEnum(jsonObject.getString("security_type")));
        params.setSignType(org.aiav.astoopsdk.constants.ESignType.getEnum(jsonObject.getString("sign_type")));
        params.setEncryptType(org.aiav.astoopsdk.constants.EEncryptType.getEnum(jsonObject.getString("encrypt_type")));
        params.setEidSignAlgorithm(org.aiav.astoopsdk.constants.EEidSignA.getEnum(jsonObject.getString("eid_sign_algorithm")));

        params.setDataToSign(jsonObject.getString("data_to_sign"));
        params.setEidSign(jsonObject.getString("eid_sign"));
        params.setIdcarrier(jsonObject.getString("idcarrier"));

        String op = "http://124.207.4.75:18081/asserver/rest/hmac/biz/signverify/sync/" + params.getAsid();
        try {
            org.aiav.astoopsdk.service.eidservice.biz.hmac.HmacRNVService services = new org.aiav.astoopsdk.service.eidservice.biz.hmac.HmacRNVService(new org.aiav.astoopsdk.service.dataprotection.sign.impl.SHmacSha1Service(OP.as_key), op); // sync request
            services.doRequestSyn(params);
        } catch (Exception e) {
            log.info("Failed to send op! message:{}", Throwables.getStackTraceAsString(e));
        }

    }

}
