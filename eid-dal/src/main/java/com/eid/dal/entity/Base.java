package com.eid.dal.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据库基础类
 * Created by:ruben Date:2017/1/21 Time:下午4:21
 */
@Getter
@Setter
@ToString
@MappedSuperclass
public class Base implements Serializable {

    private static final long serialVersionUID = 8784955224969991526L;

    /**
     * id主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 创建人
     */
    private String createdBy = "SYSTEM";

    /**
     * 更新时间
     */
    private Date updatedAt = new Date();

    /**
     * 更新人
     */
    private String updatedBy = "SYSTEM";
}
