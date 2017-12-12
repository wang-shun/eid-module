package com.eid.anonymous.process.pki;

import com.eid.anonymous.annotations.BizImpl;
import com.eid.anonymous.process.AuthenticationProcessor;
import com.eid.common.enums.BizType;
import com.eid.common.model.param.req.EidBaseDTO;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.request.pki.EidPkiAnonymousParam;
import com.eid.common.model.param.request.pki.EidPkiVerifyParam;
import com.eid.common.util.BeanMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * 匿名登陆pki
 * Created by:ruben Date:2017/2/8 Time:下午4:26
 */
@Slf4j
@Component("verifyPkiProcessorImpl")
@BizImpl(value = {BizType.VERIFY_PKI, BizType.VERIFY_PKI_ASYNC})
public class VerifyProcessorImpl extends AuthenticationProcessor {

    @Override
    public EidBaseParam getParam(EidBaseDTO eidBaseDTO) {
        EidPkiVerifyParam eidPkiVerifyParam = new EidPkiVerifyParam();
        BeanMapperUtil.copy(eidBaseDTO, eidPkiVerifyParam);
        generateRecord(eidPkiVerifyParam);
        eidPkiVerifyParam.setAppId(eidBaseDTO.getApId());
        try {
            eidPkiVerifyParam.setEidIdentification(new String(Base64.getEncoder().encode(eidPkiVerifyParam.getEidSn().getBytes()), "UTF-8"));
        } catch (Exception e) {
            log.error("failed to base64 error message:{};", e.getMessage());
        }
        return eidPkiVerifyParam;
    }

    @Override
    protected String getUserInfo(EidBaseParam eidBaseParam) {
        return null;
    }
}