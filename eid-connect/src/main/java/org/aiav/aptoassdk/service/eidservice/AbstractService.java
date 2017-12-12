package org.aiav.aptoassdk.service.eidservice;

import net.sf.json.JSONObject;

import org.aiav.aptoassdk.service.dataprotection.secresy.IEncryptService;
import org.aiav.aptoassdk.service.dataprotection.sign.ISignService;
import org.aiav.aptoassdk.util.FuncUtil;
import org.aiav.aptoassdk.util.ServiceUtil;

public abstract class AbstractService extends EidService {
    protected String url;

    public AbstractService(ISignService signService,
                           IEncryptService encryptService) {
        super(signService, encryptService);
    }

    protected abstract String buildReqParameters(Object params);

    protected abstract Object parseResponse(String resStr);

    protected String doRequest(Object params, boolean isSyn) {
        return buildReqParameters(params);
    }
    protected Object doRequestAP(Object params, boolean isSyn) {
        String requestStr = buildReqParameters(params);
        if (requestStr == null) {
            return null;
        }

        System.out.println("-----------–--------------------");
        String responseStr = ServiceUtil.doRequest(requestStr, url);
        System.out.println("request:" + requestStr);
        System.out.println("responseStr:" + responseStr);
        System.out.println("-----------–--------------------");
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
