package com.eid.anonymous.process.hmac;

import com.eid.anonymous.annotations.BizImpl;
import com.eid.anonymous.process.AuthenticationProcessor;
import com.eid.common.enums.BizType;
import com.eid.common.model.param.req.EidBaseDTO;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.request.hmac.EidHmacVerifyParam;
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
@Component("verifyHmacProcessorImpl")
@BizImpl(value = {BizType.VERIFY_HMAC, BizType.VERIFY_HMAC_ASYNC})
public class VerifyProcessorImpl extends AuthenticationProcessor {

    @Override
    public EidBaseParam getParam(EidBaseDTO eidBaseDTO) {
        EidHmacVerifyParam eidHmacVerifyParam = new EidHmacVerifyParam();
        BeanMapperUtil.copy(eidBaseDTO, eidHmacVerifyParam);
        generateRecord(eidHmacVerifyParam);
        eidHmacVerifyParam.setAppId(eidBaseDTO.getApId());
        try {
            eidHmacVerifyParam.setEidIdentification(new String(Base64.getEncoder().encode(eidHmacVerifyParam.getIdCarrier().getBytes()), "UTF-8"));
        } catch (Exception e) {
            log.error("failed to base64 error message:{};", e.getMessage());
        }
        return eidHmacVerifyParam;
    }

    @Override
    protected String getUserInfo(EidBaseParam eidBaseParam) {
        return null;
    }
}