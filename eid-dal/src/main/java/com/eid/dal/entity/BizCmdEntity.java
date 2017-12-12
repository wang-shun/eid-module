package com.eid.dal.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "t_eid_biz_cmd")
public class BizCmdEntity extends Base implements Serializable {


    private static final long serialVersionUID = 7631532220093742913L;

    /**
     * 业务ID
     */
    private String bizId;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 执行的server地址
     */
    private String serverIp;

    /**
     * 最后一次执行失败的原因
     */
    private String failReason;

    /**
     * 环境标签
     */
    private String envTag;

    /**
     * 任务状态
     */
    private String status;

    /**
     * 是否正在处理中
     */
    private String isDoing;

    /**
     * 重试次数
     */
    private Long retryTimes = 0L;

    /**
     * 最大重试次数，负数表示无限次
     */
    private Long maxRetryTimes = -1L;

    /**
     * 下次执行时间 db_column: NEXT_EXE_TIME
     */
    private Date nextExeTime;

    /**
     * 命令执行起始时间
     */
    private Date enableStartDate;

    /**
     * 命令执行终止终止时间
     */
    private Date enableEndDate;

}
