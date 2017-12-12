package com.eid.connect.biz;

import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.result.EidBaseResult;

/**
 * 发送业务类
 * Created by:ruben Date:2017/2/22 Time:下午2:36
 */
public interface SendBiz {

    /**
     * 发送至op
     *
     * @param eidBaseParam
     * @return
     */
    EidBaseResult send(EidBaseParam eidBaseParam);
}
