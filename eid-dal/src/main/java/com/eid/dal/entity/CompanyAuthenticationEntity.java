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
@Table(name = "t_company_authentication")
public class CompanyAuthenticationEntity extends Base implements Serializable {

    private static final long serialVersionUID = 3829378406918599560L;

    private String companyId;
    private String apId;
    private String appId;
    private String bizType;
    private String bizTime;
    private String bizSequenceId;
    private String attach;
    private String returnUrl;
    private String resultDetail;
    private String resultMessage;
    private String userIdInfo;
    private Date resultTime;
    private String extension;
    private String appEidCode;
    private String idHash;
    private Integer status;
    private Long verifyFee;
    private String accessToken;
    private String eidIdentification;


}
