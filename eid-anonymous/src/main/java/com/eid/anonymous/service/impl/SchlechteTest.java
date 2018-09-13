package com.eid.anonymous.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.eidlink.vals.sdk.constants.CarInfoParam;
import com.eidlink.vals.sdk.constants.PublicParam;
import com.eidlink.vals.sdk.pojo.request.CarCheckParam;
import com.eidlink.vals.sdk.pojo.result.CommonResult;
import com.eidlink.vals.sdk.service.EidlinkService;

/**
*
* 金联非eID接口测试
*
* @author pdz 2018-08-22 上午 11:06
*
**/
public class SchlechteTest 
{

//    public static void main(String[] args) throws Exception
//    {
//        // 调用IDSO车辆查询服务
//        CarInfoParam carInfoParam = new CarInfoParam("湘A88888","02");
//        CarCheckParam carCheckParam = new CarCheckParam(new PublicParam(),carInfoParam);
//        CommonResult result = EidlinkService.carMessageCheck(carCheckParam);
//        System.out.println("eid 车辆信息查询服务IDSO响应数据:{}"+ JSONObject.toJSONString(result));
//    }


    // TODO IDSO身份信息返照测试
//	public static void main(String[] args) {
//
//		UserInfoParam userInfo = new UserInfoParam("彭代忠","432503199110137518");
//		PublicParam pp = new PublicParam();
//		SimpleCheckParam scp = new SimpleCheckParam(pp,userInfo);
//		CommonResult result = EidlinkService.idphotoCheck(scp);
//
//		System.out.println("个人图像接口返回信息："+JSONObject.toJSONString(result));
//
//		if("00".equals(result.getResult()))
//			System.out.println("success");
//		else
//			System.out.println("fail");
//
//	}

    // TODO IDSO行驶证信息测试
//	public static void main(String[] args) {
//
////		CarInfoParam carInfo = new CarInfoParam("湘AA09N6","02","车架号17位","行驶证所有人:张三","初次登记时间","发动机号");
//		CarInfoParam carInfo = new CarInfoParam("湘AA09N6","02","LDC973442G2575213","姚杨","2017-04-21","1373711");
//		PublicParam pp = new PublicParam();
//		CarCheckParam ccp = new CarCheckParam(pp, carInfo);
//		CommonResult result = EidlinkService.carLicenseInfoCheck(ccp);
//
//		System.out.println("行驶证信息接口返回信息："+JSONObject.toJSONString(result));
//
//		if("00".equals(result.getResult()))
//			System.out.println("success");
//		else
//			System.out.println("fail");
//
//	}

    // TODO IDSO身份证风险测试
//	public static void main(String[] args) {
//
//		String begindate = "20121119";
//		UserInfoParam userInfo = new UserInfoParam("姚杨","430703198301310015");
//		PublicParam pp = new PublicParam();
//		IDcardInvalidCheckParam idcp = new IDcardInvalidCheckParam(pp,userInfo,begindate);
//		CommonResult result = EidlinkService.idCardInvalidCheck(idcp);
//
//		System.out.println("身份证风险接口返回信息："+JSONObject.toJSONString(result));
//
//		if("00".equals(result.getResult()))
//			System.out.println("success");
//		else
//			System.out.println("fail");
//
//	}

    // TODO IDSO手机号码身份测试
//	public static void main(String[] args) {
//
//		MobileParam mp = new MobileParam("彭代忠","432503199110137518","13574893297");
//		PublicParam pp = new PublicParam();
//		pp.setBiz_type(BizeType.MOBILE_9904000);
//		MobileCheckParam mcp = new MobileCheckParam(pp,mp);
//		CommonResult result = EidlinkService.mobileCheck(mcp);
//
//		System.out.println("手机号码身份接口返回信息："+JSONObject.toJSONString(result));
//
//		if("00".equals(result.getResult()))
//			System.out.println("success");
//		else
//			System.out.println("fail");
//
//	}

    // TODO IDSO不良身份信息服务测试
//    public static void main(String[] args) throws Exception
//    {
//        // 调用不良身份信息查询SDK
//        UserInfoParam userInfoParam = new UserInfoParam("彭代忠","432503199110137518");
//        PublicParam publicParam = new PublicParam();
//        SimpleCheckParam simpleCheckParam = new SimpleCheckParam(publicParam,userInfoParam);
//        CommonResult result = EidlinkService.badMessageCheck(simpleCheckParam);
//        log.info("不良身份信息IDSO响应结果:"+JSONObject.toJSONString(result));
//    }

}
