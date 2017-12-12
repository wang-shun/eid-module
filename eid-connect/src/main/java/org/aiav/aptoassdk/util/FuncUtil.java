package org.aiav.aptoassdk.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

public class FuncUtil {

	/**
	 * <p>
	 * Trim blanks at start or end of a string.
	 * </p>
	 * 
	 * @param toBeTrim
	 * @return String
	 */
	public static String trimStr(String toBeTrim) {
		String returnValue = toBeTrim;
		if (toBeTrim != null && !"".equals(toBeTrim)) {
			returnValue = returnValue.trim();

			for (; returnValue != null
					&& !"".equals(returnValue)
					&& (returnValue.startsWith("　") || returnValue
							.startsWith(" "));) {
				if (returnValue.startsWith("　"))
					returnValue = returnValue.substring(1);
				if (returnValue.startsWith(" "))
					returnValue = returnValue.trim();
			}

			for (; returnValue != null && !"".equals(returnValue)
					&& (returnValue.endsWith("　") || returnValue.endsWith(" "));) {
				if (returnValue.endsWith("　"))
					returnValue = returnValue.substring(0,
							returnValue.length() - 1);
				if (returnValue.endsWith(" "))
					returnValue = returnValue.trim();
			}

		}
		return returnValue;
	}

	/**
	 * <p>
	 * Check a string whether it is empty.
	 * </p>
	 * 
	 * @param value
	 * @return boolean<br>
	 *         true: value is empty<br>
	 *         false: value is not empty
	 */
	public static boolean isEmpty(String value) {
		if (value == null || "".equals(value))
			return true;
		return false;
	}

	/**
	 * <p>
	 * Check a string or a JSON object whether it is empty.
	 * </p>
	 * 
	 * @param value
	 * @return boolean<br>
	 *         true: value is empty<br>
	 *         false: value is not empty
	 */
	public static boolean isEmpty(Object value) {
		if (value == null || value.equals(null))
			return true;
		else {
			if ((value instanceof String) && isEmpty((String) value)) {
				return true;
			} else if ((value instanceof JSONObject)
					&& ((JSONObject) value).isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <p>
	 * Check strings whether they are empty.
	 * </p>
	 * 
	 * @param value
	 * @return boolean<br>
	 *         true: value is empty<br>
	 *         false: value is not empty
	 */
	public static boolean isEmpty(String... values) {
		for (String value : values) {
			if (value == null || "".equals(value))
				return true;
		}
		return false;
	}

	/**
	 * <p>
	 * Check the version whether it is correct.
	 * </p>
	 * 
	 * @param version
	 * @return boolean <br>
	 *         true: the version is correct.<br>
	 *         false: the version is not correct.
	 */
	public static boolean isCorrectVersionFormat(String version) {
		return regMatch("^[0-9]{1,}(\\.)[0-9]{1,}(\\.)[0-9]{1,}", version);
	}

	/**
	 * <p>
	 * Check the IP format whether it matched "xxx.xxx.xxx.xxx".
	 * </p>
	 * 
	 * @param ip
	 * @return boolean<br>
	 *         true: ip is correct.<br>
	 *         false: ip is not correct.
	 */
	public static boolean isCorrectIpFormat(String ip) {
		return regMatch(
				"(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])(\\.)(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])(\\.)(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])(\\.)(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])",
				ip);
	}

	/**
	 * <p>
	 * Check if the argument is a BASE64 string.<br>
	 * </p>
	 * 
	 * @param str
	 * @return boolean<br>
	 *         true: is a BASE64 string.<br>
	 *         false: not a BASE64 string.
	 */
	public static boolean isBase64Str(String str) {
		return regMatch("[A-Za-z0-9=\\/\\+]+", str);
	}

	/**
	 * <p>
	 * Check the url whether is started with 'http://' or 'https://'.
	 * </p>
	 * 
	 * @param url
	 * @return boolean<br>
	 *         true: url is correct.<br>
	 *         false: url is not correct.
	 */
	public static boolean isHttpUrl(String url) {
		return regMatch("(https|http)://([A-Za-z0-9-_%&?/=~+:]|(\\.))+", url);
	}

	public static boolean isValidFactor(String factor) {
		return regMatch("[A-Fa-f0-9]{16}", factor);
	}

	/**
	 * <p>
	 * Get exception message of last throwable.
	 * </p>
	 * 
	 * @param t
	 *            (Throwable)
	 * @return
	 */
	public static String showTraces(Throwable t) {
		Throwable next = t.getCause();
		if (next == null) {
			return t.getMessage();
		} else {
			return showTraces(next);
		}
	}

	/**
	 * <p>
	 * Check the bytes of the string is less than or equals to the length.
	 * </p>
	 * 
	 * @param value
	 * @param length
	 * @return boolean<br>
	 *         true: is less than or equals to the length;<br>
	 *         false: is more than the length;
	 */
	public static boolean leLengthOfBytes(String value, int length) {
		try {
			if (value.getBytes("UTF-8").length <= length)
				return true;
		} catch (UnsupportedEncodingException e) {
		}
		return false;
	}

	// ===============================================================================

	/**
	 * <p>
	 * The private function for doing regular expression check.
	 * </p>
	 * 
	 * @param regex
	 * @param arg
	 * @return boolean<br>
	 *         true: the arg matches regex.<br>
	 *         false: the arg does not match regex.
	 */
	private static boolean regMatch(String regex, String arg) {
		Pattern formatPattern = Pattern.compile(regex);
		Matcher matcher = formatPattern.matcher(arg);
		return matcher.matches();
	}

}
