package com.eid.anonymous.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.eid.anonymous.service.AuthenticationSIMFacade;
import com.eid.common.enums.ErrorCode;
import com.eid.common.enums.ResCode;
import com.eid.common.exception.ControllerException;
import com.eid.common.exception.FacadeException;
import com.eid.common.model.Response;
import com.eid.connect.service.EncryptionMachineFacade;
import com.eid.connect.service.IdsoCallBackFacade;
import com.eid.dal.entity.CompanyAppEntity;
import com.eid.dal.entity.CompanyAuthenticationEntity;
import com.eid.dal.manager.AuthenticationManager;
import com.eid.dal.manager.DispatchCmdManager;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

@Slf4j
@Service
public class AuthenticationSIMFacadeImpl implements AuthenticationSIMFacade {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DispatchCmdManager dispatchCmdManager;

    @Autowired
    private IdsoCallBackFacade idsoCallBackFacade;

    @Autowired
    private EncryptionMachineFacade encryptionMachineFacade;

    // eID SIM 身份识别认证回调业务处理服务
    @Override
    public Response<JSONObject> eIDSIMCallBackHandle(JSONObject jsonObject) {
        log.info("IDSO回调IDSP-eID-SIM身份识别接口，方法名：eIDSIMCallBackHandle，回调参数JSON格式：{}",jsonObject);
        Response<JSONObject> response = new Response<>();
        try {

            // 获取响应详细结果码
            String resultDetail = jsonObject.getString("result_detail");
            String bizSequenceId = jsonObject.getString("biz_sequence_id");
            CompanyAuthenticationEntity companyAuthenticationEntity = authenticationManager.findByBizSequenceId(bizSequenceId);
            if(Objects.isNull(companyAuthenticationEntity))
                throw new FacadeException(ErrorCode.BIZ_SEQUENCE_ID_ERR);

            // 此处需要验证该认证结果通知是否已经处理，根据认证记录的状态进行验证
            // ....................................

            String apId = companyAuthenticationEntity.getApId();

            // 获取app信息
            CompanyAppEntity companyAppEntity = authenticationManager.findByApId(apId);
            if(Objects.isNull(companyAppEntity))
                throw new FacadeException(ErrorCode.BIZ_SEQUENCE_ID_ERR);

            // 初始化appeidcode
            String appeidcode = "";

            // TODO ********************SIM eID
            // SIM测试环境下数据库直接放的是apkey，可以直接使用
//            String apkey = companyAppEntity.getApKeyFactor();// 用于回调AP加密
//            String apkeyfactor = apkey;// 用于回调AP签名
            // SIM正式环境下需要通过加密机生成apkey
            Response<String> responseKey = encryptionMachineFacade.getAppkey(apId,companyAppEntity.getApKeyFactor());
            String apkey = responseKey.getResult();// 正式环境下的apkey，用于回调AP加密
            String apkeyfactor = companyAppEntity.getApKeyFactor();// 正式环境下的apkeyfactor，用于回调AP签名

            // 验证认证结果
            boolean flag = verifyResult(jsonObject);
            log.info("eID SIM 身份识别认证回调业务处理服务，本次认证结果：{}",flag);

            // 认证成功处理
            if(flag){

                log.info("IDSO回调解密通过bizSequenceId查询到的apkeyFactor：{}",apkeyfactor);
                appeidcode = encryptAppeidcode(jsonObject,apkey);
                log.info("eID SIM 身份识别认证回调业务处理服务，解密appeidcode结果：{}",appeidcode);

                // 修改认证记录状态
                authenticationManager.success(companyAuthenticationEntity.getId(),resultDetail, ResCode.EID_0000000.getDesc(),appeidcode);
            }

            // 认证失败处理
            else{

                // 修改认证记录状态
                String result = jsonObject.getString("result");
                authenticationManager.failed(companyAuthenticationEntity.getId(),resultDetail,result);
            }

            // 计费
            dispatchCmdManager.feeCommand(String.valueOf(companyAuthenticationEntity.getId()));

            // 回调AP，此处应该设计回调规则，按时间递增进行多次回调AP
            // 回调AP，此处回调规则依赖IDSO回调，如果IDSP回调AP失败，或者AP处理失败等异常原因则不给IDSO返回接收成功，等待IDSO下次回调IDSP，IDSP在回调AP
            String returnUrl = companyAuthenticationEntity.getReturnUrl();// 获取回调地址
            String returnParam = appendReturnAPParam(appeidcode,apkey,apId,jsonObject,apkeyfactor);// 拼接回调参数

            log.info("IDSP回调AP的地址 :{};      回调参数：{}",returnUrl,returnParam);
//            String resultData = HttpRequest.post(returnUrl).send("pdz").body();
            String resultData = httpRequestAP(returnUrl,returnParam);
            log.info("IDSP回调AP，AP响应数据：{}",resultData);

            // 验证回调AP结果并拼接响应IDSO的数据
            if(Objects.equals(resultData,new JSONObject(){{put("received","true");}}.toString()))
                // 拼接响应IDSO的数据
                response.setResult(appendResponseIDSOParam(jsonObject));
//                response.setResult(true);
//            else
////                 拼接响应IDSO的数据
//                response.setResult(false);

        } catch (FacadeException fe) {
            log.error("Failed to AuthenticationFacade.authentication request:{};CAUSE:{};", jsonObject, Throwables.getStackTraceAsString(fe));
            response.setErrorCode(fe.getCode());
            response.setErrorMsg(fe.getMessage());
        } catch (Exception e) {
            log.error("Failed to AuthenticationFacade.authentication request:{};CAUSE:{};", jsonObject, Throwables.getStackTraceAsString(e));
            response.setErrorCode(ErrorCode.SYS_ERR.getCode());
            response.setErrorMsg(ErrorCode.SYS_ERR.getDesc());
        }

        log.info("eID SIM 身份识别认证回调业务处理服务，处理结果数据：result:{};", response);
        return response;
    }

