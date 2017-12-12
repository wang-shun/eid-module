package com.eid.company.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/1/22 Time:下午2:27
 */
@Data
public class CompanyInfoDTO implements Serializable {

    private static final long serialVersionUID = -1727993271598586212L;

    private String companyId;
    private String companyName;
    private String apId;
    private String apKey;
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
    private String description;

}
