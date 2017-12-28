package com.eid.dal.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by ruben on 2016/12/14.
 */

@Getter
@Setter
@ToString
@Entity
@Table(name = "t_app")
public class CompanyAppEntity extends Base implements Serializable {

    private static final long serialVersionUID = 4348412831765793503L;

    private String appId;
    private String appKey;
    private String apId;
    private String apKeyFactor;
    private String appName;
    private String companyId;
    private String appUrl;
    private String appDomain;
    private String appIp;
    private String appDesc;
    private String logoUrl;
    @Column(name = "app_img_1")
    private String appImg1;
    @Column(name = "app_img_2")
    private String appImg2;
    @Column(name = "app_img_3")
    private String appImg3;
    @Column(name = "app_img_4")
    private String appImg4;
    @Column(name = "app_img_5")
    private String appImg5;
    private Integer appStatus;

}
