package com.eid.dal.dao;

import com.eid.dal.entity.CompanyAuthenticationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by ruben on 2016/12/14.
 */
public interface CompanyAuthenticationDao extends CrudRepository<CompanyAuthenticationEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "update CompanyAuthenticationEntity cae set cae.resultDetail = :resultDetail, cae.resultMessage = :resultMessage, cae.resultTime = :resultTime, cae.appEidCode = :appEidCode, cae.idHash = :idHash, cae.status = :status, cae.updatedAt = :updatedAt where cae.id = :id")
    Integer updateRes(@Param("resultDetail") String resultDetail, @Param("resultMessage") String resultMessage, @Param("resultTime") Date resultTime, @Param("appEidCode") String appEidCode, @Param("idHash") String idHash, @Param("status") Integer status, @Param("updatedAt") Date updatedAt, @Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "update CompanyAuthenticationEntity cae set cae.verifyFee = :verifyFee, cae.updatedAt = :updatedAt where cae.id = :id")
    Integer updateFee(@Param("verifyFee") Long verifyFee, @Param("updatedAt") Date updatedAt, @Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "update CompanyAuthenticationEntity cae set cae.userIdInfo = :userIdInfo, cae.updatedAt = :updatedAt where cae.accessToken = :accessToken")
    Integer updateUserInfo(@Param("userIdInfo") String userIdInfo, @Param("updatedAt") Date updatedAt, @Param("accessToken") String accessToken);

    @Query(value = "update CompanyAuthenticationEntity cae set cae.resultDetail = :resultDetail, cae.resultMessage = :resultMessage, cae.resultTime = :resultTime, cae.appEidCode = :appEidCode, cae.idHash = :idHash, cae.status = :status, cae.updatedAt = :updatedAt where cae.id = :id")
    Integer failed(@Param("resultDetail") String resultDetail, @Param("resultMessage") String resultMessage, @Param("resultTime") Date resultTime);

    CompanyAuthenticationEntity findByCompanyIdAndAccessToken(String companyId, String accessToken);

    Page<CompanyAuthenticationEntity> findByAppEidCodeAndUpdatedAtBetween(String appEidCode, Date start, Date end, Pageable pageable);

    CompanyAuthenticationEntity findByBizSequenceId(String bizSequenceId);


}
