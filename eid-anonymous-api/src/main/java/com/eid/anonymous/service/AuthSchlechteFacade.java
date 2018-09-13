package com.eid.anonymous.service;

import com.eid.common.model.Response;

public interface AuthSchlechteFacade {

    Response<Object> authenticationImageContrast(String image1, String image2, String apId);

    Response<Object> authenticationOcrMore(String image, String apId);

    Response<Object> authenticationOcrSimple(String image, String apId);

    Response<Object> authenticationGainBankInfo(String bankCardNum, String apId);

    Response<Object> authenticationBankInfo(String name, String idNumber, String bankCardNum, String mobile, String apId);

    Response<Object> authenticationUserPhoto(String name, String idNumber, String image, String apId);

    Response<Object> authenticationUsersInfo(String name, String idNumber, String  dob, String sex, String edu_lev, String location, String apId);

    Response<Object> authenticationUserInfo(String name, String idNumber, String apId);

    Response<Object> authenticationCarInfo(String carNumber, String apId);

    Response<Object> authenticationUserInfoImage(String name, String idNum, String apId);

    Response<Object> authenticationLicense(String carNumber, String carType, String carFrameNumber, String name, String registerTime, String engineNumber, String apId);

    Response<Object> authenticationIdNum(String name, String idNum, String begindate, String apId);

    Response<Object> authenticationPhone(String name, String idNum, String phone, String apId);

    Response<Object> authenticationSchlechte(String name, String idNum, String apId);

}
