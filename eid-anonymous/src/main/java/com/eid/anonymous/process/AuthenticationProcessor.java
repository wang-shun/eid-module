package com.eid.anonymous.process;

import com.eid.common.enums.AuthenticationStatus;
import com.eid.common.enums.ErrorCode;
import com.eid.common.enums.ResCode;
import com.eid.common.exception.FacadeException;
import com.eid.common.model.Response;
import com.eid.common.model.param.req.EidBaseDTO;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.res.EidBaseResDTO;
import com.eid.common.model.param.result.EidBaseResult;
import com.eid.common.util.BeanMapperUtil;
import com.eid.common.util.DateUtil;
import com.eid.common.util.RedisUtil;
import com.eid.company.model.CompanyInfoDTO;
import com.eid.company.service.CompanyFacade;
import com.eid.connect.service.SendFacade;
import com.eid.dal.entity.CompanyAuthenticationEntity;
import com.eid.dal.manager.AuthenticationManager;
import com.eid.dal.manager.DispatchCmdManager;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.Date;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/17 Time:下午12:50
 */
@Slf4j
@Component
public abstract class AuthenticationProcessor extends AnnotationFactory {

    @Autowired(required = false)
    private SendFacade sendFacade;

    @Autowired(required = false)
    private CompanyFacade companyFacade;

    @Autowired(required = false)
    private JedisCluster jedisCluster;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DispatchCmdManager dispatchCmdManager;

