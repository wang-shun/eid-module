package com.eid.dal.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/7/27 Time:下午4:41
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "t_service_access")
public class ServiceAccessEntity extends Base {

    private static final long serialVersionUID = 9097427772701343440L;

    private String companyId;
    private String accessId;
    private String accessKey;
    private String item;
    private Date expiryDate;
    private Integer status;
    private Integer type;
    private String reserve;

}
