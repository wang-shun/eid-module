package com.eid.dal.dao;

import com.eid.dal.entity.BizLockEntity;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;

/**
 * Created by ruben on 2016/12/14.
 */
public interface BizLockDao extends CrudRepository<BizLockEntity, Long> {

    BizLockEntity findByLockNameAndStatus(String lockName, String status);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    BizLockEntity findById(Long id);

}
