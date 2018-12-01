package com.gopher.system.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期通用工具
 * 
 * @author dongyangyang
 *
 */
public class DateUtils {
	private final static String DATE_FMT = "yyyy-MM-dd";
	private final static String DATE_TIME_FMT = "yyyy-MM-dd HH:mm:ss";
	private final static String TIME_FMT = "HH:mm:ss";

	public static List<Long> getDayBetween(long starTime, long endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FMT);
		List<Long> result = new ArrayList<>();
		try {
			Date d1 = sdf.parse(sdf.format(starTime));
			Date d2 = sdf.parse(sdf.format(endTime));
			System.out.println(d1.getTime());
			System.out.println(d2.getTime());
			Calendar dd = Calendar.getInstance();
			dd.setTime(d1);
			while (dd.getTime().getTime() <= d2.getTime()) {
				result.add(dd.getTime().getTime());
				dd.add(Calendar.DAY_OF_YEAR, 1);
			}
		} catch (Exception e) {

		}
		return result;
	}
	
	public static List<Long> getMonthBetween(long starTime, long endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		List<Long> result = new ArrayList<>();
		try {
			Date d1 = sdf.parse(sdf.format(starTime));
			Date d2 = sdf.parse(sdf.format(endTime));
			System.out.println(d1.getTime());
			System.out.println(d2.getTime());
			Calendar dd = Calendar.getInstance();
			dd.setTime(d1);
			while (dd.getTime().getTime() <= d2.getTime()) {
				result.add(dd.getTime().getTime());
				dd.add(Calendar.MONTH, 1);
			}
		} catch (Exception e) {

		}
		return result;
	}

	public static void main(String[] args) {
		//TODO 
	}

	public static String getDateString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FMT);
		return sdf.format(date);
	}

	public static String getYearMonthString(Long timeMillis) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		return sdf.format(new Date(timeMillis));
	}

	public static String getDateString(Long timeMillis) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FMT);
		return sdf.format(new Date(timeMillis));
	}

	public static String getDateTimeString(Long timeMillis, String format) {
		if (null == format || format.equals("")) {
			return "";
		}
		if (null == timeMillis || timeMillis == 0) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(timeMillis));
	}

	public static String getDateTimeString(Long timeMillis) {
		if (null == timeMillis || timeMillis == 0) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FMT);
		return sdf.format(new Date(timeMillis));
	}

	public static boolean isSameDayInMonth(long oneTime, long anotherTime) {
		boolean flag = false;
		if (oneTime == anotherTime) {
			flag = true;
		} else if (getDayOfMonth(oneTime) == getDayOfMonth(anotherTime)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 获得当前年
	 * 
	 * @param date
	 * @return
	 */
	public static int getLocaleYear() {
		Calendar ca = Calendar.getInstance();
		return ca.get(Calendar.YEAR);
	}

	public static long getYearStart(long datetime) {
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(datetime);
		ca.set(Calendar.MONTH, 0);
		ca.set(Calendar.DAY_OF_MONTH, 1);
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		ca.set(Calendar.MILLISECOND, 0);
		return ca.getTimeInMillis() / 1000l;

	}

	public static long getYearEnd(long datetime) {
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(datetime);
		ca.set(Calendar.MONTH, 0);
		ca.set(Calendar.DAY_OF_MONTH, 1);
		ca.set(Calendar.HOUR_OF_DAY, 23);
		ca.set(Calendar.MINUTE, 59);
		ca.set(Calendar.SECOND, 59);
		ca.set(Calendar.MILLISECOND, 999);
		ca.add(Calendar.YEAR, 1);
		ca.add(Calendar.DAY_OF_MONTH, -1);
		return ca.getTimeInMillis() / 1000l;
	}

	public static long getMonthStart(long datetime) {
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(datetime);
		ca.set(Calendar.DAY_OF_MONTH, 1);
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		ca.set(Calendar.MILLISECOND, 0);
		return ca.getTimeInMillis() / 1000l;

	}

	public static long getMonthEnd(long datetime) {
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(datetime);
		ca.set(Calendar.DAY_OF_MONTH, 1);
		ca.set(Calendar.HOUR_OF_DAY, 23);
		ca.set(Calendar.MINUTE, 59);
		ca.set(Calendar.SECOND, 59);
		ca.set(Calendar.MILLISECOND, 999);
		ca.add(Calendar.MONTH, 1);
		ca.add(Calendar.DAY_OF_MONTH, -1);
		return ca.getTimeInMillis() / 1000l;
	}

	/**
	 * 获得当前年
	 * 
	 * @param date
	 * @return
	 */
	public static int getLocaleMonth() {
		Calendar ca = Calendar.getInstance();
		return ca.get(Calendar.MONTH) + 1;
	}
	
	public static String getDatetimeString(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FMT);
		return sdf.format(new Date(timestamp));
	}

	
	public static String getDatetimeString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FMT);
		return sdf.format(date);
	}

	public static String getTimeString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FMT);
		return sdf.format(date);
	}

	public static long getTheFirstDayOfMonth(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date); // someDate 为你要获取的那个月的时间
		ca.set(Calendar.DAY_OF_MONTH, 1);
		Date firstDate = ca.getTime();
		return firstDate.getTime();
	}

	public static Date getDateTime(String source) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FMT);
		try {
			return sdf.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static long getDateTimeLong(String source) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FMT);
		try {
			return sdf.parse(source).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static long getYesterdayStart() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis() / 1000;
	}

	public static long getYesterdayEnd() {
		return getOneDayEnd(-1);
	}

	/**
	 * 某一天的结束（秒）
	 * 
	 * @param day
	 *            当前天（+/- day的值）
	 * @return
	 */
	public static long getOneDayEnd(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, day);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTimeInMillis() / 1000;
	}

	/**
	 * 获得某一天的开始（秒）
	 * 
	 * @param dateTime
	 *            这一天的任意时段毫秒值
	 * @return
	 */
	public static long getOneDayStart(long dateTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(dateTime));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis() / 1000;
	}

	/**
	 * 获得某一天的开始（秒）
	 * 
	 * @param dateTime
	 *            这一天的任意时段毫秒值
	 * @return
	 */
	public static long getOneMonthStart(long dateTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(dateTime));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTimeInMillis();
	}

	/**
	 * 获得某一天的结束（秒）
	 * 
	 * @param dateTime
	 * @return
	 */
	public static long getOneDayEnd(long dateTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(dateTime));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTimeInMillis() / 1000;
	}

	/**
	 * 获得上个月的开始
	 * 
	 * @return
	 */
	public static long getLastMonthStart() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis() / 1000;
	}

	/**
	 * 获得上个月的结束
	 * 
	 * @return
	 */
	public static long getLastMonthEnd() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTimeInMillis() / 1000;
	}

	/**
	 * 指定时间，获取这个时间的本月的开始
	 * 
	 * @param monnth
	 * @return
	 */
	public static long getOneMonthStart(int monnth) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, monnth - 1);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	/**
	 * 指定时间，获取这个时间的本月的结束
	 * 
	 * @param monnth
	 * @return
	 */
	public static long getOneMonthEnd(int monnth) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.MONTH, monnth - 1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTimeInMillis();
	}

	/**
	 * 指定时间，获取这个时间的本月的结束
	 * 
	 * @param monnth
	 * @return
	 */
	public static long getOneMonthEnd(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, 1);
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTimeInMillis();
	}

	/**
	 * 指定时间，获得当前时间的月
	 * 
	 * @param time
	 * @return
	 */
	public static int getMonth(long timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 指定时间，获得当前时间的年
	 * 
	 * @param time
	 * @return
	 */
	public static int getYear(long timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 指定时间，获得当前时间的天
	 * 
	 * @param time
	 * @return
	 */
	public static int getDayOfYear(long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return calendar.get(Calendar.DAY_OF_YEAR);
	}

	public static int getDayOfMonth(long datetime) {
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(datetime);
		return ca.get(Calendar.DAY_OF_MONTH);
	}

	public static Long getMonthTimes(int m) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, m);
		return calendar.getTime().getTime() / 1000;
	}

	public static long getTimestamp(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, date, 0, 0, 0);
		return calendar.getTime().getTime();
	}

	public static int getHourByDateTime(long dateTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dateTime);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

}
