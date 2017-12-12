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
@Table(name = "t_province_city")
public class ProvinceCityEntity extends Base implements Serializable {

    private static final long serialVersionUID = -6861311102567518041L;

    private Integer code;
    private String province;
    private String city;

}
