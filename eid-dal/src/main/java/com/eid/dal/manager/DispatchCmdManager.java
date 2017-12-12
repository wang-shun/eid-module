package com.eid.dal.manager;

public interface DispatchCmdManager {

    /**
     * 添加计费命令
     *
     * @param bizId
     * @throws RuntimeException
     */
    void feeCommand(String bizId);

    /**
     * 添加通知命令
     *
     * @param bizId
     */
    void notifyCommand(String bizId);

    /**
     * 添加模拟op通知命令
     *
     * @param bizId
     */
    void opNotifyCommand(String bizId);
}
