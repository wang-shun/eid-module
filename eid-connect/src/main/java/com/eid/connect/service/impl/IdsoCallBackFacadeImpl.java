package com.eid.connect.service.impl;

import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.result.EidBaseResult;
import com.eid.connect.process.SendProcessor;
import com.eid.connect.process.async.pki.SecurityUtils;
import com.eid.connect.service.IdsoCallBackFacade;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.aiav.aptoassdk.service.dataprotection.secresy.impl.SDesedeService;
import org.aiav.aptoassdk.service.dataprotection.secresy.impl.SSm4Service;
import org.aiav.astoopsdk.constants.ESignType;
import org.aiav.astoopsdk.service.dataprotection.sign.impl.SHmacMd5Service;
import org.aiav.astoopsdk.service.dataprotection.sign.impl.SHmacSha1Service;
import org.aiav.astoopsdk.service.dataprotection.sign.impl.SHmacSha256Service;
import org.aiav.astoopsdk.service.dataprotection.sign.impl.SHmacSm3Service;
import org.aiav.astoopsdk.service.eidservice.manage.AppRegService;
import org.aiav.astoopsdk.service.eidservice.params.result.manage.AppRegResult;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

/**
*
* idso回调通知服务处理类
*
* @author pdz 2017-12-26 下午 4:33
*
**/
@Slf4j
@Service
public class IdsoCallBackFacadeImpl extends SendProcessor implements IdsoCallBackFacade
{

