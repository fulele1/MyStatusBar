package com.fy8848.concealapp;





import java.io.File;
import java.io.FileOutputStream;

import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Xml;

import com.fy8848.adaptter.TableAdapter;
import com.fy8848.adaptter.TreeAdapter;
import com.fy8848.procunit.AssistProc;
import com.fy8848.procunit.CodeProc;
import com.fy8848.procunit.DateProc;
import com.fy8848.virtualtable.TvTable;
//import com.yast.yadrly001.BtReaderClient;



public class DataManager extends SQLiteOpenHelper {
	
	private static String DB_NAME="project_video_1.db";
	private static int  DB_VERSION=1;
	private Context FoContext=null;
	
	public String FsID="";
	public String FsProject="";
	public String FsItem="";
	public String FsUnit="";
	public String FsPart="";
	public String FsSub="";
	public String FsMark="";
	public String FsFile="";
	public String FsStatus="";
	public String FsUser="";
	public String FsDate="";
	
	
	
	
	public String FsMile="";
	
	public String FsBuildMile="";
	public String FsType="";
	
	
	
	
	
	
	
	public String FsPath="";
	
	
	
	
	

	public DataManager(Context context)
	{
		
		super(context, DB_NAME, null, DB_VERSION);
		FoContext=context;
		
		
	}
	
	
	
	
	 @Override  
	 public void onCreate(SQLiteDatabase oDB) {  
		 
		 try
		 {
			createTable();
		 }
		 catch(Exception E)
		 {
			 
		 }
	       
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
	 
	 public String ListData(TableAdapter oAdapter,String sSqlCount,String sSqlSelect)
	 {
		 try
		 {
			SQLiteDatabase oDB=this.getReadableDatabase();
			 Cursor oCursor=oDB.rawQuery(sSqlCount,null);
			 oCursor.moveToFirst();
			 if(oCursor.getCount()>0) oAdapter.setTotal(oCursor.getInt(0));
			 String sPage=oAdapter.getPage();
			 if(sPage.length()>0)
			  oCursor=oDB.rawQuery(sSqlSelect+" limit "+sPage,null);
			 else
				 if(oAdapter.getCount()>0)  return "已经是最后数据";
				 else return "没有数据";
			int i,iCol; 
			iCol=oCursor.getColumnCount()-1; 
			String[] aData=new String[iCol];
			String sPk="";
			oCursor.moveToFirst();
			if(oCursor.getCount()>0)
		    while(true)
		    {
		    	sPk=oCursor.getString(0);
		    	for(i=0;i<iCol;i++) aData[i]=oCursor.getString(i+1);
		    	oAdapter.add(sPk, aData);
		    	if(!oCursor.moveToNext()) break;
		    }
		   
			return "";
			
		 }
		 catch(Exception E)
		 {
			 return E.getMessage();
		 }
	 }
	 
	 public String ListData(TableAdapter oAdapter,TvTable oTable)
	 {
		 int iCol=oTable.getColCount()-1,i;
		 if(iCol<=0) return "读取数据错误";
		 String[] aData=new String[iCol];
		 String sPk="";
		 oAdapter.setTotal(oTable.getAll());
		 oTable.first();
		 while(!oTable.eof())
		 {
			 sPk=oTable.asString(0);
		     for(i=0;i<iCol;i++) aData[i]=oTable.asString(i+1);
		     oAdapter.add(sPk, aData);
		     oTable.next();
		 }
		 return "";
	 }
	  
	 @Override  
	 public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
	  
	    }  
	 
	 
	 //创建数据库表
	 public String createTable()
	 {
		 try
		 {
			 SQLiteDatabase oDB=this.getWritableDatabase();
			
			 String sSql="create table if not exists videoList (vlid integer primary key autoincrement,vlname varchar(20), vlunit varchar(100),vlpart varchar(100),vlitem varchar(100),vlsub varchar(100),vlmark varchar(1000),vlfile varchar(20),vldate varchar(20),vlstatus varchar(10),vltype varchar(10))";
			 oDB.execSQL(sSql);
			 oDB.execSQL("create index if not exists IvideoListdate on videolist (vldate)");
			 sSql="create table if not exists ProFrag (pfcode varchar(20) primary key,pfname varchar(50))";
			 oDB.execSQL(sSql);
			 sSql="create table if not exists proItem (piid varchar(30) primary key,piname varchar(100),PIMark varchar(1000),PIMore varchar(200),PIType varchar(30),piparent varchar(30),haveChild int,PIFrag varchar(30))";
			 oDB.execSQL(sSql);
			 sSql="create table if not exists errorlog (id integer primary key autoincrement,logtext varchar(1000),logtime varchar(20))";
			 oDB.execSQL(sSql);
			 sSql="create table if not exists uptask (id varchar(30) primary key,videoid integer,status integer default 0)";
			 oDB.execSQL(sSql);
			return "";
			
		 }
		 catch(Exception E)
		 {
			 return E.getMessage();
		 }
	 }
	 
