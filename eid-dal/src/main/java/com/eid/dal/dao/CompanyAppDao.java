package com.eid.dal.dao;

import com.eid.dal.entity.CompanyAppEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ruben on 2016/12/14.
 */
public interface CompanyAppDao extends CrudRepository<CompanyAppEntity, Long> {

    CompanyAppEntity findByAppId(String appId);

    @Modifying
    @Transactional
    @Query(value = "update CompanyAppEntity cae set cae.appId = :appId, cae.appKey = :appKey where cae.id = :id")
    Integer appKey(@Param("appId") String appId, @Param("appKey") String appKey, @Param("id") Long id);
}
