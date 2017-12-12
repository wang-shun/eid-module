package com.eid.company.biz;

import com.eid.company.model.CompanyAuthenticationDTO;

import java.util.List;

/**
 * Created by Administrator on 2017/2/10.
 */
public interface CompanyAuthenticationBiz {

    CompanyAuthenticationDTO getResult(String apId, String accessToken);

    List<CompanyAuthenticationDTO> getRecordList(String appEidCode, String startDate, String endDate, Integer page, Integer pageSize);
}
