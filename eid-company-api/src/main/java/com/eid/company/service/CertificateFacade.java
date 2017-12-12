package com.eid.company.service;

import com.eid.common.model.Response;
import com.eid.common.model.param.res.CertificateResDTO;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/7/21 Time:下午5:17
 */
public interface CertificateFacade {

    /**
     * 获取证件key
     *
     * @param companyId
     * @return
     */
    Response<String> get(String companyId);

    /**
     * 证件认证
     *
     * @param id
     * @param certificateNo
     * @param certificatePassword
     * @param attach
     * @return
     */
    Response<CertificateResDTO> validate(String id, String name, String certificateNo, String certificatePassword, String attach);
}
