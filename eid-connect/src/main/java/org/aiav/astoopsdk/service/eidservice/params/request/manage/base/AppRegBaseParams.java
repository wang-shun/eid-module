package org.aiav.astoopsdk.service.eidservice.params.request.manage.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.ToString;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.constants.EBizType;
import org.aiav.astoopsdk.constants.EOrgType;
import org.aiav.astoopsdk.constants.ESecurityType;
import org.aiav.astoopsdk.service.bizservice.params.SecurityFactor;
import org.aiav.astoopsdk.service.eidservice.params.request.BaseParams;
import org.aiav.astoopsdk.util.FuncUtil;
import org.aiav.astoopsdk.util.ServiceUtil;

@ToString(callSuper = true)
@SuppressWarnings("serial")
public class AppRegBaseParams extends BaseParams {
    private String appInfo;
    private String appName;
    private String cmpName;
    private String domainName;
    private String ipAddr;
    private ESecurityType defaultSecurityType;
    private String appIcon;
    private JSONArray bizs;
    private String province;
    private String city;
    private EOrgType orgType;
    private String contact1;
    private String contact1Tel;
    private String contact1Email;
    private String contact2;
    private String contact2Tel;
    private String contact2Email;
    private String remark;
    private String appSalt;

    public JSONObject buildAppRegInfoBaseReq() {
        JSONObject req = buildBaseReq();
        req.put(Constant.SECURITY_FACTOR,
                new SecurityFactor(ServiceUtil.genHexString(8), this.getSignFactor()).toJson());
        req.put(Constant.APP_INFO, appInfo);
        req.put(Constant.APP_NAME, appName);
        req.put(Constant.CMP_NAME, cmpName);
        req.put(Constant.DOMAIN_NAME, domainName);
        req.put(Constant.IP_ADDR, ipAddr);
        if (!FuncUtil.isEmpty(defaultSecurityType)) {
            req.put(Constant.DEFAULT_SECURITY_TYPE,
                    defaultSecurityType.getIndex());
        }

        req.put(Constant.APP_ICON, appIcon);
        if (!FuncUtil.isEmpty(bizs)) {
            req.put(Constant.BIZS, bizs.toString());
        }
        req.put(Constant.PROVINCE, province);
        req.put(Constant.CITY, city);
        if (!FuncUtil.isEmpty(orgType)) {
            req.put(Constant.ORG_TYPE, orgType.getIndex());
        }
        req.put(Constant.CONTACT1, contact1);
        req.put(Constant.CONTACT1_TEL, contact1Tel);
        req.put(Constant.CONTACT1_EMAIL, contact1Email);
        req.put(Constant.CONTACT2, contact2);
        req.put(Constant.CONTACT2_TEL, contact2Tel);
        req.put(Constant.CONTACT2_EMAIL, contact2Email);
        req.put(Constant.REMARK, remark);
        req.put(Constant.APP_SALT, appSalt);
        return req;
    }

    public String getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(String appInfo) {
        this.appInfo = FuncUtil.trimStr(appInfo);
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = FuncUtil.trimStr(appName);
    }

    public String getCmpName() {
        return cmpName;
    }

    public void setCmpName(String cmpName) {
        this.cmpName = FuncUtil.trimStr(cmpName);
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = FuncUtil.trimStr(domainName);
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = FuncUtil.trimStr(ipAddr);
    }

    public ESecurityType getDefaultSecurityType() {
        return defaultSecurityType;
    }

    public void setDefaultSecurityType(ESecurityType defaultSecurityType) {
        this.defaultSecurityType = defaultSecurityType;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = FuncUtil.trimStr(appIcon);
    }

    public JSONArray getBizs() {
        return bizs;
    }

    public void setBizs(EBizType bizType, String callBackUrl) {
        if (bizType != null) {
            JSONObject e = new JSONObject();
            e.put(Constant.BIZ_TYPE, bizType.getIndex());
            String cbu = FuncUtil.trimStr(callBackUrl);
            e.put(Constant.CALL_BACK_URL,
                    FuncUtil.isEmpty(cbu) ? Constant.BLANK_STR : cbu);

            if (this.bizs == null) {
                this.bizs = new JSONArray();
            }
            this.bizs.add(e);
        }
    }

    public void setBizs(Map<EBizType, String> map) {
        List<EBizType> bizType = new ArrayList<EBizType>(map.keySet());

        for (int i = 0; i < bizType.size(); i++) {
            if (bizType.get(i) != null) {
                JSONObject e = new JSONObject();
                e.put(Constant.BIZ_TYPE, bizType.get(i).getIndex());
                String cbu = FuncUtil.trimStr(map.get(bizType.get(i)));
                e.put(Constant.CALL_BACK_URL,
                        FuncUtil.isEmpty(cbu) ? Constant.BLANK_STR : cbu);

                if (this.bizs == null) {
                    this.bizs = new JSONArray();
                }
                this.bizs.add(e);
            }
        }

    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = FuncUtil.trimStr(province);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = FuncUtil.trimStr(city);
    }

    public EOrgType getOrgType() {
        return orgType;
    }

    public void setOrgType(EOrgType orgType) {
        this.orgType = orgType;
    }

    public String getContact1() {
        return contact1;
    }

    public void setContact1(String contact1) {
        this.contact1 = FuncUtil.trimStr(contact1);
    }

    public String getContact1Tel() {
        return contact1Tel;
    }

    public void setContact1Tel(String contact1Tel) {
        this.contact1Tel = FuncUtil.trimStr(contact1Tel);
    }

    public String getContact1Email() {
        return contact1Email;
    }

    public void setContact1Email(String contact1Email) {
        this.contact1Email = FuncUtil.trimStr(contact1Email);
    }

    public String getContact2() {
        return contact2;
    }

    public void setContact2(String contact2) {
        this.contact2 = FuncUtil.trimStr(contact2);
    }

    public String getContact2Email() {
        return contact2Email;
    }

    public void setContact2Email(String contact2Email) {
        this.contact2Email = FuncUtil.trimStr(contact2Email);
    }

    public String getContact2Tel() {
        return contact2Tel;
    }

    public void setContact2Tel(String contact2Tel) {
        this.contact2Tel = FuncUtil.trimStr(contact2Tel);
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = FuncUtil.trimStr(remark);
    }

    public String getAppSalt() {
        return appSalt;
    }

    public void setAppSalt(String appSalt) {
        this.appSalt = FuncUtil.trimStr(appSalt);
    }
}
