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
@Table(name = "t_certificate_authentication")
public class CertificateAuthenticationEntity extends Base implements Serializable {

    private static final long serialVersionUID = 294678959855540671L;

    private String companyId;
    private String name;
    private String bizType;
    private String bizTime;
    private String bizSequenceId;
    private String attach;
    private String returnUrl;
    private String resultDetail;
    private String resultMessage;
    private Date resultTime;
    private Integer status;


}
