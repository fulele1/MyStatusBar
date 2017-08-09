package com.fy8848.virtualtable;

import java.util.ArrayList;
import java.util.Date;

import com.fy8848.procunit.DateProc;

public class TvTable {
	 public ArrayList<TvRow> FaRow=new ArrayList<TvRow>();
	 public ArrayList<TvColType> FaColType=new ArrayList<TvColType>();
	 public TvRow FoName=new TvRow();  //字段名称数据
	 public TvRow FoState=new TvRow();  //字段名称数据
	 protected boolean FbEof=true;
	 protected int FiP=0;//当前记录指针
	 protected boolean FbHaveState=false;//是否载入状态
	 protected boolean FbHaveName=false;//是否载入字段名称
	 protected int FiAllSize=0;//数据总数
	 protected int FiPageSize=10;//分页大小
	 protected int FiAllPage=0;//总页数
	 protected int FiCurrentPage=0;//当前页
	 protected int FiDictID=0;
	 protected String FsTableName="";
	 
	 public String getTitleData()
			 {
			   String sTitle=this.FoState.getData();
			   StringBuilder oStr=new StringBuilder();
			   int i;
			   for(i=0;i<this.FaColType.size();i++)
			   {
				 if(i>0) oStr.append("\u0001");
			     oStr.append(Integer.toString(this.FaColType.get(i).FiDataType)+":"+this.FoName.FaValue.get(i));
			   }
			   return sTitle+"\u0002"+oStr.toString();  
			   
			 };
	public String getRowData(int iRow)
			 {
			  
			   if(iRow>-1&&iRow<this.FaRow.size()) return this.FaRow.get(iRow).getData();
			   else 
			      if(this.FaRow.size()>0) return this.FaRow.get(this.FiP).getData();
			      else return "";
			 }
			 
	public String getData(boolean bAll)
			 {
			   int i;
			   StringBuilder oStr=new StringBuilder();
			   oStr.append(getTitleData());
			   if(bAll)
			    {
			      for(i=0;i<this.FaRow.size();i++)
			       oStr.append("\u0002"+this.getRowData(i));
			    }
			  else
			    if(this.FaRow.size()>0)
			    	oStr.append("\u0002"+this.getRowData(-1)); 
			  return oStr.toString();     
			 }
	
   public void replaceName(String sOld,String sNew){this.FoName.replaceValue(sOld,sNew);}
			 	 //----(外部调用)获取当前状态--------------------
   public int getStatus()
			 {
			   if(this.FoState.itemCount()==0) return -1;
			   else return this.FoState.asInt(0);
			 }
			 //----(外部调用)获取错误提示----------------
   public String getError()
			 {
			   if(this.FoState.itemCount()==0) return "数据未载入";
			   else return this.FoState.asString(1);
			 }
			 //------(外部调用)获取数据行数量-----------------
   public int recordCount()
			 {
				 return this.FaRow.size();
			 }
   
