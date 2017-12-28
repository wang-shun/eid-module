package org.aiav.astoopsdk.util;

import com.as.dev.DeviceService;
import com.eid.dev.constant.EDeviceSignType;
import com.eid.dev.constant.RC;
import com.eid.dev.pojo.key.rk2.GenAppkeyParameters;
import com.eid.dev.pojo.key.rk2.GenAppkeyResult;
import com.eid.dev.pojo.key.rk2.GenAskeyResult;
import com.eid.dev.pojo.sign.rk2.AppkeySignParameters;
import com.eid.dev.pojo.sign.rk2.AppkeySignResult;
import com.eid.dev.pojo.sign.rk2.AskeySignParameters;
import com.eid.dev.pojo.sign.rk2.AskeySignResult;
import org.apache.commons.codec.binary.Base64;

/**
*
* 加密机api
*
* @author pdz 2017-12-20 上午 11:13
*
**/
public class EncryptionMachine 
{

    /**
     * 获取实际的appkey
     * @param appid
     * @param appkeyFactor
     * @return
     */
    public String getAppkey(String appid, String appkeyFactor) {

        DeviceService ds = new DeviceService();
        GenAppkeyParameters param = new GenAppkeyParameters();
        param.setAppid(appid);
        param.setAppkeyFactor(appkeyFactor);
        GenAppkeyResult dr = ds.genAppkey(param);

        System.out.println("getAppKey result :" + dr.getResult());

        if (RC.SUCCESS.equals(dr.getResult()))
            return dr.getAppkey();

        return null;
    }

    /**
     * 获取实际的askey
     * @param askeyFactor
     * @return
     */
    public String getAskey(String askeyFactor) {

        DeviceService ds = new DeviceService();
        GenAskeyResult dr = ds.genAskey(askeyFactor);

        System.out.println("getAskey result :"+dr.getResult());

        if (RC.SUCCESS.equals(dr.getResult()))
            return dr.getAskey();

        return null;
    }

    /**
     * ap到as签名
     * @param appid
     * @param appkeyFactor
     * @param signFactor
     * @param data
     * @param type
     * @return
     * @throws Exception
     */
    public String apToAsSign(String appid, String appkeyFactor, String signFactor, String data, EDeviceSignType type) throws Exception{

        DeviceService ds = new DeviceService();

        AppkeySignParameters params = new AppkeySignParameters();
        params.setAppid(appid);
        params.setAppkeyFactor(appkeyFactor);
        params.setSignFactor(signFactor);
        params.setSignType(type);
        params.setData(data.getBytes("UTF-8"));

        AppkeySignResult dr = ds.signByAppkey(params);

        if (RC.SUCCESS.equals(dr.getResult()))
            return new String(Base64.encodeBase64(dr.getSign()),"UTF-8");// 将sign进行base64编码

        return null;
    }

    /**
     * as到op签名
     * @param data
     * @param signFactor
     * @param askeyFactor
     * @param type
     * @return
     * @throws Exception
     */
    public String asToOpSign(String data, String signFactor, String askeyFactor, EDeviceSignType type) throws Exception {

        DeviceService ds = new DeviceService();

        AskeySignParameters params = new AskeySignParameters();
        params.setAskeyFactor(askeyFactor);
        params.setSignFactor(signFactor);
        params.setSignType(type);
        params.setData(data.getBytes("UTF-8"));

        AskeySignResult dr = ds.signByAskey(params);

        if (RC.SUCCESS.equals(dr.getResult()))
            return new String(Base64.encodeBase64(dr.getSign()),"UTF-8");// 将sign进行base64编码

        return null;
    }

}
