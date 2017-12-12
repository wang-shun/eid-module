package com.eid.company.biz;

import com.eid.common.model.param.res.CertificateResDTO;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/7/21 Time:下午5:19
 */
public interface CertificateBiz {

    /**
     * 获取证件验证key
     *
     * @param companyId
     * @return
     */
    String get(String companyId);

    /**
     * 校验证件密码是否匹配
     *
     * @param certificateNo
     * @param certificatePassword
     * @return
     */
    CertificateResDTO match(String id, String name, String certificateNo, String certificatePassword, String attach);
}