   public int getAll()
   {
	   return this.FiAllSize;
   }
			 //--------（外部调用）--清除数据---
   public void clearData()
			 {
				 this.FaRow.clear();
				 this.FbEof=true;
				 this.FiP=0;
				 this.FiAllSize=0;
				 this.FiPageSize=0;
				 this.FiAllPage=0;
				 this.FiCurrentPage=0;
			 };
			 //--------(外部调用)清除所有
  public void clear()
			    {
				 this.clearData();
				 this.FaColType.clear();
				 this.FoState.clear();
				 this.FoName.clear();
				 this.FbHaveState=false;
				 this.FbHaveName=false;
				 this.FiDictID=0;
				 this.FsTableName="";
			    };
	public void addCol(String sName,int iType)
		{  
		      TvColType oItem;
		      int i;
			  if(sName.length()>0&&(iType>0)&&(iType<11))
			    	{
			    			     this.FoName.add(sName);
			    				 oItem=new TvColType();
			    				 oItem.FiDataType=iType;
			    				 this.FaColType.add(oItem);
			    				 for(i=0;i<this.FaRow.size();i++)
			    					 switch(iType)
			    				     {
			    				     case 1:
			    				     case 2:FaRow.get(i).add("0");break;
			    				     case 3:
			    				     case 4:
			    				     case 5:FaRow.get(i).add(DateProc.getTimeStr("yyyy-MM-dd HH:mm:ss"));break;
			    				     case 7:FaRow.get(i).add("false");break;
			    				     default:FaRow.get(i).add("");break;
			    				     }//switch
			    			   }
	    }
	 //----------给虚表当前行设置数据-----------------------
	 public void setData(String sData)
	 {
	   if(this.FaRow.size()>0&&this.FiP>=0&&this.FiP<this.FaRow.size()) 
		   this.FaRow.get(this.FiP).loadData(sData);
	 };
	 //---------给虚拟表载入数据-----------------------------
	 public void add(String sData)
	 {
		TvRow oRow;
		if(this.FbHaveState)
		  if(this.FbHaveName)
			 {
			   oRow=new TvRow();
			   oRow.loadData(sData);
			   this.FaRow.add(oRow);
			   this.FbEof=false;
			   
			 }
		  else
			 {
			   this.FoName.clear();
			   this.FoName.loadData(sData);
			   this.FoName.setLower();
			   this.FbHaveName=true;
			 }
		else
		 {
			this.FoState.clear();
			this.FoState.loadData(sData);
			try
			{
			 Integer.parseInt(this.FoState.FaValue.get(0));
			 if((this.FoState.FaValue.size()!=5))
			   this.FoState.loadData("1\u0001数据格式错误\u00010\u00010\u00010");
			 this.FbHaveState=true;
			}
			catch(Exception E)
			{
				this.FoState.loadData("1\u0001数据格式错误\u00010\u00010\u00010");
			}
		 }	
	 }
	 
	//----计算当前页编号-----------------------------------
	public void calPage()  //错误码,字符串,总记录数,当前页,页大小
		{
			if(this.FoState.asInt(0)==0)
				{
				  this.FiAllSize=this.FoState.asInt(2);
				  if(this.FaRow.size()>this.FiAllSize) this.FiAllSize=this.FaRow.size();
				  this.FiPageSize=this.FoState.asInt(4);
				  if(this.FiPageSize>0)
					  {
					  
					  this.FiAllPage=(int)Math.ceil(this.FiAllSize/this.FiPageSize);
					   this.FiCurrentPage=(int)Math.floor(this.FoState.asInt(3)/this.FiPageSize)+1;
					  }
				  else
					  {
					   this.FiAllPage=0;
					   this.FiCurrentPage=1;
					  }
				}
			else
				{
				  this.FiAllSize=0;
				  this.FiPageSize=10;
				  this.FiAllPage=0;
				  this.FiCurrentPage=1;
				}
		}
		
		public void append()
				{
				   TvRow oRow=new TvRow();
				   this.FaRow.add(oRow);
				   int i;
				   for(i=0;i<this.FaColType.size();i++)
					   { switch(this.FaColType.get(i).FiDataType)
					     {
					     case 1:
					     case 2:{oRow.add("0");break;}
					     case 3:
					     case 4:
					     case 5:{oRow.add(DateProc.getTimeStr("yyyy-MM-dd HH:mm:ss"));break;}
					     case 7:{oRow.add("false");break;}
					     default:{oRow.add("");break;}
					     }//switch
					    
					   }
				  this.FiP=this.FaRow.size()-1; 
				}
		public int getColIndex(String sName)
			{
				sName=sName.toLowerCase();
				int i; 
				for(i=0;i<this.FoName.itemCount();i++)
				   if((this.FoName.asString(i).toLowerCase().compareTo(sName)==0)||(this.FaColType.get(i).FsName.toLowerCase().compareTo(sName)==0)||(this.FaColType.get(i).FsFieldName.toLowerCase().compareTo(sName)==0))
							return i;
				return -1;
			}
		public int getColCount()
		{
			return this.FoName.itemCount();
		}
		
		public  void procName()  //对名称为"类型：名称"的字段名进行处理
				{
				   int i=0,iPos,iType;
				   String sName;
				   for(i=0;i<this.FaColType.size();i++)
					   {
					     sName=this.FoName.asString(i);
					     iPos=sName.indexOf(":");
					     if(iPos>-1)
					    	 {
					    	  iType=Integer.parseInt(sName.substring(0,iPos));
					    	  sName=sName.substring(iPos+1,sName.length());
					    	  if((iType>0)||(iType<11)) this.FaColType.get(i).FiDataType=iType;
					    	  this.FoName.setValue(i,sName);
					    	 }
					   }
				};
			//-----------------------------------------
		public void setColType()
				{  
					this.FaColType.clear();
					int i;
					for(i=0;i<this.FoName.itemCount();i++)
					   this.FaColType.add(new TvColType());
					this.procName();
				}
		
