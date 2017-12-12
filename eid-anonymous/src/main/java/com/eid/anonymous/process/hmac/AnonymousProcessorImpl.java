package com.eid.anonymous.process.hmac;

import com.eid.anonymous.annotations.BizImpl;
import com.eid.anonymous.process.AuthenticationProcessor;
import com.eid.common.enums.BizType;
import com.eid.common.model.param.req.EidBaseDTO;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.request.hmac.EidHmacAnonymousParam;
import com.eid.common.util.BeanMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * 匿名登陆hmac
 * Created by:ruben Date:2017/2/8 Time:下午4:26
 */
@Slf4j
@Component("hmacAnonymousProcessorImpl")
@BizImpl(value = {BizType.ANONYMOUS_HMAC, BizType.ANONYMOUS_HMAC_ASYNC})
public class AnonymousProcessorImpl extends AuthenticationProcessor {

    @Override
    public EidBaseParam getParam(EidBaseDTO eidBaseDTO) {
        EidHmacAnonymousParam eidHmacAnonymousParam = new EidHmacAnonymousParam();
        BeanMapperUtil.copy(eidBaseDTO, eidHmacAnonymousParam);
        generateRecord(eidHmacAnonymousParam);
        eidHmacAnonymousParam.setAppId(eidBaseDTO.getApId());
        try {
            eidHmacAnonymousParam.setEidIdentification(new String(Base64.getEncoder().encode(eidHmacAnonymousParam.getIdCarrier().getBytes()), "UTF-8"));
        } catch (Exception e) {
            log.error("failed to base64 error message:{};", e.getMessage());
        }
        return eidHmacAnonymousParam;
    }

    @Override
    protected String getUserInfo(EidBaseParam eidBaseParam) {
        return null;
    }
}