	 public String clearTable()
	 {
		 try
		 {
			SQLiteDatabase oDB=this.getWritableDatabase();
		    oDB.execSQL("drop table if exists videoList");
		    oDB.execSQL("drop table if exists ProFrag");
		    oDB.execSQL("drop table if exists proItem");
		   
			return "";
			
		 }
		 catch(Exception E)
		 {
			 return E.getMessage();
		 }
	 }
	 
	 
	 //同步项目标段
	 public String addProject(TvTable oTable)
	 {
		 try
		 {
			SQLiteDatabase oDB=this.getWritableDatabase();
		    oDB.execSQL("delete from ProFrag");
		    oTable.first();
		    while(!oTable.eof())
		    {
		    	oDB.execSQL("insert into ProFrag (pfcode,pfname) values('"+checkSql(oTable.asString("pfcode"))+"','"+checkSql(oTable.asString("pfname"))+"')");
		    	oTable.next();
		    }
		   
			return "";
			
		 }
		 catch(Exception E)
		 {
			 return E.getMessage();
		 }
	 }
	 
	
	 //同步项目划分
	 public String addItem(TvTable oTable)
	 {
		 try
		 {
			SQLiteDatabase oDB=this.getWritableDatabase();
		    oDB.execSQL("delete from proitem");
		    oTable.first();
		    StringBuilder oSql=new StringBuilder();
			 //创建标段
		    while(!oTable.eof())
		    {
		    	oSql.delete(0, oSql.length());
		    	oSql.append("insert into proitem (piid,piname,piparent,PImark,pitype,pimore,havechild,pifrag) values('"+oTable.asString("piid")+"'");
		    	oSql.append(",'"+checkSql(oTable.asString("piname"))+"'");
		    	oSql.append(",'"+oTable.asString("piparent")+"'");
		    	oSql.append(",'"+checkSql(oTable.asString("pimark"))+"'");
		    	oSql.append(",'"+checkSql(oTable.asString("pitype"))+"'");
		       	oSql.append(",'"+checkSql(oTable.asString("pimore"))+"'");
		    	oSql.append(","+oTable.asString("havechild"));
		    	oSql.append(",'"+checkSql(oTable.asString("pifrag"))+"')");
		    	oDB.execSQL(oSql.toString());
		    	oTable.next();
		    }
		   
			return "";
			
		 }
		 catch(Exception E)
		 {
			 return E.getMessage();
		 }
	 }
	 
	 
	 
