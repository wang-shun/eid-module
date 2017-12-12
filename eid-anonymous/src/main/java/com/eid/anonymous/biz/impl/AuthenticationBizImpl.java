package com.eid.anonymous.biz.impl;

import com.eid.anonymous.biz.AuthenticationBiz;
import com.eid.anonymous.process.AnnotationFactory;
import com.eid.anonymous.process.AuthenticationProcessor;
import com.eid.common.enums.BizType;
import com.eid.common.model.param.req.EidBaseDTO;
import com.eid.common.model.param.res.EidBaseResDTO;
import org.springframework.stereotype.Component;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/17 Time:下午12:43
 */
@Component
public class AuthenticationBizImpl extends AnnotationFactory implements AuthenticationBiz {

    @Override
    public EidBaseResDTO authentication(EidBaseDTO eidBaseDTO) {
        BizType bizType = BizType.parse(eidBaseDTO.getBizType());
        AuthenticationProcessor imps = getBizImpl(AuthenticationProcessor.class, bizType);
        return imps.authentication(eidBaseDTO);
    }
}
