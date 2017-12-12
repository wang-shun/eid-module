package com.eid.dal.dao;

import com.eid.dal.entity.CompanyAccountEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ruben on 2016/12/14.
 */
public interface CompanyAccountDao extends CrudRepository<CompanyAccountEntity, Long> {

    CompanyAccountEntity findByCompanyId(String companyId);

}
