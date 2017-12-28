package com.eid.connect.service.impl;

import com.eid.common.enums.ErrorCode;
import com.eid.common.exception.FacadeException;
import com.eid.common.model.Response;
import com.eid.connect.service.EncryptionMachineFacade;
import com.eid.dev.constant.EDeviceSignType;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.util.EncryptionMachine;
import org.springframework.stereotype.Service;

/**
*
* 加密机ap操作
*
* @author pdz 2017-12-20 下午 4:03
*
**/
@Slf4j
@Service
public class EncryptionMachineImpl implements EncryptionMachineFacade
{

    @Override
    public Response<String> getAppkey(String appid, String appkeyFactor) {

        log.info("Call EncryptionMachineFacade.getAppkey ,appid:"+appid+",appkeyFactor:"+appkeyFactor);
        Response<String> response = new Response<>();
        try {
            if (Strings.isNullOrEmpty(appid) || Strings.isNullOrEmpty(appkeyFactor))
                throw new FacadeException(ErrorCode.PARAM_ERR);

            response.setResult(new EncryptionMachine().getAppkey(appid,appkeyFactor));

        } catch (FacadeException fe) {
            log.error("Failed to EncryptionMachineFacade.getAppkey request:{};CAUSE:{};");
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to EncryptionMachineFacade.getAppkey request:{};CAUSE:{};");
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("Call EncryptionMachineFacade.getAppkey request:{};result:{};");
        return response;
    }

    @Override
    public Response<String> apToAsSign(String appid, String appkeyFactor, String signFactor, String data, String signType) {

        log.info("Call EncryptionMachineFacade.apToAsSign ,appid:"+appid+",appkeyFactor:"+appkeyFactor+",data:"+data+",signType:"+signType);
        Response<String> response = new Response<>();
        try {
            if (Strings.isNullOrEmpty(appid) || Strings.isNullOrEmpty(appkeyFactor) || Strings.isNullOrEmpty(signFactor)
                    || Strings.isNullOrEmpty(data) || Strings.isNullOrEmpty(signType))
                throw new FacadeException(ErrorCode.PARAM_ERR);

            response.setResult(new EncryptionMachine().apToAsSign(appid,appkeyFactor,signFactor,data,getSignType(signType)));

        } catch (FacadeException fe) {
            log.error("Failed to EncryptionMachineFacade.apToAsSign request:{};CAUSE:{};");
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to EncryptionMachineFacade.apToAsSign request:{};CAUSE:{};");
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("Call EncryptionMachineFacade.apToAsSign request:{};result:{};");

        return response;
    }

    private EDeviceSignType getSignType (String signType) {

        switch (signType) {
            case Constant.HMAC_MD5:
                return EDeviceSignType.HMAC_MD5;
            case Constant.HMAC_SHA1:
                return EDeviceSignType.HMAC_SHA1;
            case Constant.HMAC_SHA256:
                return EDeviceSignType.HMAC_SHA256;
            case Constant.HMAC_SM3:
                return EDeviceSignType.HMAC_SM3;
            default:
                return EDeviceSignType.HMAC_SHA1;
        }

    }

}
