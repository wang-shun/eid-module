package com.eid.connect.process.management;

import com.eid.common.enums.BizType;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.request.management.EidAppkeyUpdateParam;
import com.eid.common.model.param.result.EidBaseResult;
import com.eid.common.model.param.result.management.EidAppkeyUpdateResult;
import com.eid.common.util.BeanMapperUtil;
import com.eid.common.util.MD5Encrypt;
import com.eid.connect.annotations.InterfaceImpl;
import com.eid.connect.process.SendProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 应用秘钥信息更换
 * Created by:ruben Date:2017/2/8 Time:下午4:26
 */
@Slf4j
@Component("appKeyUpdateProcessorImpl")
@InterfaceImpl(value = {BizType.APP_KEY_UPDATE})
public class AppKeyUpdateProcessorImpl extends SendProcessor {

    @Override
    public EidBaseResult send(EidBaseParam eidBaseParam) {
        EidAppkeyUpdateParam eidAppkeyUpdateParam = (EidAppkeyUpdateParam) eidBaseParam;
//        AppkeyUpdateParams params = new AppkeyUpdateParams();
//        params.setReturnUrl(eidAppkeyUpdateParam.getReturnUrl());
//        params.setBizSequenceId(eidAppkeyUpdateParam.getBizSequenceId());
//        params.setSignType(ESignType.getEnum(eidAppkeyUpdateParam.getSignType()));
//        params.setSecurityType(ESecurityType.getEnum(eidAppkeyUpdateParam.getSecurityType()));
//        params.setAsid(asId);
//        params.setAttach(eidAppkeyUpdateParam.getAttach());
//        params.setAppid(eidAppkeyUpdateParam.getAppid());
//
//        AppkeyUpdateService service = new AppkeyUpdateService(new SHmacSha1Service(asKey), opAddress + params.getAsid()); // sync request
//        AppkeyUpdateResult akur = service.doRequestSyn(params);
//        EidAppkeyUpdateResult eidAppkeyUpdateResult = new EidAppkeyUpdateResult();
//        BeanMapperUtil.copy(akur, eidAppkeyUpdateResult);
//        return eidAppkeyUpdateResult;

        EidAppkeyUpdateResult eidAppkeyUpdateResult = new EidAppkeyUpdateResult();
        BeanMapperUtil.copy(eidAppkeyUpdateParam, eidAppkeyUpdateResult);
        MD5Encrypt encoderMd5 = new MD5Encrypt(eidAppkeyUpdateParam.getAppid());
        UUID uuid = UUID.randomUUID();
        eidAppkeyUpdateResult.setAppkeyFactor(encoderMd5.encode(uuid.toString()));
        return eidAppkeyUpdateResult;
    }
}
