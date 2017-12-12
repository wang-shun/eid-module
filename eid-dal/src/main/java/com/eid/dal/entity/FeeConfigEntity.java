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
@Table(name = "t_fee_config")
public class FeeConfigEntity extends Base implements Serializable {

    private static final long serialVersionUID = -6158485703936675741L;

    @Column(name = "c_id")
    private Long cId;

    private String companyId;
    private Integer status;

}
