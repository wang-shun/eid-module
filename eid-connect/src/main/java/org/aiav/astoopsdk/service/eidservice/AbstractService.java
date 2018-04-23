package org.aiav.astoopsdk.service.eidservice;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.aiav.astoopsdk.service.dataprotection.sign.ISignService;
import org.aiav.astoopsdk.util.FuncUtil;
import org.aiav.astoopsdk.util.ServiceUtil;

@Slf4j
public abstract class AbstractService extends EidService {
    protected String url;

    public AbstractService(ISignService signService) {
        super(signService);
    }

    protected abstract String buildReqParameters(Object params);

    protected abstract Object parseResponse(String resStr);

    protected abstract JSONObject parseResponseJson(String resStr);

    // 完成请求签名并发送认证请求
    protected Object doRequest(Object params, boolean isSyn) {
        // 签名
        System.out.println("签名原文params：{}"+params);
        String requestStr = buildReqParameters(params);
        if (requestStr == null) {
            return null;
        }

        System.out.println("eID认证请求IDSO的完整参数：{}"+requestStr);
        String responseStr = ServiceUtil.doRequest(requestStr, url);
        System.out.println("eID认证返回的完整参数：{}"+responseStr);
        if (FuncUtil.isEmpty(responseStr)) {
            return null;
        }

        if (isSyn) {
            return parseResponse(responseStr);
        } else {
            return JSONObject.fromObject(responseStr);
        }
    }
}
