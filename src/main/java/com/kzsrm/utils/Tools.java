package com.kzsrm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.tools.Tool;

/**
 * 工具类
 * 
 * @author wwy
 *
 */
public class Tools {
	public static SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 
	 * @param time
	 *            验证码时间
	 * @param type
	 *            0:邮件 1:手机
	 * @return true：过期 false：未过期
	 * @throws ParseException
	 */
	public static boolean codeInvalid(String time, int type) throws ParseException {
		java.util.Date date = ymdhms.parse(time);
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		if (type == 0) {
			ca.add(Calendar.HOUR_OF_DAY, 2);
		} else if (type == 1) {
			ca.add(Calendar.MINUTE, 5);
		}
		String afterTime = ymdhms.format(ca.getTime()); // n分钟/小时后的时间
		Date dt1 = new Date();
		Date dt2 = ymdhms.parse(afterTime);
		if (dt1.getTime() > dt2.getTime()) {
			return true;
		} else if (dt1.getTime() <= dt2.getTime()) {
			return false;
		} else {
			return false;
		}
	}

	/**
	 * 签到时间和当前时间做对比 签到时间=当前时间 签到时间<当前时间
	 * 
	 * @param signInTime
	 * @return boolean
	 * @throws ParseException 
	 */
	public static boolean compareSignInTime(Date signInTime) throws ParseException {
		Date date1 = Tools.ymd.parse(Tools.ymd.format(new Date()));
		Date date2 = Tools.ymd.parse(Tools.ymd.format(signInTime));
		if (date1.getTime() > date2.getTime()) {
			System.out.println("至少今天没签到");
			return false;
		}
		if (date1.getTime() == date2.getTime()) {
			System.out.println("今天已经签到了");
			return false;
		}
		return false;
	}
	
	public List<String> getNoSignInDay(){
		return null;
	}

	public static void main(String[] args) throws ParseException {
		// System.out.println(codeInvalid("2015-08-04 17:25:56",1));
		String s = "2015-08-08";//数据库签到日期
		Date d = Tools.ymd.parse(s);
		System.out.println(compareSignInTime(d));
	}
}
