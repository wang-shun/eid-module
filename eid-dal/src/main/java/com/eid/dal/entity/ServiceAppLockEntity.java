package com.eid.dal.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ruben on 2016/12/14.
 */

@Getter
@Setter
@ToString
@Entity
@Table(name = "t_service_app_lock")
public class ServiceAppLockEntity extends Base implements Serializable {

    private static final long serialVersionUID = -920884855248979408L;

    private String name;
    private String password;
    private String telPhone;
    private String idCard;
    private Integer sex;
    private Integer status;
    private String cardType;


}
