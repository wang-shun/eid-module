package com.eid.common.model.param.res;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/5/23 Time:下午5:19
 */
@Data
public class CertificateResDTO implements Serializable {

    private static final long serialVersionUID = 8314397689311211999L;

    /**
     * 响应描述 例如：证件错误
     */
    private String resultMessage;

    /**
     * 响应码 例如：CERTIFICATE_ERROR
     */
    private String resultDetail;

    /**
     * 响应时间 例如：2017-07-21 16:35:55
     */
    private String resultTime;

    /**
     * 状态 例如：0-成功、1-失败
     */
    private Integer status;

    /**
     * 附加信息
     */
    private String attach;

}
