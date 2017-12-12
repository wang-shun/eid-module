package com.eid.anonymous.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eid.anonymous.BaseServiceTest;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/2/9 Time:上午11:22
 */
@Slf4j
public class AnonymousBizTest extends BaseServiceTest {

    @Test
    public void sendTest() {
//        String userIdInfo = "{\"idnum\":\"43010519620907791x\",\"idtype\":\"01\",\"name\":\"曹介南\"}";
//        log.info("Call save identity information request:{};", userIdInfo);
//        try {
//            if (Strings.isNullOrEmpty(userIdInfo))
//                return;
//
//            JSONObject jsonObject = JSON.parseObject(userIdInfo);
//            String idNum = jsonObject.getString("idnum");
//            if (!Strings.isNullOrEmpty(idNum) && idNum.length() == 18) {
//                String area = idNum.substring(0, 6);
//                String province = area.substring(0, 2);
//                String city = area.substring(0, 4);
//                log.info("get information province:{};city:{};area:{};", province, city, area);
//            }
//
//            String idType = jsonObject.getString("idtype");
//            String name = jsonObject.getString("name");
//
//
//
//        } catch (Exception e) {
//            log.error("Failed to save identity information request:{};errorMessage:{}", userIdInfo, Throwables.getStackTraceAsString(e));
//        }


        String a = "99582.9";
        String b = "11";

        System.out.println(Double.parseDouble(a) / Double.parseDouble(b));


    }
}
