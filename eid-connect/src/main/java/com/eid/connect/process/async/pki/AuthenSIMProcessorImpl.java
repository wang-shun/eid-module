package com.eid.connect.process.async.pki;

import com.eid.common.enums.AuthenticationStatus;
import com.eid.common.enums.BizType;
import com.eid.common.enums.ErrorCode;
import com.eid.common.enums.ResCode;
import com.eid.common.exception.FacadeException;
import com.eid.common.model.Response;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.request.pki.EidPkiRealNameSIMParam;
import com.eid.common.model.param.result.EidBaseResult;
import com.eid.common.model.param.result.pki.EidPkiRealNameResult;
import com.eid.common.util.BeanMapperUtil;
import com.eid.connect.annotations.InterfaceImpl;
import com.eid.connect.process.SendProcessor;
import com.eid.connect.service.EncryptionMachineFacade;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.aiav.aptoassdk.service.dataprotection.secresy.impl.SSm4Service;
import org.aiav.aptoassdk.util.ServiceUtil;
import org.aiav.astoopsdk.constants.EBizType;
import org.aiav.astoopsdk.constants.EEncryptType;
import org.aiav.astoopsdk.constants.ESecurityType;
import org.aiav.astoopsdk.constants.ESignType;
import org.aiav.astoopsdk.service.dataprotection.sign.impl.SHmacSha1Service;
import org.aiav.astoopsdk.service.eidservice.biz.pki.PkiRNVSIMService;
import org.aiav.astoopsdk.service.eidservice.params.request.biz.pki.PkiRNVSIMParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 签名验签pki方式
 * Created by:ruben Date:2017/2/8 Time:下午4:26
 */
@Slf4j
@Component("pkiAsyncAuthenSIMProcessorImpl")
@InterfaceImpl(value = {BizType.AUTHEN_SIM_ASYNC})
public class AuthenSIMProcessorImpl extends SendProcessor {

    @Autowired(required = false)
    private EncryptionMachineFacade encryptionMachineFacade;

