package com.eid.company.biz.impl;

import com.eid.common.enums.BizType;
import com.eid.common.enums.CertificateStatus;
import com.eid.common.enums.ErrorCode;
import com.eid.common.exception.FacadeException;
import com.eid.common.model.param.res.CertificateResDTO;
import com.eid.common.util.BeanMapperUtil;
import com.eid.common.util.DateUtil;
import com.eid.common.util.Encodes;
import com.eid.common.util.MD5Encrypt;
import com.eid.company.biz.CertificateBiz;
import com.eid.dal.dao.CertificateAuthenticationDao;
import com.eid.dal.dao.ServiceAccessDao;
import com.eid.dal.dao.ServiceAppLockDao;
import com.eid.dal.entity.CertificateAuthenticationEntity;
import com.eid.dal.entity.ServiceAccessEntity;
import com.eid.dal.entity.ServiceAppLockEntity;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/7/21 Time:下午5:19
 */
@Slf4j
@Component
public class CertificateBizImpl implements CertificateBiz {

    @Autowired
    private ServiceAppLockDao serviceAppLockDao;

    @Autowired
    private CertificateAuthenticationDao certificateAuthenticationDao;

    @Autowired
    private ServiceAccessDao serviceAccessDao;

    @Override
    public String get(String companyId) {
        ServiceAccessEntity serviceAccessEntity = serviceAccessDao.findByAccessId(companyId);
        log.info("Call serviceAccessDao.get request:{};response:{};", companyId, serviceAccessEntity);
        if (Objects.equal(serviceAccessEntity, null))
            throw new FacadeException(ErrorCode.NON_EXISTENT);
        if (!Objects.equal(serviceAccessEntity.getItem(), "1"))
            throw new FacadeException(ErrorCode.STATUS_UN_NORMAL);
        if (!Objects.equal(serviceAccessEntity.getStatus(), 1))
            throw new FacadeException(ErrorCode.STATUS_UN_NORMAL);
        if (Objects.equal(serviceAccessEntity.getExpiryDate(), null) || serviceAccessEntity.getExpiryDate().before(new Date()))
            throw new FacadeException(ErrorCode.SERVICE_EXPIRY);

        return serviceAccessEntity.getAccessKey();
    }

    @Override
    public CertificateResDTO match(String id, String name, String certificateNo, String certificatePassword, String attach) {
        CertificateResDTO certificateResDTO = new CertificateResDTO();

        MD5Encrypt md5Encrypt = new MD5Encrypt();
        String no = md5Encrypt.encode(certificateNo);
        ServiceAppLockEntity serviceAppLockEntity = serviceAppLockDao.findByIdCard(no);
        log.info("Call serviceAppLockDao.findByIdCard request:{};response:{};", certificateNo, serviceAppLockEntity);

        if (Objects.equal(serviceAppLockEntity, null) || (Strings.isNullOrEmpty(certificatePassword) && Strings.isNullOrEmpty(serviceAppLockEntity.getPassword()))) {
            certificateResDTO.setResultDetail(ErrorCode.UNABLE_AUTHENTICATION.getCode());
            certificateResDTO.setResultMessage(ErrorCode.UNABLE_AUTHENTICATION.getDesc());
            certificateResDTO.setStatus(CertificateStatus.UNABLE.getCode());
        } else if (!Encodes.validatePassword(certificatePassword, serviceAppLockEntity.getPassword())) {
            certificateResDTO.setResultDetail(ErrorCode.PASSWORD_ERROR.getCode());
            certificateResDTO.setResultMessage(ErrorCode.PASSWORD_ERROR.getDesc());
            certificateResDTO.setStatus(CertificateStatus.FAILURE.getCode());
        } else if (!Objects.equal(serviceAppLockEntity.getStatus(), 1)) {
            certificateResDTO.setResultDetail(ErrorCode.STATUS_ERROR.getCode());
            certificateResDTO.setResultMessage(ErrorCode.STATUS_ERROR.getDesc());
            certificateResDTO.setStatus(CertificateStatus.FAILURE.getCode());
        } else {
            certificateResDTO.setStatus(CertificateStatus.SUCCESS.getCode());
        }

        certificateResDTO.setAttach(attach);
        CertificateAuthenticationEntity certificateAuthenticationEntity = BeanMapperUtil.objConvert(certificateResDTO, CertificateAuthenticationEntity.class);
        certificateAuthenticationEntity.setCreatedAt(new Date());
        certificateAuthenticationEntity.setResultTime(new Date());
        certificateAuthenticationEntity.setName(name);
        certificateAuthenticationEntity.setCompanyId(id);
        certificateAuthenticationEntity.setBizSequenceId(UUID.randomUUID().toString().replaceAll("-", ""));
        certificateAuthenticationEntity.setBizTime(DateUtil.getCurrent(DateUtil.timePattern));
        certificateAuthenticationEntity.setBizType(BizType.CERTIFICATE.getCode());
        certificateAuthenticationDao.save(certificateAuthenticationEntity);
        certificateResDTO.setResultTime(DateUtil.getCurrent(DateUtil.timePattern));
        return certificateResDTO;
    }
}
