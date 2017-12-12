package com.eid.dal.dao;

import com.eid.dal.entity.CompanyAgreementEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ruben on 2016/12/14.
 */
public interface CompanyAgreementDao extends CrudRepository<CompanyAgreementEntity, Long> {

    CompanyAgreementEntity findByIdAndStatus(Long id, Integer status);
}
