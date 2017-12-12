package com.eid.company.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/1/22 Time:下午2:27
 */
@Data
public class CompanyAppInfoDTO implements Serializable {

    private static final long serialVersionUID = 1963992468503036017L;

    private String appId;
    private String appKey;
    private String appName;
    private String companyId;
    private String appUrl;
    private String appDomain;
    private String appIp;
    private String appDesc;
    private String logoUrl;
    private String appImg1;
    private String appImg2;
    private String appImg3;
    private String appImg4;
    private String appImg5;
    private Integer appStatus;

}
