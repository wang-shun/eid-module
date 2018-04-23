package com.eid.anonymous.process.pki;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eid.anonymous.annotations.BizImpl;
import com.eid.anonymous.process.AuthenticationProcessor;
import com.eid.common.enums.BizType;
import com.eid.common.model.param.req.EidBaseDTO;
import com.eid.common.model.param.request.EidBaseParam;
import com.eid.common.model.param.request.pki.EidPkiRealNameParam;
import com.eid.common.model.param.request.pki.EidPkiRealNameSIMParam;
import com.eid.common.util.BeanMapperUtil;
import com.eid.dal.dao.CompanyAuthenticationDao;
import com.eid.dal.entity.ProvinceCityEntity;
import com.eid.dal.manager.ProvinceCityManager;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 实名认证pki
 * Created by:ruben Date:2017/2/8 Time:下午4:26
 */
@Slf4j
@Component("pkiAuthenProSIMProcessorImpl")
@BizImpl(value = {BizType.AUTHEN_SIM_ASYNC})
public class AuthenProSIMProcessorImpl extends AuthenticationProcessor {

    @Override
    public EidBaseParam getParam(EidBaseDTO eidBaseDTO) {
        EidPkiRealNameSIMParam eidPkiRealNameSIMParam = new EidPkiRealNameSIMParam();
        BeanMapperUtil.copy(eidBaseDTO, eidPkiRealNameSIMParam);
        generateRecord(eidPkiRealNameSIMParam);
        eidPkiRealNameSIMParam.setAppId(eidBaseDTO.getApId());
        try {
//            eidPkiRealNameSIMParam.setEidIdentification(new String(Base64.getEncoder().encode(eidPkiRealNameParam.getEidSn().getBytes()), "UTF-8"));
            eidPkiRealNameSIMParam.setEidIdentification("");
        } catch (Exception e) {
            log.error("failed to base64 error message:{};", e.getMessage());
        }
        return eidPkiRealNameSIMParam;
    }

    @Autowired
    private ProvinceCityManager provinceCityManager;

    @Autowired
    private CompanyAuthenticationDao companyAuthenticationDao;

    @Override
    protected String getUserInfo(EidBaseParam eidBaseParam) {
        EidPkiRealNameParam eidHmacRealNameParam = (EidPkiRealNameParam) eidBaseParam;
        String userIdInfo = eidHmacRealNameParam.getUserIdInfo();
        log.info("Call save identity information request:{};", userIdInfo);
        try {
            if (Strings.isNullOrEmpty(userIdInfo))
                return null;

            JSONObject jsonObject = JSON.parseObject(userIdInfo);
            String idNum = jsonObject.getString("idnum");
            String name = jsonObject.getString("name");
            JSONObject json = new JSONObject();
            if (!Strings.isNullOrEmpty(idNum) && idNum.length() == 18) {

                String gender = Integer.parseInt(idNum.substring(16, 17)) % 2 == 0 ? "女" : "男";
                String birthDate = idNum.substring(6, 14);
                String code = idNum.substring(0, 4);
                ProvinceCityEntity provinceCityEntity = provinceCityManager.queryCity(Integer.parseInt(code));
                String city = "";
                String province = "";
                if (!Objects.equal(provinceCityEntity, null)) {
                    province = provinceCityEntity.getProvince();
                    city = provinceCityEntity.getCity();
                }

                json.put("gender", gender);
                json.put("city", city);
                json.put("province", province);
                json.put("birthDate", birthDate);
            }
            if (name.length() > 1)
                json.put("name", name.substring(0, 1));

            companyAuthenticationDao.updateUserInfo(json.toString(), new Date(), eidBaseParam.getAccessToken());
            return json.toString();
        } catch (Exception e) {
            log.error("Failed to save identity information request:{};errorMessage:{}", userIdInfo, Throwables.getStackTraceAsString(e));
        }

        return null;
    }
}