    //-----------------------------测试idso回调
    public static String http_post_json(String url,String param)
    {
        // 创建HttpClient对象
        DefaultHttpClient client = new DefaultHttpClient();

        // 2)DefaultHttpClient设置超时
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,10000);// 连接超时
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,30000);// 响应超时

        // 创建请求方法实例并指定请求地址
        HttpPost post = new HttpPost(url);

        // 设置请求头信息
        post.setHeader("Content-type", "application/json;charset=UTF-8");

        try
        {
            // 设置请求参数 （此处json）
            StringEntity s = new StringEntity(param.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);

            // 发送请求
            HttpResponse res = client.execute(post);

            // 获取响应吗
            int i = res.getStatusLine().getStatusCode();
            System.out.println(i);
            if(i == HttpStatus.SC_OK)
            {
                // 获取返回数据
                HttpEntity entity = res.getEntity();
                String result = EntityUtils.toString(entity);// 返回json格式
                System.out.println(result);

                return result;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("sign_type","1");
//        jsonObject.put("encrypt_type","1");
//        jsonObject.put("biz_sequence_id","20180329114149000016");
//        jsonObject.put("version","2.0.0");
//        jsonObject.put("return_url","http://chneid.com/eid/api/back/register");
//        jsonObject.put("biz_time","20180329114150");
//        jsonObject.put("biz_type","0104001");
//        JSONObject jsonObject1 = new JSONObject();
//        jsonObject1.put("encrypt_factor","638ACC77BABB4B7B");
//        jsonObject1.put("sign_factor","E09CCFF149254976");
//        jsonObject.put("security_factor",jsonObject1.toString());
//        jsonObject.put("app_id","0PSJ1709291530051805");
//        jsonObject.put("idsp_id","0PSJ1709210854202051");
//        jsonObject.put("user_id_info","CE/axtVBspERLOfMMBsgXkywy1UCDsRsOlHJdza8+acpfL4spFo5bITrUohRZ7EcZ5pni3/N7fw=");
//        jsonObject.put("data_to_sign","Q3Raj1+7lHvG4ktbenxrhA==");
//        jsonObject.put("msisdn","15877778888");
//        jsonObject.put("data_to_be_displayed","dataToBeDisplayedtest");
//        jsonObject.put("extension","");
//        jsonObject.put("sign","OlaR7lwdFmLI22q5AD/wGABXytc=");
//        String result = http_post_json("http://222.240.1.18:8022/eid/api/interface/idspserver/authorize/async/test", jsonObject.toString());
////        String result = http_post_json("http://192.168.0.3:8100/eid/api/back/idsp/simeid", jsonObject.toString());
////        String result = http_post_json("http://222.240.1.18:8022/eid/api/back/idsp/simeid", "disp");
//        System.out.println("result:{}"+result);
//    }
    //-----------------------------测试idso回调

    /**
     * idso回调通知报文验签
     * @param requestData diso回调通知报文
     * @return boolean
     */
    @Override
    public com.alibaba.fastjson.JSONObject idsoCallBackVerifySign(String requestData) {

        log.info("idso回调通知报文验签，原始数据:"+requestData);

        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(requestData);

        // IDSO回调签名算法
        String signType = jsonObject.getString("sign_type");
        log.info("IDSO回调签名算法：{}",signType);
        AppRegService service = createSign(Integer.valueOf(signType));

        // A:idso回调验签
//        AppRegService service = new AppRegService(new SHmacSha1Service(asKey), opAddress+"/app/register/async/"+asId); // async request
        JSONObject resultData = service.parseResponseJson(requestData);// 有扩展字段
//        AppRegResult appRegResult = service.parseResponse(requestData);// 没有扩展数据
        log.info("idso回调通知报文验签结果：{}",resultData);

        // 测试排除验签
//        JSONObject appRegResult = JSONObject.fromObject(requestData);
        return com.alibaba.fastjson.JSONObject.parseObject(resultData.toString());
    }

    /**
     * 暴露给IDSP响应IDSO参数的签名使用
     * @return
     */
    public String createSign(String data, String signFacotr) {
        SHmacSha1Service sHmacSha1Service = new SHmacSha1Service(asKey);
        return sHmacSha1Service.createSign(data,signFacotr);
    }

//    // 3des解密参数(使用IDSO提供的SDK进行解密，这种方式不能解密IDSO通过加密机加密的参数)
//    public String doEncrypt3DesECBPKCS5(String appKey, String data, String factor){
//
//        System.out.println("appKey:"+appKey+";       data:"+data+";         factor:"+factor);
//        SDesedeService sDesedeService = new SDesedeService(appKey);
//        return sDesedeService.doDecrypt(data,factor);
//    }

//    public static void main(String[] args) throws Exception
//    {
////        g3cCwjcfY5IZ5MA/5oFCHG/XTihGKDfp3AALYc3Q5yIP5u0X+nDuSMlim3Q6smcSg/wICWt9eSo=
//        IdsoCallBackFacadeImpl idsoCallBackFacade = new IdsoCallBackFacadeImpl();
//        System.out.println(idsoCallBackFacade.doEncrypt3DesECBPKCS5("0FF7EEDC3255E887C3960949869B4A53","goNKUzPDPsRyUV6dy8pIiWCLzK+1dVERDuOW94AIdyeNynvxzEkLtoY81Ad/bz75yENdKrZkKUrhQixm+Tpptg==","AD972567F8864FF4"));
//    }

    // 3des解密参数(使用IDSO单独提供的解密方式，这种方式能解密IDSO加密机，这种方式是和加密机配套的)
    public String doEncrypt3DesECBPKCS5(String appKey, String data, String factor){

        return  SecurityUtils.do3desDecrypt(data,appKey,factor);
    }

    // ssm4解密参数
    public String doEncryptSSm4(String appKey, String data, String factor){
        SSm4Service sSm4Service = new SSm4Service(appKey);
        return sSm4Service.doDecrypt(data,factor);
    }

    // 创建签名算法
    private AppRegService createSign (int signType){
        switch (signType) {
            case 1:
                return new AppRegService(new SHmacSha1Service(asKey), opAddress+"/app/register/async/"+asId); // async request
            case 2:
                return new AppRegService(new SHmacSha256Service(asKey), opAddress+"/app/register/async/"+asId); // async request
            case 3:
                return new AppRegService(new SHmacMd5Service(asKey), opAddress+"/app/register/async/"+asId); // async request
            case 4:
                return new AppRegService(new SHmacSm3Service(asKey), opAddress+"/app/register/async/"+asId); // async request
            default:
                return new AppRegService(new SHmacSha1Service(asKey), opAddress+"/app/register/async/"+asId); // async request
        }
    }

    @Override
    public EidBaseResult send(EidBaseParam eidBaseParam) {
        return null;
    }
}
