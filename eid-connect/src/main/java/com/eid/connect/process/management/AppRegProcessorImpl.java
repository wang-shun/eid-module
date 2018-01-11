package com.eid.connect.process.management;

import com.eid.common.enums.BizType;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.request.management.EidAppRegParam;
import com.eid.common.model.param.result.EidBaseResult;
import com.eid.common.model.param.result.management.EidAppRegResult;
import com.eid.common.util.BeanMapperUtil;
import com.eid.connect.annotations.InterfaceImpl;
import com.eid.connect.process.SendProcessor;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.aiav.astoopsdk.constants.*;
import org.aiav.astoopsdk.service.dataprotection.sign.impl.SHmacSha1Service;
import org.aiav.astoopsdk.service.eidservice.manage.AppRegService;
import org.aiav.astoopsdk.service.eidservice.params.request.manage.AppRegParams;
import org.aiav.astoopsdk.service.eidservice.params.result.manage.AppRegResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.Map;
import java.util.UUID;

/**
 * 应用信息申请
 * Created by:ruben Date:2017/2/8 Time:下午4:26
 */
@Slf4j
@Component("appRegProcessorImpl")
@InterfaceImpl(value = {BizType.APP_REG})
public class AppRegProcessorImpl extends SendProcessor {

    @Autowired(required = false)
//    private JedisCluster jedisCluster;

    @Override
    public EidBaseResult send(EidBaseParam eidBaseParam) {
        EidAppRegParam eidAppRegParam = (EidAppRegParam) eidBaseParam;
        AppRegParams params = new AppRegParams();
        params.setReturnUrl(eidAppRegParam.getReturnUrl());// 回调接口
//        params.setReturnUrl("http://www.baidu.com");// 回调接口
        params.setBizSequenceId(eidAppRegParam.getBizSequenceId());
        params.setSignType(ESignType.getEnum(eidAppRegParam.getSignType()));
        params.setSecurityType(ESecurityType.getEnum(eidAppRegParam.getSecurityType()));
        params.setAsid(asId);
        params.setAttach(eidAppRegParam.getAttach());
        params.setAppInfo(eidAppRegParam.getAppInfo());
        params.setAppName(eidAppRegParam.getAppName());
        params.setCmpName(eidAppRegParam.getCmpName());
        params.setDomainName(eidAppRegParam.getDomainName());
        params.setIpAddr(eidAppRegParam.getIpAddr());
        params.setDefaultSecurityType(ESecurityType.getEnum(eidAppRegParam.getDefaultSecurityType()));
        params.setAppIcon(eidAppRegParam.getAppIcon());
        Map<EBizType, String> bizsMap = Maps.newHashMap();
        bizsMap.put(EBizType.BIZ_SIGN_VERIFY_HMAC, eidAppRegParam.getBizs().get(EBizType.BIZ_SIGN_VERIFY_HMAC.getIndex()));
        bizsMap.put(EBizType.BIZ_SIGN_VERIFY_PKI, eidAppRegParam.getBizs().get(EBizType.BIZ_SIGN_VERIFY_PKI.getIndex()));
        bizsMap.put(EBizType.REAL_NAME_SIGN_VERIFY_HMAC, eidAppRegParam.getBizs().get(EBizType.REAL_NAME_SIGN_VERIFY_HMAC.getIndex()));
        bizsMap.put(EBizType.REAL_NAME_SIGN_VERIFY_PKI, eidAppRegParam.getBizs().get(EBizType.REAL_NAME_SIGN_VERIFY_PKI.getIndex()));
        bizsMap.put(EBizType.BIZ_DIRECT_LOGIN_PKI, eidAppRegParam.getBizs().get(EBizType.BIZ_DIRECT_LOGIN_PKI.getIndex()));
        bizsMap.put(EBizType.BIZ_DIRECT_LOGIN_HMAC, eidAppRegParam.getBizs().get(EBizType.BIZ_DIRECT_LOGIN_HMAC.getIndex()));
        params.setBizs(bizsMap);
        params.setProvince(eidAppRegParam.getProvince());
        params.setCity(eidAppRegParam.getCity());
        params.setOrgType(EOrgType.getEnum(eidAppRegParam.getOrgType()));
        params.setContact1(eidAppRegParam.getContact1());
        params.setContact1Tel(eidAppRegParam.getContact1Tel());
        params.setContact1Email(eidAppRegParam.getContact1Email());
        params.setContact2(eidAppRegParam.getContact2());
        params.setContact2Tel(eidAppRegParam.getContact2Tel());
        params.setContact2Email(eidAppRegParam.getContact2Email());
        params.setRemark(eidAppRegParam.getRemark());
        params.setAppSalt(eidAppRegParam.getAppSalt());
        params.setRelatedAppid(eidAppRegParam.getRelatedAppid());

        log.info("app应用注册异步接口扩展字段传递的app信息表ID:"+eidAppRegParam.getRelatedAppid());

        params.setAppSalt("http://www.chneid.com");//计算索引值的盐
        params.setEncryptType(EEncryptType.getEnum(eidBaseParam.getEncryptType()));//3DES_ECB_PKCS5PADDING
        params.setVersion(eidBaseParam.getVersion());//指定版本号
//        params.setEncryptType(EEncryptType.getEnum("1"));//3DES_ECB_PKCS5PADDING
//        params.setVersion("2.0.0");//指定版本号

        // 改为异步
        AppRegService service = new AppRegService(new SHmacSha1Service(asKey), opAddress+"/app/register/async/"+params.getAsid()); // sync request
        JSONObject result = service.doRequestAsyn(params);// 异步注册返回（{"received":"true"}）
//        service.parseResponse("");// op异步回调处理
        log.info("ap应用注册异步返回数据:"+result);

        EidAppRegResult eidAppRegResult = new EidAppRegResult();
        if(result.containsKey("received"))
            eidAppRegResult.setStatus(result.getString("received").equals("true") ? 1 : 2);// op返回true，false，这里转为int标识1和2。适应以前app应用注册同步返回appid和appkey的数据模板设计
        else
            eidAppRegResult.setStatus(2);

        return eidAppRegResult;

//        AppRegService service = new AppRegService(new SHmacSha1Service(asKey), opAddress+"/app/register/async/"+params.getAsid()); // sync request
//        AppRegResult result = service.doRequestSyn(params);// 同步注册。已废弃

//        EidAppRegResult eidAppRegResult = new EidAppRegResult();
//        BeanMapperUtil.copy(result, eidAppRegResult);

//        log.info("appid:"+eidAppRegResult.getAppid());
//        log.info("appkeyfactor:"+eidAppRegResult.getAppkeyFactor());

        // as内部生成appid和appkeyfactor
//        EidAppRegResult eidAppRegResult = new EidAppRegResult();
//        BeanMapperUtil.copy(eidAppRegParam, eidAppRegResult);
//        // 生成apID规则
//        Long seqNum = jedisCluster.incr(RedisUtil.APP_ID_SEQUENCE_KEY);
//        if (seqNum >= 100000)
//            jedisCluster.set(RedisUtil.APP_ID_SEQUENCE_KEY, "0");
//        String appId = RedisUtil.generateAppId(seqNum.toString());
//
//        MD5Encrypt encoderMd5 = new MD5Encrypt(appId);
//        UUID uuid = UUID.randomUUID();
//        String token = encoderMd5.encode(uuid.toString());
//        eidAppRegResult.setAppid(appId);
//        eidAppRegResult.setAppkeyFactor(token);
//        return eidAppRegResult;
    }
}
