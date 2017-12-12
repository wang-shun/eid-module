package org.aiav.astoopsdk.service.eidservice;

import net.sf.json.JSONObject;
import org.aiav.astoopsdk.service.dataprotection.sign.ISignService;
import org.aiav.astoopsdk.util.FuncUtil;
import org.aiav.astoopsdk.util.ServiceUtil;

public abstract class AbstractService extends EidService {
    protected String url;

    public AbstractService(ISignService signService) {
        super(signService);
    }

    protected abstract String buildReqParameters(Object params);

    protected abstract Object parseResponse(String resStr);

    protected Object doRequest(Object params, boolean isSyn) {
        String requestStr = buildReqParameters(params);
        if (requestStr == null) {
            return null;
        }

        System.out.println("request string:" + requestStr);
        String responseStr = ServiceUtil.doRequest(requestStr, url);
        System.out.println("response string:" + responseStr);
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
