package com.eid.common.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * Created by:ruben Date:2017/2/7 Time:下午3:58
 */
public class DateUtil {

    /** 日期格式：yyyyMMdd*/
    public static final String datePattern = "yyyyMMdd";
    /** 日期格式：yyyyMM*/
    public static final String monthPattern = "yyyyMM";
    /** 日期格式：yyMMdd*/
    public static final String shortDatePattern = "yyMMdd";
    /** 日期时间格式：yyyyMMddHHmmss*/
    public static final String fullPattern = "yyyyMMddHHmmss";
    /** 日期时间格式：yyyyMMddHHmmss*/
    public static final String readPattern = "yyyy-MM-dd HH:mm:ss,SSS";

    public static final String timePattern = "yyyy-MM-dd HH:mm:ss";

    /** 日期时间格式：yyMMddHHmmss*/
    public static final String partPattern = "yyMMddHHmmss";

    private static final String INVALID_PARAM_MSG = "The payDate could not be null!";

    /**
     * 获取当前日期
     *
     * @return          当前日期
     */
    public static Date getCurrentDate() {
        return DateTime.now().toDate();
    }

    /**
     * 获取当前时间 格式： yyyyMMddHHmmss
     *
     * @return          字符日期 格式：yyyyMMddHHmmss
     */
    public static String getCurrent() {
        return getCurrent(fullPattern);
    }

    /**
     * 获取当前时间 格式： 自定义
     *
     * @param pattern   时间格式
     * @return          自定义格式的当前时间
     */
    public static String getCurrent(String pattern) {
        return DateTime.now().toString(pattern);
    }

    /**
     * 将字符串转换成固定格式时间
     *
     * @param date      日期
     * @param pattern   自定义格式
     * @return          转换后日期
     */
    public static Date parse(String date, String pattern) {
        DateTime dateTime = parseTime(date, pattern);
        if (dateTime == null) return null;
        return dateTime.toDate();
    }

    public static DateTime parseTime(String date, String pattern) {
        return DateTimeFormat.forPattern(pattern).parseDateTime(date);
    }


    public static String format(Date date, String pattern) {
        if (date == null) return null;
        return new DateTime(date).toString(pattern);
    }

    public static String convert(String date, String targetPattern) {
        return convert(date, fullPattern, targetPattern);
    }

    public static String convert(String date, String originPattern, String targetPattern) {
        Date originDate = parse(date, originPattern);
        return format(originDate, targetPattern);
    }

    /**
     * 获取当前时间
     *
     * @return Date
     */
    public static Date getCurrentDate(String pattern) {
        DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
        return DateTime.parse(new DateTime().toString(pattern), format).toDate();
    }

    /**
     * 根据 pattern 将 dateTime 时间进行格式化
     *
     * 用来去除时分秒，具体根据结果以 pattern 为准
     * @param date payDate 时间
     * @return payDate 时间
     */
    public static Date formatToDate(Date date, String pattern) {
        if (date == null) return null;
        DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
        return DateTime.parse(new DateTime(date).toString(pattern), format).toDate();
    }

    /**
     * 日期增减，负数为减
     *
     * @param dayNum 天数
     * @return 时间
     */
    public static Date plusDays(int dayNum) {
        return new DateTime().plusDays(dayNum).toDate();
    }

    /**
     * 按秒偏移,根据{@code source}得到{@code seconds}秒之后的日期<Br>
     *
     * @param source  , 要求非空
     * @param seconds , 秒数,可以为负
     * @return 新创建的Date对象
     */
    public static Date addSeconds(Date source, int seconds) {
        return addDate(source, Calendar.SECOND, seconds);
    }

    private static Date addDate(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException(INVALID_PARAM_MSG);
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }
}
