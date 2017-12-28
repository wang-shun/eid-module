package com.eid.company.biz.impl;

import com.eid.common.enums.CompanyAccountStatus;
import com.eid.common.enums.CompanyAppStatus;
import com.eid.common.enums.CompanyStatus;
import com.eid.common.enums.ErrorCode;
import com.eid.common.exception.FacadeException;
import com.eid.common.util.BeanMapperUtil;
import com.eid.company.biz.CompanyBiz;
import com.eid.company.model.CompanyAppInfoDTO;
import com.eid.company.model.CompanyInfoDTO;
import com.eid.dal.dao.CompanyAccountDao;
import com.eid.dal.dao.CompanyAppDao;
import com.eid.dal.entity.CompanyAccountEntity;
import com.eid.dal.entity.CompanyAppEntity;
import com.eid.dal.entity.CompanyInfoEntity;
import com.eid.dal.manager.CompanyAppManager;
import com.eid.dal.manager.CompanyInfoManager;
import com.google.common.base.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class CompanyBizImpl implements CompanyBiz {

    @Autowired
    private CompanyInfoManager companyInfoManager;

    @Autowired
    private CompanyAppManager companyAppManager;

    @Autowired
    private CompanyAccountDao companyAccountDao; // 目前使用dao直接操作数据，待时间充足将调用dao改为manager。manager操作redis及数据库

    @Autowired
    private CompanyAppDao companyAppDao;

    /**
     *
     * @param apId
     * @return
     */
    @Override
    public String queryApkeyFactor(String apId) {

        CompanyAppEntity companyAppEntity = companyAppDao.findByApId(apId);

        System.out.println("apId:"+apId+",对应的apkeyfactor:"+companyAppEntity.getApKeyFactor());

        return companyAppEntity.getApKeyFactor();
    }

    @Override
    public CompanyInfoDTO validate(String companyId) {
        // 校验公司信息是否正常
        CompanyInfoDTO companyInfoDTO = queryCompanyInfo(companyId);
        log.info("Call queryCompanyInfo request:{};response:{};", companyId, companyInfoDTO);
        if (Objects.equal(companyInfoDTO, null))
            throw new FacadeException(ErrorCode.NON_EXISTENT);
        if (!Objects.equal(companyInfoDTO.getStatus(), CompanyStatus.NORMAL.getCode()))
            throw new FacadeException(ErrorCode.STATUS_UN_NORMAL);
        if (Objects.equal(companyInfoDTO.getExpiryDate(), null) ||
                (!Objects.equal(companyInfoDTO.getExpiryDate(), null) && companyInfoDTO.getExpiryDate().before(new Date())))
            throw new FacadeException(ErrorCode.SERVICE_EXPIRY);

        // 校验公司账户信息是否正常
        CompanyAccountEntity companyAccountEntity = companyAccountDao.findByCompanyId(companyId);
        if (Objects.equal(companyAccountEntity, null))
            throw new FacadeException(ErrorCode.NON_EXISTENT);
        if (!Objects.equal(companyAccountEntity.getStatus(), CompanyAccountStatus.NORMAL.getCode()))
            throw new FacadeException(ErrorCode.STATUS_UN_NORMAL);
        if (companyAccountEntity.getMoney() <= 0)
            throw new FacadeException(ErrorCode.BALANCE_NOT_ENOUGH);

        return companyInfoDTO;
    }

    @Override
    public CompanyInfoDTO validateByApId(String apId) {
        // 校验公司信息是否正常
        CompanyInfoDTO companyInfoDTO = queryCompanyInfoByApId(apId);
        log.info("call queryCompanyInfoByApId request:{};response:{};", apId, companyInfoDTO);
        if (Objects.equal(companyInfoDTO, null))
            throw new FacadeException(ErrorCode.NON_EXISTENT);
        if (!Objects.equal(companyInfoDTO.getStatus(), CompanyStatus.NORMAL.getCode()))
            throw new FacadeException(ErrorCode.STATUS_UN_NORMAL);
        if (!Objects.equal(companyInfoDTO.getExpiryDate(), null) && companyInfoDTO.getExpiryDate().before(new Date()))
            throw new FacadeException(ErrorCode.SERVICE_EXPIRY);

        // 校验公司账户信息是否正常
        CompanyAccountEntity companyAccountEntity = companyAccountDao.findByCompanyId(companyInfoDTO.getCompanyId());
        if (Objects.equal(companyAccountEntity, null))
            throw new FacadeException(ErrorCode.NON_EXISTENT);
        if (!Objects.equal(companyAccountEntity.getStatus(), CompanyAccountStatus.NORMAL.getCode()))
            throw new FacadeException(ErrorCode.STATUS_UN_NORMAL);
        if (companyAccountEntity.getMoney() <= 0)
            throw new FacadeException(ErrorCode.BALANCE_NOT_ENOUGH);

        return companyInfoDTO;
    }

    @Override
    public CompanyAppInfoDTO validateByAppId(String appId) {
        // 校验公司信息是否正常
        CompanyAppInfoDTO companyAppInfoDTO = queryCompanyAppInfo(appId);
        log.info("call queryCompanyAppInfo request:{};response:{};", appId, companyAppInfoDTO);
        if (Objects.equal(companyAppInfoDTO, null))
            throw new FacadeException(ErrorCode.NON_EXISTENT);
        if (!Objects.equal(companyAppInfoDTO.getAppStatus(), CompanyAppStatus.TRIAL_YES.getCode()))
            throw new FacadeException(ErrorCode.STATUS_UN_NORMAL);

        return companyAppInfoDTO;
    }

    @Override
    public CompanyInfoDTO queryCompanyInfo(String companyId) {
        CompanyInfoEntity companyInfoEntity = companyInfoManager.queryCompanyInfoById(companyId);
        return BeanMapperUtil.objConvert(companyInfoEntity, CompanyInfoDTO.class);
    }

    @Override
    public CompanyInfoDTO queryCompanyInfoByApId(String apId) {
        CompanyInfoEntity companyInfoEntity = companyInfoManager.queryCompanyInfoByApId(apId);
        return BeanMapperUtil.objConvert(companyInfoEntity, CompanyInfoDTO.class);
    }

    @Override
    public CompanyAppInfoDTO queryCompanyAppInfo(String appId) {
        CompanyAppEntity companyAppEntity = companyAppManager.queryAppInfoByApp(appId);
        return BeanMapperUtil.objConvert(companyAppEntity, CompanyAppInfoDTO.class);
    }
}
