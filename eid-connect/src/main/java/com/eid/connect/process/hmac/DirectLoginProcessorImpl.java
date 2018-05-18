package com.eid.connect.process.hmac;

import com.alibaba.fastjson.JSONObject;
import com.eid.common.enums.BizType;
import com.eid.common.model.Response;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.request.hmac.EidHmacAnonymousParam;
import com.eid.common.model.param.result.EidBaseResult;
import com.eid.common.model.param.result.hmac.EidHmacAnonymousResult;
import com.eid.common.util.BeanMapperUtil;
import com.eid.connect.annotations.InterfaceImpl;
import com.eid.connect.process.SendProcessor;
import com.eid.connect.process.async.pki.SecurityUtils;
import com.eid.connect.service.EncryptionMachineFacade;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 匿名登陆hmac方式
 * Created by:ruben Date:2017/2/8 Time:下午4:26
 */
@Slf4j
@Component("hmacDirectLoginProcessorImpl")
@InterfaceImpl(value = {BizType.ANONYMOUS_HMAC})
public class DirectLoginProcessorImpl extends SendProcessor {

    @Autowired(required = false)
    private EncryptionMachineFacade encryptionMachineFacade;

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

        // TODO ********************SIM eID
        // 通过加密机生成，正式环境
        Response<String> responseKey = encryptionMachineFacade.getAppkey(eidHmacAnonymousParam.getAppId(),eidHmacAnonymousParam.getAppKey());
        String appKey = responseKey.getResult();
        // 直接获取数据库的，测试环境
//       String appKey = eidHmacAnonymousParam.getAppKey();

        HmacBizDirectLoginService service = new HmacBizDirectLoginService(new SHmacSha1Service(appKey), new SDesedeService(appKey), "");
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

        params.setEidSign(jsonObject.getString("eid_sign"));
        params.setIdcarrier(jsonObject.getString("idcarrier"));

        // TODO -------------- 普通eID认证更新，不带身份信息，hmac
//        params.setDataToSign(jsonObject.getString("data_to_sign"));
        log.info("DirectLoginProcessorImpl匿名登录（不带身份信息），datatosign加密，appKey:{}-------,datatosign:{}-------",appKey,parameters.getDataToSign());
        params.setDataToSign(doEncrypt3DesECBPKCS5(appKey,parameters.getDataToSign(),parameters.getEncryptFactor()));

        EidHmacAnonymousResult eidHmacAnonymousResult = new EidHmacAnonymousResult();
        String op = opAddress + "/hmac/biz/directlogin/sync/" + params.getAsid();
        try {
            org.aiav.astoopsdk.service.eidservice.biz.hmac.HmacBizDirectLoginService services = new org.aiav.astoopsdk.service.eidservice.biz.hmac.HmacBizDirectLoginService(new org.aiav.astoopsdk.service.dataprotection.sign.impl.SHmacSha1Service(asKey), op); // sync request
            org.aiav.astoopsdk.service.eidservice.params.result.biz.hmac.HmacBizDirectLoginResult result = services.doRequestSyn(params);
            BeanMapperUtil.copy(result, eidHmacAnonymousResult);
            if (Objects.equal("00", result.getResult())) {
                log.info("hmac,不带身份信息，解密返回的userinfo，appkey：{}-----userinfo:{}-----encryptFactor：{}",appKey,result.getUserInfo(),result.getEncryptFactor());
//                JSONObject resultJson = JSONObject.parseObject(service.doDecrypt(result.getUserInfo(), result.getEncryptFactor()));
                JSONObject resultJson = JSONObject.parseObject(decrypt3DesECBPKCS5(appKey,result.getUserInfo(),result.getEncryptFactor()));
                eidHmacAnonymousResult.setResult(resultJson.getString("appeidcode"));
            }
            return eidHmacAnonymousResult;

        } catch (Exception e) {
            log.info("Failed to send op! message:{}", Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    // 3DesECBPKCS5加密 (使用IDSO单独提供的加密方式，这种方式IDSO加密机能解密，这种方式是和加密机配套的)
    public static String doEncrypt3DesECBPKCS5(String appKey, String data, String factor){
        return SecurityUtils.do3desEncrypt(data,appKey,factor);
    }

    // 3DesECBPKCS5解密 (使用IDSO单独提供的加密方式，这种方式IDSO加密机能解密，这种方式是和加密机配套的)
    public static String decrypt3DesECBPKCS5(String appKey, String data, String factor){
        return SecurityUtils.do3desDecrypt(data,appKey,factor);
    }

}
