package no.steras.opensamlbook.util.json;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 正则表达式工具类
 * 
 * @author chenzk
 * 
 */
public class RegUtil {

	public static Long MOST_BIG_LONG = new Long(9223372036854775807L);

	public static final String STRINGTOKENIZER_OBJECT_VALUE = "||";

	public static final String STRINGTOKENIZER_OBJECT = "#";

	public static final String STRINGTOKENIZER_EQUALS = "#";

	/**
	 * 判断是否是浮点型数据
	 * 
	 * @param s
	 *            要判断的字符串
	 * @return chenzk create the method at 2009-11-4 14:08:28
	 */
	public static boolean isFloat(String s) {
		if (s == null || "".equals(s.trim())) {
			return false;
		}
		final String REG_FLOAT = "^(-?\\d+)(\\.\\d+)?$";
		return s.matches(REG_FLOAT);
	}
	/**
	 * Des: 判断是否是正double
	 * @param s
	 * @return
	 */
	public static boolean isDouble(String s) {
		if(s == null || "".equals(s)) {
			return false;
		}
		final String REG_DOUBLE = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0$";
		return s.matches(REG_DOUBLE);
	}
	/**
	 * 判断是否是整型数据
	 * 
	 * @param s
	 *            要判断的字符串
	 * @return chenzk create the method at 2009-11-4 14:08:28
	 */
	public static boolean isInteger(String s) {
		if (s == null || "".equals(s.trim())) {
			return false;
		}
		final String REG_INT = "^-?\\d+$";
		return s.matches(REG_INT);
	}

	/** 是否是电话号码 */
	public static boolean isTelphone(String s) {
		if (s == null || "".equals(s.trim())) {
			return false;
		}
		final String REG_INT = "((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)";
		return s.matches(REG_INT);
	}

	/**
	 * 转换为Long型
	 * 
	 * @param value
	 * @return chenzk create the method at 2009-12-29 17:29:44
	 */
	public static Long getLong(String value) {
		Long lvalue = null;
		if (value != null && !"".equals(value) && !"null".equals(value.trim().toLowerCase())) {
			try {
				lvalue = Long.valueOf(value.trim());
			} catch (Exception e) {
			}
		}
		return lvalue;
	}
	
	public static long getLongValue(Long value) {
		long result = 0;
		if (value != null) {
			result = value.longValue();
		}
		return result;
	}

	public static String getObjStr(Object obj) {
		return obj == null ? "" : obj.toString();
	}

	/** 去头去尾的字符串比较 */
	public boolean compareStr(String s1, String s2) {
		boolean flag = false;
		if (s1 != null && s2 != null) {
			flag = s1.equals(s2);
		}
		return flag;
	}

	/***************************************************************************
	 * WebService 解析参数 例如 1001||a 解析结果为{"1001","a"}
	 * 
	 * @param params1
	 *            参数 类型如1001||a
	 * @return 结果数组 kuhn created the mothed at 2010-3-31 11:15:07
	 *         getWebServiceParams
	 */
	public static String[] getWebServiceParams(String params1) {
		String[] params = new String[200];
		if(params1!=null&&!"".equals(params1)){
			int i = params1.indexOf(STRINGTOKENIZER_OBJECT_VALUE);
			int n = 0;
			while (i >= 0) {
				params[n] = params1.substring(0, i);
				params1 = params1.substring(i + STRINGTOKENIZER_OBJECT_VALUE.length());
				i = params1.indexOf(STRINGTOKENIZER_OBJECT_VALUE);
				n++;
			}
		}
		return params;
	}

	/***************************************************************************
	 * 判断字符串是否为null或者为"null"或"NULL"字符串，如果是，则将其转化为""
	 */
	public static String RemoveNull(String param) {
		if (param == null) {
			return "";
		} else if (param.toLowerCase().equals("null")) {
			return "";
		} else {
			return param;
		}
	}

	/**
	 * 从身份证获取出生年月
	 * @param idno
	 * @return
	 */
	public static Date getBirthdayFromIdNO(String idno){
		
		Date date=null;
		if(idno!=null){
			String bthStr=null;
			switch (idno.length()) {
			case 15:
				bthStr="19"+idno.substring(6,8)+"-"+idno.substring(8,10)+"-"+idno.substring(10,12);
				break;
			case 18:
				bthStr=idno.substring(6,10)+"-"+idno.substring(10,12)+"-"+idno.substring(12,14);
				break;
			default:
				break;
			}
			
			if(bthStr!=null){
				try {
					date=DateUtil.getDateFromStr(bthStr,"yyyy-MM-dd");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return date;
	}

	public static String getLongToString(Long arg){
		String result = "";
		if(arg != null){
			result = String.valueOf(arg.longValue());
		}
		return result;
	}
	/**
	 * Des:将对象转换为Float类型<br>
	 * Logic:<br>
	 * @param obj
	 * @return
	 * <br>Kuhn Chen crated the method at 2010-6-21
	 */
	public static Float getFloat(Object obj){
		Float value=null;
		try {
			if(obj!=null){
				value=Float.valueOf(String.valueOf(obj));
			}
		} catch (Exception e) {
		}

		return value;
	}
	
	
	/**
	 * Des:
	 * 字符转Double型
	 * 2012-5-22
	 * @author mopy 
	 * @param obj
	 * @return
	 * Double
	 */
	public static Double getDouble(Object obj) {
		if (obj == null) {
			return null;
		}
		try {
			return Double.parseDouble(String.valueOf(obj));
		} catch (Exception e) {
		}
		return null;
	}
	/**
	 * 字符转换Integer
	 * @param value
	 * @return Integer
	 */
	public static Integer getInteger(String value){
		Integer lvalue = null;
		if (value != null && !"".equals(value) && !"null".equals(value.trim().toLowerCase())) {
			try {
				lvalue = Integer.valueOf(value.trim());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lvalue;
	}
	
	/**
	 * 字符型转换成日期
	 * @param date 字符型日期
	 * @param pattern 转换格式:"yyyy-MM-dd"
	 * @return java.util.Date
	 */
	public static Date parseDate(String date, String pattern) {
		if ((date == null) || (pattern == null) || ("".equals(date))
				|| ("".equals(pattern))) {
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 日期转换成字符串
	 * @param date java.util.Date
	 * @param pattern 转换日期格式："yyyy-MM-dd"
	 * @return String
	 */
	public static String formatDate(Date date, String pattern) {
		if ((date == null) || (pattern == null) || ("".equals(pattern))) {
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}
}
