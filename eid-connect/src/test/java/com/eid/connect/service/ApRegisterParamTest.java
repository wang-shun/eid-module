package com.eid.connect.service;

import com.alibaba.fastjson.JSONObject;
import com.eid.connect.BaseServiceTest;
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
import org.aiav.astoopsdk.constants.EBizType;
import org.aiav.astoopsdk.constants.EOrgType;
import org.aiav.astoopsdk.service.eidservice.manage.AppRegService;
import org.aiav.astoopsdk.service.eidservice.params.request.manage.AppRegParams;
import org.aiav.astoopsdk.service.eidservice.params.result.biz.hmac.HmacBizDirectLoginResult;
import org.aiav.astoopsdk.service.eidservice.params.result.manage.AppRegResult;
import org.aiav.crypto.util.CryptoFuncUtil;
import org.junit.Test;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/4/12 Time:上午9:54
 */
@Slf4j
public class ApRegisterParamTest extends BaseServiceTest {

    //    private String asId = "06YD1606290925100648";
//    private String asKey = "1AF9188630E6BE44B422AAE166CF6E8B";
    private String asId = "3BSJ1709291449000831";
    private String asKey = "7B85B147E431348DEE480152CC40104E";

    @Test
    public void paramTest() {
        AppRegParams params = new AppRegParams();
        params.setReturnUrl("http://chneid.com/library/api/wechat/back");
        params.setSignType(org.aiav.astoopsdk.constants.ESignType.HMAC_SHA1);
        params.setSecurityType(org.aiav.astoopsdk.constants.ESecurityType.SKEY);
        params.setAsid(asId);
        params.setAttach("");
        params.setAppInfo("eID云，eID云平台");
        params.setAppName("eID云");
        params.setCmpName("壹证通科技有限公司");
        params.setDomainName("www.chneid.com");
        params.setIpAddr("110.53.131.198");
        params.setDefaultSecurityType(org.aiav.astoopsdk.constants.ESecurityType.getEnum("10"));
        params.setAppIcon(CryptoFuncUtil.encodeBytesToBase64("eID".getBytes()));
        Map<EBizType, String> bizsMap = new HashMap<>();
        bizsMap.put(EBizType.BIZ_SIGN_VERIFY_HMAC, "");
        bizsMap.put(EBizType.BIZ_SIGN_VERIFY_PKI, "");
        bizsMap.put(EBizType.REAL_NAME_SIGN_VERIFY_HMAC, "");
        bizsMap.put(EBizType.REAL_NAME_SIGN_VERIFY_PKI, "");
        bizsMap.put(EBizType.BIZ_DIRECT_LOGIN_PKI, "");
        bizsMap.put(EBizType.BIZ_DIRECT_LOGIN_HMAC, "");
        params.setBizs(bizsMap);
        params.setEncryptType(org.aiav.astoopsdk.constants.EEncryptType.TRIPLE_DES_ECB_PKCS5PADDING);
        params.setProvince("430000");
        params.setCity("430100");
        params.setOrgType(EOrgType.getEnum("01"));
        params.setContact1("曹学宇");
        params.setContact1Tel("18932465589");
        params.setContact1Email("James6116@163.com");
        params.setContact2("ruben");
        params.setContact2Tel("18108440899");
        params.setContact2Email("rubenchan@live.com");
        params.setRemark("eID");
        params.setAppSalt("test");
        params.setRelatedAppid("");

        String address = "http://124.207.4.75:18081/asserver/rest/app/register/async/" + params.getAsid();
        try {
            AppRegService service = new AppRegService(new org.aiav.astoopsdk.service.dataprotection.sign.impl.SHmacSha1Service(asKey), address); // sync request
            service.doRequestAsyn(params);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
