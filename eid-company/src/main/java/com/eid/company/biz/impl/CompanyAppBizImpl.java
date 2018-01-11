package com.eid.company.biz.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eid.common.enums.BizType;
import com.eid.common.enums.CompanyAppStatus;
import com.eid.common.enums.ErrorCode;
import com.eid.common.exception.FacadeException;
import com.eid.common.model.Response;
import com.eid.common.model.param.request.management.EidAppRegParam;
import com.eid.common.model.param.request.management.EidAppkeyUpdateParam;
import com.eid.common.model.param.result.EidBaseResult;
import com.eid.common.model.param.result.management.EidAppRegResult;
import com.eid.common.model.param.result.management.EidAppkeyUpdateResult;
import com.eid.common.util.DateUtil;
import com.eid.common.util.MD5Encrypt;
import com.eid.common.util.MathUtils;
import com.eid.common.util.RedisUtil;
import com.eid.company.biz.CompanyAppBiz;
import com.eid.connect.service.IdsoCallBackFacade;
import com.eid.connect.service.SendFacade;
import com.eid.dal.dao.CompanyAppDao;
import com.eid.dal.entity.CompanyAppEntity;
import com.eid.dal.entity.CompanyInfoEntity;
import com.eid.dal.manager.CompanyAppManager;
import com.eid.dal.manager.CompanyInfoManager;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import redis.clients.jedis.JedisCluster;

import java.util.UUID;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/2/20 Time:下午3:59
 */
@Slf4j
@Component
public class CompanyAppBizImpl implements CompanyAppBiz {

    @Autowired(required = false)
    private SendFacade sendFacade;

//    @Autowired(required = false)
//    private IdsoCallBackFacade idsoCallBackFacade;

    @Autowired(required = false)
    private JedisCluster jedisCluster;

    @Autowired
    private CompanyInfoManager companyInfoManager;

    @Autowired
    private CompanyAppManager companyAppManager;

    @Autowired
    private CompanyAppDao companyAppDao;

    @Override
    public Boolean get(EidAppRegParam eidAppRegParam) {
        CompanyInfoEntity companyInfoEntity = companyInfoManager.queryCompanyInfoById(eidAppRegParam.getCompanyId());
        log.info("Call companyInfoManager.queryCompanyInfo request:{};response:{};", eidAppRegParam.getCompanyId(), companyInfoEntity);
        if (Objects.equal(companyInfoEntity, null))
            throw new FacadeException(ErrorCode.NON_EXISTENT);

        eidAppRegParam.setBizType(BizType.APP_REG.getCode());
        Response<EidBaseResult> resultResponse = sendFacade.request(eidAppRegParam);
        log.info("Call sendFacade.request request:{};response:{};", eidAppRegParam, resultResponse);
        if (!resultResponse.isSuccess() || Objects.equal(resultResponse.getResult(), null))
            throw new FacadeException(resultResponse.getErrorCode(), resultResponse.getErrorMsg());

        companyInfoManager.delCompanyInfo(companyInfoEntity.getCompanyId());

        return resultResponse.getResult().getStatus() == 1 ? true : false;

        // 此处companyInfo表的apid和apkey已经取消，需要去app信息表中去查询apid和apkey
        // **************************************************************
//        if (!Strings.isNullOrEmpty(companyInfoEntity.getApId()) && !Strings.isNullOrEmpty(companyInfoEntity.getApKey()))
//            return true;

//        eidAppRegParam.setBizType(BizType.APP_REG.getCode());
//        Response<EidBaseResult> resultResponse = sendFacade.request(eidAppRegParam);
//        log.info("Call sendFacade.request request:{};response:{};", eidAppRegParam, resultResponse);
//        if (!resultResponse.isSuccess() || Objects.equal(resultResponse.getResult(), null))
//            throw new FacadeException(resultResponse.getErrorCode(), resultResponse.getErrorMsg());

//        EidAppRegResult eidAppRegResult = (EidAppRegResult) resultResponse.getResult();
        // todo 获取appid appkeyfactory并更新数据库
//        String apId = eidAppRegResult.getAppid();
//        String apKey = eidAppRegResult.getAppkeyFactor();

        // 更新companInfo表的apid和apkey（companyInfo表的apid和apkey已经取消）
        // 此处需要更新app信息表的app注册状态
        // ***********************************************************************
//        if (companyInfoManager.updateApInfo(apId, apKey, companyInfoEntity.getId()) != 1)
//            throw new FacadeException(ErrorCode.DATA_ERR);

//        companyInfoManager.delCompanyInfo(companyInfoEntity.getCompanyId());
//        return true;
    }

    @Override
    public Boolean getAppId(EidAppRegParam eidAppRegParam) {
        CompanyAppEntity companyAppEntity = companyAppManager.queryAppInfoById(Long.parseLong(eidAppRegParam.getCompanyId()));
        log.info("Call companyAppManager.queryAppInfoById request:{};response:{};", eidAppRegParam.getCompanyId(), companyAppEntity);
        if (Objects.equal(companyAppEntity, null))
            throw new FacadeException(ErrorCode.NON_EXISTENT);

        if (!Strings.isNullOrEmpty(companyAppEntity.getAppId()) && !Strings.isNullOrEmpty(companyAppEntity.getAppKey()))
            return true;

        // 生成appid、appkey
        Long seqNum = jedisCluster.incr(RedisUtil.APP_ID_SEQUENCE_KEY);
        if (seqNum >= 100000)
            jedisCluster.set(RedisUtil.APP_ID_SEQUENCE_KEY, "0");
        String appId = RedisUtil.generateAppId(seqNum.toString());
        MD5Encrypt encoderMd5 = new MD5Encrypt(appId);
        UUID uuid = UUID.randomUUID();
        String appKey = encoderMd5.encode(uuid.toString());

        // 更新数据库
        if (companyAppManager.updateKey(appId, appKey, companyAppEntity.getId()) != 1)
            throw new FacadeException(ErrorCode.DATA_ERR);

        return true;
    }