    // 验证eID SIM认证是否成功
    private boolean verifyResult(JSONObject jsonObject){
        String result = jsonObject.getString("result");
        String result_detail = jsonObject.getString("result_detail");
        if(result.equals("00") && result_detail.equals("0000000"))
            return true;
        return false;
    }

    // 解密appeidcode
    private String encryptAppeidcode(JSONObject jsonObject, String appKey){

        JSONObject security_factor = jsonObject.getJSONObject("security_factor");
        String encrypt_facotr = security_factor.getString("encrypt_factor");
        String user_id_info = jsonObject.getString("user_info");

        log.info("eID SIM 身份识别认证回调业务处理服务，解密请求参数,security_factor:{};encrypt_facotr:{};appkey:{};user_id_info:{}",security_factor,encrypt_facotr,appKey,user_id_info);
        String encryptUserIdInfo = idsoCallBackFacade.doEncrypt3DesECBPKCS5(appKey,user_id_info,encrypt_facotr);
        log.info("eID SIM 身份识别认证回调业务处理服务，解密结果user_info:{}",encryptUserIdInfo);

        JSONObject appeidcodeJSON = JSONObject.parseObject(encryptUserIdInfo);
        return appeidcodeJSON.getString("appeidcode");

    }

    // 拼接IDSP回调AP的参数
    private String appendReturnAPParam(String appeidcode, String apkey, String apId, JSONObject jsonObject, String apkeyfactor){
        HashMap<String , String> hashMap = new HashMap<String , String>();
        hashMap.put("version","2.0.0");
        hashMap.put("result",jsonObject.getString("result"));
        hashMap.put("result_detail",jsonObject.getString("result_detail"));
        hashMap.put("result_time", DateFormatUtils.format(new Date(),"yyyyMMddHHmmss"));
        hashMap.put("biz_sequence_id",jsonObject.getString("biz_sequence_id"));

        String userInfo = Strings.isNullOrEmpty(appeidcode) ? "" : userIdInfoAES(new JSONObject(){{put("appeidcode",appeidcode);}},apkey,apId);
        hashMap.put("user_info",userInfo);//需要使用AES加密，秘钥为APPKEY

        hashMap.put("eid_sign",jsonObject.getString("eid_sign"));
        hashMap.put("extension",jsonObject.getString("extension"));
        String signFactor = genHexString(8);
        hashMap.put("security_factor",new JSONObject(){{put("encrypt_factor",genHexString(8));put("sign_factor",signFactor);}}.toJSONString());
        hashMap.put("encrypt_type","AES");
        hashMap.put("sign_type","HMAC_SHA1");
        hashMap.put("signFactor",signFactor);
        log.info("IDSP回调AP未签名参数：{}",hashMap.toString());
        hashMap.put("sign",backAPSign("HMAC_SHA1", apkeyfactor, apId,hashMap,signFactor));

        JSONObject jsonObject2 = (JSONObject) JSONObject.toJSON(hashMap);

        return jsonObject2.toString();
    }