    public EidBaseResDTO authentication(EidBaseDTO eidBaseDTO) {
        log.info("Call AuthenticationProcessor.authentication request:{};", eidBaseDTO);
        EidBaseResDTO eidBaseResDTO = new EidBaseResDTO();
        CompanyAuthenticationEntity companyAuthenticationEntity = new CompanyAuthenticationEntity();
        try {
            // 1. 拼装参数
            EidBaseParam eidBaseParam = getParam(eidBaseDTO);
            BeanMapperUtil.copy(eidBaseParam, eidBaseResDTO);

            System.out.println("eidBaseDTO.getApId()----------------------:"+eidBaseDTO.getApId());

            // 2. 获取companyId
            Response<CompanyInfoDTO> companyInfoDTOResponse = companyFacade.availableByApId(eidBaseDTO.getApId());
            if (!companyInfoDTOResponse.isSuccess() || Objects.equal(companyInfoDTOResponse.getResult(), null))
                throw new FacadeException(companyInfoDTOResponse.getErrorCode(), companyInfoDTOResponse.getErrorMsg());

            // 3. 认证请求入库
            BeanMapperUtil.copy(eidBaseParam, companyAuthenticationEntity);

            //这里设置apkey（companyInfo表中的apid和apkey已经取消，这里返回的companyInfoDTO已经没有apid和apkey）
//            eidBaseParam.setAppKey(companyInfoDTOResponse.getResult().getApKey());
            eidBaseParam.setAppKey(companyFacade.getApkeyFactor(eidBaseDTO.getApId()).getResult());

            companyAuthenticationEntity.setApId(eidBaseDTO.getApId());
            companyAuthenticationEntity.setAppId(eidBaseDTO.getAppId());
            companyAuthenticationEntity.setCompanyId(companyInfoDTOResponse.getResult().getCompanyId());
            companyAuthenticationEntity.setCreatedAt(new Date());
            companyAuthenticationEntity = authenticationManager.insert(companyAuthenticationEntity);
            log.info("Call authenticationManager.insert response:{};", companyAuthenticationEntity);

            // 4. ap、app是否有效，ap、app是否绑定关系
//            Response<Boolean> availableResponse = companyFacade.isAvailable(companyInfoDTOResponse.getResult().getCompanyId());
//            if (!companyInfoDTOResponse.isSuccess() || !availableResponse.getResult())
//                throw new FacadeException(companyInfoDTOResponse.getErrorCode(), companyInfoDTOResponse.getErrorMsg());

            // 5. 请求op认证
            Response<EidBaseResult> requestResponse = sendFacade.request(eidBaseParam);
            log.info("Call sendFacade.request response:{};", requestResponse);

            if (!requestResponse.isSuccess() || Objects.equal(requestResponse.getResult(), null))
                throw new FacadeException(requestResponse.getErrorCode(), requestResponse.getErrorMsg());

            EidBaseResult eidBaseResult = requestResponse.getResult();
            BeanMapperUtil.copy(eidBaseResult, eidBaseResDTO);
            eidBaseResDTO.setBizTime(eidBaseParam.getBizTime());
            eidBaseResDTO.setResultTime(DateUtil.getCurrent(DateUtil.timePattern));

            // TODO eID认证完成后进行计费，不管认证是否成功
            // 6. 更新认证结果
            if (Objects.equal(eidBaseResult.getResultDetail(), ResCode.EID_0000000.getCode())) {
                eidBaseResDTO.setAppEidCode(eidBaseResult.getResult());
                eidBaseResDTO.setStatus(AuthenticationStatus.SUCCESS.getCode());
                eidBaseResDTO.setUserIdInfo(getUserInfo(eidBaseParam));
                authenticationManager.success(companyAuthenticationEntity.getId(), eidBaseResult.getResultDetail(), ResCode.EID_0000000.getDesc(), eidBaseResult.getResult());
                // 7. 计费
                dispatchCmdManager.feeCommand(companyAuthenticationEntity.getId().toString());
            } else if (Objects.equal(eidBaseResult.getResultDetail(), ResCode.EID_0000001.getCode())) {
                eidBaseResDTO.setStatus(AuthenticationStatus.PROCESSING.getCode());
                authenticationManager.processing(companyAuthenticationEntity.getId());
                dispatchCmdManager.opNotifyCommand(companyAuthenticationEntity.getId().toString());
                log.info("call AuthenticationProcessor.authentication request:{};response:{};", eidBaseDTO, eidBaseResDTO);
                return eidBaseResDTO;
            } else {
                eidBaseResDTO.setStatus(AuthenticationStatus.FAILED.getCode());
                authenticationManager.failed(companyAuthenticationEntity.getId(), eidBaseResult.getResultDetail(), eidBaseResult.getResult());
            }

            // 通知
            if (!Strings.isNullOrEmpty(companyAuthenticationEntity.getReturnUrl()))
                dispatchCmdManager.notifyCommand(companyAuthenticationEntity.getId().toString());
        } catch (FacadeException e) {
            log.error("Failed to AuthenticationProcessor.authentication request:{};CAUSE:{}", eidBaseDTO, Throwables.getStackTraceAsString(e));
            eidBaseResDTO.setResultDetail(e.getCode());
            eidBaseResDTO.setResultMessage(e.getMessage());
            // 失败处理
            eidBaseResDTO.setStatus(AuthenticationStatus.FAILED.getCode());
            authenticationManager.failed(companyAuthenticationEntity.getId(), e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("Failed to AuthenticationProcessor.authentication request:{};CAUSE:{}", eidBaseDTO, Throwables.getStackTraceAsString(e));
            eidBaseResDTO.setResultDetail(ErrorCode.SYS_ERR.getCode());
            eidBaseResDTO.setResultMessage(e.getMessage());
            // 失败处理
            eidBaseResDTO.setStatus(AuthenticationStatus.FAILED.getCode());
            authenticationManager.failed(companyAuthenticationEntity.getId(), ErrorCode.SYS_ERR.getCode(), e.getMessage());
        }

        log.info("call AuthenticationProcessor.authentication request:{};response:{};", eidBaseDTO, eidBaseResDTO);
        return eidBaseResDTO;
    }

    private final String version = "2.0.0";

    protected void generateRecord(EidBaseParam eidBaseParam) {
        String bizSequenceId = DateUtil.getCurrent() + Strings.padStart(jedisCluster.incr(RedisUtil.BIZ_SEQUENCE_KEY) + "", 6, '0');
        eidBaseParam.setBizSequenceId(bizSequenceId);
        eidBaseParam.setBizTime(DateUtil.getCurrent(DateUtil.timePattern));
        eidBaseParam.setVersion(version);
        eidBaseParam.setEncryptType("");
        eidBaseParam.setSecurityType("");
        eidBaseParam.setSignType("");
    }

    protected abstract EidBaseParam getParam(EidBaseDTO eidBaseDTO);

    protected abstract String getUserInfo(EidBaseParam eidBaseParam);
}
