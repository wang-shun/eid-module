package com.eid.dal.dao;

import com.eid.dal.entity.BizCmdEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * Created by ruben on 2016/12/14.
 */
public interface BizCmdDao extends Repository<BizCmdEntity, Long> {

    BizCmdEntity save(BizCmdEntity bizCmdEntity);

    @Query("from BizCmdEntity cmd where (cmd.status = 'Initial' or cmd.status = 'Wait') and cmd.isDoing = 'n' and cmd.nextExeTime <= current_timestamp and cmd.enableStartDate <= current_timestamp and cmd.enableEndDate > current_timestamp and cmd.bizType = :bizType and cmd.envTag = :envTag")
    Page<BizCmdEntity> selectToDoCmdList(@Param("bizType") String bizType, @Param("envTag") String envTag, Pageable pageable);
//    List<BizCmdEntity> selectToDoCmdList(@Param("bizType") String bizType, @Param("envTag") String envTag);

    @Modifying(clearAutomatically = true)
    @Query("update BizCmdEntity cmd set cmd.isDoing = 'n', cmd.updatedAt = current_timestamp where cmd.isDoing ='y' and cmd.updatedAt < :updatedAt")
    Integer reactiveCommandsBeforeDate(@Param("updatedAt") Date updatedAt);

}