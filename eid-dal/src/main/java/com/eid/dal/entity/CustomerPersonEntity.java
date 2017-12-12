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
@ToString(callSuper = true)
@Entity
@Table(name = "t_customer_person")
public class CustomerPersonEntity extends Base implements Serializable {

    private static final long serialVersionUID = -4799295176559939952L;

    private String gender;
    private String name;
    private Integer idType;
    private Integer province;
    private Integer city;
    private String address;
    private String birthDate;


}
