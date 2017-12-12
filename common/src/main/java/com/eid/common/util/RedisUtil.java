package com.eid.common.util;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/2/7 Time:下午3:59
 */
public class RedisUtil {

    // 认证记录缓存key
    private final static String AUTHENTICATION_COUNT_KEY = "C_AUTHENTICATION_COUNT";
    // 商户信息缓存key
    private final static String COMPANY_INFO_KEY = "S_COMPANY_INFO_KEY";
    // APP信息缓存key
    private final static String APP_INFO_KEY = "S_APP_INFO_KEY";
    // 身份证前四位key
    private final static String PROVINCE_CITY_CODE_KEY = "S_PROVINCE_CITY_CODE_KEY";
    // APP ID 生成序列key
    public final static String APP_ID_SEQUENCE_KEY = "I_APP_ID_SEQUENCE_KEY";
    // 认证流水生成序列key
    public final static String BIZ_SEQUENCE_KEY = "S_BIZ_SEQUENCE_KEY";


    private final static String prefix = "eID";

    public final static int CACHE_INFO_SECONDS = 60 * 60 * 24 * 7;

    public static String getAuthenticationCount(String key) {
        return Joiner.on("_").join(AUTHENTICATION_COUNT_KEY, DateUtil.getCurrent(DateUtil.monthPattern), key);
    }

    public static String getCompanyInfoKey(String companyId) {
        return Joiner.on("_").join(COMPANY_INFO_KEY, companyId);
    }

    public static String getAppInfoKey(String appId) {
        return Joiner.on("_").join(APP_INFO_KEY, appId);
    }

    public static String getProvinceCodeKey(Integer code) {
        return Joiner.on("_").join(PROVINCE_CITY_CODE_KEY, code);
    }

    public static String generateAppId(String num) {
        String seq = Strings.padStart(num, 5, '0');
        return Joiner.on("").join(prefix, DateUtil.getCurrent(DateUtil.partPattern), seq);
    }

}
