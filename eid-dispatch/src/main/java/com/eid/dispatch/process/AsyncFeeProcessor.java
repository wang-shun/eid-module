package com.eid.dispatch.process;

import com.eid.common.enums.ErrorCode;
import com.eid.common.exception.FacadeException;
import com.eid.common.model.Response;
import com.eid.common.util.RedisUtil;
import com.eid.company.enums.DebitType;
import com.eid.company.model.DebitInfo;
import com.eid.company.service.CompanyAccountFacade;
import com.eid.dal.dao.CompanyAuthenticationDao;
import com.eid.dal.entity.CompanyAgreementEntity;
import com.eid.dal.entity.CompanyAuthenticationEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.Date;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/13 Time:下午6:24
 */
@Slf4j
@Component
public abstract class AsyncFeeProcessor {

    @Autowired(required = false)
    private CompanyAccountFacade companyAccountFacade;

    @Autowired
    private CompanyAuthenticationDao companyAuthenticationDao;

    @Autowired(required = false)
    private JedisCluster jedisCluster;

    protected abstract Long costCompanyFee(CompanyAgreementEntity companyAgreementEntity, CompanyAuthenticationEntity companyAuthenticationEntity);

    public void asyncFee(CompanyAgreementEntity companyAgreementEntity, CompanyAuthenticationEntity companyAuthenticationEntity) {
        Long fee = costCompanyFee(companyAgreementEntity, companyAuthenticationEntity);

        companyAuthenticationDao.updateFee(fee, new Date(), companyAuthenticationEntity.getId());

        DebitInfo debitInfo = new DebitInfo();
        debitInfo.setCompanyId(companyAuthenticationEntity.getCompanyId());
        debitInfo.setMoney(fee);
        debitInfo.setDebitType(DebitType.AUTHENTICATION);
        debitInfo.setContent(DebitType.AUTHENTICATION.getDesc());
        Response<Boolean> response = companyAccountFacade.deduction(debitInfo);
        if (!response.isSuccess() || !response.getResult())
            throw new FacadeException(ErrorCode.SYS_ERR);

        String authenticationCountKey = RedisUtil.getAuthenticationCount(companyAuthenticationEntity.getCompanyId());
        jedisCluster.incr(authenticationCountKey);
        jedisCluster.expire(authenticationCountKey, authExpireTime);
    }

    private final int authExpireTime = 60 * 60 * 24 * 50;
}
