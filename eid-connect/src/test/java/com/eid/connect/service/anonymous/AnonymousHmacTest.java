package com.eid.connect.service.anonymous;

import com.alibaba.fastjson.JSONObject;
import com.eid.connect.BaseServiceTest;
import com.eid.connect.config.OP;
import com.eidlink.sdk.EidlinkService;
import com.eidlink.sdk.a.o;
import com.eidlink.sdk.conf.Config;
import com.eidlink.sdk.constants.EidHmac;
import com.eidlink.sdk.pojo.request.HMACRealNameParam;
import com.eidlink.sdk.pojo.request.HMACSignVerifyParam;
import com.eidlink.sdk.pojo.request.base.HMACParameters;
import com.eidlink.sdk.pojo.request.base.RealName;
import com.eidlink.sdk.pojo.result.CommonResult;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.aiav.aptoassdk.constants.EEidSignA;
import org.aiav.aptoassdk.constants.EEncryptType;
import org.aiav.aptoassdk.constants.ESecurityType;
import org.aiav.aptoassdk.constants.ESignType;
import org.aiav.aptoassdk.service.dataprotection.secresy.impl.SDesedeService;
import org.aiav.aptoassdk.service.dataprotection.sign.impl.SHmacSha1Service;
import org.aiav.aptoassdk.service.eidservice.biz.hmac.HmacBizDirectLoginService;
import org.aiav.aptoassdk.service.eidservice.params.request.biz.hmac.HmacBizDirectLoginParams;
import org.aiav.aptoassdk.util.ServiceUtil;
import org.aiav.astoopsdk.constants.EBizType;
import org.aiav.astoopsdk.service.eidservice.params.result.biz.hmac.HmacBizDirectLoginResult;
import org.aiav.crypto.util.CryptoFuncUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Base64;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/4/12 Time:上午9:54
 */
@Slf4j
public class AnonymousHmacTest extends BaseServiceTest {

    @Test
    public void anonymousHmacTest() {
        String dataToSign = "MjAxNzExMDIxNjUwMzg6RTR2TDE3NkhpNmEzRXZLR1hPRENkZz09OjkwZTMxODJkLWRlOTEtNDg4Yi1hNjEwLTRjMDM5ZTgxMzkxZQ==";
        String eidSign = "7LC+6zcJfEFGYk4FXziscm7kfM=";
        String idcarrier = "AAoAAAHSuR4=";
        String resultCode = "00";

        // hmac实名认证
        HmacBizDirectLoginParams parameters = new HmacBizDirectLoginParams();
        parameters.setAppId(OP.app_id);
        parameters.setDataToSign(dataToSign);
        parameters.setEidSign(eidSign);
        parameters.setEidSignAlgorithm(EEidSignA.SHA1);
        parameters.setEncryptType(EEncryptType.TRIPLE_DES_ECB_PKCS5PADDING);
        parameters.setIdcarrier(idcarrier);
        parameters.setSecurityType(ESecurityType.SKEY);
        parameters.setSignType(ESignType.HMAC_SHA1);
        parameters.setEncryptFactor("77A92A4B553B49A2");
        parameters.setReturnUrl("returnUrl");
        parameters.setAttach("attach");

        HmacBizDirectLoginService service = new HmacBizDirectLoginService(new SHmacSha1Service(OP.app_key), new SDesedeService(OP.app_key), "");
        String requestStr = service.doRequestSyn(parameters);
        JSONObject jsonObject = JSONObject.parseObject(requestStr);
        System.out.println(jsonObject);

        org.aiav.astoopsdk.service.eidservice.params.request.biz.hmac.HmacBizDirectLoginParams params = new org.aiav.astoopsdk.service.eidservice.params.request.biz.hmac.HmacBizDirectLoginParams();
        params.setAsid(OP.as_id);
        params.setAppid(parameters.getAppId());
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

        String op = "http://124.207.4.75:18081/asserver/rest/hmac/biz/directlogin/sync/" + params.getAsid();
        try {
            org.aiav.astoopsdk.service.eidservice.biz.hmac.HmacBizDirectLoginService services = new org.aiav.astoopsdk.service.eidservice.biz.hmac.HmacBizDirectLoginService(new org.aiav.astoopsdk.service.dataprotection.sign.impl.SHmacSha1Service(OP.as_key), op); // sync request
            HmacBizDirectLoginResult result = services.doRequestSyn(params);
            String userInfo = service.doDecrypt(result.getUserInfo(), result.getEncryptFactor());
            System.out.println(userInfo);
//            JSONObject resultJson = JSONObject.parseObject(o.b(result.getUserInfo(), OP.app_key, result.getEncryptFactor()));
//            System.out.println(resultJson);

        } catch (Exception e) {
            log.info("Failed to send op! message:{}", Throwables.getStackTraceAsString(e));
        }


//        String res = HttpRequest.post("http://124.207.4.75:18080/appserver/rest/hmac/biz/directlogin/sync/01SJ1612011633420791").send(requestStr).body();
//        System.out.println(service.decryptUserInfoInRes(net.sf.json.JSONObject.fromObject(res)));

//        EidHmac eidSignAlgorithm = EidHmac.SHA1;
//        RealName realName = new RealName("曹介南", "43010519620907791X");
//        HMACParameters hmcParam = new HMACParameters(idcarrier, dataToSign, eidSign, eidSignAlgorithm);
//        HMACRealNameParam realNameParam = new HMACRealNameParam(hmcParam, realName);
//        com.eidlink.sdk.pojo.result.CommonResult result = EidlinkService.HMACRealName(realNameParam);
//        System.out.println(result.getUserInfo());
//        Assert.assertEquals("预期结果：" + resultCode + "与实际结果：" + result.getResult() + " 不一致！错误码：" + result.getResultDetail(), resultCode, result.getResult());

        // hmac签名验签
//        EidHmac eidSignAlgorithm = EidHmac.SHA1;
//        HMACParameters hmacParam = new HMACParameters(idcarrier, dataToSign, eidSign, eidSignAlgorithm);
//        HMACSignVerifyParam reqParam = new HMACSignVerifyParam(hmacParam);
//        CommonResult result = EidlinkService.HMACSignVerify(reqParam);
//        System.out.println(result.getUserInfo());
//        Assert.assertEquals("预期结果：" + resultCode + "与实际结果：" + result.getResult() + " 不一致！错误码：" + result.getResultDetail(), resultCode, result.getResult());

        // hmac匿名认证
//        EidHmac eidSignAlgorithm = EidHmac.SHA1;
//        HMACParameters hmacParam = new HMACParameters(idcarrier, dataToSign, eidSign, eidSignAlgorithm);
//        HMACSignVerifyParam reqParam = new HMACSignVerifyParam(hmacParam);
//        CommonResult result = EidlinkService.HMACDirectLogin(reqParam);
//        System.out.println(result.getUserInfo());
//        Assert.assertEquals("预期结果：" + resultCode + "与实际结果：" + result.getResult() + " 不一致！错误码：" + result.getResultDetail(), resultCode, result.getResult());

    }

}
