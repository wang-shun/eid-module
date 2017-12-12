package com.eid.company.biz.impl;

import com.eid.common.enums.ErrorCode;
import com.eid.common.exception.FacadeException;
import com.eid.common.util.BeanMapperUtil;
import com.eid.common.util.DateUtil;
import com.eid.company.biz.CompanyAuthenticationBiz;
import com.eid.company.biz.CompanyBiz;
import com.eid.company.model.CompanyAuthenticationDTO;
import com.eid.company.model.CompanyInfoDTO;
import com.eid.dal.dao.CompanyAuthenticationDao;
import com.eid.dal.entity.CompanyAuthenticationEntity;
import com.google.common.base.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2017/2/10.
 */
@Slf4j
@Component
public class CompanyAuthenticationBizImpl implements CompanyAuthenticationBiz {

    @Autowired
    private CompanyAuthenticationDao companyAuthenticationDao;

    @Autowired
    private CompanyBiz companyBiz;

    @Override
    public CompanyAuthenticationDTO getResult(String apId, String accessToken) {
        CompanyInfoDTO companyInfoDTO = companyBiz.queryCompanyInfoByApId(apId);
        if (Objects.equal(companyInfoDTO, null))
            throw new FacadeException(ErrorCode.NON_EXISTENT);

        CompanyAuthenticationEntity cae = companyAuthenticationDao.findByCompanyIdAndAccessToken(companyInfoDTO.getCompanyId(), accessToken);
        log.info("call companyAuthenticationDao.findByCompanyIdAndAccessToken response:{};", cae);

        return BeanMapperUtil.objConvert(cae, CompanyAuthenticationDTO.class);
    }

    @Override
    public List<CompanyAuthenticationDTO> getRecordList(String appEidCode, String startDate, String endDate, Integer page, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(page, pageSize, new Sort(Sort.Direction.DESC, "updatedAt"));
        Page<CompanyAuthenticationEntity> pages = companyAuthenticationDao.findByAppEidCodeAndUpdatedAtBetween(appEidCode, DateUtil.parse(startDate, DateUtil.datePattern), DateUtil.parse(endDate, DateUtil.datePattern), pageRequest);
        log.info("call companyAuthenticationDao.findByAppEidCodeAndUpdatedAtBetween response:{};", pages);
        return BeanMapperUtil.mapList(pages.getContent(), CompanyAuthenticationDTO.class);
    }
}
