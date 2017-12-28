package com.eid.dal.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ruben on 2016/12/14.
 */

@Getter
@Setter
@ToString
@Entity
@Table(name = "t_company_info")
public class CompanyInfoEntity extends Base implements Serializable {

    private static final long serialVersionUID = 3723258779919585820L;

    private String companyId;
    private String companyName;
//    private String apId;
//    private String apKey;
    private Date expiryDate;
    private String shortName;
    private Integer status;
    private String organizationCode;
    private String address;
    private String legalName;
    private String licenseUrl;
    private String licenseAge;
    private String licenseScope;
    private String businessPermit;
    private String contactName;
    private String contactMobile;
    private String contactEmail;
    private String bankName;
    private String bankCard;
    private Integer source;
    private Integer deleteFlag;
    private String description;


}
