package com.fy8848.virtualtable;

import java.util.ArrayList;

public class TvRow {

	public ArrayList<String> FaValue=new ArrayList<String>();//数据数组
	public  void assign(TvRow oRow)
	{
	  
	     this.FaValue.clear();
	     int i;
	     for(i=0;i<oRow.FaValue.size();i++)
	       this.FaValue.add(oRow.FaValue.get(i));
	    
	}
	//-------清除虚拟表行的数据------------------
	public void clear(){
       	this.FaValue.clear();
    }
    //--------获取虚拟表中数据的数量---------------
    public int itemCount()
    {
    	return this.FaValue.size();
    }
    //--------往虚拟表行添加一个数据-------------
    public void add(String sData){//把键值对加入数组
        this.FaValue.add(sData);
    }
 //-----------载入字符串-----------------------------------   
	public void loadData(String sData) {//载入数据
	   String[] aData=sData.split("\u0001");
	   int i;
	   for(i=0;i<aData.length;i++) FaValue.add(aData[i]);
	}
//--------------获取取值--------------
    public String getData(){
    	StringBuilder oStr=new StringBuilder();
    	int i;
    	for(i=0;i<FaValue.size();i++)
    	{
    		if(i>0) oStr.append("\u0001");
    		oStr.append(FaValue.get(i));
    	}
    	return oStr.toString();
    }	
    
  //-------//转换为小写----------------------------------  
    public void setLower(){
    
    	int i;
    	for(i=0;i<this.FaValue.size();i++)
    		this.FaValue.set(i, FaValue.get(i).toLowerCase());
    		
    };
  //---------//获取指定no的值-------------------------------------  
    public String asString(int iNo){
       	if((iNo>=0)&&(iNo<this.FaValue.size()))
    		return FaValue.get(iNo);
    	else return "";
    }
    
    public int asInt(int iNo){
    	if((iNo>=0)&&(iNo<this.FaValue.size()))
    		try
    	    {
    		  int  iData=Integer.parseInt(this.FaValue.get(iNo));
    		  return iData;
    		}
    	catch(Exception E)
    	{
    		return 0;
    	}
    	return 0;
    }
    
    public float asFloat(int iNo){
    	if((iNo>=0)&&(iNo<this.FaValue.size()))
    		try
    	    {
    		  float  fData=Float.parseFloat(this.FaValue.get(iNo));
    		  return fData;
    		}
    	catch(Exception E)
    	{
    		return 0;
    	}
    	return 0;
    }
  //--------//设定的值-------------------------------------------  
       
    public void setValue(int iNo,String sValue){//设置指定no的值
       if((iNo>=0)&&(iNo<this.FaValue.size()))
			 this.FaValue.set(iNo, sValue);
	}
	public void replaceValue(String sOld,String sNew)
	 {
	   sOld=sOld.toLowerCase();
	   int i;
	   for(i=0;i<this.FaValue.size();i++)
	    if(this.FaValue.get(i).toLowerCase().compareTo(sOld)==0) {this.FaValue.set(i,sNew);break;}
	 }
}
