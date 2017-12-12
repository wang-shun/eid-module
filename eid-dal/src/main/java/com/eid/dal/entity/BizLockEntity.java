package com.eid.dal.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
@Table(name = "t_eid_biz_lock")
public class BizLockEntity extends Base implements Serializable {

    private static final long serialVersionUID = 8960049671042923901L;

    /**
     * 锁名称
     */
    private String lockName;

    /**
     * 锁状态，1有效，0无效（此时锁存在但无效，取这个锁的业务会被完全挂起，无法继续运行）
     */
    private String status;

}