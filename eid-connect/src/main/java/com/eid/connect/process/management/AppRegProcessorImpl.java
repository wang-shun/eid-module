package com.eid.connect.process.management;

import com.eid.common.enums.BizType;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.request.management.EidAppRegParam;
import com.eid.common.model.param.result.EidBaseResult;
import com.eid.common.model.param.result.management.EidAppRegResult;
import com.eid.common.util.BeanMapperUtil;
import com.eid.common.util.MD5Encrypt;
import com.eid.common.util.RedisUtil;
import com.eid.connect.annotations.InterfaceImpl;
import com.eid.connect.process.SendProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

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
    private JedisCluster jedisCluster;

    @Override
    public EidBaseResult send(EidBaseParam eidBaseParam) {
        EidAppRegParam eidAppRegParam = (EidAppRegParam) eidBaseParam;
//        AppRegParams params = new AppRegParams();
//        params.setReturnUrl(eidAppRegParam.getReturnUrl());
//        params.setBizSequenceId(eidAppRegParam.getBizSequenceId());
//        params.setSignType(ESignType.getEnum(eidAppRegParam.getSignType()));
//        params.setSecurityType(ESecurityType.getEnum(eidAppRegParam.getSecurityType()));
//        params.setAsid(asId);
//        params.setAttach(eidAppRegParam.getAttach());
//        params.setAppInfo(eidAppRegParam.getAppInfo());
//        params.setAppName(eidAppRegParam.getAppName());
//        params.setCmpName(eidAppRegParam.getCmpName());
//        params.setDomainName(eidAppRegParam.getDomainName());
//        params.setIpAddr(eidAppRegParam.getIpAddr());
//        params.setDefaultSecurityType(ESecurityType.getEnum(eidAppRegParam.getDefaultSecurityType()));
//        params.setAppIcon(eidAppRegParam.getAppIcon());
//        Map<EBizType, String> bizsMap = Maps.newHashMap();
//        bizsMap.put(EBizType.BIZ_SIGN_VERIFY_HMAC, eidAppRegParam.getBizs().get(EBizType.BIZ_SIGN_VERIFY_HMAC.getIndex()));
//        bizsMap.put(EBizType.BIZ_SIGN_VERIFY_PKI, eidAppRegParam.getBizs().get(EBizType.BIZ_SIGN_VERIFY_PKI.getIndex()));
//        bizsMap.put(EBizType.REAL_NAME_SIGN_VERIFY_HMAC, eidAppRegParam.getBizs().get(EBizType.REAL_NAME_SIGN_VERIFY_HMAC.getIndex()));
//        bizsMap.put(EBizType.REAL_NAME_SIGN_VERIFY_PKI, eidAppRegParam.getBizs().get(EBizType.REAL_NAME_SIGN_VERIFY_PKI.getIndex()));
//        bizsMap.put(EBizType.BIZ_DIRECT_LOGIN_PKI, eidAppRegParam.getBizs().get(EBizType.BIZ_DIRECT_LOGIN_PKI.getIndex()));
//        bizsMap.put(EBizType.BIZ_DIRECT_LOGIN_HMAC, eidAppRegParam.getBizs().get(EBizType.BIZ_DIRECT_LOGIN_HMAC.getIndex()));
//        params.setBizs(bizsMap);
//        params.setProvince(eidAppRegParam.getProvince());
//        params.setCity(eidAppRegParam.getCity());
//        params.setOrgType(EOrgType.getEnum(eidAppRegParam.getOrgType()));
//        params.setContact1(eidAppRegParam.getContact1());
//        params.setContact1Tel(eidAppRegParam.getContact1Tel());
//        params.setContact1Email(eidAppRegParam.getContact1Email());
//        params.setContact2(eidAppRegParam.getContact2());
//        params.setContact2Tel(eidAppRegParam.getContact2Tel());
//        params.setContact2Email(eidAppRegParam.getContact2Email());
//        params.setRemark(eidAppRegParam.getRemark());
//        params.setAppSalt(eidAppRegParam.getAppSalt());
//        params.setRelatedAppid(eidAppRegParam.getRelatedAppid());
//
//        AppRegService service = new AppRegService(new SHmacSha1Service(asKey), opAddress + params.getAsid()); // sync request
//        AppRegResult result = service.doRequestSyn(params);
//        EidAppRegResult eidAppRegResult = new EidAppRegResult();
//        BeanMapperUtil.copy(result, eidAppRegResult);
//        return eidAppRegResult;

        EidAppRegResult eidAppRegResult = new EidAppRegResult();

        BeanMapperUtil.copy(eidAppRegParam, eidAppRegResult);
        // 生成apID规则
        Long seqNum = jedisCluster.incr(RedisUtil.APP_ID_SEQUENCE_KEY);
        if (seqNum >= 100000)
            jedisCluster.set(RedisUtil.APP_ID_SEQUENCE_KEY, "0");
        String appId = RedisUtil.generateAppId(seqNum.toString());

        MD5Encrypt encoderMd5 = new MD5Encrypt(appId);
        UUID uuid = UUID.randomUUID();
        String token = encoderMd5.encode(uuid.toString());
        eidAppRegResult.setAppid(appId);
        eidAppRegResult.setAppkeyFactor(token);
        return eidAppRegResult;
    }
}
