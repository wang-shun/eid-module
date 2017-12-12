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
@Table(name = "t_company_agreement_ladder")
public class CompanyAgreementLadderEntity extends Base implements Serializable {

    private static final long serialVersionUID = 4269446106757905650L;

    @Column(name = "m_id")
    private Long mId;
    private Integer status;
    private Integer beginNum;
    private Integer endNum;
    private Long eidFee;
    private Long caFee;

}
