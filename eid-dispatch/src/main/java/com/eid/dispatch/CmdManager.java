package com.eid.dispatch;

import com.eid.dal.entity.BizCmdEntity;
import com.eid.dispatch.exception.DispatchException;

import java.util.Date;
import java.util.List;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/8 Time:上午9:57
 */
public interface CmdManager {

    /**
     * 查询待处理命令
     *
     * @param bizType
     * @param cmdNum
     * @return
     * @throws RuntimeException
     */
    List<BizCmdEntity> lockAndListCommands(String bizType, int cmdNum) throws RuntimeException, DispatchException;

    /**
     * 添加命令
     *
     * @param cmdObject
     * @throws RuntimeException
     */
    void insert(BizCmdEntity cmdObject) throws RuntimeException;

    /**
     * 更新命令
     *
     * @param cmdObject
     * @throws RuntimeException
     */
    void update(BizCmdEntity cmdObject) throws RuntimeException;

    /**
     * 重新激活因异常终止的任务
     *
     * @param date
     * @return
     */
    int reactiveCommandBeforeDate(Date date);

    /**
     * ServerIP激活命令
     * update LZQ
     *
     * @return count
     */
//    int reactiveCommandServerIP();

}
