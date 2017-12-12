package com.eid.dal.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@Table(name = "t_company_agreement")
public class CompanyAgreementEntity extends Base implements Serializable {

    private static final long serialVersionUID = 5714540932037095440L;

    private Integer status;
    private Integer feeType;
    private Long eidFee;
    private Long caFee;

}
