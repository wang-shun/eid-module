package com.eid.dispatch.handler;

import com.eid.common.enums.AuthenticationStatus;
import com.eid.common.enums.ResCode;
import com.eid.dal.dao.CompanyAuthenticationDao;
import com.eid.dal.entity.BizCmdEntity;
import com.eid.dal.entity.CompanyAuthenticationEntity;
import com.eid.dal.manager.DispatchCmdManager;
import com.eid.dispatch.impl.BaseCmdHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Objects;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/8 Time:上午10:49
 */
@Slf4j
public class AsyncResultHandler extends BaseCmdHandler {

    @Autowired
    private CompanyAuthenticationDao companyAuthenticationDao;

    @Autowired
    private DispatchCmdManager dispatchCmdManager;

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
        if (!Objects.equals(AuthenticationStatus.PROCESSING.getCode(), companyAuthenticationEntity.getStatus())) {
            log.error("无需处理");
            return;
        }

        companyAuthenticationEntity.setStatus(AuthenticationStatus.SUCCESS.getCode());
        companyAuthenticationEntity.setResultDetail(ResCode.EID_0000000.getCode());
        companyAuthenticationEntity.setResultMessage(ResCode.EID_0000000.getDesc());
        companyAuthenticationEntity.setAppEidCode("jSHj0pwi6nz0baSk/cO/JBEfPy8EyGlqL2q6+HOUCNIxMDAw");
        companyAuthenticationEntity.setUpdatedAt(new Date());
        companyAuthenticationEntity.setResultTime(new Date());
        companyAuthenticationDao.save(companyAuthenticationEntity);

        dispatchCmdManager.feeCommand(companyAuthenticationEntity.getId().toString());
        dispatchCmdManager.notifyCommand(companyAuthenticationEntity.getId().toString());
    }

    @Override
    protected void failedFinally(BizCmdEntity command) {
        log.debug("重试业务号:{} failedFinally", command.getBizId());
    }
}