package com.eid.common.model.param.request.management;

import com.eid.common.model.param.request.EidBaseParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * EID app注册接口基础参数
 * Created by:ruben Date:2017/2/20 Time:下午3:31
 */
@Getter
@Setter
@ToString(callSuper = true)
public class EidAppRegBaseParam extends EidBaseParam {

    private static final long serialVersionUID = -7288196963686433729L;

    private String appInfo;
    private String appName;
    private String cmpName;
    private String domainName;
    private String ipAddr;
    private String defaultSecurityType;
    private String appIcon;
    private Map<String, String> bizs;
    private String province;
    private String city;
    private String orgType;
    private String contact1;
    private String contact1Tel;
    private String contact1Email;
    private String contact2;
    private String contact2Tel;
    private String contact2Email;
    private String remark;
    private String appSalt;
}
