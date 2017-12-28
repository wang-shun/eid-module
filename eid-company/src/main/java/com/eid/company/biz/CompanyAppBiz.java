package com.eid.company.biz;

import com.alibaba.fastjson.JSONObject;
import com.eid.common.model.param.request.management.EidAppRegParam;
import com.eid.common.model.param.request.management.EidAppkeyUpdateParam;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/2/20 Time:下午3:59
 */
public interface CompanyAppBiz {

    /**
     * 获取ap信息
     *
     * @param eidAppRegParam
     * @return
     */
    Boolean get(EidAppRegParam eidAppRegParam);

    /**
     * 获取app信息
     *
     * @param eidAppRegParam
     * @return
     */
    Boolean getAppId(EidAppRegParam eidAppRegParam);

    /**
     * 重置app信息
     *
     * @param eidAppkeyUpdateParam
     * @return
     */
    Boolean resetAppKey(EidAppkeyUpdateParam eidAppkeyUpdateParam);

    /**
     * 重置ap信息
     *
     * @param eidAppkeyUpdateParam
     * @return
     */
    Boolean resetApKey(EidAppkeyUpdateParam eidAppkeyUpdateParam);

    /**
     * 初始化app信息表中的id和key
     * @param requestData idso回调报文
     * @return boolean
     */
    Boolean initIdAndKey(JSONObject requestData);

}
