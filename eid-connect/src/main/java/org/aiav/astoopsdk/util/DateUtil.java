package org.aiav.astoopsdk.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static final String DATE_TIME_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_TIME_FORMAT_2 = "yyyyMMddHHmmss";
	public static final String DATE_TIME_FORMAT_3 = "yyMMddHHmmssSSS";

	public static String getNow(SimpleDateFormat df) {
		df.setLenient(false);
		return df.format(new Date());
	}

	public static Date convertStringToDateWithFormat(String pDateStr,
			SimpleDateFormat df) {
		df.setLenient(false);
		Date result = null;
		try {
			result = df.parse(pDateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean isOutOf5Min(Date dt) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, -300);
		if (!dt.after(calendar.getTime())) {
			return true;
		}

		calendar.add(Calendar.SECOND, 600);
		if (!dt.before(calendar.getTime())) {
			return true;
		}

		return false;
	}

}
