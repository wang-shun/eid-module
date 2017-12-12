package com.eid.company.biz;

import com.eid.company.model.DebitInfo;

/**
 * 商户账户业务层
 * Created by:ruben Date:2017/1/19 Time:下午4:44
 */
public interface CompanyAccountBiz {

    /**
     * 商户账户扣款
     *
     * @param debitInfo
     * @return
     */
    Boolean debit(DebitInfo debitInfo);
}
