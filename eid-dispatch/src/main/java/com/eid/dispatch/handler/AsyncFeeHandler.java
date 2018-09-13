package com.eid.dispatch.handler;

import com.eid.common.enums.AuthenticationStatus;
import com.eid.common.enums.FeeStatus;
import com.eid.common.enums.FeeType;
import com.eid.dal.dao.CompanyAgreementDao;
import com.eid.dal.dao.CompanyAuthenticationDao;
import com.eid.dal.dao.FeeConfigDao;
import com.eid.dal.entity.BizCmdEntity;
import com.eid.dal.entity.CompanyAgreementEntity;
import com.eid.dal.entity.CompanyAuthenticationEntity;
import com.eid.dal.entity.FeeConfigEntity;
import com.eid.dispatch.impl.BaseCmdHandler;
import com.eid.dispatch.process.AnnotationFactory;
import com.eid.dispatch.process.AsyncFeeProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * 描述类的作用
 * 异步计费处理入口
 * Created by:ruben Date:2017/3/8 Time:上午10:49
 */
@Slf4j
public class AsyncFeeHandler extends BaseCmdHandler {

    @Autowired
    private CompanyAuthenticationDao companyAuthenticationDao;

    @Autowired
    private FeeConfigDao feeConfigDao;

    @Autowired
    private CompanyAgreementDao companyAgreementDao;

    @Autowired
    private AnnotationFactory annotationFactory;

    /**
     * 异步计费处理入口
     *
     * @param command 任务命令
     * @throws Exception 处理异常
     */
    @Override
    protected void doCmd(BizCmdEntity command) throws Exception {
        String bizId = command.getBizId();
        log.info("异步计费开始----------------------------------------------||");
        log.info("get bizId:{};", bizId);

        // 根据bizId（就是商户id）查询认证记录
        CompanyAuthenticationEntity companyAuthenticationEntity = companyAuthenticationDao.findOne(Long.parseLong(bizId));
        log.info("call companyAuthenticationDao.findOne request:{};response:{};", bizId, companyAuthenticationEntity);
        if (!Objects.equals(AuthenticationStatus.SUCCESS.getCode(), companyAuthenticationEntity.getStatus()))
            return;

        // 根据company_id和服务类型查询到该商户签署不同协议的c_id
        // 新版本，兼容普通eID\SIMeId\非eID的认证收费
        String type = companyAuthenticationEntity.getBizType();// 获取认证的服务类型
        if(type.contains("HMAC") || type.contains("PKI"))// 验证如果该类型是eID的，则设置为指定标识，其他类型原样传递
            type = "EID_VERIFY_CODE";
        FeeConfigEntity feeConfigEntity = feeConfigDao.queryByCompanyIdAndType(companyAuthenticationEntity.getCompanyId(),type);
        // 原来的版本
//        FeeConfigEntity feeConfigEntity = feeConfigDao.findByCompanyId(companyAuthenticationEntity.getCompanyId());
        log.info("call feeConfigDao.findByCompanyId request:{};response:{};", companyAuthenticationEntity.getCompanyId(), feeConfigEntity);

        // 根据company_agreement的主键查询协议信息和收费类型
        CompanyAgreementEntity companyAgreementEntity = companyAgreementDao.findByIdAndStatus(feeConfigEntity.getCId(), FeeStatus.NORMAL.getCode());
        log.info("call companyAgreementDao.findOne request:{};response:{};", feeConfigEntity.getCId(), companyAgreementEntity);

        // 获取计费类型， 1：单笔计费，2：月结，3：阶梯计费
        FeeType feeType = FeeType.getEnum(companyAgreementEntity.getFeeType());
        AsyncFeeProcessor asyncFeeProcessor = annotationFactory.getFeeImpl(AsyncFeeProcessor.class, feeType);
        log.info("计费类型："+companyAgreementEntity.getFeeType());

        // 扣费
        asyncFeeProcessor.asyncFee(companyAgreementEntity, companyAuthenticationEntity);
    }

    @Override
    protected void failedFinally(BizCmdEntity command) {
        log.debug("重试业务号:{} failedFinally", command.getBizId());
    }
}