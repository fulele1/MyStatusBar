package com.fy8848.virtualtable;

import java.util.ArrayList;

public class TvRow {

	public ArrayList<String> FaValue=new ArrayList<String>();//��������
	public  void assign(TvRow oRow)
	{
	  
	     this.FaValue.clear();
	     int i;
	     for(i=0;i<oRow.FaValue.size();i++)
	       this.FaValue.add(oRow.FaValue.get(i));
	    
	}
	//-------���������е�����------------------
	public void clear(){
       	this.FaValue.clear();
    }
    //--------��ȡ����������ݵ�����---------------
    public int itemCount()
    {
    	return this.FaValue.size();
    }
    //--------������������һ������-------------
    public void add(String sData){//�Ѽ�ֵ�Լ�������
        this.FaValue.add(sData);
    }
 //-----------�����ַ���-----------------------------------   
	public void loadData(String sData) {//��������
	   String[] aData=sData.split("\u0001");
	   int i;
	   for(i=0;i<aData.length;i++) FaValue.add(aData[i]);
	}
//--------------��ȡȡֵ--------------
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
    
  //-------//ת��ΪСд----------------------------------  
    public void setLower(){
    
    	int i;
    	for(i=0;i<this.FaValue.size();i++)
    		this.FaValue.set(i, FaValue.get(i).toLowerCase());
    		
    };
  //---------//��ȡָ��no��ֵ-------------------------------------  
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
  //--------//�趨��ֵ-------------------------------------------  
       
    public void setValue(int iNo,String sValue){//����ָ��no��ֵ
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
