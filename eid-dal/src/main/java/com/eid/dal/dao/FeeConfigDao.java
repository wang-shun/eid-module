package com.eid.dal.dao;

import com.eid.dal.entity.FeeConfigEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ruben on 2016/12/14.
 */
public interface FeeConfigDao extends CrudRepository<FeeConfigEntity, Long> {

    FeeConfigEntity findByCompanyId(String companyId);

}
