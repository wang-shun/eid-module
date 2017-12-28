package com.eid.connect.sign;

import com.as.dev.DeviceService;
import com.eid.dev.constant.EDeviceSignType;
import com.eid.dev.constant.RC;
import com.eid.dev.pojo.key.rk2.GenAppkeyParameters;
import com.eid.dev.pojo.key.rk2.GenAppkeyResult;
import com.eid.dev.pojo.sign.rk2.AppkeySignParameters;
import com.eid.dev.pojo.sign.rk2.AppkeySignResult;
import org.aiav.astoopsdk.util.FuncUtil;
import org.aiav.crypto.EidCryptoUtils;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

/**
*
* AP TO AS HMAC_SHA1软key方式和加密机方式测试
*
* @author pdz 2017-12-18 上午 10:22
*
**/
public class HMAC_SHA1
{

    @Test
    public void test_hamc () throws Exception {
//        getAppKey();
        hmac_sha1();
        hmac_sha1("2");
    }

    // 加密机
    public void hmac_sha1() throws Exception {

        DeviceService ds = new DeviceService();

        String appid = "0PSJ1709291530051805";
        String appkeyFactor = "820948D2EF54CACD28722751D4031C7E";
        String signFactor = "6162636465666768";
        String data = "彭代忠";

        AppkeySignParameters params = new AppkeySignParameters();
        params.setAppid(appid);
        params.setAppkeyFactor(appkeyFactor);
        params.setSignFactor(signFactor);
        params.setSignType(EDeviceSignType.HMAC_SHA1);
        params.setData(data.getBytes("UTF-8"));
        AppkeySignResult dr = ds.signByAppkey(params);
        if (RC.SUCCESS.equals(dr.getResult())) {
            System.out.println("sign: " + new String(Base64.encodeBase64(dr.getSign()),"UTF-8"));
        } else {
            System.out.println("result: " + dr.getResult());
        }

        System.out.println("  as 使用加密机方式签名值："+new String(Base64.encodeBase64(dr.getSign())));

    }

    // 软加密
    public void hmac_sha1(String s) throws Exception {
        String appKey = "3F77EB4BC91E10C00231122A851EB867";
        String signFactor = "6162636465666768";
        String data = "彭代忠";
        String sign = null;
        byte[] result = EidCryptoUtils.createHMACSHA1(
				CryptoFuncUtil.getStringBytes(data),
                buildSM4Key(signFactor,appKey));
        if (!FuncUtil.isEmpty(result))
//			sign = CryptoFuncUtil.encodeBytesToBase64(result);
            sign = new String(Base64.encodeBase64(result),"UTF-8");

        System.out.println("  as 使用软加密方式签名值1："+sign);
    }

    protected byte[] buildSM4Key(String securityFactor, String appKey) {
        return EidCryptoUtils.encryptSM4ECB(
                CryptoFuncUtil.rebuildFactor(securityFactor),
                CryptoFuncUtil.hexStringToBytes(appKey));
    }

    protected byte[] rebuildFactor(String securityFactor) {
        byte[] factorHex = CryptoFuncUtil.hexStringToBytes(securityFactor);
        byte[] ret = new byte[16];
        for (int i = 0; i < factorHex.length; i++) {
            ret[i] = factorHex[i];
            ret[i + factorHex.length] = (byte) (factorHex[i] ^ 0xFFFFFFFF);
        }
        return ret;
    }

//    public String getAppKey (){
//        DeviceService ds = new DeviceService();
//        String appid = "0PSJ1709291530051805";
//        String appkeyFactor = "820948D2EF54CACD28722751D4031C7E";
//        GenAppkeyParameters param = new GenAppkeyParameters();
//        param.setAppid(appid);
//        param.setAppkeyFactor(appkeyFactor);
//        GenAppkeyResult dr = ds.genAppkey(param);
//        if (RC.SUCCESS.equals(dr.getResult())) {
//            System.out.println("appkey: " + dr.getAppkey());
//            return dr.getAppkey();
//        } else {
//            System.out.println("result: " + dr.getResult());
//            return null;
//        }
//
//    }

}
