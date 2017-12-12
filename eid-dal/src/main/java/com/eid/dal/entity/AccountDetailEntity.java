package com.eid.dal.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ruben on 2016/12/14.
 */

@Getter
@Setter
@ToString
@Entity
@Table(name = "t_account_detail")
public class AccountDetailEntity extends Base implements Serializable {

    private static final long serialVersionUID = 4344940464570672568L;

    private String companyId;
    private Long money;
    private Long balance;
    private String content;
    private Integer type;
    private Long poundage;


}
