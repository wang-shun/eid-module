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
@Table(name = "t_company_account")
public class CompanyAccountEntity extends Base implements Serializable {

    private static final long serialVersionUID = 342179304924725083L;

    private String companyId;
    private Long money;
    private Long minRemind;
    private Long rechargeMoney;
    private Long consumeMoney;
    private Long invoiceMoney;
    private String payPassword;
    private Integer status;
    private Date lastPaySuccessDate;
    private Date lastPayFailDate;
    private Integer payPswErrNum;
    private Date payPswLockDate;
    private Date lastModfiypwdTime;


}
