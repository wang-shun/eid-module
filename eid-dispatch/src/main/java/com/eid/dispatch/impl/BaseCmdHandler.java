package com.eid.dispatch.impl;

import com.eid.common.enums.CmdStatus;
import com.eid.common.util.DateUtil;
import com.eid.dal.entity.BizCmdEntity;
import com.eid.dispatch.CmdHandler;
import com.eid.dispatch.CmdManager;
import com.eid.dispatch.StatusUtils;
import com.eid.dispatch.common.PerHandler;
import com.eid.dispatch.exception.DispatchErrorCode;
import com.eid.dispatch.exception.DispatchException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.eid.dispatch.common.MessageHelper.formatMsg;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/8 Time:上午10:50
 */
public abstract class BaseCmdHandler implements CmdHandler {

    private final static Log TASK_DIGEST_LOGGER = LogFactory.getLog(BaseCmdHandler.class);
    private static final String TASK_START = "任务[{0}]开始，参数[{1}]";
    private static final String TASK_END = "任务[{0}]结束[{1}ms]，参数[{2}]";
    private static final String TASK_EXCEPTION = "任务[{0}]异常, 参数[{1}], 错误码[{2}], 异常信息[{3}]";

    /**
     * 用于日志显示
     */
    @Getter
    @Setter
    private String handlerName;

    /**
     * 默认重试间隔，单位：秒
     */
    protected int retryInterval = 600;

    public void setRetryInterval(int retryInterval) {
        this.retryInterval = retryInterval;
    }

    @Autowired
    private CmdManager cmdManager;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void execute(BizCmdEntity command) throws Exception {
        if (StatusUtils.isCmdEnd(command.getStatus())) {
            return;
        }

        try {
            TASK_DIGEST_LOGGER.info(formatMsg(TASK_START, getHandlerName(), command.toString()));
            long startTime = System.currentTimeMillis();
            doCmd(command);
            success(command);
            long endTime = System.currentTimeMillis();
            TASK_DIGEST_LOGGER.info(formatMsg(TASK_END, getHandlerName(), endTime - startTime, command.toString()));
        } catch (DispatchException e) {
            String message = formatMsg(TASK_EXCEPTION, getHandlerName(), command.toString(), e.getMessage());
//            String message = formatMsg(TASK_EXCEPTION, getHandlerName(), command.toString(), e.getErrorCode(), e.getMessage());
            TASK_DIGEST_LOGGER.error(message);
            throw e;
        } catch (Exception e) {
            String message = formatMsg(TASK_EXCEPTION, getHandlerName(), command.toString(), DispatchErrorCode.SYSTEM_INNER_ERROR, e.getMessage());
            TASK_DIGEST_LOGGER.error(message);
            throw e;
        }
    }

    /**
     * 错误处理
     *
     * @param command
     * @param failReason
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void handlerException(BizCmdEntity command, String failReason) {
        //获取执行的错误原因，如果有的话
        if (StringUtils.isNotBlank(failReason) && failReason.length() > 512) {
            failReason = failReason.substring(0, 511);
        }
        command.setFailReason(failReason);

        if (needRetry(command)) {
            retry(command);
        } else {
            fail(command);
            failedFinally(command);
        }
    }

    protected abstract void doCmd(BizCmdEntity command) throws Exception;

    protected abstract void failedFinally(BizCmdEntity command);

    private void success(BizCmdEntity command) {
        command.setStatus(CmdStatus.Success.getCode());
        command.setIsDoing("n");
        cmdManager.update(command);
    }

    private boolean needRetry(BizCmdEntity command) {
        if (command.getEnableEndDate() != null && DateUtil.addSeconds(new Date(), retryInterval).after(command.getEnableEndDate())) {
            return false;
        }
        if (command.getMaxRetryTimes() > 0 && command.getRetryTimes() >= command.getMaxRetryTimes()) {
            return false;
        }
        return true;
    }

    protected void retry(BizCmdEntity command) {
        command.setStatus(CmdStatus.Wait.getCode());
        command.setIsDoing("n");
        command.setRetryTimes(command.getRetryTimes() + 1);
        command.setNextExeTime(DateUtil.addSeconds(new Date(), retryInterval));
        if (command.getBizType().contains("NOTIFY"))
            command.setNextExeTime(DateUtil.addSeconds(new Date(), retryInterval * command.getRetryTimes().intValue()));
        TASK_DIGEST_LOGGER.info(formatMsg("间格{0},下次执行时间为{1}", retryInterval, command.getNextExeTime()));
        PerHandler.subStringFailReason(command.getFailReason(), 512);
        cmdManager.update(command);
    }

    private void fail(BizCmdEntity command) {
        command.setStatus(CmdStatus.Failure.getCode());
        command.setIsDoing("n");
        PerHandler.subStringFailReason(command.getFailReason(), 512);
        cmdManager.update(command);
    }

    protected void createCommand(BizCmdEntity command) {
        cmdManager.insert(command);
    }

}
