package com.eid.common.util;

import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetIPUtil {

    private static volatile String ipAddress = "";

    /**
     * 获取本机IP地址
     *
     * @return
     */
    public static String getLocalIPAddress() {
        if (StringUtils.isNotBlank(ipAddress)) {
            return ipAddress;
        }

        try {
            InetAddress address = InetAddress.getLocalHost();
            ipAddress = address.getHostAddress();
        } catch (UnknownHostException e) {
        }
        return ipAddress;
    }
}
