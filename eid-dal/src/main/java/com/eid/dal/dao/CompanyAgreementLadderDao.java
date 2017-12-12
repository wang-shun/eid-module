package com.eid.dal.dao;

import com.eid.dal.entity.CompanyAgreementLadderEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ruben on 2016/12/14.
 */
public interface CompanyAgreementLadderDao extends CrudRepository<CompanyAgreementLadderEntity, Long> {

    CompanyAgreementLadderEntity findBymIdAndStatus(Long id, Integer status);

    @Query(value = "select ladder from CompanyAgreementLadderEntity ladder where ladder.mId = :mId and :beginNum between ladder.beginNum and ladder.endNum and ladder.status = :status")
    CompanyAgreementLadderEntity queryFee(@Param("mId") Long mId, @Param("beginNum") Integer beginNum, @Param("status") Integer status);

}
