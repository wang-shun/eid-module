package com.eid.company.biz.impl;

import com.eid.common.enums.ErrorCode;
import com.eid.common.exception.FacadeException;
import com.eid.company.biz.CompanyAccountBiz;
import com.eid.company.model.DebitInfo;
import com.eid.dal.dao.AccountDetailDao;
import com.eid.dal.dao.CompanyAccountDao;
import com.eid.dal.entity.AccountDetailEntity;
import com.eid.dal.entity.CompanyAccountEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
public class CompanyAccountBizImpl implements CompanyAccountBiz {

    @Autowired
    private CompanyAccountDao companyAccountDao;

    @Autowired
    private AccountDetailDao accountDetailDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean debit(DebitInfo debitInfo) {
        // 修改账户金额
        CompanyAccountEntity companyAccountEntity = companyAccountDao.findByCompanyId(debitInfo.getCompanyId());
        if (Objects.equals(companyAccountEntity, null))
            throw new FacadeException(ErrorCode.NON_EXISTENT);

        Long money = companyAccountEntity.getMoney() == null ? 0 : companyAccountEntity.getMoney();
        Long consumeMoney = companyAccountEntity.getConsumeMoney() == null ? 0 : companyAccountEntity.getConsumeMoney();
        money -= debitInfo.getMoney();
        consumeMoney += debitInfo.getMoney();
        companyAccountEntity.setMoney(money);
        companyAccountEntity.setConsumeMoney(consumeMoney);
        companyAccountEntity.setUpdatedAt(new Date());
        companyAccountDao.save(companyAccountEntity);

        // 账户明细入库
        AccountDetailEntity accountDetailEntity = new AccountDetailEntity();
        accountDetailEntity.setMoney(debitInfo.getMoney());
        accountDetailEntity.setBalance(companyAccountEntity.getMoney());
        accountDetailEntity.setCompanyId(debitInfo.getCompanyId());
        accountDetailEntity.setContent(debitInfo.getContent());
        accountDetailEntity.setType(debitInfo.getDebitType().getCode());
        accountDetailEntity.setCreatedAt(new Date());
        accountDetailDao.save(accountDetailEntity);

        return true;
    }
}
