package com.siran.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TimeUtil {
	
	public static final int YYMMDDhhmmssxxx = 15;

	public static final int YYYYMMDDhhmmss = 14;

	public static final int YYMMDDhhmmss = 12;

	public static final int YYMMDDhhmm = 10;

	public static final int YYMMDDhh = 8;
	public static final DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");


	/**
	 * 
	 * @param format
	 * @param years
	 * @return
	 */
	public static String getTime(int format, int years) {
		StringBuffer cTime = new StringBuffer(15);
		Calendar time = Calendar.getInstance();
		int miltime = time.get(Calendar.MILLISECOND);
		int second = time.get(Calendar.SECOND);
		int minute = time.get(Calendar.MINUTE);
		int hour = time.get(Calendar.HOUR_OF_DAY);
		int day = time.get(Calendar.DAY_OF_MONTH);
		int month = time.get(Calendar.MONTH) + 1;
		int year = -1;
		if (years != 0)
			year = years;
		else
			year = time.get(Calendar.YEAR);
		time = null;
		if (format != 14) {
			if (year >= 2000)
				year = year - 2000;
			else
				year = year - 1900;
		}
		if (format >= 2) {
			if (format == 14)
				cTime.append(year);
			else
				cTime.append(getFormatTime(year, 2));
		}
		if (format >= 4)
			cTime.append(getFormatTime(month, 2));
		if (format >= 6)
			cTime.append(getFormatTime(day, 2));
		if (format >= 8)
			cTime.append(getFormatTime(hour, 2));
		if (format >= 10)
			cTime.append(getFormatTime(minute, 2));
		if (format >= 12)
			cTime.append(getFormatTime(second, 2));
		if (format >= 15)
			cTime.append(getFormatTime(miltime, 3));
		return cTime.toString().trim();
	}

	/**
	 * 取得本地系统的时间，时间格式由参数决定
	 * 
	 * @param format
	 *            时间格式由常量决定
	 * @return String 具有format格式的字符串
	 */

	public static String getTime(int format) {

		return getTime(format, 0);
	}

	/**
	 * 产生任意位的字符串
	 * 
	 * @param iyear
	 *            int 要转换格式的时间
	 * @param format
	 *            int 转换的格式
	 * @return String 转换的时间
	 */
	public synchronized static String getYearAdd(int format, int iyear) {
		StringBuffer cTime = new StringBuffer(10);
		Calendar time = Calendar.getInstance();
		time.add(Calendar.YEAR, iyear);
		int miltime = time.get(Calendar.MILLISECOND);
		int second = time.get(Calendar.SECOND);
		int minute = time.get(Calendar.MINUTE);
		int hour = time.get(Calendar.HOUR_OF_DAY);
		int day = time.get(Calendar.DAY_OF_MONTH);
		int month = time.get(Calendar.MONTH) + 1;
		int year = time.get(Calendar.YEAR);
		if (format != 14) {
			if (year >= 2000)
				year = year - 2000;
			else
				year = year - 1900;
		}
		if (format >= 2) {
			if (format == 14)
				cTime.append(year);
			else
				cTime.append(getFormatTime(year, 2));
		}
		if (format >= 4)
			cTime.append(getFormatTime(month, 2));
		if (format >= 6)
			cTime.append(getFormatTime(day, 2));
		if (format >= 8)
			cTime.append(getFormatTime(hour, 2));
		if (format >= 10)
			cTime.append(getFormatTime(minute, 2));
		if (format >= 12)
			cTime.append(getFormatTime(second, 2));
		if (format >= 15)
			cTime.append(getFormatTime(miltime, 3));
		return cTime.toString();
	}

	
	private static String getFormatTime(int time, int format) {
		StringBuffer numm = new StringBuffer(format);
		int length = String.valueOf(time).length();

		if (format < length)
			return null;

		for (int i = 0; i < format - length; i++) {
			numm.append("0");
		}
		numm.append(time);
		return numm.toString().trim();
	}

	/**
	 * 本函数主要作用是返回当前年份 #len 返回位数，2位 4位
	 * @param len
	 * @return
	 */
	public static String getYear(int len) {
		Calendar time = Calendar.getInstance();
		int year = time.get(Calendar.YEAR);
		String djyear = Integer.toString(year);
		if (len == 2) {
			djyear = djyear.substring(2);
		}
		return djyear;
	}

	/**
	 * 本函数作用是返回当前月份（2位）
	 * @return
	 */
	public static String getMonth() {
		Calendar time = Calendar.getInstance();
		int month = time.get(Calendar.MONTH) + 1;
		String djmonth = "";
		if (month < 10) {
			djmonth = "0" + Integer.toString(month);
		} else {
			djmonth = Integer.toString(month);
		}
		return djmonth;
	}

	/**
	 * 本函数主要作用是返回当前天数
	 * @return
	 */
	public static String getDay() {
		Calendar time = Calendar.getInstance();
		int day = time.get(Calendar.DAY_OF_MONTH);
		String djday = "";
		if (day < 10) {
			djday = "0" + Integer.toString(day);
		} else {
			djday = Integer.toString(day);
		}
		return djday;
	}

	/**
	 * 本函数作用是返回当前小时
	 * @return
	 */
	public static String getHour() {
		Calendar time = Calendar.getInstance();
		int hour = time.get(Calendar.HOUR_OF_DAY);
		String djhour = "";
		if (hour < 10) {
			djhour = "0" + Integer.toString(hour);
		} else {
			djhour = Integer.toString(hour);
		}
		return djhour;
	}

	/**
	 * 本函数作用是返回当前分钟
	 * @return
	 */
	public static String getMin() {
		Calendar time = Calendar.getInstance();
		int min = time.get(Calendar.MINUTE);
		String djmin = "";
		if (min < 10) {
			djmin = "0" + Integer.toString(min);
		} else {
			djmin = Integer.toString(min);
		}
		return djmin;
	}

	
	/**
	 * 取得两个时间段的时间间隔天数
	 * 
	 * @param startTime
	 *            时间1
	 * @param endTime
	 *            时间2
	 * @param formatStr
	 *            传入的数字时间格式 如:yyyyMMdd
	 * @return 结束时间和开始时间的间隔天数
	 * @throws java.text.ParseException
	 *             如果输入的日期格式不是0000-00-00 格式抛出异常
	 */
	public static int getBetweenDays(String startTime, String endTime,
									 String formatStr) throws ParseException {
		int betweenDays = 0;

		DateFormat format = new SimpleDateFormat(formatStr);
		Date startDate = format.parse(startTime);
		Date endDate = format.parse(endTime);
		if (startDate.compareTo(endDate) > 0) {
			betweenDays = (int) ((startDate.getTime() - endDate.getTime()) / (24 * 60 * 60 * 1000));
		} else {
			betweenDays = (int) ((endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000));
		}
		return betweenDays;
	}

	/**
	 * 日期转换N秒N分钟前
	 * 
	 * @param time
	 *            20101124161900
	 * @return N秒N分钟前
	 * @author zhanglv
	 */
	public static String getWebTime(String time) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return time;
		}
		Calendar hisCal = new GregorianCalendar();
		hisCal.setTime(date);
		long start = hisCal.getTimeInMillis();

		Calendar nowCal = Calendar.getInstance();
		long end = nowCal.getTimeInMillis();

		long hour = (end - start) / 1000 / 60 / 60;
		long minute = (end - start) / 1000 / 60;
		long second = (end - start) / 1000;
		if (hour < 12) {
			// 小时
			if (minute < 60) {
				// 分钟
				if (second < 60) {
					// 秒
					return second + "秒前";
				}
				return minute + "分钟前";
			}
			return hour + "小时前";
		}
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 转换List(Map)中指定的日期字段为：N秒前，N分钟前
	 * 
	 * @param fieldName
	 *            字段名
	 * @param list
	 *            目标List(Map)
	 * @return
	 * @author zhanglv
	 */
	public static List<Map> getWebTime(
			List<Map> list, String fieldName) {
		for (Map<String, Object> m : list) {
			m.put(fieldName, TimeUtil
					.getWebTime(m.get(fieldName) == null ? "unknown" : m
							.get(fieldName).toString()));
		}
		return list;
	}
	
	/**
	 * 获得当前更新时间“年月日时分秒”
	 * @return
	 */
	public static String getTimeString(){
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(new Date());
	}


	/**
	 * 获得当前更新时间“年月日时分秒”
	 * @return
	 */
	public static String getTimeStringDay(){
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(new Date());
	}


	/**
	 * 获得当前时间字符串“年月日时分秒毫秒”
	 * @return
	 */

	public synchronized static String getTimeStringMillisecond(){
		return format.format(new Date())+ ThreadLocalRandom.current().nextInt(100,1000);
	}
	
	public static String getFormatDate(String time){
		if(time==null||time.length()<14){
			return "";
		}else{
			StringBuffer s = new StringBuffer();
			s.append(time.substring(0, 4));
			s.append("年");
			s.append(time.substring(4, 6));
			s.append("月");
			s.append(time.substring(6, 8));
			s.append("日");
			return s.toString();
		}
	}
	
	
	
	public static String getFormatPhone(String terminal){
		if(terminal==null){
			return "";
		}
		if(terminal.length()==11){
			return terminal.substring(0,4)+"****"+terminal.substring(8,11);
		}else{
			return terminal;
		}
	}
	
	public static String getFormatNumber(String number){
		if(number!=null&&number.length()==1){
			number = "00"+number;
		}else if(number!=null&&number.length()==2){
			number = "0"+number;
		}
		return number;
	}
	
	public static int parseInt(String n){
		return Integer.parseInt(n);
	}

	public static void main(String[] args) {

		Thread thread = new Thread(){
			@Override
			public void run() {
				while (true)
				System.out.println(TimeUtil.getTimeStringMillisecond());

			}
		};

		thread.start();
		thread.start();
	}
}