		public void loadData(String sData,boolean bAppend)
				{
			    int i;
				this.FbHaveName=false;
				this.FbHaveState=false;
				if(!bAppend) this.clearData();
				String[] aTmp=sData.split("\u0002");
				if(bAppend)
				{
				  this.FbHaveName=true;
				  this.FbHaveState=true;
				  this.FoState.loadData(aTmp[0]);
				  for(i=2;i<aTmp.length;i++)  this.add(aTmp[i]);
				}
				else
				{
				  for(i=0;i<aTmp.length;i++)  this.add(aTmp[i]);
				}
				if(!bAppend) 
				   {
						this.FiP=0;
				        this.setColType();
			        }
					if(this.FaRow.size()==0)  this.FbEof=true;
					else this.FbEof=false;
					this.calPage();
				}
			//--------------------------------------------------
		public void first()
				{
				 this.FiP=0;
				 if(this.recordCount()>0) this.FbEof=false;
				 else this.FbEof=true;
				}
			//--------------------------------	
	   public void last()
			    {
			     this.FiP=this.recordCount()>0?this.recordCount()-1:0;	
			     if(this.recordCount()>0) this.FbEof=false;
				 else this.FbEof=true;
			    }
			//----------------------------    
	   public void next()
			    {
			      if(this.recordCount()>0)
			    	  {
			    	   if(this.FiP<this.recordCount()-1)
			    		   {
			    		     this.FiP++;
			    		     this.FbEof=false;
			    		   }
			    	   else 
			    		   {
			    		     this.FiP=this.recordCount()-1;
			    		     this.FbEof=true;
			    		   }
			    	  }
			      else
			    	  {
			    	   this.FiP=0;
			    	   this.FbEof=true;
			    	  }
			    }
			    //-------------------
	  public void prio()
			    {
			      if(this.FiP>0) this.FiP--;
			      if(this.recordCount()>0) this.FbEof=false;
			 	  else this.FbEof=true;
			    }
	  public int moveBy(int iOffset)
			    {  int  iOld=this.FiP;
			       if(this.recordCount()<=0) return 0;
			       this.FiP+=iOffset;
			       this.FbEof=false;
			       if(this.FiP<0) this.FiP=0;
			       if(this.FiP>=this.recordCount())
			    	  	{
			    	   	  this.FiP=this.recordCount()-1;
			    	   	  this.FbEof=true;
			    	   	}
			       return this.FiP-iOld;
			    
			    };
	  public int go(int iP)
			    {   
			    	
			    	if(this.recordCount()==0) return 0;
			    	if(iP<0)
			    		{
			    		 this.FiP=0;
			    		 this.FbEof=false;
			    		}
			    	else
			    	if(iP>=this.recordCount())
			    		{
			    		 this.FiP=this.recordCount()-1;
			    		 this.FbEof=true;
			    		}
			    	else
			    		{
			    		 this.FiP=iP;
			    		 this.FbEof=false;
			    		}
			    	return this.FiP;
			    }
     public boolean eof(){ return this.FbEof;}
     
     
	 public  void deleteRow()
			    {
			      if(this.recordCount()>0&&this.FiP>=0&&this.FiP<this.recordCount())
			    	  {
			    	   this.FaRow.remove(FiP);
			    	   if(this.FiP>=this.recordCount()) this.FiP=this.recordCount()-1;
			    	  }
			    }//删除当前行
	 
	 public String asString(int iNo)
			   {
				 String sResult="";
				 if(this.recordCount()>0)
					 {
					//  iNo=this.getColIndex(uCol);
					  if(iNo>=0)
						  {
						    sResult=this.FaRow.get(this.FiP).asString(iNo);
						    if((this.FaColType.get(iNo).FiDataType>2)&&(this.FaColType.get(iNo).FiDataType<6))
						    	{
						    	    
						    	  Date oDate=DateProc.strToDate(sResult, "");
						    	  if(oDate!=null)
						    	  switch(this.FaColType.get(iNo).FiDataType)
						    	  {
						    	  case 3:sResult=DateProc.formatDate(oDate, "yyyy-MM-dd");break;
						    	  case 4:sResult=DateProc.formatDate(oDate, "HH:mm:ss");break;
						    	  case 5:sResult=DateProc.formatDate(oDate, "");break;
						    	  }
						    	}
						    else
						    	if(this.FaColType.get(iNo).FiDataType==7)
						    		{
						    		  if((sResult.compareTo("true")==0)||(sResult.compareTo("1")==0)||(sResult.compareTo("是")==0)||(sResult.compareTo("√")==0))
						    			  sResult="1";
						    		  else sResult="0";
						    		}
						    return sResult; 
						  }
					  
					 }
				 
				return ""; 
			   };
	  public String asString(String sName)
	  {
		  int iNo=this.getColIndex(sName);
		  return asString(iNo);
	  }
	  
