package com.ucm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomUtil {
	
	public static String convertDateToString(Date dateStr) throws ParseException{
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(dateStr);
		return date.toString();
	}
	
	public static Date convertStringToDate(String dateStr) throws ParseException{
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date parse = simpleDateFormat.parse(dateStr);
		return parse;
	}
}
