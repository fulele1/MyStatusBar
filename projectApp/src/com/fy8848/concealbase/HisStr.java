package com.fy8848.concealbase;

import java.util.ArrayList;

public class HisStr {
	protected ArrayList<String> FoData=new ArrayList<String>();
	public int FiMax=20;
	
	public void load(String sData)
	{
		String[] aData=sData.split("\u0001");
		int i;
		for(i=0;i<aData.length;i++) 
			if(aData[i].trim().length()>0)
				FoData.add(aData[i].trim());
	}
	
	public void add(String sData)
	{
		int i,j;
		if(sData.trim().length()==0) return;
		for(i=0;i<FoData.size();i++)
			if(FoData.get(i).compareTo(sData)==0) 
				{
				 for(j=i;j>0;j--) FoData.set(j,FoData.get(j-1));
				 FoData.set(0, sData);
				 return;
				}
		if(FoData.size()<FiMax)	FoData.add("");
		int iFrom=FoData.size()-1;
		for(i=iFrom;i>0;i--)
			FoData.set(i, FoData.get(i-1));
		FoData.set(0,sData);
	}
	
	public String get(int iNum)
	{
		StringBuilder oStr=new StringBuilder();
		int i,iUp=FoData.size();
		if(iNum>0&&iNum<iUp) iUp=iNum;
		if(FoData.size()>0)
		{
			  oStr.append(FoData.get(0));
		      for(i=1;i<iUp;i++)
		        oStr.append("\u0001"+FoData.get(i));
		}
		return oStr.toString();
	}

}
