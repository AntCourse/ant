package com.kzsrm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
	 * @param time  验证码时间
	 * @param type  0:邮件  1:手机
	 * @return true：过期   false：未过期
	 * @throws ParseException
	 */
	public static boolean codeInvalid(String time ,int type) throws ParseException {
		java.util.Date date = ymdhms.parse(time);
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		if (type == 0) {
			ca.add(Calendar.HOUR_OF_DAY, 2);
		} else if (type == 1) {
			ca.add(Calendar.MINUTE, 5);
		}
		String afterTime = ymdhms.format(ca.getTime()); //n分钟/小时后的时间
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

	public static void main(String[] args) throws ParseException {
		System.out.println(codeInvalid("2015-08-04 17:25:56",1));
	}
}