    // 拼接IDSP响应IDSO的参数
    private JSONObject appendResponseIDSOParam(JSONObject jsonObject){
        HashMap<String , String> hashMap = new HashMap<String , String>();
        hashMap.put("version","2.0.0");
        hashMap.put("result",jsonObject.getString("result"));
        hashMap.put("result_detail",jsonObject.getString("result_detail"));
        hashMap.put("result_time", DateFormatUtils.format(new Date(),"yyyyMMddHHmmss"));
        hashMap.put("biz_sequence_id",jsonObject.getString("biz_sequence_id"));
        String signFactor = genHexString(8);
        hashMap.put("security_factor",new JSONObject(){{put("encrypt_factor",genHexString(8));put("sign_factor",signFactor);}}.toJSONString());
//        hashMap.put("sign_type","HMAC_SHA1");
        hashMap.put("sign_type","1");
        hashMap.put("extension",jsonObject.getString(jsonObject.getString("extension")));
        log.info("IDSP响应IDSO未签名参数：{}",hashMap.toString());
        hashMap.put("sign",backIDSOSign(hashMap,signFactor));

        JSONObject jsonObject2 = (JSONObject) JSONObject.toJSON(hashMap);

        return jsonObject2;
    }

    // 给user_id_info进行AES加密
    private String userIdInfoAES(JSONObject jsonObject, String apkey, String apId){

        String strIn = "";
        try
        {
            strIn = Base64.getEncoder().encodeToString(jsonObject.toString().getBytes("UTF-8"));

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return encrypt(strIn,apkey);

    }

    // AES
    private String encrypt(String strIn, String strKey) {
        try {
            SecretKeySpec skeySpec = getKey(strKey);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(strIn.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
//            return new AESBase64().encode(encrypted);
        } catch (Exception e) {
            log.info("Failed to decrypt! message:{}", Throwables.getStackTraceAsString(e));
            throw new ControllerException(ErrorCode.ENCRYPT_ERROR);
        }
    }

    private SecretKeySpec getKey(String strKey) throws Exception {
        byte[] arrBTmp = strKey.getBytes();
        byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");
        return skeySpec;
    }

    // 生成加密因子和签名因子
    private String genHexString(int byteLen) {
        if (byteLen < 1) {
            return null;
        }
        int n = 1;
        if (byteLen % 16 == 0) {
            n = byteLen / 16;
        } else {
            n = byteLen / 16 + 1;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            sb.append(UUID.randomUUID().toString().replace("-", "")
                    .toUpperCase());
        }
        return sb.toString().substring(0, byteLen * 2);
    }

    // IDSP回调AP，生成sign
    private String backAPSign(String signType, String key, String apId, Map<String, String> strToSign,String signFactor) {
        Map<String, String> param = paraFilter(strToSign);// 去除不参与签名的键值对
        String preSignStr = createLinkString(param);// 生成待签原文
        log.info("IDSP回调AP，调用加密机参数，signType:{};    key:{};     apId:{}     strToSign:{};    signFactor:{};",signType,key,apId,strToSign,signFactor);
        Response<String> response = encryptionMachineFacade.apToAsSign(apId,key,signFactor,preSignStr,signType);
        log.info("IDSP回调AP，生成sign，加密机返回结果:{}",response);
        return response.getResult();
    }

    // IDSP响应IDSO，生成sign
    private String backIDSOSign(Map<String, String> strToSign,String signFactor) {
        Map<String, String> param = paraFilter(strToSign);// 去除不参与签名的键值对
        String preSignStr = createLinkString(param);// 生成待签原文
        log.info("IDSP回调AP，调用加密机参数 strToSign:{};    signFactor:{};",strToSign,signFactor);
        String sign = idsoCallBackFacade.createSign(preSignStr,signFactor);
        log.info("IDSP响应IDSO，生成sign，签名返回结果:{}",sign);
        return sign;
    }

    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    private static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("signType")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    private static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    // 回调AP
    public String httpRequestAP(String url,String param)
    {
        // 创建HttpClient对象
        DefaultHttpClient client = new DefaultHttpClient();

        // 2)DefaultHttpClient设置超时
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,10000);// 连接超时
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,30000);// 响应超时

        // 创建请求方法实例并指定请求地址
        HttpPost post = new HttpPost(url);

        // 设置请求头信息
        post.setHeader("Content-type", "application/json;charset=UTF-8");

        try
        {
            // 设置请求参数 （此处json）
            StringEntity s = new StringEntity(param.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);

            // 发送请求
            HttpResponse res = client.execute(post);

            // 获取响应吗
            int i = res.getStatusLine().getStatusCode();
            System.out.println(i);
            if(i == HttpStatus.SC_OK)
            {
                // 获取返回数据
                HttpEntity entity = res.getEntity();
                String result = EntityUtils.toString(entity);// 返回json格式
                System.out.println(result);

                return result;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
