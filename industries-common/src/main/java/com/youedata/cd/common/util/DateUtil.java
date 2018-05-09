package com.youedata.cd.common.util;

/**
 * @FileName: DateUtil.java
 * @Author 
 * @Description:
 * @Date 2016年9月13日 下午6:18:01
 * @CopyRight youedata
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {
	/**
	 * 
	 * @Title: checkTimeOut
	 * @Description:判断两个时间差是否查过30分钟
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return boolean true表示不超过，false表示超过
	 */
	public static boolean checkNotTimeOut(Date startTime, Date endTime) {
		if (startTime != null && endTime != null) {
			long between = endTime.getTime() - startTime.getTime();
			long day = between / (24 * 60 * 60 * 1000);
			long hour = (between / (60 * 60 * 1000) - day * 24);
			long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
			return ((hour == 0) && (day == 0) && (min <= 30));
		} else {
			return false;
		}
	}

	/**
	 * @Title: daysBetween
	 * @Description: 判断相差天数
	 * @param @param date1
	 * @param @param date2
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	public static int daysBetween(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1 + 1000) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 
	 * @Title: checkAfterToday
	 * @Description:判断所给时间是否超过今天
	 * @param date
	 * @return boolean 返回true表示date大于等于今天，返回false表示小于今天
	 */
	public static boolean checkAfterToday(Date date) {
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DAY_OF_YEAR, day);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return date.getTime() >= calendar.getTimeInMillis();
	}

	/**
	 * 日期类型转换
	 * 
	 * @param source
	 *            日期字符串
	 * @param format
	 *            字符串格式
	 * @return 日期
	 * @throws ParseException
	 */
	public static Date parse(String source, String format) {
		try {
			if (source == null || format == null) {
				return null;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(source);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @Title: format
	 * @Description:date转换为指定格式的string
	 * @param date
	 * @param pattern
	 * @return
	 * @throws Exception
	 *             String
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String dateformat;
		try {
			dateformat = sdf.format(date);
		} catch (Exception e) {
			return "";
		}
		return dateformat;
	}
	
	/**
	 * 当前时间转化为字符串
	 * @return
	 */
	public static String nowTimeToStr() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateformat;
		try {
			dateformat = sdf.format(date);
		} catch (Exception e) {
			return "";
		}
		return dateformat;	
	}

	/**
	 * @Title: subtract
	 * @Description 计算两个日期相差的天数, 不足1天时计算为1天
	 * @param bigDate
	 *            较大的时间
	 * @param smallDate
	 *            较小的时间
	 * @return Integer 天数
	 */
	public static Integer subtract(Date bigDate, Date smallDate) {
		if (bigDate == null || smallDate == null)
			return -1;

		return (int) ((bigDate.getTime() - smallDate.getTime()
				+ DateUtils.MILLIS_PER_DAY - 1) / DateUtils.MILLIS_PER_DAY);
	}
    
	/**
	 * 是否合法的年份
	 * @param year
	 * @return
	 */
	public static boolean isYear(String year) {
		Pattern p = Pattern.compile("^[1-9][0-9]{3}$");
		return p.matcher(year).matches();
	}
	
	/**
	 * 是否是合法的年月
	 * @param dateStr
	 * @return
	 */
	public static boolean isYearAndMonth(String dateStr) {
		Pattern p = Pattern.compile("^[1-9][0-9]{3}\\-((0[1-9])|(1[012]))$");
		return p.matcher(dateStr).matches();
	}
	
	/**
	 * 是否是合法的月份
	 * @param dateStr
	 * @return
	 */
	public static boolean isMonth(String dateStr) {
		Pattern p = Pattern.compile("^((0[1-9])|(1[012]))$");
		return p.matcher(dateStr).matches();
	}
	
	
	/**
	 * 日期是否合法
	 * @param dateStr
	 * @return
	 */
	public static boolean isDate(String dateStr) {
		Pattern p = Pattern.compile("^[1-9][0-9]{3}\\-((0[1-9])|(1[012]))\\-((0[1-9])|([12][0-9])|(3[01]))$");
		return p.matcher(dateStr).matches();
	}
	
	public static Date addMonthOne(Date dt) {
        Calendar g = Calendar.getInstance();  
        g.setTime(dt);  
        g.add(Calendar.MONTH, 1);             
        return g.getTime();  
	}
	
	/**
	 * 两个日期之间相差的秒数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long calLastedTime(Date startDate, Date endDate) {
		return (long) ((endDate.getTime() - startDate.getTime()) / 1000);
	}
	
	public static Date getCalendarDate(int year, int month) {
        Calendar calendar = Calendar.getInstance();  
        calendar.set(Calendar.YEAR, year); 
        calendar.set(Calendar.MONTH, month-1 ); 
        calendar.set(Calendar.DAY_OF_MONTH, 1);//设置日期  
        return calendar.getTime();
	}
	
	public static Date addDateTimeCalendar(int year, int month, int day, int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year); 
		cal.set(Calendar.MONTH, month-1 ); 
		cal.set(Calendar.DAY_OF_MONTH, day);//设置日期  
		cal.add(Calendar.SECOND, second);
		return cal.getTime();
	}
	
	public static String dateAutoCompletion(String dates) {
		if (dates.length() < 2) {
		   return "0" + dates;
		}
		return dates;
	}
	
 	public static String dateHandle(String dateStr) {
		if(YoueStringUtils.isNotEmpty(dateStr)) {
			dateStr = dateStr.replace("-", "") + "000000";
		}
		return dateStr;
	}

    public static String timeStr(String timeStr) {
    	
    	if (YoueStringUtils.isEmpty(timeStr) || timeStr.length() != 14) {
    		return "";
    	}
    	
		String year = timeStr.substring(0, 4);
		String month = timeStr.substring(4, 6);
		String day = timeStr.substring(6, 8);
		String times= "";
	    if ("00".equals(month) && "00".equals(day)) {
	    	times = year+"年";
	    } else if ("00".equals(day)) {
	    	times = year+"年" + month + "月";
	    } else if (!"00".equals(day)) {
	    	times = year + "年" + month + "月" + day + "日";
	    }
	    return times;
    }
	
    public static Date nowDateMinusDay(int dayCount) {
    	Date dNow = new Date();   //当前时间
    	Date dBefore = new Date();
    	Calendar calendar = Calendar.getInstance(); //得到日历
    	calendar.setTime(dNow);//把当前时间赋给日历
    	calendar.add(Calendar.DAY_OF_MONTH, - dayCount);  //设置为前一天
    	dBefore = calendar.getTime();   //得到前一天的时间
	    return dBefore;
    }
    
	/**
	 * 年份是否合法
	 * @param year
	 * @return
	 */
	public static boolean isIndexYear(String year) {
		Pattern p = Pattern.compile("^[1-9][0-9]{3}0000000000$");
		return p.matcher(year).matches();
	}
	
	/**
	 * 月份是否合法
	 * @param dateStr
	 * @return
	 */
	public static boolean isIndexMonth(String dateStr) {
		Pattern p = Pattern.compile("^[1-9][0-9]{3}((0[1-9])|(1[012]))00000000$");
		return p.matcher(dateStr).matches();
	}
    
	public static void main(String[] args) throws ParseException {
//		String date1 = "2015-01-01 00:00:00";
//		String date2 = "2015-01-02 23:59:59";
//		System.out.println("==========="
//				+ daysBetween(parse(date1, "yyyy-MM-dd HH:mm:ss"),
//						parse(date2, "yyyy-MM-dd HH:mm:ss")));
//		System.out.println(isDate("2003-02-29"));
//		System.out.println(isMonth("2003-10"));
//		Date now = new Date();
//		Date now_10 = new Date(now.getTime() - 300000);
//		System.out.println(calLastedTime(now_10, new Date()));
//		System.out.println(parse("201607", "yyyy-MM-dd HH:mm:ss"));
//		Date startDate= new Date("2016-07-00-000000");
//		System.out.println(startDate);
//		Date endDate= new Date(20160800000000l);
//		System.out.println(calLastedTime(startDate, endDate));
		
//		int year = 2016;
//        int month = 10;
//		Date startDate = getCalendarDate(year, month);
//		Date endDate = getCalendarDate(2016, 11);
//		System.out.println(calLastedTime(startDate, endDate));
		
//		int year = 2015;
//        int month = 1;
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.YEAR, year); 
//		cal.set(Calendar.MONTH, month-1 ); 
//		cal.set(Calendar.DAY_OF_MONTH, 1);//设置日期  
//		cal.add(Calendar.SECOND, 31536002);
//		System.out.println(format(cal.getTime(), "yyyyMM"));
//		
//		String ss = "2016-08-26";
//		String ee = "2016-08-27"; 
//		System.out.println(calLastedTime(parse(ss, "yyyy-MM-dd"), parse(ee, "yyyy-MM-dd")));
		
		//System.out.println(isMonth("13"));

		String s = "100.12";
		if (s.indexOf(".") > 0) {
			s = s.replaceAll("0+?$", "");
			s = s.replaceAll("[.]$", "");
		}
		System.out.println(s);

	}
	
}