    @Override
    public Boolean resetApKey(EidAppkeyUpdateParam eidAppkeyUpdateParam) {
        CompanyInfoEntity companyInfoEntity = companyInfoManager.queryCompanyInfoByApId(eidAppkeyUpdateParam.getAppid());
        log.info("Call companyInfoManager.queryCompanyInfoByApId request:{};response:{};", eidAppkeyUpdateParam.getAppid(), companyInfoEntity);
        if (Objects.equal(companyInfoEntity, null))
            throw new FacadeException(ErrorCode.NON_EXISTENT);

        eidAppkeyUpdateParam.setBizType(BizType.APP_KEY_UPDATE.getCode());
        Response<EidBaseResult> resultResponse = sendFacade.request(eidAppkeyUpdateParam);
        log.info("Call sendFacade.request request:{};response:{};", eidAppkeyUpdateParam, resultResponse);
        if (!resultResponse.isSuccess() || Objects.equal(resultResponse.getResult(), null))
            throw new FacadeException(resultResponse.getErrorCode(), resultResponse.getErrorMsg());

        EidAppkeyUpdateResult eidAppkeyUpdateResult = (EidAppkeyUpdateResult) resultResponse.getResult();
        // todo 重置appkey 算法待讨论
//        String apId = companyInfoEntity.getApId();
//        String apKey = eidAppkeyUpdateResult.getAppkeyFactor();

        // 此处companyInfo表的apid和apkey已经取消，需要去app信息表中去查询api和apkey
        // **************************************************************
        String apId = "";
        String apKey = eidAppkeyUpdateResult.getAppkeyFactor();

        // todo 生成appkey后 更新数据库
        // 更新companInfo表的apid和apkey（companyInfo表的apid和apkey已经取消，所有要从新写）
        // ***********************************************************************
//        if (companyInfoManager.updateApInfo(apId, apKey, companyInfoEntity.getId()) != 1)
//            throw new FacadeException(ErrorCode.DATA_ERR);

        companyInfoManager.delCompanyInfo(companyInfoEntity.getCompanyId());
        return true;
    }

    @Override
    public Boolean resetAppKey(EidAppkeyUpdateParam eidAppkeyUpdateParam) {
        CompanyAppEntity companyAppEntity = companyAppManager.queryAppInfoByApp(eidAppkeyUpdateParam.getAppid());
        log.info("Call companyAppManager.queryAppInfoByApp request:{};response:{};", eidAppkeyUpdateParam.getAppid(), companyAppEntity);
        if (Objects.equal(companyAppEntity, null))
            throw new FacadeException(ErrorCode.NON_EXISTENT);

        // 重置appkey
        String appId = companyAppEntity.getAppId();
        MD5Encrypt encoderMd5 = new MD5Encrypt(appId);
        UUID uuid = UUID.randomUUID();
        String appKey = encoderMd5.encode(uuid.toString());

        // 重置后 更新数据库
        if (companyAppManager.updateKey(appId, appKey, companyAppEntity.getId()) != 1)
            throw new FacadeException(ErrorCode.DATA_ERR);

        companyAppManager.delAppInfo(appId);
        return true;
    }

    @Override
    public Boolean initIdAndKey(JSONObject requestData) {

        log.info("IDSO应用注册回调后IDSP初始化id和key:"+requestData);

        // 获取jsonObject中的apid和apkeyFactor
        String apId = requestData.getString("apid");// idso下发的apid
        String apkeyFactor = requestData.getString("appkey_factor");// idso下发的apkeyFactor
        String extension = requestData.getString("extension");// ap应用申请时提交的app信息表主键

        // 防止IDSP响应IDSO失败的重复处理
        CompanyAppEntity companyAppEntity = companyAppDao.findById(Long.valueOf(extension));
        if(companyAppEntity.getAppStatus()!=CompanyAppStatus.AUDIT_YES.getCode())// 已经处理过该回调通知
        return true;

        // 生成appid、appkey
        String appId = RedisUtil.prefix.concat(DateUtil.getCurrent()).concat(MathUtils.randomNum(7));// 'eID'+年月日时分秒+6位随机数
        MD5Encrypt encoderMd5 = new MD5Encrypt(appId);
        UUID uuid = UUID.randomUUID();
        String appkey = encoderMd5.encode(uuid.toString());

        // 更新数据库
        int updateCount = companyAppDao.initIDAndKey(appId,appkey,apId,apkeyFactor,Strings.isNullOrEmpty(apId) ? CompanyAppStatus.TRIAL_NO.getCode():CompanyAppStatus.TRIAL_YES.getCode(),Long.valueOf(extension));
        if (updateCount != 1)
            throw new FacadeException(ErrorCode.DATA_ERR);

        return true;
    }

}
