package com.eid.dispatch;

import com.eid.dal.entity.BizCmdEntity;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/8 Time:上午9:50
 */
@Slf4j
public class HandlerWrapper implements Runnable {

    /**
     * 处理命令对象
     */
    private BizCmdEntity command;
    /**
     * 具体命令处理类
     */
    private CmdHandler cmdHandler;

    public HandlerWrapper(BizCmdEntity command, CmdHandler cmdHandler) {
        this.command = command;
        this.cmdHandler = cmdHandler;
    }

    @Override
    public void run() {
        try {
            cmdHandler.execute(command);
        } catch (Throwable t) {
            log.error("executeCmdTread 异常，" + command.getBizId() + command.getBizType() + t.getMessage(), t);
            cmdHandler.handlerException(command, t.getMessage());
        }
    }
}
