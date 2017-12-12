package com.eid.company.service;

import com.eid.common.model.Response;
import com.eid.company.model.CompanyAuthenticationDTO;

import java.util.List;

/**
 * Created by Administrator on 2017/2/10.
 */
public interface CompanyAuthenticationFacade {

    Response<CompanyAuthenticationDTO> getAuthenticationResult(String apId, String accessToken);

    Response<List<CompanyAuthenticationDTO>> getRecordPage(String appEidCode, String startDate, String endDate, Integer page, Integer pageSize);
}
