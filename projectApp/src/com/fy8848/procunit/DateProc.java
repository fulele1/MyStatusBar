package com.fy8848.procunit;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateProc {
	
	public static String DateFormat="yyyy-MM-dd HH:mm:ss";
	
	public static String formatDate(Date oDate,String sFormat)
	{
		if(sFormat.length()==0) sFormat=DateFormat;
		SimpleDateFormat oFormat=new SimpleDateFormat(sFormat);
		return oFormat.format(oDate);
	}
	
	public static String getTimeStr(String sFormat)
	{
		if(sFormat.length()==0) sFormat=DateFormat;
		SimpleDateFormat oFormat=new SimpleDateFormat(sFormat);
		return oFormat.format(new Date());
	}
	
	public static Date strToDate(String sDate,String sFormat)
	{
		if(sFormat.length()==0) sFormat=DateFormat;
		SimpleDateFormat oFormat=new SimpleDateFormat(sFormat);
		try
		{
		  Date oDate=oFormat.parse(sDate);
	      return oDate;
		}
		catch(Exception E)
		{
			return null;
		}
	}

}
