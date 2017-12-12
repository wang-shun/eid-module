package com.eid.dal.dao;

import com.eid.dal.entity.ProvinceCityEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ruben on 2016/12/14.
 */
public interface ProvinceCityDao extends CrudRepository<ProvinceCityEntity, Long> {

    ProvinceCityEntity findByCode(Integer code);
}
