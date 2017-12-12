package com.eid.anonymous.process.hmac;

import com.eid.anonymous.annotations.BizImpl;
import com.eid.anonymous.process.AuthenticationProcessor;
import com.eid.common.enums.BizType;
import com.eid.common.model.param.req.EidBaseDTO;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.request.hmac.EidHmacRealNameParam;
import com.eid.common.util.BeanMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * 实名认证hmac
 * Created by:ruben Date:2017/2/8 Time:下午4:26
 */
@Slf4j
@Component("hmacIdentityProcessorImpl")
@BizImpl(value = {BizType.IDENTITY_HMAC, BizType.IDENTITY_HMAC_ASYNC})
public class IdentityProcessorImpl extends AuthenticationProcessor {

    @Override
    public EidBaseParam getParam(EidBaseDTO eidBaseDTO) {
        EidHmacRealNameParam eidHmacRealNameParam = new EidHmacRealNameParam();
        BeanMapperUtil.copy(eidBaseDTO, eidHmacRealNameParam);
        generateRecord(eidHmacRealNameParam);
        eidHmacRealNameParam.setAppId(eidBaseDTO.getApId());
        try {
            eidHmacRealNameParam.setEidIdentification(new String(Base64.getEncoder().encode(eidHmacRealNameParam.getIdCarrier().getBytes()), "UTF-8"));
        } catch (Exception e) {
            log.error("failed to base64 error message:{};", e.getMessage());
        }
        return eidHmacRealNameParam;
    }

    @Override
    protected String getUserInfo(EidBaseParam eidBaseParam) {
        return null;
    }
}