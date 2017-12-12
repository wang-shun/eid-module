package com.eid.anonymous.process.pki;

import com.eid.anonymous.annotations.BizImpl;
import com.eid.anonymous.process.AuthenticationProcessor;
import com.eid.common.enums.BizType;
import com.eid.common.model.param.req.EidBaseDTO;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.request.pki.EidPkiAnonymousParam;
import com.eid.common.util.BeanMapperUtil;
import com.eid.dal.entity.CompanyAuthenticationEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * 匿名登陆pki
 * Created by:ruben Date:2017/2/8 Time:下午4:26
 */
@Slf4j
@Component("pkiAnonymousProcessorImpl")
@BizImpl(value = {BizType.ANONYMOUS_PKI, BizType.ANONYMOUS_PKI_ASYNC})
public class AnonymousProcessorImpl extends AuthenticationProcessor {

    @Override
    public EidBaseParam getParam(EidBaseDTO eidBaseDTO) {
        EidPkiAnonymousParam eidPkiAnonymousParam = new EidPkiAnonymousParam();
        BeanMapperUtil.copy(eidBaseDTO, eidPkiAnonymousParam);
        generateRecord(eidPkiAnonymousParam);
        eidPkiAnonymousParam.setAppId(eidBaseDTO.getApId());
        try {
            eidPkiAnonymousParam.setEidIdentification(new String(Base64.getEncoder().encode(eidPkiAnonymousParam.getEidSn().getBytes()), "UTF-8"));
        } catch (Exception e) {
            log.error("failed to base64 error message:{};", e.getMessage());
        }
        return eidPkiAnonymousParam;
    }

    @Override
    protected String getUserInfo(EidBaseParam eidBaseParam) {
        return null;
    }
}