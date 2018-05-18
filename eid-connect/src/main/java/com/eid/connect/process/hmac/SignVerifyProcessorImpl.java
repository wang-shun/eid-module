package com.eid.connect.process.hmac;

import com.alibaba.fastjson.JSONObject;
import com.eid.common.enums.BizType;
import com.eid.common.model.Response;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.request.hmac.EidHmacVerifyParam;
import com.eid.common.model.param.result.EidBaseResult;
import com.eid.common.model.param.result.hmac.EidHmacVerifyResult;
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
import org.aiav.aptoassdk.service.eidservice.biz.hmac.HmacBizService;
import org.aiav.aptoassdk.util.ServiceUtil;
import org.aiav.astoopsdk.constants.EBizType;
import org.aiav.astoopsdk.service.eidservice.params.result.biz.hmac.HmacRNVResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 签名验签hmac方式
 * Created by:ruben Date:2017/2/8 Time:下午4:26
 */
@Slf4j
@Component("hmacSignVerifyProcessorImpl")
@InterfaceImpl(value = {BizType.VERIFY_HMAC})
public class SignVerifyProcessorImpl extends SendProcessor {

    @Autowired(required = false)
    private EncryptionMachineFacade encryptionMachineFacade;

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

        // TODO ********************SIM eID
        // 通过加密机生成，正式环境
        Response<String> responseKey = encryptionMachineFacade.getAppkey(eidHmacVerifyParam.getAppId(),eidHmacVerifyParam.getAppKey());
        String appKey = responseKey.getResult();
        // 直接获取数据库的，测试环境
//       String appKey = eidPkiRealNameParam.getAppKey();

        HmacBizService service = new HmacBizService(new SHmacSha1Service(appKey), new SDesedeService(appKey), "");
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

        params.setEidSign(jsonObject.getString("eid_sign"));
        params.setIdcarrier(jsonObject.getString("idcarrier"));

        // TODO -------------- 普通eID认证更新，不带身份信息，hmac
//        params.setDataToSign(jsonObject.getString("data_to_sign"));
        log.info("SignVerifyProcessorImpl签名验签（不带身份信息），datatosign加密，appKey:{}-------,datatosign:{}-------",appKey,parameters.getDataToSign());
        params.setDataToSign(doEncrypt3DesECBPKCS5(appKey,parameters.getDataToSign(),parameters.getEncryptFactor()));

        EidHmacVerifyResult eidHmacVerifyResult = new EidHmacVerifyResult();
        String op = opAddress + "/hmac/biz/signverify/sync/" + params.getAsid();
        try {
            org.aiav.astoopsdk.service.eidservice.biz.hmac.HmacRNVService services = new org.aiav.astoopsdk.service.eidservice.biz.hmac.HmacRNVService(new org.aiav.astoopsdk.service.dataprotection.sign.impl.SHmacSha1Service(asKey), op); // sync request
            HmacRNVResult result = services.doRequestSyn(params);
            BeanMapperUtil.copy(result, eidHmacVerifyResult);
            if (Objects.equal("00", result.getResult())) {
                log.info("hmac,不带身份信息，签名验签，解密返回的userinfo，appkey：{}-----userinfo:{}-----encryptFactor：{}",appKey,result.getUserInfo(),result.getEncryptFactor());
//                JSONObject resultJson = JSONObject.parseObject(service.doDecrypt(result.getUserInfo(), result.getEncryptFactor()));
                JSONObject resultJson = JSONObject.parseObject(decrypt3DesECBPKCS5(appKey,result.getUserInfo(),result.getEncryptFactor()));

                eidHmacVerifyResult.setResult(resultJson.getString("appeidcode"));
            }
            return eidHmacVerifyResult;
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
