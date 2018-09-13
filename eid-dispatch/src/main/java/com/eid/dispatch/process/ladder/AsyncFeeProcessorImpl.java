package com.eid.dispatch.process.ladder;

import com.eid.common.enums.FeeStatus;
import com.eid.common.enums.FeeType;
import com.eid.common.util.RedisUtil;
import com.eid.dal.dao.CompanyAgreementLadderDao;
import com.eid.dal.entity.CompanyAgreementEntity;
import com.eid.dal.entity.CompanyAgreementLadderEntity;
import com.eid.dal.entity.CompanyAuthenticationEntity;
import com.eid.dispatch.annotations.FeeTypeImpl;
import com.eid.dispatch.process.AsyncFeeProcessor;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/13 Time:下午6:23
 */
@Slf4j
@Component("ladderAsyncFeeProcessorImpl")
@FeeTypeImpl(value = {FeeType.LADDER})// 阶梯计费
public class AsyncFeeProcessorImpl extends AsyncFeeProcessor {

    @Autowired(required = false)
    private JedisCluster jedisCluster;

    @Autowired
    private CompanyAgreementLadderDao companyAgreementLadderDao;

    @Override
    protected Long costCompanyFee(CompanyAgreementEntity companyAgreementEntity, CompanyAuthenticationEntity companyAuthenticationEntity) {
//        Integer num = companyAuthenticationDao.countByCompanyIdAndStatus(companyAuthenticationEntity.getCompanyId(), AuthenticationStatus.SUCCESS.getCode());
        String tmpNum = jedisCluster.get(RedisUtil.getAuthenticationCount(companyAuthenticationEntity.getCompanyId()));
        if (Strings.isNullOrEmpty(tmpNum))
            tmpNum = "0";
        Integer num = Integer.parseInt(tmpNum);
        CompanyAgreementLadderEntity companyAgreementLadderEntity = companyAgreementLadderDao.queryFee(companyAgreementEntity.getId(), num, FeeStatus.NORMAL.getCode());
        return companyAgreementLadderEntity.getEidFee();
    }
}
