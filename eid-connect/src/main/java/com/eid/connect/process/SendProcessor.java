package com.eid.connect.process;

import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.result.EidBaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * 接口业务类
 * Created by:ruben Date:2017/2/8 Time:下午3:58
 */
@Slf4j
@Component
public abstract class SendProcessor extends AnnotationFactory {

    // as id
    @Value("#{app.asId}")
    protected String asId;

    // as key
    @Value("#{app.asKey}")
    protected String asKey;

    // op ip address
    @Value("#{app.opAddress}")
    protected String opAddress;

    // op ip address
    @Value("#{app.returnUrl}")
    protected String returnUrl;

    public abstract EidBaseResult send(EidBaseParam eidBaseParam);
}
