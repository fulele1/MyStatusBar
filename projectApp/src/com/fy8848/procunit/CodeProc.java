package com.fy8848.procunit;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class CodeProc {
	
	public static String md5(String string) {
	    byte[] hash;
	    try {
	        hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException("不支持md5", e);
	    } catch (UnsupportedEncodingException e) {
	        throw new RuntimeException("不支持utf-8", e);
	    }

	    StringBuilder hex = new StringBuilder(hash.length * 2);
	    for (byte b : hash) {
	        if ((b & 0xFF) < 0x10) hex.append("0");
	        hex.append(Integer.toHexString(b & 0xFF));
	    }
	    return hex.toString();
	}
	
	public static String fKeyValue(String sData)
	{
		int iPos=sData.indexOf("-");
		if(iPos>0) return sData.substring(0,iPos);
		else return sData;
	}
	
	public static String fKeyShow(String sData)
	{
		int iPos=sData.indexOf("-");
		if(iPos>0) return sData.substring(iPos+1);
		else return sData;
	}
	
	public static String RandomStr(int iLen)
	{
		Random oRad=new Random();
		int i;
		StringBuilder oRet=new StringBuilder();
		for(i=0;i<iLen;i++)
		  oRet.append((char)(48+oRad.nextInt(10)));
		return oRet.toString();
	}
	
	public static String checkSql(String sSql)
	{
		int i,iLen;
		String sTmp="";
		StringBuilder oStr=new StringBuilder();
		iLen=sSql.length();
		for(i=0;i<iLen;i++)
		{
			sTmp=sSql.substring(i,i+1);
			if(sTmp.compareTo("'")==0) oStr.append("''");
			else oStr.append(sTmp);
		}
		return oStr.toString();
	}
	

}
