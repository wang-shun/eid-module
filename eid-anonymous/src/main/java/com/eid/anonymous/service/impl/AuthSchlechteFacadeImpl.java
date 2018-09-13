package com.eid.anonymous.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.eid.anonymous.service.AuthSchlechteFacade;
import com.eid.common.enums.ErrorCode;
import com.eid.common.enums.ServiceType;
import com.eid.common.exception.FacadeException;
import com.eid.common.model.Response;
import com.eid.dal.dao.CompanyAppDao;
import com.eid.dal.entity.CompanyAppEntity;
import com.eid.dal.entity.CompanyAuthenticationEntity;
import com.eid.dal.manager.AuthenticationManager;
import com.eid.dal.manager.DispatchCmdManager;
import com.eidlink.vals.sdk.constants.*;
import com.eidlink.vals.sdk.pojo.request.*;
import com.eidlink.vals.sdk.pojo.result.CommonResult;
import com.eidlink.vals.sdk.service.EidlinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

/**
* 金联非eID服务
* 1.返照身份核验服务
 * 2.行驶证信息核查服务
 * 3.身份证风险核验服务
 * 4.手机号实名认证服务
 * 5.不良身份信息服务
 * 6.车辆查询服务
 * 7.简项身份信息核查服务
 * 8.多项身份信息核查服务
 * 9.人像比对服务
 * 10.银行卡信息核验服务
 * 11.银行卡基本信息服务
 * 12.OCR简项
 * 13.OCR多项
 * 14.两照对比
*
* @author pdz 2018-07-05 下午 1:56
*
**/
@Slf4j
@Service
public class AuthSchlechteFacadeImpl implements AuthSchlechteFacade
{

    @Autowired
    private DispatchCmdManager dispatchCmdManager;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CompanyAppDao companyAppDao;

