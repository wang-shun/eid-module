package com.eid.dispatch.impl;

import com.eid.common.enums.CmdStatus;
import com.eid.common.util.DateUtil;
import com.eid.common.util.GetIPUtil;
import com.eid.dal.dao.BizCmdDao;
import com.eid.dal.entity.BizCmdEntity;
import com.eid.dispatch.CmdManager;
import com.eid.dispatch.DispatchLockManager;
import com.eid.dispatch.exception.DispatchErrorCode;
import com.eid.dispatch.exception.DispatchException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/8 Time:上午10:02
 */
@Slf4j
@Service
public class CmdManagerImpl implements CmdManager {

    private final static String BASE_CMD_Y_STATUS = "y";

    public final static String BASE_CMD_N_STATUS = "n";

    /**
     * 业务锁管理器
     */
    @Autowired
    private DispatchLockManager dispatchLockManager;

    @Autowired
    private BizCmdDao bizCmdDao;

    @Value("#{app.envTag}")
    private String envTag;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public List<BizCmdEntity> lockAndListCommands(final String bizType, final int cmdNum) throws DispatchException {
        String lockName = envTag + "." + bizType;

        if (!dispatchLockManager.getLockNoWait(lockName)) {
            return new ArrayList<>();
        }
        return queryToDoCmdList(bizType, cmdNum);
    }

    /**
     * 取待处理的命令数量
     *
     * @param cmdNum
     * @return
     */
    public List<BizCmdEntity> queryToDoCmdList(String bizType, int cmdNum) throws DispatchException {
        List<BizCmdEntity> retCmds = new ArrayList<BizCmdEntity>();
        try {
            // todo 分页查询待新增
            //查询带有环境标签的命令
            PageRequest pageRequest = new PageRequest(0, cmdNum, new Sort(Sort.Direction.ASC, "id"));
//            List<BizCmdEntity> cmds = bizCmdDao.selectToDoCmdList(bizType, envTag, pageRequest);
            Page<BizCmdEntity> cmds = bizCmdDao.selectToDoCmdList(bizType, envTag, pageRequest);
            for (BizCmdEntity cmd : cmds) {
                if (StringUtils.equals(BASE_CMD_Y_STATUS, cmd.getIsDoing())) {
                    continue;
                }
                cmd.setIsDoing(BASE_CMD_Y_STATUS);
                //添加IP地址
                cmd.setServerIp(GetIPUtil.getLocalIPAddress());
                bizCmdDao.save(cmd);
                retCmds.add(cmd);
            }
            return retCmds;
        } catch (Exception e) {
            String message = "查待处理命令异常";
            log.error(message, e);
        }
        return new ArrayList<>();
    }

    /**
     * 更新命令
     *
     * @param cmdObject
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(BizCmdEntity cmdObject) throws DispatchException {
        try {
            bizCmdDao.save(cmdObject);
        } catch (Exception e) {
            String message = "更新待处理命令异常[" + cmdObject.toString() + "]";
            log.error(message, e);
            throw new DispatchException(DispatchErrorCode.DATA_BASE_ERROR, message, e);
        }
    }

    @Override
    public void insert(BizCmdEntity cmdObject) throws RuntimeException {
        if (cmdObject == null) {
            throw new DispatchException(DispatchErrorCode.NULL_IS_ILLEGAL_PARAM, "命令不能创建为Null, " + DispatchErrorCode.NULL_IS_ILLEGAL_PARAM.getDesc());
        }
        //命令添加环境标签
        cmdObject.setEnvTag(envTag);
        cmdObject.setServerIp(GetIPUtil.getLocalIPAddress());
        cmdObject.setIsDoing(CmdManagerImpl.BASE_CMD_N_STATUS);
        cmdObject.setStatus(CmdStatus.Initial.getCode());
        cmdObject.setCreatedBy("SYSTEM");
        cmdObject.setUpdatedBy("SYSTEM");
        cmdObject.setEnableEndDate(DateUtil.plusDays(1));

        //添加一条命令
        bizCmdDao.save(cmdObject);
    }

    @Override
    public int reactiveCommandBeforeDate(Date date) {
        return bizCmdDao.reactiveCommandsBeforeDate(date);
    }

    /**
     * ServerIP激活命令
     *
     * @return count
     */
//    @Override
//    public int reactiveCommandServerIP() {
//        return bizCmdDao.reactiveCommandsServerIP(GetIPUtil.getLocalIPAddress());
//    }

}