    // eID SIM 签名验签异步请求IDSO
    @Override
    public EidBaseResult send(EidBaseParam eidBaseParam) {
        log.info("eID SIM 签名验签异步认证发送请求到IDSO，方法名：send，请求IDSO原始参数:{};", eidBaseParam);
//        EidPkiRealNameParam eidPkiRealNameParam = (EidPkiRealNameParam) eidBaseParam;
        EidPkiRealNameSIMParam eidPkiRealNameParam = (EidPkiRealNameSIMParam) eidBaseParam;

//        PkiRNVParams parameters = new PkiRNVParams();
        // 拼接请求IDSO的最终数据
        PkiRNVSIMParams parameters = new PkiRNVSIMParams();
        parameters.setVersion("2.0.0");
        parameters.setAsid(asId);
        parameters.setAppid(eidPkiRealNameParam.getAppId());
        parameters.setBizSequenceId(eidPkiRealNameParam.getBizSequenceId());
//        parameters.setBizType(EBizType.DENTITY_SIM_ASYNC);//身份识别
        parameters.setBizType(EBizType.AUTHEN_SIM_ASYNC);//签名验签
        parameters.setSecurityType(ESecurityType.getEnum(eidPkiRealNameParam.getSecurityType()));
        parameters.setEncryptFactor(ServiceUtil.genHexString(8));
        parameters.setSignFactor(eidPkiRealNameParam.getSignFactor());

        parameters.setSignType(ESignType.HMAC_SHA1);
        parameters.setEncryptType(EEncryptType.TRIPLE_DES_ECB_PKCS5PADDING);

        parameters.setReturnUrl(returnAuthenSIMUrl);
        parameters.setMsisdn(eidPkiRealNameParam.getMsisdn());
        parameters.setDataToBeDisplayed(eidPkiRealNameParam.getDataToBeDisplayed());
        parameters.setExtension("");//消息扩展字段

        // 需加密字段
        // TODO ********************SIM eID
        // SIM测试环境下数据库直接放的是apkey，可以直接使用
//        String appkey = eidBaseParam.getAppKey();
        // SIM正式环境下需要通过加密机生成appkey
        log.info("SIM eID请求IDSO参数加密，appid:{};   appkey:{}",eidPkiRealNameParam.getAppId(),eidBaseParam.getAppKey());
        Response<String> responseKey = encryptionMachineFacade.getAppkey(eidPkiRealNameParam.getAppId(),eidBaseParam.getAppKey());
        String appkey = responseKey.getResult();
        parameters.setDataToSign(doEncrypt3DesECBPKCS5(appkey,eidPkiRealNameParam.getDataToSign(),parameters.getEncryptFactor()));
        // 测试签名验签时注销，如果是身份识别的话需要放开注释
//        parameters.setUserIdInfo(doEncrypt3DesECBPKCS5(eidBaseParam.getAppKey(),eidPkiRealNameParam.getUserIdInfo(),parameters.getEncryptFactor()));

//        parameters.setEncryptFactor(eidPkiRealNameParam.getEncryptFactor());
//        parameters.setAttach(eidPkiRealNameParam.getAttach());
//        parameters.setBizType(EBizType.getEnum(eidPkiRealNameParam.getBizType()));
//        parameters.setSignType(ESignType.getEnum(eidPkiRealNameParam.getSignType()));
//        parameters.setEncryptType(EEncryptType.getEnum(eidPkiRealNameParam.getEncryptType()));
//        parameters.setDataToSign(eidPkiRealNameParam.getDataToSign());
//        parameters.setUserIdInfo(eidPkiRealNameParam.getUserIdInfo());

//        parameters.setEidSign(eidPkiRealNameParam.getEidSign());
//        parameters.setEidSignAlgorithm(EEidSignA.getEnum(eidPkiRealNameParam.getEidSignAlgorithm()));
//        parameters.setEidIssuer(eidPkiRealNameParam.getEidIssuer());
//        parameters.setEidIssuerSn(eidPkiRealNameParam.getEidIssuerSn());
//        parameters.setEidSn(eidPkiRealNameParam.getEidSn());

        log.info("eID SIM 签名验签异步认证请求IDSO地址:{}",opSIMAddress);
        log.info("eID SIM 签名验签异步认证请求IDSO参数(未签名):{}",parameters);

        EidPkiRealNameResult eidPkiRealNameResult = new EidPkiRealNameResult();
        try {
//            PkiRNVService service = new PkiRNVService(new SHmacSha1Service(asKey), opSIMAddress+"/appserver/identify/async/" + eidPkiRealNameParam.getAppId()); // sync request
//            JSONObject result = service.doRequestAsyn(parameters);// 异步认证返回（{"received":"true"}）

            // IDSO联调代码
            // 身份识别接口
//            String requestIDSOUrl = opSIMAddress+"/idsoserver/identify/async/"+asId;
            // 签名验签接口
            String requestIDSOUrl = opSIMAddress+"/idsoserver/authen/async/"+asId;
            log.info("eID SIM 签名验签异步认证请求IDSO完整路径：{}",requestIDSOUrl);
            PkiRNVSIMService service = new PkiRNVSIMService(new SHmacSha1Service(asKey), requestIDSOUrl); // sync request
            JSONObject result = service.doRequestAsyn(parameters);// 异步认证返回（{"received":"true"}）
            log.info("eID SIM 异步签名验签认证返回数据:"+result);
            // IDSO联调代码 end

            BeanMapperUtil.copy(eidPkiRealNameParam, eidPkiRealNameResult);

            // 本地测试使用
//            JSONObject result = new JSONObject();
//            result.put("received","true");
            // 本地测试使用 end

            if(result.containsKey("received")){
                boolean received = result.getBoolean("received");
                eidPkiRealNameResult.setStatus(received == true ? AuthenticationStatus.PROCESSING.getCode() : AuthenticationStatus.FAILED.getCode());// 设置状态为处理中
                eidPkiRealNameResult.setResultDetail(received == true ? ResCode.EID_0000001.getCode() : ResCode.EID_0401001.getCode());// 设置结果码为等待通知
            }else{
                eidPkiRealNameResult.setStatus(AuthenticationStatus.FAILED.getCode());// 设置状态为失败
                eidPkiRealNameResult.setResultDetail(ResCode.EID_0401001.getCode());// 设置结果码认证请求失败
            }

            log.info("eID SIM 异步认证完成，返回结果：{}",eidPkiRealNameResult);

            return eidPkiRealNameResult;

        } catch (Exception e) {
            log.info("Failed to send op! message:{}", e);
            throw new FacadeException(ErrorCode.SEND_ERROR);
        }

    }

//    public static void main(String[] args) throws Exception
//    {
//        System.out.println(doEncrypt3DesECBPKCS5("09F0DDAFE687937914F019B14E2F8B14","ISDP","6F3FF455B55A4A8F"));
//        System.out.println("");
//        System.out.println(SecurityUtils.do3desEncrypt("IDSP","09F0DDAFE687937914F019B14E2F8B14","6F3FF455B55A4A8F"));
//    }

    // 3DesECBPKCS5加密 (使用IDSO提供的SDK进行加密，这种方式IDSO不能解密)
//    public static String doEncrypt3DesECBPKCS5(String appKey, String data, String factor){
//        SDesedeService sDesedeService = new SDesedeService(appKey);
//        return sDesedeService.doEncrypt(data,factor);
//    }

    // 3DesECBPKCS5加密 (使用IDSO单独提供的加密方式，这种方式IDSO加密机能解密，这种方式是和加密机配套的)
    public static String doEncrypt3DesECBPKCS5(String appKey, String data, String factor){
        return SecurityUtils.do3desEncrypt(data,appKey,factor);
    }

    // ssm4加密
    public String doEncryptSSm4(String appKey, String data, String factor){
        SSm4Service sSm4Service = new SSm4Service(appKey);
        return sSm4Service.doEncrypt(data,factor);
    }

}