	 public String getProject()
	 {
		 try
		 {
			SQLiteDatabase oDB=this.getReadableDatabase();
			Cursor oCursor=oDB.rawQuery("select pfcode,pfname from  profrag",null);
			  if(oCursor!=null)
			  {
				  StringBuilder oStr=new StringBuilder();
				  oCursor.moveToFirst();
				  if(oCursor.getCount()>0)
				  while(true)
				  {
					  if(oStr.length()>0) oStr.append("\u0001");
					  oStr.append(oCursor.getString(0)+"-"+oCursor.getString(1));
					  if(!oCursor.moveToNext()) break;
				  }
				  
				  return oStr.toString();
			  }
			  else return "";
		   			
		 }
		 catch(Exception E)
		 {
			 return "";
		 }
	 }
	 
	 
	 public String getItem(String sFrag,TreeAdapter oAdapter)
	 {
		 try
		 {
			String[] aData={"",""};
			int iParent=0;
			String sPk="",sParent="";
			oAdapter.clearData();
			SQLiteDatabase oDB=this.getReadableDatabase();
			Cursor oCursor=oDB.rawQuery("select piid,piname,pitype,piparent from  proitem where pifrag='"+sFrag+"' order by piid",null);
			 if(oCursor!=null)
			  {
				  StringBuilder oStr=new StringBuilder();
				  oCursor.moveToFirst();
				  if(oCursor.getCount()>0)
				  do
				  {
					 aData[0]=oCursor.getString(1);
					 aData[1]=oCursor.getString(2);
					 sPk=oCursor.getString(0);
					 sParent=oCursor.getString(3);
					 
					 
					 oAdapter.add(sPk, sParent, aData, 0);
							 
				  }while(oCursor.moveToNext());
				  
				  return oStr.toString();
			  }
			  else return "";
			
		   			
		 }
		 catch(Exception E)
		 {
			 return "";
		 }
	 }
	 
	 
	 public String getItem(String sPk)
	 {
		 try
		 {
			String sUnit="",sPart="",sItem="",sSub="",sType="";
			Cursor oCursor;
			int i=0;
			SQLiteDatabase oDB=this.getReadableDatabase();
			while(sPk.length()>0&&i<=5)
			{
			 i++;
			 oCursor=oDB.rawQuery("select piname,pitype,piparent from  proitem where piid='"+sPk+"'",null);
			 sPk="";
			 if(oCursor!=null)
			  {
				  oCursor.moveToFirst();
				  if(oCursor.getCount()>0)
				  {
					  sType=oCursor.getString(1);
					  if(sType.compareTo("单位工程")==0) sUnit=oCursor.getString(0);
					  else
						  if(sType.compareTo("分部工程")==0) sPart=oCursor.getString(0);
						  else
							  if(sType.compareTo("分项工程")==0) sItem=oCursor.getString(0);
							  else
								  if(sType.compareTo("子分项工程")==0) sSub=oCursor.getString(0);
					  sPk=oCursor.getString(2);
				  }
				  
				
			  }
			 
			 
			}
			
			return sUnit+"\u0001"+sPart+"\u0001"+sItem+"\u0001"+sSub;
		   			
		 }
		 catch(Exception E)
		 {
			 return "";
		 }
		 
	 }
	 
	 public String getItem(String sID,String sFrag,String sType)
	 {
		 try
		 {
			 StringBuilder oData=new StringBuilder();
			Cursor oCursor;
			SQLiteDatabase oDB=this.getReadableDatabase();
			oCursor=oDB.rawQuery("select piid,piname from proitem where piparent='"+sID+"' and pitype='"+sType+"' and pifrag='"+sFrag+"'",null);
			oCursor.moveToFirst();
			 if(oCursor.getCount()>0)
				  do
				  {
					 if(oData.length()>0) oData.append("\u0001");
					 oData.append(oCursor.getString(0)+"-"+oCursor.getString(1));
							 
				  }while(oCursor.moveToNext());
		   	 return oData.toString();		
		 }
		 catch(Exception E)
		 {
			 return "";
		 }
	 }
	 
	 
	 
