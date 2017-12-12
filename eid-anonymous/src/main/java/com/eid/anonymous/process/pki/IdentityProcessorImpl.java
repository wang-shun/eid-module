package com.eid.anonymous.process.pki;

import com.eid.anonymous.annotations.BizImpl;
import com.eid.anonymous.process.AuthenticationProcessor;
import com.eid.common.enums.BizType;
import com.eid.common.model.param.req.EidBaseDTO;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.request.pki.EidPkiRealNameParam;
import com.eid.common.util.BeanMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * 实名认证pki
 * Created by:ruben Date:2017/2/8 Time:下午4:26
 */
@Slf4j
@Component("pkiIdentityProcessorImpl")
@BizImpl(value = {BizType.IDENTITY_PKI, BizType.IDENTITY_PKI_ASYNC})
public class IdentityProcessorImpl extends AuthenticationProcessor {

    @Override
    public EidBaseParam getParam(EidBaseDTO eidBaseDTO) {
        EidPkiRealNameParam eidPkiRealNameParam = new EidPkiRealNameParam();
        BeanMapperUtil.copy(eidBaseDTO, eidPkiRealNameParam);
        generateRecord(eidPkiRealNameParam);
        eidPkiRealNameParam.setAppId(eidBaseDTO.getApId());
        try {
            eidPkiRealNameParam.setEidIdentification(new String(Base64.getEncoder().encode(eidPkiRealNameParam.getEidSn().getBytes()), "UTF-8"));
        } catch (Exception e) {
            log.error("failed to base64 error message:{};", e.getMessage());
        }
        return eidPkiRealNameParam;
    }

    @Override
    protected String getUserInfo(EidBaseParam eidBaseParam) {
        return null;
    }
}