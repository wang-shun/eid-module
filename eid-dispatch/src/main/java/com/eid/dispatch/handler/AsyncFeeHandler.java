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
        log.info("get bizId:{};", bizId);

        CompanyAuthenticationEntity companyAuthenticationEntity = companyAuthenticationDao.findOne(Long.parseLong(bizId));
        log.info("call companyAuthenticationDao.findOne request:{};response:{};", bizId, companyAuthenticationEntity);
        if (!Objects.equals(AuthenticationStatus.SUCCESS.getCode(), companyAuthenticationEntity.getStatus()))
            return;

        FeeConfigEntity feeConfigEntity = feeConfigDao.findByCompanyId(companyAuthenticationEntity.getCompanyId());
        log.info("call feeConfigDao.findByCompanyId request:{};response:{};", companyAuthenticationEntity.getCompanyId(), feeConfigEntity);
        CompanyAgreementEntity companyAgreementEntity = companyAgreementDao.findByIdAndStatus(feeConfigEntity.getCId(), FeeStatus.NORMAL.getCode());
        log.info("call companyAgreementDao.findOne request:{};response:{};", feeConfigEntity.getCId(), companyAgreementEntity);

        FeeType feeType = FeeType.getEnum(companyAgreementEntity.getFeeType());
        AsyncFeeProcessor asyncFeeProcessor = annotationFactory.getFeeImpl(AsyncFeeProcessor.class, feeType);
        asyncFeeProcessor.asyncFee(companyAgreementEntity, companyAuthenticationEntity);
    }

    @Override
    protected void failedFinally(BizCmdEntity command) {
        log.debug("重试业务号:{} failedFinally", command.getBizId());
    }
}