package com.eid.dispatch.handler;

import com.eid.common.enums.AuthenticationStatus;
import com.eid.common.exception.FacadeException;
import com.eid.dal.dao.CompanyAuthenticationDao;
import com.eid.dal.entity.BizCmdEntity;
import com.eid.dal.entity.CompanyAuthenticationEntity;
import com.eid.dispatch.biz.NotifyBiz;
import com.eid.dispatch.impl.BaseCmdHandler;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/8 Time:上午10:49
 */
@Slf4j
public class AsyncNotifyHandler extends BaseCmdHandler {

    @Autowired
    private CompanyAuthenticationDao companyAuthenticationDao;

    @Autowired
    private NotifyBiz notifyBiz;

    /**
     * 异步通知处理入口
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
        if (!Objects.equals(AuthenticationStatus.FAILED.getCode(), companyAuthenticationEntity.getStatus()) &&
                !Objects.equals(AuthenticationStatus.SUCCESS.getCode(), companyAuthenticationEntity.getStatus())) {
            log.info("无需通知");
            return;
        }

        try {
            notifyBiz.notify(companyAuthenticationEntity);
        } catch (FacadeException e) {
            log.error("Failed to NotifyBiz.notify request:{};\nerrorMessage:{}", companyAuthenticationEntity, Throwables.getStackTraceAsString(e));
            throw e;
        } catch (Exception e) {
            log.error("Failed to NotifyBiz.notify request:{};\nerrorMessage:{}", companyAuthenticationEntity, Throwables.getStackTraceAsString(e));
            throw e;
        }

    }

    @Override
    protected void failedFinally(BizCmdEntity command) {
        log.debug("重试业务号:{} failedFinally", command.getBizId());
    }
}