	  public String asString(int iRow,int iNo)
	  {
		  this.go(iRow);
		  return asString(iNo);
	  }
	  
	  public String asString(int iRow,String sName)
	  {
		  this.go(iRow);
		  return asString(sName);
	  }
	  
	  
	  public int asInt(int iNo)
	  {
			String sValue=asString(iNo);
			int iRet=0;
			try
			{
				iRet=Integer.parseInt(sValue);
			}
			catch(Exception E)
			{
				return iRet;	
			}
			return iRet;
	  };
	  
	  public int asInt(String sName)
	  {
		  int iNo=this.getColIndex(sName);
		  return asInt(iNo);
	  }
	  
	  public int asInt(int iRow,int iNo)
	  {
		  go(iRow);
		  return asInt(iNo);
	  }
	  
	  public int asInt(int iRow,String sName)
	  {
		  go(iRow);
		  return asInt(sName);
	  }
	  
	  public float asFloat(int iNo)
	  {
			String sValue=asString(iNo);
			float fRet=0;
			try
			{
				fRet=Float.parseFloat(sValue);
			}
			catch(Exception E)
			{
				return fRet;	
			}
			return fRet;
	  };
	  
	  public float asFloat(String sName)
	  {
		  int iNo=this.getColIndex(sName);
		  return asFloat(iNo);
	  }
	  
	  public float asFloat(int iRow,int iNo)
	  {
		  go(iRow);
		  return asFloat(iNo);
	  }
	  
	  public float asFloat(int iRow,String sName)
	  {
		  go(iRow);
		  return asFloat(sName);
	  }
	  
	  public boolean asBoolean(int iNo)
			   {  
				   if(this.recordCount()>0)
					 {
					  //iNo=this.getColIndex(uCol); 
					  if(iNo>=0)
						{ 
						  String sBool=this.FaRow.get(this.FiP).asString(iNo);
						  if((sBool.compareTo("true")==0)||(sBool.compareTo("1")==0)||(sBool.compareTo("是")==0)||(sBool.compareTo("√")==0))
			    			  return true;
			    		  else return false;
						}  
					 }
				   return false;
			   };
	  public boolean asBoolean(String sName)
	  {
		  int iNo=this.getColIndex(sName);
		  return asBoolean(iNo);
	  }
	  
	  public boolean asBoolean(int iRow,int iNo)
	  {
		  go(iRow);
		  return asBoolean(iNo);
	  }
	  
	  public boolean asBoolean(int iRow,String sName)
	  {
		  go(iRow);
		  return asBoolean(sName);
	  }
	  
	  public Date asDate(int iNo)
			   {   
		           if(this.recordCount()>0)
		           {
		              String sResult=this.FaRow.get(this.FiP).asString(iNo);
		              return DateProc.strToDate(sResult, "");
		           }
		           return null;
			   }
	  public Date asDate(String sName)
	  {
		  int iNo=this.getColIndex(sName);
		  return asDate(iNo);
	  }
	  
	  public Date asDate(int iRow,int iNo)
	  {
		  go(iRow);
		  return asDate(iNo);
	  }
	  
	  public Date asDate(int iRow,String sName)
	  {
		  go(iRow);
		  return asDate(sName);
	  }
	  
	  
	  public boolean existField(String sName)
			   {
				  if(this.getColIndex(sName)==-1) return false;
				  else return true;
			   }
			//------------------------------------
	  public TvColType getColType(int iNo)
			   {
				   if(iNo>-1&&iNo<this.FaColType.size())
					   return this.FaColType.get(iNo);
				  return null;
			   };
			   
	  public int getAllPage()
	  {
		  return FiAllPage;
	  }
						
}