    /**
     * 两照比对
     * @param image1   图片
     * @param image2   图片
     * @param apId
     * @return
     */
    @Override
    public Response<Object> authenticationImageContrast(String image1, String image2, String apId){
        log.info("eid OCR多项：image1:{},image2:{}, apId:{}",image1,image2,apId);
        Response<Object> response = new Response<>();
        CompanyAuthenticationEntity companyAuthenticationEntity = new CompanyAuthenticationEntity();

        try
        {
            // 调用IDSO两照比对服务
            PublicParam publicParam = new PublicParam();
            ImageCheckParam imageCheckParam = new ImageCheckParam(publicParam,image1,image2);
            CommonResult result = EidlinkService.facecompCheck(imageCheckParam);
            log.info("eid 两照比对服务IDSO响应数据:{}", JSONObject.toJSONString(result));

            // 根据apId查询商户信息
            CompanyAppEntity companyAppEntity = companyAppDao.findByApId(apId);
            if(Objects.isNull(companyAppEntity))
                throw new FacadeException(ErrorCode.COMPANY_INFO_NOTFAND);

            // 入库
            companyAuthenticationEntity = this.saveCompanyAuthentication(result,companyAppEntity,ServiceType.EID_IMAGE_CONTRAST.getCode());

            // 响应结果
            response.setErrorCode("");
            response.setErrorMsg("");
            response.setResult(JSONObject.toJSON(result));

        }catch (FacadeException fe) {
            log.error("eid 两照比对服务异常：image1:{},image2:{}, apId:{}",image1,image2, apId, fe);
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("eid 两照比对系统异常：image1:{},image2:{}, apId:{}",image1,image2, apId, e);
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        // 计费（两照比对服务由于是用户直接支付，所以不扣商户的钱，这里取消扣费）
        dispatchCmdManager.feeCommand(companyAuthenticationEntity.getId().toString());

        log.info("eid 两照比对服务响应数据：{};", response);
        return response;
    }

    /**
     * OCR多项
     * @param image   图片，身份证正面照
     * @param apId
     * @return
     */
    @Override
    public Response<Object> authenticationOcrMore(String image, String apId){
        log.info("eid OCR多项：image:{}, apId:{}",image, apId);
        Response<Object> response = new Response<>();
        CompanyAuthenticationEntity companyAuthenticationEntity = new CompanyAuthenticationEntity();

        try
        {
            // 调用IDSO-OCR多项服务
            PublicParam publicParam = new PublicParam();
            ImageCheckParam imageCheckParam = new ImageCheckParam(publicParam,image);
            CommonResult result = EidlinkService.ocrMoreCheck(imageCheckParam);
            log.info("eid OCR多项服务IDSO响应数据:{}", JSONObject.toJSONString(result));

            // 根据apId查询商户信息
            CompanyAppEntity companyAppEntity = companyAppDao.findByApId(apId);
            if(Objects.isNull(companyAppEntity))
                throw new FacadeException(ErrorCode.COMPANY_INFO_NOTFAND);

            // 入库
            companyAuthenticationEntity = this.saveCompanyAuthentication(result,companyAppEntity,ServiceType.EID_OCR_MORE.getCode());

            // 响应结果
            response.setErrorCode("");
            response.setErrorMsg("");
            response.setResult(JSONObject.toJSON(result));

        }catch (FacadeException fe) {
            log.error("eid OCR多项服务异常：image:{}, apId:{}",image, apId, fe);
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("eid OCR多项系统异常：image:{}, apId:{}", image, apId, e);
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        // 计费（OCR多项服务由于是用户直接支付，所以不扣商户的钱，这里取消扣费）
        dispatchCmdManager.feeCommand(companyAuthenticationEntity.getId().toString());

        log.info("eid OCR多项服务响应数据：{};", response);
        return response;
    }

    /**
     * OCR简项
     * @param image   图片，身份证正面照
     * @param apId
     * @return
     */
    @Override
    public Response<Object> authenticationOcrSimple(String image, String apId){
        log.info("eid OCR简项：image:{}, apId:{}",image, apId);
        Response<Object> response = new Response<>();
        CompanyAuthenticationEntity companyAuthenticationEntity = new CompanyAuthenticationEntity();

        try
        {
            // 调用IDSO-OCR简项服务
            PublicParam publicParam = new PublicParam();
            ImageCheckParam imageCheckParam = new ImageCheckParam(publicParam,image);
            CommonResult result = EidlinkService.ocrSimpleCheck(imageCheckParam);
            log.info("eid OCR简项服务IDSO响应数据:{}", JSONObject.toJSONString(result));

            // 根据apId查询商户信息
            CompanyAppEntity companyAppEntity = companyAppDao.findByApId(apId);
            if(Objects.isNull(companyAppEntity))
                throw new FacadeException(ErrorCode.COMPANY_INFO_NOTFAND);

            // 入库
            companyAuthenticationEntity = this.saveCompanyAuthentication(result,companyAppEntity,ServiceType.EID_OCR_SIMPLE.getCode());

            // 响应结果
            response.setErrorCode("");
            response.setErrorMsg("");
            response.setResult(JSONObject.toJSON(result));

        }catch (FacadeException fe) {
            log.error("eid OCR简项服务异常：image:{}, apId:{}",image, apId, fe);
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("eid OCR简项系统异常：image:{}, apId:{}", image, apId, e);
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        // 计费（OCR简项服务由于是用户直接支付，所以不扣商户的钱，这里取消扣费）
        dispatchCmdManager.feeCommand(companyAuthenticationEntity.getId().toString());

        log.info("eid OCR简项服务响应数据：{};", response);
        return response;
    }

    /**
     * 银行卡基本信息
     * @param bankCardNum   银行卡号
     * @param apId
     * @return
     */
    @Override
    public Response<Object> authenticationGainBankInfo(String bankCardNum, String apId){
        log.info("eid 银行卡基本信息：bankCardNum:{}, apId:{}",bankCardNum, apId);
        Response<Object> response = new Response<>();
        CompanyAuthenticationEntity companyAuthenticationEntity = new CompanyAuthenticationEntity();

        try
        {
            // 调用IDSO银行卡基本信息服务
            PublicParam publicParam = new PublicParam();
            CardCheckParam cardCheckParam = new CardCheckParam(publicParam,bankCardNum);
            CommonResult result = EidlinkService.cardBaseCheck(cardCheckParam);
            log.info("eid 银行卡基本信息服务IDSO响应数据:{}", JSONObject.toJSONString(result));

            // 根据apId查询商户信息
            CompanyAppEntity companyAppEntity = companyAppDao.findByApId(apId);
            if(Objects.isNull(companyAppEntity))
                throw new FacadeException(ErrorCode.COMPANY_INFO_NOTFAND);

            // 入库
            companyAuthenticationEntity = this.saveCompanyAuthentication(result,companyAppEntity,ServiceType.EID_GAIN_BANK_INFO.getCode());

            // 响应结果
            response.setErrorCode("");
            response.setErrorMsg("");
            response.setResult(JSONObject.toJSON(result));

        }catch (FacadeException fe) {
            log.error("eid 银行卡基本信息服务异常：bankCardNum:{}, apId:{}",bankCardNum, apId, fe);
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("eid 银行卡基本信息系统异常：bankCardNum:{}, apId:{}", bankCardNum, apId, e);
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        // 计费（银行卡基本信息服务由于是用户直接支付，所以不扣商户的钱，这里取消扣费）
        dispatchCmdManager.feeCommand(companyAuthenticationEntity.getId().toString());

        log.info("eid 银行卡基本信息服务响应数据：{};", response);
        return response;
    }

    /**
     * 银行卡信息核验
     * @param name          姓名
     * @param idNumber      证件号码
     * @param bankCardNum   银行卡号
     * @param mobile        银行预留手机号码
     * @param apId
     * @return
     */
    @Override
    public Response<Object> authenticationBankInfo(String name, String idNumber, String bankCardNum, String mobile, String apId){
        log.info("eid 银行卡信息核验：name:{}，idNumber:{}，bankCardNum:{}, mobile:{}, apId:{}",name ,idNumber ,bankCardNum, mobile, apId);
        Response<Object> response = new Response<>();
        CompanyAuthenticationEntity companyAuthenticationEntity = new CompanyAuthenticationEntity();

        try
        {
            // 调用IDSO银行卡信息核验服务
            PublicParam publicParam = new PublicParam("","","","");
            UserInfoParam userInfoParam = new UserInfoParam(name, idNumber);
            CardInfoParam cardInfoParam = new CardInfoParam(bankCardNum,"1",mobile);
            CardCheckParam cardCheckParam = new CardCheckParam(publicParam,userInfoParam,cardInfoParam);
            CommonResult result = EidlinkService.CardCheck(cardCheckParam);
            log.info("eid 银行卡信息核验服务IDSO响应数据:{}", JSONObject.toJSONString(result));

            // 根据apId查询商户信息
            CompanyAppEntity companyAppEntity = companyAppDao.findByApId(apId);
            if(Objects.isNull(companyAppEntity))
                throw new FacadeException(ErrorCode.COMPANY_INFO_NOTFAND);

            // 入库
            companyAuthenticationEntity = this.saveCompanyAuthentication(result,companyAppEntity,ServiceType.EID_BANK_INFO.getCode());

            // 响应结果
            response.setErrorCode("");
            response.setErrorMsg("");
            response.setResult(JSONObject.toJSON(result));

        }catch (FacadeException fe) {
            log.error("eid 银行卡信息核验服务异常：name:{}，idNumber:{}，bankCardNum:{}, mobile:{}, apId:{}",name ,idNumber ,bankCardNum, mobile, apId, fe);
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("eid 银行卡信息核验系统异常：name:{}，idNumber:{}，bankCardNum:{}, mobile:{}, apId:{}",name ,idNumber ,bankCardNum, mobile, apId, e);
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        // 计费（银行卡信息核验服务由于是用户直接支付，所以不扣商户的钱，这里取消扣费）
        dispatchCmdManager.feeCommand(companyAuthenticationEntity.getId().toString());

        log.info("eid 银行卡信息核验服务响应数据：{};", response);
        return response;
    }

    /**
     * 人像比对
     * @param idNumber      身份证号码
     * @param name          姓名
     * @param image         图片的二进制base64字符串
     * @param apId
     * @return
     */
    @Override
    public Response<Object> authenticationUserPhoto(String name, String idNumber, String image, String apId){
        log.info("eid 人像比对：name:{}，idNumber:{}，image:{}, apId:{}",name ,idNumber ,image, apId);
        Response<Object> response = new Response<>();
        CompanyAuthenticationEntity companyAuthenticationEntity = new CompanyAuthenticationEntity();

        try
        {
            // 调用IDSO人像比对服务
            UserInfoParam userInfo = new UserInfoParam(name, idNumber, EidType.ID_CARD, image);
            PublicParam publicParam = new PublicParam("","","","");
            PhotoCheckParam photoCheckParam = new PhotoCheckParam(publicParam,userInfo);
            CommonResult result = EidlinkService.PhotoCheck(photoCheckParam);
            log.info("eid 人像比对服务IDSO响应数据:{}", JSONObject.toJSONString(result));

            // 根据apId查询商户信息
            CompanyAppEntity companyAppEntity = companyAppDao.findByApId(apId);
            if(Objects.isNull(companyAppEntity))
                throw new FacadeException(ErrorCode.COMPANY_INFO_NOTFAND);

            // 入库
            companyAuthenticationEntity = this.saveCompanyAuthentication(result,companyAppEntity,ServiceType.EID_USER_PHOTO.getCode());

            // 响应结果
            response.setErrorCode("");
            response.setErrorMsg("");
            response.setResult(JSONObject.toJSON(result));

        }catch (FacadeException fe) {
            log.error("eid 人像比对服务异常：name:{}，idNumber:{}，image:{},apId:{}",name ,idNumber ,image ,apId, fe);
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("eid 人像比对系统异常：name:{}，idNumber:{}，image:{},apId:{}",name ,idNumber ,image ,apId, e);
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        // 计费（人像比对服务由于是用户直接支付，所以不扣商户的钱，这里取消扣费）
        dispatchCmdManager.feeCommand(companyAuthenticationEntity.getId().toString());

        log.info("eid 人像比对服务响应数据：{};", response);
        return response;
    }

    /**
     * 多项身份信息核查
     * @param idNumber      身份证号码
     * @param name          姓名
     * @param dob           出生年月日
     * @param sex           性别
     * @param edu_lev       文化程度
     * @param location      地址
     * @param apId
     * @return
     */
    @Override
    public Response<Object> authenticationUsersInfo(String name, String idNumber, String  dob, String sex, String edu_lev, String location, String apId) {
        log.info("eid 多项身份信息核查：name:{}，idNumber:{}，dob:{}， sex:{}， edu_lev:{}，location:{}，apId:{}",name ,idNumber ,dob ,sex ,edu_lev ,location ,apId);
        Response<Object> response = new Response<>();
        CompanyAuthenticationEntity companyAuthenticationEntity = new CompanyAuthenticationEntity();

        try
        {
            // 调用IDSO多项身份信息核查服务
            UserInfoParam userInfo = new UserInfoParam(name, idNumber, EidType.ID_CARD, dob, sex, edu_lev, location);
            PublicParam publicParam = new PublicParam("","","","");
            MoreCheckParam moreCheckParam = new MoreCheckParam(publicParam,userInfo);
            CommonResult result = EidlinkService.MoreCheck(moreCheckParam);
            log.info("eid 身份多项比对服务IDSO响应数据:{}", JSONObject.toJSONString(result));

            // 根据apId查询商户信息
            CompanyAppEntity companyAppEntity = companyAppDao.findByApId(apId);
            if(Objects.isNull(companyAppEntity))
                throw new FacadeException(ErrorCode.COMPANY_INFO_NOTFAND);

            // 入库
            companyAuthenticationEntity = this.saveCompanyAuthentication(result,companyAppEntity,ServiceType.EID_USERS_INFO.getCode());

            // 响应结果
            response.setErrorCode("");
            response.setErrorMsg("");
            response.setResult(JSONObject.toJSON(result));

        }catch (FacadeException fe) {
            log.error("eid 多项身份信息核查服务异常：name:{}，idNumber:{}，dob:{}， sex:{}， edu_lev:{}，location:{}，apId:{}",name ,idNumber ,dob ,sex ,edu_lev ,location ,apId, fe);
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("eid 多项身份信息核查系统异常：name:{}，idNumber:{}，dob:{}， sex:{}， edu_lev:{}，location:{}，apId:{}",name ,idNumber ,dob ,sex ,edu_lev ,location ,apId, e);
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        // 计费（多项身份信息核查服务由于是用户直接支付，所以不扣商户的钱，这里取消扣费）
        dispatchCmdManager.feeCommand(companyAuthenticationEntity.getId().toString());

        log.info("eid 多项身份信息核查服务响应数据：{};", response);
        return response;
    }

    /**
     * 简项身份信息核查
     * @param idNumber      身份证号码
     * @param name          姓名
     * @param apId
     * @return
     */
    @Override
    public Response<Object> authenticationUserInfo(String name, String idNumber, String apId){
        log.info("eid 简项身份信息核查：name:{}，idNumber:{}，apId:{}",name ,idNumber ,apId);
        Response<Object> response = new Response<>();
        CompanyAuthenticationEntity companyAuthenticationEntity = new CompanyAuthenticationEntity();

        try
        {
            // 调用IDSO简项身份信息核查服务
            UserInfoParam userInfo = new UserInfoParam(name, idNumber);
            PublicParam publicParam = new PublicParam("","","","");
            SimpleCheckParam simpleCheckParam = new SimpleCheckParam(publicParam,userInfo);
            CommonResult result = EidlinkService.SimpleCheck(simpleCheckParam);
            log.info("eid 身份简项比对服务IDSO响应数据:{}", JSONObject.toJSONString(result));

            // 根据apId查询商户信息
            CompanyAppEntity companyAppEntity = companyAppDao.findByApId(apId);
            if(Objects.isNull(companyAppEntity))
                throw new FacadeException(ErrorCode.COMPANY_INFO_NOTFAND);

            // 入库
            companyAuthenticationEntity = this.saveCompanyAuthentication(result,companyAppEntity,ServiceType.EID_USER_INFO.getCode());

            // 响应结果
            response.setErrorCode("");
            response.setErrorMsg("");
            response.setResult(JSONObject.toJSON(result));

        }catch (FacadeException fe) {
            log.error("eid 简项身份信息核查服务异常：name:{}，idNumber:{}，apId:{}",name ,idNumber ,apId, fe);
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("eid 简项身份信息核查系统异常：name:{}，idNumber:{}，apId:{}",name ,idNumber ,apId, e);
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        // 计费（简项身份信息核查服务由于是用户直接支付，所以不扣商户的钱，这里取消扣费）
        dispatchCmdManager.feeCommand(companyAuthenticationEntity.getId().toString());

        log.info("eid 简项身份信息核查服务响应数据：{};", response);
        return response;
    }

    /**
     * 车辆信息查询
     * @param carNumber     车牌号码
     * @param apId
     * @return
     */
    @Override
    public Response<Object> authenticationCarInfo(String carNumber, String apId){
        log.info("eid 车辆信息查询：carNumber:{}，apId:{}",carNumber,apId);
        Response<Object> response = new Response<>();
        CompanyAuthenticationEntity companyAuthenticationEntity = new CompanyAuthenticationEntity();

        try
        {
            // 调用IDSO车辆查询服务
            CarInfoParam carInfoParam = new CarInfoParam(carNumber,"02");
            CarCheckParam carCheckParam = new CarCheckParam(new PublicParam(),carInfoParam);
            CommonResult result = EidlinkService.carMessageCheck(carCheckParam);
            log.info("eid 车辆信息查询服务IDSO响应数据:{}", JSONObject.toJSONString(result));

            // 根据apId查询商户信息
            CompanyAppEntity companyAppEntity = companyAppDao.findByApId(apId);
            if(Objects.isNull(companyAppEntity))
                throw new FacadeException(ErrorCode.COMPANY_INFO_NOTFAND);

            // 入库
            companyAuthenticationEntity = this.saveCompanyAuthentication(result,companyAppEntity,ServiceType.EID_CAR_INFO.getCode());

            // 响应结果
            response.setErrorCode("");
            response.setErrorMsg("");
            response.setResult(JSONObject.toJSON(result));

        }catch (FacadeException fe) {
            log.error("eid 车辆信息查询服务异常：carNumber:{}，apId:{}",carNumber,apId,fe);
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("eid 车辆信息查询系统异常：carNumber:{}，apId:{}",carNumber,apId,e);
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        // 计费（车辆信息查询服务由于是用户直接支付，所以不扣商户的钱，这里取消扣费）
        dispatchCmdManager.feeCommand(companyAuthenticationEntity.getId().toString());

        log.info("eid 车辆信息查询服务响应数据：{};", response);
        return response;
    }

    /**
     * 返照身份核验服务
     * @param name      姓名
     * @param idNum     证件号码
     * @param apId      商户id
     * @return  Response
     */
    @Override
    public Response<Object> authenticationUserInfoImage(String name, String idNum, String apId){
        log.info("eid 返照身份核验服务调用：name:{};idNum:{};apId:{}",name,idNum,apId);
        Response<Object> response = new Response<>();
        CompanyAuthenticationEntity companyAuthenticationEntity = new CompanyAuthenticationEntity();

        try
        {
            // 调用IDSO返照身份核验服务
    		UserInfoParam userInfo = new UserInfoParam(name,idNum);
    		PublicParam pp = new PublicParam();
    		SimpleCheckParam scp = new SimpleCheckParam(pp,userInfo);
    		CommonResult result = EidlinkService.idphotoCheck(scp);
    		log.info("eid 返照身份核验服务IDSO响应数据:{}", JSONObject.toJSONString(result));

            // 根据apId查询商户信息
            CompanyAppEntity companyAppEntity = companyAppDao.findByApId(apId);
            if(Objects.isNull(companyAppEntity))
                throw new FacadeException(ErrorCode.COMPANY_INFO_NOTFAND);

            // 入库
            companyAuthenticationEntity = this.saveCompanyAuthentication(result,companyAppEntity,ServiceType.EID_USER_INFO_IMAGE.getCode());

            // 响应结果
            response.setErrorCode("");
            response.setErrorMsg("");
            response.setResult(JSONObject.toJSON(result));

        }catch (FacadeException fe) {
            log.error("eid 返照身份核验服务异常：name:{};idNum:{};apId:{}；cause:{};",name,idNum,apId,fe);
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("eid 返照身份核验系统异常：name:{};idNum:{};apId:{}；cause:{};",name,idNum,apId,e);
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        // 计费（返照身份核验服务由于是用户直接支付，所以不扣商户的钱，这里取消扣费）
        dispatchCmdManager.feeCommand(companyAuthenticationEntity.getId().toString());

        log.info("eid 返照身份核验服务响应数据：{};", response);
        return response;
    }

    /**
     * 行驶证信息核查服务
     * @param carNumber         车牌号
     * @param carType           车辆类型
     * @param carFrameNumber    车架号
     * @param name              车辆所属人姓名
     * @param registerTime      除此登记时间
     * @param engineNumber      发动机号
     * @param apId              商户id
     * @return  Response
     */
    @Override
    public Response<Object> authenticationLicense(String carNumber, String carType, String carFrameNumber, String name, String registerTime, String engineNumber, String apId){
        log.info("eid 行驶证信息核查服务调用：carNumber:{};carType:{};carFrameNumber:{};name:{};registerTime:{};engineNumber:{};apId:{}",carNumber,carType,carFrameNumber,name,registerTime,engineNumber,apId);
        Response<Object> response = new Response<>();
        CompanyAuthenticationEntity companyAuthenticationEntity = new CompanyAuthenticationEntity();

        try
        {
            // 调用IDSO行驶证信息核查服务
            // CarInfoParam carInfo = new CarInfoParam("湘AA09N6","02","车架号17位","行驶证所有人:张三","初次登记时间","发动机号");
            CarInfoParam carInfo = new CarInfoParam(carNumber,carType,carFrameNumber,name,registerTime,engineNumber);
            PublicParam pp = new PublicParam();
            CarCheckParam ccp = new CarCheckParam(pp, carInfo);
            CommonResult result = EidlinkService.carLicenseInfoCheck(ccp);
            log.info("eid 行驶证信息核查服务IDSO响应数据:{}", JSONObject.toJSONString(result));

            // 根据apId查询商户信息
            CompanyAppEntity companyAppEntity = companyAppDao.findByApId(apId);
            if(Objects.isNull(companyAppEntity))
                throw new FacadeException(ErrorCode.COMPANY_INFO_NOTFAND);

            // 入库
            companyAuthenticationEntity = this.saveCompanyAuthentication(result,companyAppEntity,ServiceType.EID_LICENSE.getCode());

            // 响应结果
            response.setErrorCode("");
            response.setErrorMsg("");
            response.setResult(JSONObject.toJSON(result));

        }catch (FacadeException fe) {
            log.error("eid 行驶证信息核查服务异常：carNumber:{};carType:{};carFrameNumber:{};name:{};registerTime:{};engineNumber:{};apId:{};fe:{}",carNumber,carType,carFrameNumber,name,registerTime,engineNumber,apId,fe);
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("eid 行驶证信息核查系统异常：carNumber:{};carType:{};carFrameNumber:{};name:{};registerTime:{};engineNumber:{};apId:{};fe:{}",carNumber,carType,carFrameNumber,name,registerTime,engineNumber,apId,e);
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        // 计费（行驶证信息核查服务由于是用户直接支付，所以不扣商户的钱，这里取消扣费）
        dispatchCmdManager.feeCommand(companyAuthenticationEntity.getId().toString());

        log.info("eid 行驶证信息核查服务响应数据：{};", response);
        return response;
    }

    /**
     * 身份证风险核验服务
     * @param name      姓名
     * @param idNum     证件号码
     * @param begindate 身份证初次登记日期，格式：yyyy-MM-dd
     * @param apId      商户id
     * @return  Response
     */
    @Override
    public Response<Object> authenticationIdNum(String name, String idNum, String begindate, String apId){
        log.info("eid 身份证风险核验服务调用：name:{};idNum:{};begindate:{};apId:{}",name,idNum,begindate,apId);
        Response<Object> response = new Response<>();
        CompanyAuthenticationEntity companyAuthenticationEntity = new CompanyAuthenticationEntity();

        try
        {
            // 调用IDSO身份风险核验服务
            UserInfoParam userInfo = new UserInfoParam(name,idNum);
            PublicParam pp = new PublicParam();
            IDcardInvalidCheckParam idcp = new IDcardInvalidCheckParam(pp,userInfo,begindate);
            CommonResult result = EidlinkService.idCardInvalidCheck(idcp);
            log.info("eid 身份证风险核验服务IDSO响应结果:{}",JSONObject.toJSONString(result));

            // 根据apId查询商户信息
            CompanyAppEntity companyAppEntity = companyAppDao.findByApId(apId);
            if(Objects.isNull(companyAppEntity))
                throw new FacadeException(ErrorCode.COMPANY_INFO_NOTFAND);

            // 入库
            companyAuthenticationEntity = this.saveCompanyAuthentication(result,companyAppEntity,ServiceType.EID_IDNUM.getCode());

            // 响应结果
            response.setErrorCode("");
            response.setErrorMsg("");
            response.setResult(JSONObject.toJSON(result));

        }catch (FacadeException fe) {
            log.error("eid 身份证风险核验服务异常：name:{};idNum:{};begindate:{};apId:{}；cause:{};",name,idNum,begindate,apId,fe);
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("eid 身份证风险核验系统异常：name:{};idNum:{};begindate:{};apId:{}；cause:{};",name,idNum,begindate,apId,e);
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        // 计费（身份证风险核验服务由于是用户直接支付，所以不扣商户的钱，这里取消扣费）
        dispatchCmdManager.feeCommand(companyAuthenticationEntity.getId().toString());

        log.info("eid 身份证风险核验服务响应数据：{};", response);
        return response;
    }

    /**
     * 手机号实名认证服务
     * @param name      姓名
     * @param idNum     证件号码
     * @param phone     手机号码
     * @param apId      商户id
     * @return  Response
     */
    @Override
    public Response<Object> authenticationPhone(String name, String idNum, String phone, String apId){
        log.info("eid 手机号码实名验证服务调用：name:{};idNum:{};phone:{};apId:{}",name,idNum,phone,apId);
        Response<Object> response = new Response<>();
        CompanyAuthenticationEntity companyAuthenticationEntity = new CompanyAuthenticationEntity();

        try
        {
            // 调用IDSO手机号码实名认证服务
            MobileParam mp = new MobileParam(name,idNum,phone);
            PublicParam pp = new PublicParam();
            pp.setBiz_type(BizeType.MOBILE_9904000);
            MobileCheckParam mcp = new MobileCheckParam(pp,mp);
            CommonResult result = EidlinkService.mobileCheck(mcp);
            log.info("eid 手机号码实名验证服务IDSO响应数据:{}",JSONObject.toJSONString(result));

            // 根据apId查询商户信息
            CompanyAppEntity companyAppEntity = companyAppDao.findByApId(apId);
            if(Objects.isNull(companyAppEntity))
                throw new FacadeException(ErrorCode.COMPANY_INFO_NOTFAND);

            // 入库
            companyAuthenticationEntity = this.saveCompanyAuthentication(result,companyAppEntity,ServiceType.EID_PHONE.getCode());

            // 响应结果
            response.setErrorCode("");
            response.setErrorMsg("");
            response.setResult(JSONObject.toJSON(result));

        }catch (FacadeException fe) {
            log.error("eid 手机号码实名验证服务异常：name:{};idNum:{};phone:{};apId:{}；cause:{};",name,idNum,phone,apId,fe);
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("eid 手机号码实名验证系统异常：name:{};idNum:{};phone:{};apId:{}；cause:{};",name,idNum,phone,apId,e);
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        // 计费（手机号实名认证服务由于是用户直接支付，所以不扣商户的钱，这里取消扣费）
        dispatchCmdManager.feeCommand(companyAuthenticationEntity.getId().toString());

        log.info("eid 手机号码实名验证服务响应数据：{};", response);
        return response;
    }

    /**
     * 不良身份信息服务
     */
    @Override
    public Response<Object> authenticationSchlechte(String name, String idNum, String apId){
        log.info("eid 不良身份信息服务调用：name:{};idNUm:{};apId:{};",name,idNum,apId);
        Response<Object> response = new Response<>();
        CompanyAuthenticationEntity companyAuthenticationEntity = new CompanyAuthenticationEntity();
        try {

            // 调用不良身份信息查询SDK
            UserInfoParam userInfoParam = new UserInfoParam(name,idNum);
            PublicParam publicParam = new PublicParam();
            SimpleCheckParam simpleCheckParam = new SimpleCheckParam(publicParam,userInfoParam);
            CommonResult result = EidlinkService.badMessageCheck(simpleCheckParam);
            log.info("不良身份信息IDSO响应结果:{}",JSONObject.toJSONString(result));

            // 根据apId查询商户信息
            CompanyAppEntity companyAppEntity = companyAppDao.findByApId(apId);
            if(Objects.isNull(companyAppEntity))
                throw new FacadeException(ErrorCode.COMPANY_INFO_NOTFAND);

            // 入库
            companyAuthenticationEntity = this.saveCompanyAuthentication(result,companyAppEntity,ServiceType.EID_SCHLECHTE.getCode());

            // 响应结果
            response.setErrorCode("");
            response.setErrorMsg("");
            response.setResult(JSONObject.toJSON(result));
        } catch (FacadeException fe) {
            log.error("eid 不良身份信息服务异常：name：{}；idNum：{}；cause:{};",name,idNum,fe);
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("eid 不良身份信息系统异常：name：{}；idNum：{}；cause:{};",name,idNum,e);
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        // 计费（不良信息服务由于是用户直接支付，所以不扣商户的钱，这里取消扣费）
        dispatchCmdManager.feeCommand(companyAuthenticationEntity.getId().toString());

        log.info("eid 不良身份信息服务响应数据：{};", response);
        return response;
    }

    // 添加非eid认证服务信息记录【认证记录】
    private CompanyAuthenticationEntity saveCompanyAuthentication(CommonResult result, CompanyAppEntity companyAppEntity, String serviceType) throws Exception {
        CompanyAuthenticationEntity companyAuthenticationEntity = new CompanyAuthenticationEntity();
        // 动态获取的数据
        companyAuthenticationEntity.setCompanyId(companyAppEntity.getCompanyId());
        companyAuthenticationEntity.setApId(companyAppEntity.getApId());
        companyAuthenticationEntity.setAppId(companyAppEntity.getAppId());
        companyAuthenticationEntity.setStatus(Objects.equals(result.getResult(),"00") ? 0 : 1);//0:成功，1:失败，2:处理中
        // IDSO响应数据
        companyAuthenticationEntity.setBizSequenceId(result.getBiz_sequence_id());
        companyAuthenticationEntity.setResultDetail(result.getResult_detail());
        companyAuthenticationEntity.setResultMessage(result.getResult_msg());
        companyAuthenticationEntity.setUserIdInfo(JSONObject.toJSONString(result.getUser_id_info()));
        companyAuthenticationEntity.setAppEidCode(result.getAppEidCode());
        // 固定数据
        companyAuthenticationEntity.setReturnUrl("returnUrl");
        companyAuthenticationEntity.setBizType(serviceType);//非eid认证服务类型，参考ServiceType枚举类
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        companyAuthenticationEntity.setBizTime(sim.format(new Date()));
        companyAuthenticationEntity.setAttach("attach");
        companyAuthenticationEntity.setResultTime(new Date());
        companyAuthenticationEntity.setCreatedAt(new Date());

//        companyAuthenticationEntity.setEidIdentification();
//        companyAuthenticationEntity.setAccessToken("");
//        companyAuthenticationEntity.setExtension("");
//        companyAuthenticationEntity.setIdHash("");
//        companyAuthenticationEntity.setVerifyFee(1L);

        return authenticationManager.insert(companyAuthenticationEntity);

    }

}