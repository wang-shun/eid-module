package com.eid.common.util;

import java.util.Random;

/**
*
* 数字处理类
*
* @author pdz 2017-12-27 上午 11:19
*
**/
public class MathUtils 
{

    /**
     * 生成指定位数随机数的字符串
     * @param num 指定位数
     * @return String
     */
    public static String randomNum(int num){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++)
            sb.append(String.valueOf(new Random().nextInt(10)));
        return sb.toString();
    }

}
