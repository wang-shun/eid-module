package com.eid.dal.manager.impl;


import com.eid.common.enums.AuthenticationStatus;
import com.eid.dal.dao.CompanyAppDao;
import com.eid.dal.dao.CompanyAuthenticationDao;
import com.eid.dal.entity.CompanyAppEntity;
import com.eid.dal.entity.CompanyAuthenticationEntity;
import com.eid.dal.manager.AuthenticationManager;
import com.google.common.base.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class AuthenticationManagerImpl implements AuthenticationManager {

    @Autowired
    private CompanyAuthenticationDao companyAuthenticationDao;

    @Autowired
    private CompanyAppDao companyAppDao;

    @Override
    public void failed(Long id, String errorCode, String errorMessage) {
        CompanyAuthenticationEntity companyAuthenticationEntity = companyAuthenticationDao.findOne(id);
        if (Objects.equal(companyAuthenticationEntity, null))
            return;
        companyAuthenticationEntity.setResultDetail(errorCode);
        companyAuthenticationEntity.setResultMessage(errorMessage);
        companyAuthenticationEntity.setStatus(AuthenticationStatus.FAILED.getCode());
        companyAuthenticationEntity.setResultTime(new Date());
        companyAuthenticationEntity.setUpdatedAt(new Date());
        companyAuthenticationDao.save(companyAuthenticationEntity);
    }

    @Override
    public void success(Long id, String resultDetail, String resultMessage, String appEidCode) {
        CompanyAuthenticationEntity companyAuthenticationEntity = companyAuthenticationDao.findOne(id);
        if (Objects.equal(companyAuthenticationEntity, null))
            return;
        companyAuthenticationEntity.setResultDetail(resultDetail);
        companyAuthenticationEntity.setResultMessage(resultMessage);
        companyAuthenticationEntity.setAppEidCode(appEidCode);
        companyAuthenticationEntity.setStatus(AuthenticationStatus.SUCCESS.getCode());
        companyAuthenticationEntity.setResultTime(new Date());
        companyAuthenticationEntity.setUpdatedAt(new Date());
        companyAuthenticationDao.save(companyAuthenticationEntity);
    }

    @Override
    public void success(Long id, String resultDetail, String resultMessage, String appEidCode, String userInfo) {
        CompanyAuthenticationEntity companyAuthenticationEntity = companyAuthenticationDao.findOne(id);
        if (Objects.equal(companyAuthenticationEntity, null))
            return;
        companyAuthenticationEntity.setResultDetail(resultDetail);
        companyAuthenticationEntity.setResultMessage(resultMessage);
        companyAuthenticationEntity.setAppEidCode(appEidCode);
        companyAuthenticationEntity.setStatus(AuthenticationStatus.SUCCESS.getCode());
        companyAuthenticationEntity.setResultTime(new Date());
        companyAuthenticationEntity.setUpdatedAt(new Date());
        companyAuthenticationEntity.setUserIdInfo(userInfo);
        companyAuthenticationDao.save(companyAuthenticationEntity);
    }

    @Override
    public void processing(Long id) {
        CompanyAuthenticationEntity companyAuthenticationEntity = companyAuthenticationDao.findOne(id);
        if (Objects.equal(companyAuthenticationEntity, null))
            return;
        companyAuthenticationEntity.setStatus(AuthenticationStatus.PROCESSING.getCode());
        companyAuthenticationEntity.setResultTime(new Date());
        companyAuthenticationEntity.setUpdatedAt(new Date());
        companyAuthenticationDao.save(companyAuthenticationEntity);
    }

    @Override
    public CompanyAuthenticationEntity insert(CompanyAuthenticationEntity companyAuthenticationEntity) {
        return companyAuthenticationDao.save(companyAuthenticationEntity);
    }

    public CompanyAuthenticationEntity findByBizSequenceId(String bizSequenceId){
        return companyAuthenticationDao.findByBizSequenceId(bizSequenceId);
    }

    public CompanyAppEntity findByApId(String apId){
        return companyAppDao.findByApId(apId);
    }

}