	 public String addVideo()
	 {
		 try
		 {
			SQLiteDatabase oDB=this.getWritableDatabase();
		    StringBuilder oSql=new StringBuilder();
		    oSql.append("insert into videolist (vlname,vlunit,vlpart,vlItem,vlsub,vlmark,vlfile,vldate,vlstatus,vltype) values(");
		    oSql.append("'"+checkSql(FsProject)+"'");
		    oSql.append(",'"+checkSql(FsUnit)+"'");
		    oSql.append(",'"+checkSql(FsPart)+"'");
		    oSql.append(",'"+checkSql(FsItem)+"'");
		    oSql.append(",'"+checkSql(FsSub)+"'");
		    oSql.append(",'"+checkSql(FsMark)+"'");
		    oSql.append(",'"+checkSql(FsFile)+"'");
		    String sExt=FsFile.substring(FsFile.length()-3).toLowerCase();
		    if(sExt.compareTo("jpg")==0) FsType="图像";
		    else FsType="视频";
		    oSql.append(",'"+DateProc.getTimeStr("yyyy-MM-dd HH:mm:ss")+"'");
		    oSql.append(",'"+checkSql(FsStatus)+"'");
		    oSql.append(",'"+FsType+"')");
		    oDB.execSQL(oSql.toString());
		    AssistProc.log("error",oSql.toString());
		    return ""; 
		 }
		 catch(Exception E)
		 {
			 return E.getMessage();
		 }
	 }
	 
	 public String delVideo(String[] aID)
	 {
		 try
		 {
			String sFile="";
			 SQLiteDatabase oDB=this.getWritableDatabase();
			SQLiteDatabase oRB=this.getReadableDatabase();
			int i;
			for(i=0;i<aID.length;i++)
			{
			 Cursor oCursor=oRB.rawQuery("select vlfile from videoList where vlid="+aID[i],null);
			 if(oCursor!=null&&oCursor.getCount()==1)
			 {
				 oCursor.moveToFirst();
				 sFile=oCursor.getString(0);
				 File oFile=new File(FsPath+"/"+sFile);
				 if(oFile.exists()) oFile.delete();
				 oDB.execSQL("delete from videoList where vlid="+aID[i]);
			 }
		     
			}
		    return ""; 
		 }
		 catch(Exception E)
		 {
			 return E.getMessage();
		 }
	 }
	 
