package com.fy8848.procunit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import android.content.Context;



public class CodeTable {
	protected HashMap<String, String> FoKey=new HashMap<String, String>();
	protected HashMap<String, String> FoValue=new HashMap<String, String>();
	
	public CodeTable(Context oCon,int iRes)
	{
		String[] aData=oCon.getResources().getStringArray(iRes);
		int i,iPos;
		String sKey="",sValue="";
		for(i=0;i<aData.length;i++)
		{
			iPos=aData[i].indexOf("-");
			if(iPos>0)
			{
			   sKey=aData[i].substring(0, iPos);
			   sValue=aData[i].substring(iPos+1);
			   FoKey.put(sKey, sValue);
			   FoValue.put(sValue, sKey);
			}
		}
	}
	
	public void add(String sKey,String sValue)
	{
		FoKey.put(sKey, sValue);
		FoValue.put(sValue, sKey);
	}
	
	public String getKey(String sValue)
	{
		return FoValue.get(sValue);
	}
	
	public String getByValue(String sValue)
	{
		return getKey(sValue)+"-"+sValue;
	}
	
	public String getValue(String sKey)
	{
		return FoKey.get(sKey);
	}
	
	public String getByKey(String sKey)
	{
		return sKey+"-"+getValue(sKey);
	}
	
	public String getKeyByValue(String sValue)
	{
		return "";
	}
	
	public static String keyValue(String sData)
	{
		int iPos=sData.indexOf("-");
		if(iPos>0) return sData.substring(0,iPos);
		else return sData;
	}
	
	public static String keyShow(String sData)
	{
		int iPos=sData.indexOf("-");
		if(iPos>0) return sData.substring(iPos+1);
		else return sData;
	}
	
	public static String dateStr(String sDate)
	{
	  try
	  {
		SimpleDateFormat oFormat = new SimpleDateFormat("yyyy-MM-dd");
		 Date oDate=oFormat.parse(sDate);
		 oFormat = new SimpleDateFormat("yyyyMMdd");
		 return oFormat.format(oDate);
	  }
	  catch(Exception E)
	  {
		  return "";
	  }
	}
	
	public static String dateStrMore(String sDate)
	{
	  try
	  {
		SimpleDateFormat oFormat = new SimpleDateFormat("yyyyMMdd");
		 Date oDate=oFormat.parse(sDate);
		 oFormat = new SimpleDateFormat("yyyy-MM-dd");
		 return oFormat.format(oDate);
	  }
	  catch(Exception E)
	  {
		  return "";
	  }
	}

}
