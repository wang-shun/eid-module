package com.eid.dal.dao;

import com.eid.dal.entity.CustomerPersonEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ruben on 2016/12/14.
 */
public interface CustomerPersonDao extends CrudRepository<CustomerPersonEntity, Long> {

}