	 public int getCount()
	 {
		 try
		 {
			SQLiteDatabase oDB=this.getReadableDatabase();
			Cursor oCursor=oDB.rawQuery("select count(*) from videolist",null);
			if(oCursor!=null&&oCursor.getCount()==1)
			{
				oCursor.moveToFirst();
				return oCursor.getInt(0);
			}
			return 0;
		 }
		 catch(Exception E)
		 {
			 return 0;
		 }
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 public String getVideo(int iID)
	 {
		 try
			{
			 SQLiteDatabase oDB=this.getReadableDatabase();
			 Cursor oCursor;
			 oCursor=oDB.rawQuery("select vlid,vlname,vlunit,vlpart,vlItem,vlsub,vlmark,vldate,vlstatus,vlfile,vltype from videolist where vlid="+Integer.toString(iID),null);
			 if(oCursor.getCount()>0)
				 {
				    oCursor.moveToFirst();
				    FsID=oCursor.getString(0);
				    FsProject=oCursor.getString(1);
				    FsUnit=oCursor.getString(2);
				    FsPart=oCursor.getString(3);
				    FsItem=oCursor.getString(4);
				    FsSub=oCursor.getString(5);
				    FsMark=oCursor.getString(6);
				    FsDate=oCursor.getString(7);
				    FsStatus=oCursor.getString(8);
				    FsFile=oCursor.getString(9);
				    FsType=oCursor.getString(10);
				    return "";
				 }
			 else  return "数据不存在";
			}
		catch(Exception E)
			{
			  return E.getMessage();	
			}  
	 }
	 
	 public String setUp(String sID)
	 {
		 try
		 {
			SQLiteDatabase oRDB=this.getReadableDatabase();
			Cursor oCursor=oRDB.rawQuery("select vlfile from videolist where vlid="+sID,null);
			if(oCursor.getCount()==0) return "数据不存在";
			oCursor.moveToFirst();
			String sFile=oCursor.getString(0);
			String sExt="";
			int i=sFile.indexOf(".");
			if(i>0) 
				{
				  sExt=sFile.substring(sFile.length()-3).toLowerCase();
				  sFile=sFile.substring(0,i);
				}
			SQLiteDatabase oDB=this.getWritableDatabase();
			oDB.execSQL("update videoList set vlstatus='正在上传' where vlid="+sID);
			oDB.execSQL("insert into uptask (id,videoid,Status) values('"+sFile+"',"+sID+",3)");
		    return ""; 
		 }
		 catch(Exception E)
		 {
			 return E.getMessage();
		 }
	 }
	 
	 public String upStatus(String sFile)
	 {
		 try
		 {
			int i=sFile.indexOf("."),iStatus=0;
			String sID=sFile.substring(0,i);
			//String sExt=sFile.substring(i+1).toLowerCase();
			SQLiteDatabase oRDB=this.getReadableDatabase();
			Cursor oCursor=oRDB.rawQuery("select videoid,status from uptask where id='"+sID+"'",null);
			if(oCursor.getCount()==0) return "数据不存在";
			oCursor.moveToFirst();
			String sVID=oCursor.getString(0);
			iStatus=oCursor.getInt(1);
			/*
			if(sExt.compareTo("jpg")==0) iStatus=iStatus|2;
			else
				if(sExt.compareTo("xml")==0) iStatus=iStatus|4;
				else iStatus=iStatus|1;
				*/
			iStatus-=1;
			SQLiteDatabase oDB=this.getWritableDatabase();
			if(iStatus<=0)
			{
				oDB.execSQL("update videoList set vlstatus='已上传' where vlid="+sVID);
				oDB.execSQL("delete from uptask where id='"+sID+"'");
			}
			else
				oDB.execSQL("update uptask set status="+Integer.toString(iStatus)+" where id='"+sID+"'");
		    return ""; 
		 }
		 catch(Exception E)
		 {
			 return E.getMessage();
		 }
	 }
	 
	 public String addLog(String sLog)
	 {
		 try
		 {
			SQLiteDatabase oDB=this.getWritableDatabase();
			oDB.execSQL("insert into errorlog(logtext,logtime) values('"+CodeProc.checkSql(sLog)+"','"+DateProc.getTimeStr("yyyy-MM-dd HH:mm:ss")+"')");
			oDB.execSQL("delete from errorlog where id<(select max(id)-100 from  errorlog)");
		    return ""; 
		 }
		 catch(Exception E)
		 {
			 return E.getMessage();
		 }
	 }
	 
	 
	 
	 public String createPackage()
	 {
		
				try
				{
				  
				  String sFile=FsPath+"/upload/"+FsFile.substring(0,FsFile.length()-4)+".xml";
				  File oFile = new File(sFile);
				  FileOutputStream oFileStream= new FileOutputStream(oFile);
				  XmlSerializer oXml = Xml.newSerializer();
				  
				  oXml.setOutput(oFileStream,"UTF-8");
				  oXml.startDocument("UTF-8", true);
				  oXml.startTag(null, "package");
				  String[] aTag={"vlname","vlunit","vlpart","vlitem","vlsub","vlmark","vldate","vlfile","vluser","vltype"};
				  String[] aValue={FsProject,FsUnit,FsPart,FsItem,FsSub,FsMark,FsDate,FsFile,FsUser,FsType};
				  int i;
				  for(i=0;i<aTag.length;i++)
				  {
					  oXml.startTag(null, aTag[i]);
					  oXml.text(aValue[i]);
					  oXml.endTag(null, aTag[i]);
				  }
				  
				  oXml.endTag(null, "package");
				  oXml.endDocument();
				  oFileStream.flush();
				  oFileStream.close();
				  return "";
				}
				catch(Exception E)
				{
					return E.getMessage();
				}
		 
	 }
	 
	 
	 
	 
	 
	
	 
     
	 
	 
}
