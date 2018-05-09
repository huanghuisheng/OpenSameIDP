package com.smal.sso.util.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
	private static Logger log = LoggerFactory.getLogger(DateUtil.class);

	private DateUtil() {
	}

	public static final String PAGE_DATE_INPUT_FORMAT = "yyyy-MM-dd";

	public static final String DB_DATE_INPUT_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public final static int YEAR = 0;

	public final static int MONTH = 1;

	public final static int DAY = 2;

	private static final int ONE_MINUTE = 60 * 1000;

	private static final int ONE_HOUR = 60 * ONE_MINUTE;

	private static final int ONE_DAY = 24 * ONE_HOUR;

	/**
	 * parse date, the string's format must be "yyyy-MM-dd"
	 * 
	 * @param dateString
	 * @return
	 */
	public static long parseDate(String dateStr) {
		Date date = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat(PAGE_DATE_INPUT_FORMAT);
			date = format.parse(dateStr);
		} catch (Exception e) {
			log.error("date parse error: " + dateStr);
		}
		if (date != null) {
			return date.getTime();
		} else {
			return 0;
		}
	}

	/**
	 * parse date, the pattern is the dateStr's format
	 * 
	 * @param pattern
	 * @param dateStr
	 * @return
	 */
	public static long parseDate(String pattern, String dateStr) {
		Date date = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			date = format.parse(dateStr);
		} catch (Exception e) {
			log.error("date parse error: " + dateStr + " to " + dateStr);
		}
		return date.getTime();
	}

	public static String formatDate(Date date, String pattern) {
		try {
			String ret = "";
			if (date != null && pattern != null) {
				SimpleDateFormat format = new SimpleDateFormat(pattern);
				ret = format.format(date);
				return ret;
			}
		} catch (Exception e) {
			log.error("date format error: " + date + " to " + pattern);
		}
		return "";
	}

	public static final String CREAT_DATE_FORMAT = "yyyy-MM-dd H:mm:ss:SSS";

	public static String formatCreateDate(Date date) {
		if (date != null) {
			try {
				SimpleDateFormat format = new SimpleDateFormat(CREAT_DATE_FORMAT);
				String ret = format.format(date);
				return ret;
			} catch (Exception e) {
				log.error("date format error: " + date + " to " + CREAT_DATE_FORMAT);
			}
		}
		return "";
	}

	public static long parseCreateDate(String dateStr) {
		Date date = null;
		if (dateStr != null && dateStr.trim().length() > 0) {
			try {
				SimpleDateFormat format = new SimpleDateFormat(CREAT_DATE_FORMAT);
				date = format.parse(dateStr);
			} catch (Exception e) {
				log.error("date parse error: " + dateStr + " to " + dateStr);
			}
		}
		if (date != null) {
			return date.getTime();
		} else {
			return 0;
		}
	}

	public static Date getDate(Date sDate, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = util2Sql(sdf.parse(sdf.format(sDate)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(year, month - 1, day);
		return new Date(calendar.getTime().getTime());
	}

	public static Date getDate(int year, int month, int day, int hour, int minute, int second, int millionSecond) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(year, month - 1, day, hour, minute, second);
		calendar.set(Calendar.MILLISECOND, millionSecond);
		return new Date(calendar.getTime().getTime());
	}

	public static Date getDateNoTime() {
		Date dateTemp = getDate();
		return getDate(getYear(dateTemp), getMonth(dateTemp), getDay(dateTemp), 0, 0, 0, 0);
	}

	public static Date getDateNoTime(Date dateTemp) {
		return getDate(getYear(dateTemp), getMonth(dateTemp), getDay(dateTemp), 0, 0, 0, 0);
	}

	/**
	 * get current date
	 * 
	 * @return
	 */
	public static Date getDate() {
		return new Date(new Date().getTime());
	}

	public static Date util2Sql(Date date) {
		return new Date(date.getTime());
	}

	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	public static int getMaximumDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}

	public static Date addYear(Date date, int iYear) {
		return add(date, iYear, DateUtil.YEAR);
	}

	public static Date addMonth(Date date, int iMonth) {
		return add(date, iMonth, DateUtil.MONTH);
	}

	public static Date addDay(Date date, int iDay) {
		return add(date, iDay, DateUtil.DAY);
	}

	public static Date add(Date date, int iCount, int iField) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		switch (iField) {
		case YEAR:
			calendar.add(Calendar.YEAR, iCount);
			break;
		case MONTH:
			calendar.add(Calendar.MONTH, iCount);
			break;
		case DAY:
			calendar.add(Calendar.DATE, iCount);
			break;
		case Calendar.HOUR:
			calendar.add(Calendar.HOUR, iCount);
			break;
		case Calendar.MINUTE:
			calendar.add(Calendar.MINUTE, iCount);
			break;
		case Calendar.SECOND:
			calendar.add(Calendar.SECOND, iCount);
			break;
		default:
			break;
		}
		return new Date(calendar.getTime().getTime());
	}
	/**
	 * 计算两个整型日期之间的天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Integer daysBetweenDate(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return null;
		}
		Long interval = endDate.getTime() - startDate.getTime();
		interval = interval / (24 * 60 * 60 * 1000);
		return interval.intValue();
	}	
	/**
	 * 1、 先得到两个日期的天数差 daydiff， 然后daydiff/365 取整 yeardiff,余数为：remainedDays，  如果yeardiff >=3 ,那么返回yeardiff
	 * 2、 如果1=< yeardiff < 3, 返回格式：x年y月 余数 remainedDays/30 取整得到 月数差monthdiff， 那么返回 yeardiff, monthdiff
	 * 3、 如果 0 < yeardiff < 1, 那么返回格式： x月y天 
	 */
	public static String calcBabyAge(Date date) {
		if(date == null){
			return "";
		}
		String result = "";
		long birthYear = date.getYear(); //出生日期 - 年
		long birthMonth = date.getMonth();//出生日期 - 月
		long birthDay = date.getDate();//出生日期 - 天
		Date now = new Date();
		long nowYear = now.getYear();//当前日期 - 年
		long nowMonth = now.getMonth();//当前日期 - 月
		long nowDay = now.getDate(); //当前日期 - 天
		if (birthYear > nowYear) {
			return null;
		}
		/**
		 * 1、 先得到两个日期的天数差 daydiff， 然后daydiff/365 取整 yeardiff,余数为：remainedDays，  如果yeardiff >=3 ,那么返回yeardiff
		 * 2、 如果1=< yeardiff < 3, 返回格式：x年y月 余数 remainedDays/30 取整得到 月数差monthdiff， 那么返回 yeardiff, monthdiff
		 * 3、 如果 0 < yeardiff < 1, 那么返回格式： x月y天 
		 */
		Integer daydiff = daysBetweenDate(date, now);
		Integer yeardiff = daydiff/365;
		Integer remainedDays = daydiff%365;
		Integer monthdiff = remainedDays/30;
		
		
		if(yeardiff >= 0 && yeardiff < 1){
			Integer monthRemained = daydiff/30;
			Integer dayRemained = daydiff%30;
			result = monthRemained+"月"+dayRemained+"天";
		}else if(yeardiff >=1 && yeardiff < 3){
			result = yeardiff+"岁"+monthdiff+"月";
		}else if(yeardiff >= 3){
			result = yeardiff+"岁";
		}
		return result;
		
	}
	public static String calcAge(Date birthday) {
		Date date = birthday;
		long birthYear = date.getYear();
		long birthMonth = date.getMonth();
		long birthDay = date.getDate();
		Date now = new Date();
		long nowYear = now.getYear();
		long nowMonth = now.getMonth();
		long nowDay = now.getDate();
		if (birthYear > nowYear) {
			return null;
		}

		long resultYear = 0;
		long resultMonth = 0;
		long resultDay = 0;

		if (nowMonth > birthMonth || (nowMonth == birthMonth && nowDay >= birthDay)) {
			resultYear = nowYear - birthYear;
			if (nowDay >= birthDay) {
				resultMonth = nowMonth - birthMonth;
				resultDay = nowDay - birthDay;
			} else {
				resultMonth = nowMonth - birthMonth - 1;
				resultDay = 30 - birthDay + nowDay;
			}
		} else {
			resultYear = nowYear - birthYear - 1;
			if (nowDay >= birthDay) {
				resultMonth = 12 - birthMonth + nowMonth;
				resultDay = nowDay - birthDay;
			} else {
				resultMonth = 12 - birthMonth + nowMonth - 1;
				resultDay = 30 - birthDay + nowDay;
			}
		}

		String ageStr = "";
		if (resultYear > 0) {
			if (resultYear >= 2) {
				ageStr = ageStr + resultYear + "岁";
			} else {
				ageStr = ageStr + (resultYear * 12 + resultMonth) + "岁";
			}
		} else if (resultMonth > 0) {
			ageStr = ageStr + resultMonth + "岁";
		} else if (resultDay >= 0) {
			ageStr = ageStr + resultDay + "岁";
		}
		return ageStr;
	}

	public static long getCreateDate(String dateStr) {
		Date date = null;
		if (dateStr != null && dateStr.trim().length() > 0) {
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				date = format.parse(dateStr);
			} catch (Exception e) {
				log.error("date parse error: " + dateStr + " to " + dateStr);
			}
		}
		if (date != null) {
			return date.getTime();
		} else {
			return 0;
		}
	}

	public static Date getDateFromStr(String dateStr) {
		Date date = null;
		if (dateStr != null && dateStr.trim().length() > 0) {
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				date = format.parse(dateStr);
			} catch (Exception e) {
				log.error("date parse error: " + dateStr + " to " + dateStr);
			}
		}
		return date;
	}

	/**
	 * 根据字符串转换为日期
	 * 
	 * @param dateStr
	 *            要转换的日期字符串
	 * @param pattern
	 *            字符串模式
	 * @return 如果字符串为空或长度为0，返回空，否则返回转换后的日期
	 */
	public static Date getDateFromStr(String dateStr, String pattern) {
		Date date = null;
		if (dateStr != null && dateStr.trim().length() > 0) {
			try {
				SimpleDateFormat format = new SimpleDateFormat(pattern);
				date = format.parse(dateStr);
			} catch (Exception e) {
				log.error("date parse error: " + dateStr + " to " + dateStr);
			}
		}
		return date;
	}

	/**
	 * 取得给定日期所在月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMinDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.DAY_OF_MONTH, calendar.getActualMinimum(calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/**
	 * 取得给定日期所在的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMaxDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.DAY_OF_MONTH, calendar.getActualMaximum(calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	public static String getNowDateStrForLog() {
		return "Time：" + formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 根据生日算出年龄
	 * 
	 * @param birthday
	 * @return chenzk create the method at 2009-11-4 11:14:50
	 */
	public static int getAgeByBirthday(Date birthday) {
		Date date = new Date();
		return date.getYear() - birthday.getYear();
	}

	/**
	 * 算出当前或给出的时间的前N年时间
	 * 
	 * @param d
	 * @param n
	 * @return chenzk create the method at 2009-11-4 11:14:01
	 */
	public static Date calBeforeDate(Date d, int n) {
		if (d == null)
			d = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(d);
		calendar.add(calendar.YEAR, -n);
		return calendar.getTime();
	}

	/**
	 * 算出当前或给出的时间的前N天时间
	 * 
	 * @param d
	 * @param n
	 * @return chenzk create the method at 2009-11-4 11:25:17
	 */
	public static Date calBeforeDay(Date d, int n) {
		if (d == null)
			d = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(d);
		calendar.add(calendar.DAY_OF_YEAR, -n);
		return calendar.getTime();
	}

	/**
	 * 算出当前或给出的时间的前N个月时间
	 * 
	 * @param d
	 * @param n
	 * @return chenzk create the method at 2009-11-4 11:25:17
	 */
	public static Date calBeforeMonth(Date d, int n) {
		if (d == null)
			d = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(d);
		calendar.add(calendar.MONTH, -n);
		return calendar.getTime();
	}

	/**
	 * 得到当前时间：格式：20091010
	 * 
	 * @return chenzk create the method at 2009-11-26 15:40:26
	 */
	public static String getCurrDateStrYyyyMMdd() {
		return formatDate(new Date(), "yyyyMMdd");
	}

	/**
	 * 得到当前时间：格式：2009-10-10
	 * 
	 * @return chenzk create the method at 2009-11-26 15:40:26
	 */
	public static String getCurrDateStr() {
		return formatDate(new Date(), "yyyy-MM-dd");
	}

	public static int getMonthByBirthday(Date birthday) {
		Date date = new Date();
		return date.getMonth() - birthday.getMonth();
	}

	/**
	 * 把时间设置为当天的23:59:59
	 * 
	 * @param date
	 * @return chenzk create the method at 2009-11-4 17:27:39
	 */
	public static Date formatDateForMaxDay(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(date);
		str = str + " 23:59:59";
		try {
			return format2.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 把时间设置为当天的00:00:00
	 * 
	 * @param date
	 * @return chenzk create the method at 2009-12-4 10:54:24
	 */
	public static Date formatDateForMinDay(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(date);
		str = str + " 00:00:00";
		try {
			return format2.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getYearMonStr(Date date) {
		String str = "";
		String rs = "";
		str = DateUtil.formatDate(date, "yyyyMMdd");
		if (str != null && str.length() >= 6) {
			rs = str.substring(0, 6);
		}
		return rs;
	}

	/**
	 * 根据年份和当年的年龄算出生日
	 * 
	 * @param age
	 * @param bornDay
	 * @return
	 */
	public static Date getBirthdayFromAgeAndBornDay(Long age, Date bornDay) {
		Date birth = null;
		if (age != null && bornDay != null) {
			int year = 0;
			year = getYear(bornDay);
			year = year - age.intValue();
			if (year > 1000) {
				birth = getDateFromStr(String.valueOf(year) + "-01-01", PAGE_DATE_INPUT_FORMAT);
			}
		}
		if (birth == null)
			birth = new Date();
		return birth;
	}

	/**
	 * 根据开始年月和结束年月获得所有的月份
	 * 
	 * @param yearStart
	 *            2010
	 * @param monthStart
	 *            06
	 * @param yearEnd
	 *            2011
	 * @param monthEnd
	 *            02
	 * @return 201006 201007 201008 201009 201010 201011 201012 201101 201102
	 *         <br>
	 *         Arlon create this method at 2010-6-29
	 */
	public static List getMonthList(String yearStart, String monthStart, String yearEnd, String monthEnd) {
		List list = new ArrayList();
		Calendar calendar1 = Calendar.getInstance();
		calendar1.clear();
		calendar1.set(Integer.valueOf(yearStart), Integer.valueOf(monthStart) - 1, 1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.clear();
		calendar2.set(Integer.valueOf(yearEnd), Integer.valueOf(monthEnd) - 1, 1);
		while (calendar2.getTimeInMillis() >= calendar1.getTimeInMillis()) {

			String month = "";
			if (calendar1.get(Calendar.MONTH) < 9) {
				month = "0" + (calendar1.get(Calendar.MONTH) + 1);
			} else {
				month = "" + (calendar1.get(Calendar.MONTH) + 1);
			}
			list.add(calendar1.get(Calendar.YEAR) + "" + month);
			calendar1.add(Calendar.MONTH, 1);
		}
		return list;
	}

	/**
	 * 根据开始年月和结束年月获得所有的季度的最后一个月份，如果结束时间不是季度的末月，则返回结束年月
	 * 
	 * @param yearStart
	 *            2010
	 * @param monthStart
	 *            06
	 * @param yearEnd
	 *            2011
	 * @param monthEnd
	 *            05
	 * @return 201006 201009 201012 201103 201105 <br>
	 *         Arlon create this method at 2010-6-29
	 */
	public static List getQuartList(String yearStart, String monthStart, String yearEnd, String monthEnd) {
		List list = new ArrayList();
		Calendar calendar1 = Calendar.getInstance();
		calendar1.clear();
		calendar1.set(Integer.valueOf(yearStart), Integer.valueOf(monthStart) - 1, 1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.clear();
		calendar2.set(Integer.valueOf(yearEnd), Integer.valueOf(monthEnd) - 1, 1);
		while (calendar2.getTimeInMillis() >= calendar1.getTimeInMillis()) {

			String month = "";
			if (calendar1.get(Calendar.MONTH) == 2 || calendar1.get(Calendar.MONTH) == 5 || calendar1.get(Calendar.MONTH) == 8 || calendar1.get(Calendar.MONTH) == 11) {
				if (calendar1.get(Calendar.MONTH) < 9) {
					month = "0" + (calendar1.get(Calendar.MONTH) + 1);
				} else {
					month = "" + (calendar1.get(Calendar.MONTH) + 1);
				}
				list.add(calendar1.get(Calendar.YEAR) + "" + month);
			}

			calendar1.add(Calendar.MONTH, 1);
		}
		int endmonth = Integer.parseInt(monthEnd);
		if (endmonth != 3 && endmonth != 6 && endmonth != 9 && endmonth != 12) {
			list.add(yearEnd + monthEnd);
		}
		return list;
	}

	/**
	 * 将所有时间（201006）拼成一个字符串
	 * 
	 * @param monthList[201006,201007,201008]
	 * @return ('201006','201007','201008','-1') <br>
	 *         Arlon create this method at 2010-6-29
	 */
	public static String getMonthstr(List monthList) {
		String str = "(";
		String endStr = "'-1')";
		if (monthList != null && monthList.size() > 0) {
			for (Iterator iter = monthList.iterator(); iter.hasNext();) {
				String month = (String) iter.next();
				str = str + "'" + month + "',";
			}
		}
		return str + endStr;
	}

	/**
	 * 根据时间获得所在季度
	 * 
	 * @param monthStr
	 *            201004
	 * @return 第二进度 <br>
	 *         Arlon create this method at 2010-6-29
	 */
	public static String getQuartStr(String monthStr) {
		String quartName = "";
		int month = Integer.parseInt(monthStr.substring(4, monthStr.length()));
		switch (month) {
		case 1:
		case 2:
		case 3:
			quartName = "第一季度";
			break;
		case 4:
		case 5:
		case 6:
			quartName = "第二季度";
			break;
		case 7:
		case 8:
		case 9:
			quartName = "第三季度";
			break;
		case 10:
		case 11:
		case 12:
			quartName = "第四季度";
			break;
		}
		return quartName;

	}

	/**
	 * 计算两个日期之间相差的月数
	 * 
	 * @param strDate1
	 * @param strDate2
	 * @return
	 */
	public static int dispersionMonth(Date Date1, Date Date2) {
		int iMonth = 0;
		int flag = 0;
		try {
			Calendar objCalendarDate1 = Calendar.getInstance();
			objCalendarDate1.setTime(Date1);

			Calendar objCalendarDate2 = Calendar.getInstance();
			objCalendarDate2.setTime(Date2);

			if (objCalendarDate2.equals(objCalendarDate1))
				return 0;
			if (objCalendarDate1.after(objCalendarDate2)) {
				Calendar temp = objCalendarDate1;
				objCalendarDate1 = objCalendarDate2;
				objCalendarDate2 = temp;
			}
			if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1.get(Calendar.DAY_OF_MONTH))
				flag = 1;

			if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1.get(Calendar.YEAR))
				iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1.get(Calendar.YEAR)) * 12 + objCalendarDate2.get(Calendar.MONTH) - flag) - objCalendarDate1.get(Calendar.MONTH);
			else
				iMonth = objCalendarDate2.get(Calendar.MONTH) - objCalendarDate1.get(Calendar.MONTH) - flag;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return iMonth;
	}

	/**
	 * 计算两个日期之间相差的月数
	 * 
	 * @param strDate1
	 * @param strDate2
	 * @return
	 */
	public static int dispersionMonth(String strDate1, String strDate2) {
		int iMonth = 0;
		int flag = 0;
		try {
			Calendar objCalendarDate1 = Calendar.getInstance();
			objCalendarDate1.setTime(DateFormat.getDateInstance().parse(strDate1));

			Calendar objCalendarDate2 = Calendar.getInstance();
			objCalendarDate2.setTime(DateFormat.getDateInstance().parse(strDate2));

			if (objCalendarDate2.equals(objCalendarDate1))
				return 0;
			if (objCalendarDate1.after(objCalendarDate2)) {
				Calendar temp = objCalendarDate1;
				objCalendarDate1 = objCalendarDate2;
				objCalendarDate2 = temp;
			}
			if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1.get(Calendar.DAY_OF_MONTH))
				flag = 1;

			if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1.get(Calendar.YEAR))
				iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1.get(Calendar.YEAR)) * 12 + objCalendarDate2.get(Calendar.MONTH) - flag) - objCalendarDate1.get(Calendar.MONTH);
			else
				iMonth = objCalendarDate2.get(Calendar.MONTH) - objCalendarDate1.get(Calendar.MONTH) - flag;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return iMonth;
	}

}
