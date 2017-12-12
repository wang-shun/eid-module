package com.eid.dal.manager.impl;


import com.eid.common.enums.CmdBizType;
import com.eid.common.enums.CmdStatus;
import com.eid.common.util.DateUtil;
import com.eid.common.util.GetIPUtil;
import com.eid.dal.dao.BizCmdDao;
import com.eid.dal.entity.BizCmdEntity;
import com.eid.dal.manager.DispatchCmdManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Async
@Component
public class DispatchCmdManagerImpl implements DispatchCmdManager {

    public final static String BASE_CMD_N_STATUS = "n";

    @Autowired
    private BizCmdDao bizCmdDao;

    @Value("#{db.envTag}")
    private String envTag;

    @Override
    public void feeCommand(String bizId) {
        //命令添加环境标签
        BizCmdEntity cmdObject = new BizCmdEntity();
        cmdObject.setBizId(bizId);
        cmdObject.setBizType(CmdBizType.ASYNC_FEE.getCode());
        cmdObject.setRetryTimes(1L);
        cmdObject.setMaxRetryTimes(10L);
        cmdObject.setEnvTag(envTag);
        cmdObject.setServerIp(GetIPUtil.getLocalIPAddress());
        cmdObject.setIsDoing(BASE_CMD_N_STATUS);
        cmdObject.setStatus(CmdStatus.Initial.getCode());
        cmdObject.setCreatedBy("SYSTEM");
        cmdObject.setUpdatedBy("SYSTEM");
        cmdObject.setNextExeTime(new Date());
        cmdObject.setEnableStartDate(new Date());
        cmdObject.setEnableEndDate(DateUtil.plusDays(7));
        cmdObject.setUpdatedAt(new Date());
        //添加一条命令
        bizCmdDao.save(cmdObject);
    }

    @Override
    public void notifyCommand(String bizId) {
        //命令添加环境标签
        BizCmdEntity cmdObject = new BizCmdEntity();
        cmdObject.setBizId(bizId);
        cmdObject.setBizType(CmdBizType.ASYNC_NOTIFY.getCode());
        cmdObject.setRetryTimes(1L);
        cmdObject.setMaxRetryTimes(10L);
        cmdObject.setEnvTag(envTag);
        cmdObject.setServerIp(GetIPUtil.getLocalIPAddress());
        cmdObject.setIsDoing(BASE_CMD_N_STATUS);
        cmdObject.setStatus(CmdStatus.Initial.getCode());
        cmdObject.setCreatedBy("SYSTEM");
        cmdObject.setUpdatedBy("SYSTEM");
        cmdObject.setNextExeTime(new Date());
        cmdObject.setEnableStartDate(new Date());
        cmdObject.setEnableEndDate(DateUtil.plusDays(7));
        cmdObject.setUpdatedAt(new Date());
        //添加一条命令
        bizCmdDao.save(cmdObject);
    }

    @Override
    public void opNotifyCommand(String bizId) {
        //命令添加环境标签
        BizCmdEntity cmdObject = new BizCmdEntity();
        cmdObject.setBizId(bizId);
        cmdObject.setBizType(CmdBizType.ASYNC_RESULT.getCode());
        cmdObject.setRetryTimes(1L);
        cmdObject.setMaxRetryTimes(5L);
        cmdObject.setEnvTag(envTag);
        cmdObject.setServerIp(GetIPUtil.getLocalIPAddress());
        cmdObject.setIsDoing(BASE_CMD_N_STATUS);
        cmdObject.setStatus(CmdStatus.Initial.getCode());
        cmdObject.setCreatedBy("SYSTEM");
        cmdObject.setUpdatedBy("SYSTEM");
        cmdObject.setNextExeTime(new Date());
        cmdObject.setEnableStartDate(new Date());
        cmdObject.setEnableEndDate(DateUtil.plusDays(7));
        cmdObject.setUpdatedAt(new Date());
        //添加一条命令
        bizCmdDao.save(cmdObject);
    }

}
