package com.eid.dal.dao;

import com.eid.dal.entity.CompanyAgreementLadderEntity;
import com.eid.dal.entity.FeeConfigEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by ruben on 2016/12/14.
 */
public interface FeeConfigDao extends CrudRepository<FeeConfigEntity, Long> {

    FeeConfigEntity findByCompanyId(String companyId);

//    @Query(value = "select ladder from CompanyAgreementLadderEntity ladder where ladder.mId = :mId and :beginNum between ladder.beginNum and ladder.endNum and ladder.status = :status")
    @Query(value = "select feeConfig from FeeConfigEntity feeConfig where feeConfig.companyId = :companyId and feeConfig.type = :type")
    FeeConfigEntity queryByCompanyIdAndType(@Param("companyId") String companyId, @Param("type") String type);

}
