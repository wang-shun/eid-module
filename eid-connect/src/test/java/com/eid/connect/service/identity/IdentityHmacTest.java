package com.eid.connect.service.identity;

import com.alibaba.fastjson.JSONObject;
import com.eid.connect.BaseServiceTest;
import com.eid.connect.config.OP;
import com.eidlink.sdk.constants.EidLinkSign;
import com.eidlink.sdk.constants.EidSign;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.aiav.aptoassdk.constants.*;
import org.aiav.aptoassdk.service.dataprotection.secresy.impl.SDesedeService;
import org.aiav.aptoassdk.service.dataprotection.sign.impl.SHmacSha1Service;
import org.aiav.aptoassdk.service.eidservice.biz.hmac.HmacRNVService;
import org.aiav.aptoassdk.service.eidservice.params.request.biz.hmac.HmacRNVParams;
import org.aiav.aptoassdk.util.ServiceUtil;
import org.aiav.astoopsdk.constants.EBizType;
import org.junit.Test;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/4/12 Time:上午9:54
 */
@Slf4j
public class IdentityHmacTest extends BaseServiceTest {

    @Test
    public void identityHmacTest() {
        String dataToSign = "MjAxNzEwMjAxNzE1NTk6MjAxNzEwMjAxNzE1NTk2NTI0MzkzNDA6OTcyODM3";
        String eidSign = "tNYjcgKAQMzvPKAJbiTAyyHMEudbGd3BMo49KYhBLi4=";
//        String idcarrier = "EAwAAAH3U/4=";
        String idcarrier = "EAwAAAHP6hA=";

        HmacRNVParams parameters = new HmacRNVParams();

        parameters.setAppId(OP.app_id);
        parameters.setAttach("");

        parameters.setSecurityType(ESecurityType.SKEY);
        parameters.setSignType(ESignType.HMAC_SHA1);
        parameters.setEncryptType(EEncryptType.TRIPLE_DES_ECB_PKCS5PADDING);
        parameters.setEidSignAlgorithm(EEidSignA.RSA_WITH_SHA1);
        parameters.setEncryptFactor(ServiceUtil.genHexString(8));

        parameters.setDataToSign(dataToSign);
        parameters.setEidSign(eidSign);
        parameters.setIdtype(EIdType.ID_CARD);
        parameters.setIdnum(OP.id_num);
        parameters.setName(OP.name);
        parameters.setIdcarrier(idcarrier);

        HmacRNVService service = new HmacRNVService(new SHmacSha1Service(OP.app_key), new SDesedeService(OP.app_key), "");
        String requestStr = service.doRequestSyn(parameters);
        JSONObject jsonObject = JSONObject.parseObject(requestStr);
        System.out.println(jsonObject);

        org.aiav.astoopsdk.service.eidservice.params.request.biz.hmac.HmacRNVParams params = new org.aiav.astoopsdk.service.eidservice.params.request.biz.hmac.HmacRNVParams();

        params.setAsid(OP.as_id);
        params.setAppid(parameters.getAppId());
        params.setAttach(parameters.getAttach());
        params.setBizSequenceId(jsonObject.getString("biz_sequence_id"));
        params.setBizType(EBizType.REAL_NAME_SIGN_VERIFY_HMAC);
        params.setSecurityType(org.aiav.astoopsdk.constants.ESecurityType.getEnum(jsonObject.getString("security_type")));
        params.setSignType(org.aiav.astoopsdk.constants.ESignType.getEnum(jsonObject.getString("sign_type")));
        params.setEncryptType(org.aiav.astoopsdk.constants.EEncryptType.getEnum(jsonObject.getString("encrypt_type")));
        params.setEidSignAlgorithm(org.aiav.astoopsdk.constants.EEidSignA.getEnum(jsonObject.getString("eid_sign_algorithm")));
        params.setEncryptFactor(parameters.getEncryptFactor());

        params.setUserIdInfo(jsonObject.getString("user_id_info"));
        params.setDataToSign(jsonObject.getString("data_to_sign"));
        params.setEidSign(jsonObject.getString("eid_sign"));
        params.setIdcarrier(jsonObject.getString("idcarrier"));

        String op = OP.address + "/hmac/realname/signverify/sync/" + params.getAsid();
        try {
            org.aiav.astoopsdk.service.eidservice.biz.hmac.HmacRNVService services = new org.aiav.astoopsdk.service.eidservice.biz.hmac.HmacRNVService(new org.aiav.astoopsdk.service.dataprotection.sign.impl.SHmacSha1Service(OP.as_key), op); // sync request
            services.doRequestSyn(params);
        } catch (Exception e) {
            log.info("Failed to send op! message:{}", Throwables.getStackTraceAsString(e));
        }

    }

}
