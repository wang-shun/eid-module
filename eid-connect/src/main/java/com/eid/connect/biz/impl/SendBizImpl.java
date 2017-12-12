package com.eid.connect.biz.impl;

import com.eid.common.enums.BizType;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.result.EidBaseResult;
import com.eid.connect.biz.SendBiz;
import com.eid.connect.enums.InterfaceType;
import com.eid.connect.process.AnnotationFactory;
import com.eid.connect.process.SendProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/2/22 Time:下午2:36
 */
@Slf4j
@Component
public class SendBizImpl extends AnnotationFactory implements SendBiz {

    @Override
    public EidBaseResult send(EidBaseParam eidBaseParam) {
        BizType bizType = BizType.parse(eidBaseParam.getBizType());
        SendProcessor imps = getBizImpl(SendProcessor.class, bizType);
        return imps.send(eidBaseParam);
    }
}
