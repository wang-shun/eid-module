package com.eid.anonymous.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.eid.anonymous.service.AuthSchlechteFacade;
import com.eid.common.enums.ErrorCode;
import com.eid.common.exception.FacadeException;
import com.eid.common.model.Response;
import com.eid.dal.dao.CompanyAppDao;
import com.eid.dal.entity.CompanyAppEntity;
import com.eid.dal.entity.CompanyAuthenticationEntity;
import com.eid.dal.manager.AuthenticationManager;
import com.eid.dal.manager.DispatchCmdManager;
import com.eidlink.vals.sdk.constants.PublicParam;
import com.eidlink.vals.sdk.constants.UserInfoParam;
import com.eidlink.vals.sdk.pojo.request.SimpleCheckParam;
import com.eidlink.vals.sdk.pojo.result.CommonResult;
import com.eidlink.vals.sdk.service.EidlinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
*
* 不良身份信息服务
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

    public static void main(String[] args) throws Exception
    {
        // 调用不良身份信息查询SDK
        UserInfoParam userInfoParam = new UserInfoParam("彭代忠","432503199110137518");
        PublicParam publicParam = new PublicParam();
        SimpleCheckParam simpleCheckParam = new SimpleCheckParam(publicParam,userInfoParam);
        CommonResult result = EidlinkService.badMessageCheck(simpleCheckParam);
        log.info("不良身份信息IDSO响应结果:"+JSONObject.toJSONString(result));
    }

    // 不良身份信息查询
    @Override
    public Response<Object> authentication(String name, String idNum, String apId){
        log.info("eid 不良身份信息服务调用：name:{};idNUm:{};apId:{};",name,idNum,apId);
        Response<Object> response = new Response<>();
        CompanyAuthenticationEntity companyAuthenticationEntity = new CompanyAuthenticationEntity();
        CommonResult result = new CommonResult();
        try {

            // 调用不良身份信息查询SDK
            UserInfoParam userInfoParam = new UserInfoParam(name,idNum);
            PublicParam publicParam = new PublicParam();
            SimpleCheckParam simpleCheckParam = new SimpleCheckParam(publicParam,userInfoParam);
            result = EidlinkService.badMessageCheck(simpleCheckParam);
            log.info("不良身份信息IDSO响应结果:"+JSONObject.toJSONString(result));

            // 根据apId查询商户信息
            CompanyAppEntity companyAppEntity = companyAppDao.findByApId(apId);
            if(Objects.isNull(companyAppEntity))
                throw new FacadeException(ErrorCode.COMPANY_INFO_NOTFAND);

            // 入库
            companyAuthenticationEntity = this.saveCompanyAuthentication(result,companyAppEntity);

//            // 验证不良信息是否查询成功
//            if(!Objects.equals(result.getResult(),"00"))
//                throw new FacadeException(null);//不成功，返回IDSO响应的错误码对应的错误

            //响应结果
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

        // 计费
        dispatchCmdManager.feeCommand(companyAuthenticationEntity.getId().toString());

        log.info("eid 不良身份信息服务响应数据：{};", response);
        return response;
    }

    // 添加不良身份信息查询记录【认证记录】
    private CompanyAuthenticationEntity saveCompanyAuthentication(CommonResult result, CompanyAppEntity companyAppEntity) throws Exception {
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
        companyAuthenticationEntity.setBizType("SCHLECHTE_INFO_AUTH");//不良身份信息查询
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
