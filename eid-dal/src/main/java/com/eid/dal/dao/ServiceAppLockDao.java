package com.eid.dal.dao;

import com.eid.dal.entity.ServiceAppLockEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ruben on 2016/12/14.
 */
public interface ServiceAppLockDao extends CrudRepository<ServiceAppLockEntity, Long> {

    ServiceAppLockEntity findByIdCard(String idCard);
}
