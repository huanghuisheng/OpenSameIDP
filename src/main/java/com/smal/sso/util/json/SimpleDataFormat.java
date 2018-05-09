package com.smal.sso.util.json;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Des:格式化输出数据<br>
 * Logic:<br>
 * personHealth com.bdcc.hoffice.personhealth.web.util Wuhao created the Class
 * at 2010-8-17
 */
public final class SimpleDataFormat {

	/**
	 * 格式2010-8-17 11:12:12
	 */
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	private static SimpleDateFormat df = new SimpleDateFormat();

	/**
	 * 格式2010-8-17
	 */
	public static final String DATE_PATTERN = "yyyy-MM-dd";

	/**
	 * 格式化yyyy-MM-dd日期 Des:<br>
	 * Logic:<br>
	 * 
	 * @param date
	 *            <br>
	 *            Wuhao crated the method at 2010-8-17
	 */
	public static String format(Date date) {
		return format(date, DATE_PATTERN);
	}

	/**
	 * 格式化yyyy-MM-dd HH:mm:ss日期 Des:<br>
	 * Logic:<br>
	 * 
	 * @param date
	 *            <br>
	 *            Wuhao crated the method at 2010-8-17
	 */
	public static String formatDateTime(Date date) {
		return format(date, DATE_TIME_PATTERN);
	}

	/**
	 * 按指定格式日期 Des:<br>
	 * Logic:<br>
	 * 
	 * @param date
	 * @param pattern
	 *            日期格式
	 * @return <br>
	 *         Wuhao crated the method at 2010-8-17
	 */
	public static String format(Date date, String pattern) {
		if(date==null){
			return "";
		}
		df.applyPattern(pattern);
		return df.format(date);
	}

	/**
	 * 格式化字符串 如果string 为null 返回"" Des:<br>
	 * Logic:<br>
	 * 
	 * @param string
	 * @return <br>
	 *         Wuhao crated the method at 2010-8-17
	 */
	public static String format(String string) {
		return string == null || "".equals(string) ? "" : string;
	}

	/**
	 * 
	 * Des:如果number为null就返回-1<br>
	 * Logic:<br>
	 * @param number
	 * @return
	 * <br>Wuhao crated the method at 2010-8-17
	 */
	public static String format(Long number) {
		return number == null ? "-1" : String.valueOf(number);
	}
}
