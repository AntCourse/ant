package com.kzsrm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ComUtils {
	public static String getuuid(){
		return UUID.randomUUID().toString();
	}
	
	public static String date2Str(Date date){
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		if (date == null)
			return "";
		return formatDate.format(date);
	}
	public static String dateTime2Str(Date date){
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (date == null)
			return "";
		return formatDate.format(date);
	}
	
	public static Date str2Date(String strDate) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Date time = null;
        try {
            time = formatDate.parse(strDate);
        } catch (Exception e) {
            return null;
        }
        return time;
    }
	public static Date str2DateTime(String strDate) {
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = null;
		try {
			time = formatDate.parse(strDate);
		} catch (Exception e) {
			return null;
		}
		return time;
	}
	public static long daysBetweenDate(Date begin, Date end){
		if (begin == null)
			return 0;
		if (end == null)
			return 0;
		
		return (end.getTime() - begin.getTime()) / (24 * 60 * 60 * 1000);
	}
	public static int parseInt(String input){
		try{
			return Integer.parseInt(input);
		}catch (Exception e){
			return 0;
		}
	}
}
