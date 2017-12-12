package com.eid.dal.dao;

import com.eid.dal.entity.CompanyInfoEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ruben on 2016/12/14.
 */
public interface CompanyInfoDao extends CrudRepository<CompanyInfoEntity, Long> {

    CompanyInfoEntity findByApId(String apId);

    CompanyInfoEntity findByCompanyId(String companyId);

    @Modifying
    @Transactional
    @Query(value = "update CompanyInfoEntity ci set ci.apId = :apId, ci.apKey = :apKey where ci.id = :id")
    Integer updateApInfo(@Param("apId") String apId, @Param("apKey") String apKey, @Param("id") Long id);

}
