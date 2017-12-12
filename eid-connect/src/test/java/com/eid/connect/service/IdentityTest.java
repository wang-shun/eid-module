package com.eid.connect.service;

import com.alibaba.fastjson.JSONObject;
import com.eid.connect.BaseServiceTest;
import com.eidlink.sdk.a.o;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.aiav.aptoassdk.constants.*;
import org.aiav.aptoassdk.service.dataprotection.secresy.impl.SDesedeService;
import org.aiav.aptoassdk.service.dataprotection.sign.impl.SHmacSha1Service;
import org.aiav.aptoassdk.service.eidservice.biz.hmac.HmacRNVService;
import org.aiav.aptoassdk.service.eidservice.params.request.biz.hmac.HmacRNVParams;
import org.aiav.astoopsdk.constants.EBizType;
import org.aiav.crypto.util.CryptoFuncUtil;
import org.junit.Test;

import java.util.Base64;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/4/12 Time:上午9:54
 */
@Slf4j
public class IdentityTest extends BaseServiceTest {


    @Test
    public void identityHmacAsyncTest() throws Exception {
        String decode = new String(Base64.getDecoder().decode("QhDlshIKeIvmTcGAn6MZ6elnW9mgatzPp3hAJrfxwPqB6hLHOHo6EesMEN+o4eXo9BhUKOvdvpAaOahrG5ZcZ5g42pLI4GU3/bW++rGgLAbf3v6vpBaNYXP/PJgEK+t2G+8+bFEPJq6DrxMnxFZhZw=="));
//        String decode = new String(Base64.getDecoder().decode("MjAxNzEwMTkxNTMzMTc6O04IVBIDFmRmcA0MTywpAjowM0IxMDNFQzMzQUI0NUJDODQ0MTQwOTgyQzI0NzdGQQ=="));
        System.out.println(decode);
//        dataToSign:;idCarrier:123;eidSignAlgorithm:456;eidSign:k3DIryatD9zTQh5L/my2nRm/SLg=;
        String encode = new String(Base64.getEncoder().encode("100C000001CFEA10".getBytes()));
        System.out.println(encode);






        System.out.println(org.apache.commons.codec.binary.Base64.encodeBase64String(CryptoFuncUtil.hexStringToBytes("100C000001CFEA10")));




    }
}
