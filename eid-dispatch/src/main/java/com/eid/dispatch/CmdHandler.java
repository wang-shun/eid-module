package com.eid.dispatch;

import com.eid.dal.entity.BizCmdEntity;

/**
 * 命令执行具体业务的
 * Created by:ruben Date:2017/3/8 Time:上午9:48
 */
public interface CmdHandler {

    /**
     * 处理具体命令
     *
     * @param command
     * @throws Exception
     */
    void execute(BizCmdEntity command) throws Exception;

    /**
     * 任务执行失败的异常处理
     *
     * @param command
     * @param failReason
     */
    void handlerException(BizCmdEntity command, String failReason);
}
