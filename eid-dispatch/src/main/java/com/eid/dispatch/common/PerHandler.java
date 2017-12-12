package com.eid.dispatch.common;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/8 Time:上午10:53
 */
public class PerHandler {

    public static String subStringFailReason(String failReason, int length) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(failReason) && failReason.length() > length) {
            failReason = failReason.substring(0, length - 1);
        }
        return failReason;
    }
}