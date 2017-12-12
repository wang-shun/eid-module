package com.eid.company.service;

import com.eid.common.model.Response;
import com.eid.company.model.DebitInfo;

/**
 * 商户账户
 * Created by:ruben Date:2017/1/17 Time:下午5:40
 */
public interface CompanyAccountFacade {

    Response<Boolean> deduction(DebitInfo debitInfo);
}
