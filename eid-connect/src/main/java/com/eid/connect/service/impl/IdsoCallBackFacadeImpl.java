package com.eid.connect.service.impl;

import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.result.EidBaseResult;
import com.eid.connect.process.SendProcessor;
import com.eid.connect.service.IdsoCallBackFacade;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.aiav.astoopsdk.service.dataprotection.sign.impl.SHmacSha1Service;
import org.aiav.astoopsdk.service.eidservice.manage.AppRegService;
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

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("apid","0PSJ1709291530051806");
        jsonObject.put("appkey_factor","820948D2EF54CACD28722751D4031C7F");
        jsonObject.put("extension","40");
        http_post_json("http://192.168.0.3:8100/eid/api/back/idsoCallBack", jsonObject.toString());
    }

    /**
     * diso回调通知报文验签
     * @param requestData diso回调通知报文
     * @return boolean
     */
    @Override
    public com.alibaba.fastjson.JSONObject idsoCallBackVerifySign(String requestData) {

        log.info("-------------idso回调通知原始数据:"+requestData);

        // A:idso回调验签
        AppRegService service = new AppRegService(new SHmacSha1Service(asKey), opAddress+"/app/register/async/"+asId); // async request
        JSONObject appRegResult = service.parseResponseJson(requestData);// 有扩展字段
        //        AppRegResult appRegResult = service.parseResponse(requestData);// 没有扩展数据


        // 测试排除验签
//        JSONObject appRegResult = JSONObject.fromObject(requestData);
        return com.alibaba.fastjson.JSONObject.parseObject(appRegResult.toString());
    }

    @Override
    public EidBaseResult send(EidBaseParam eidBaseParam) {
        return null;
    }
}
