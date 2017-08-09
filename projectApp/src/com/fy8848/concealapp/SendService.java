package com.fy8848.concealapp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.fy8848.procunit.AssistProc;

public class SendService extends Service {

	protected SendFileThread FoThread=null;
	protected String FsAppPath="";
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override  
    public void onCreate() {  
         
		//FoThread=new SendFileThread();
		super.onCreate();  
		
    }
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {  
        
		if(FoThread==null) FoThread=new SendFileThread();
		else AssistProc.log("error","no create");
		//FoThread.FsPath=intent.getExtras().getString("path");
        //FoThread.FsUrl=intent.getExtras().getString("url");
        //FoThread.FsUser=intent.getExtras().getString("user");
       // FoThread.FsRight=intent.getExtras().getString("right");
        //Log.w("error",FoThread.FsPath);
       //Log.w("error",FoThread.FsUrl);
       //Log.w("error",FoThread.FsUser);
       // Log.w("error",FoThread.FsRight);
		//Log.w("error","start service");
		 String[] aName={"server","user","pass","checkcode"};
		 String[] aValue={"","","","",""};
		 readConfig(aName,aValue);
		 FoThread.FsPath=Environment.getExternalStorageDirectory().getAbsolutePath() + "/projecVideo/upload";
		 FoThread.FsUrl=aValue[0];
		 FoThread.FsUser=aValue[1];
		 FoThread.FsPass=aValue[2];
		 FoThread.FsRight=aValue[1];
		 FoThread.FsCheck=aValue[3];
	     FoThread.FoHandler=FoHandler;
         FoThread.open();
		return super.onStartCommand(intent, flags, startId);
		
	}
	
	@Override
	public void onDestroy()
	{
		if(FoThread!=null) FoThread.over();
		FoThread=null;
		super.onDestroy();
	}
	
	protected String appPath()
	{
		if(FsAppPath.length()==0)
		{
		   Context oContext=this;//首先，在Activity里获取context  
           File oFile=oContext.getFilesDir();  
           FsAppPath=oFile.getAbsolutePath();
		}
        return FsAppPath;
	}
	
	protected void readConfig(String[] aName,String[] aValue)
	{
		SharedPreferences oConfig = getSharedPreferences("config", Activity.MODE_PRIVATE);
		int i;
		for(i=0;i<aName.length;i++)
			aValue[i]=oConfig.getString(aName[i], "");
	}
	
	protected void writeConfig(String sName,String sValue)
	{
		SharedPreferences oConfig = getSharedPreferences("config", Activity.MODE_PRIVATE);
		Editor oEdit = oConfig.edit();//获得编辑器   
		oEdit.putString(sName, sValue);     
		oEdit.commit();//提交内容 
	}
	
	public void writeExpire(String sExpire)
	{
		  SimpleDateFormat oFormat = new SimpleDateFormat("yyyy年MM月dd日");
		  try
		  {
			 
				   Date oDate=oFormat.parse(sExpire);
				   writeConfig("expire",sExpire);
				
		  }
		  catch(Exception E)
		  {}
	}
	
	public void addLog(String sText)
	{
		AssistProc.log("error",sText);
		DataManager FoData=new DataManager(this);
		String sError=FoData.addLog(sText);
		if(sError.length()>0) AssistProc.log("error",sError);
		
	}
	
	Handler FoHandler = new Handler() {  
		@Override
        public void handleMessage(Message msg) {   
			Bundle oData;
             switch (msg.what) {   
                  case 0: //获取版本号 
                	   oData=msg.getData();
                	   String sFile=oData.getString("file");
                	   upStatus(sFile);
                	  break;
                  case 1:
                	  //writeExpire((String)msg.obj);
                	  oData=msg.getData();
                	  String sError=oData.getString("error");
                	  addLog(sError);
                	  break;
                  case 2:
                	  oData=msg.getData();
                	  Intent oInt=new Intent("com.fy8848.conceal.activity");
               	      oInt.putExtra("file", oData.getString("file"));
               	      oInt.putExtra("percent", oData.getInt("percent",0));
               	      sendBroadcast(oInt);
                	  break;
                     
                  
             }   
             super.handleMessage(msg);   
        }   
   };  
   
   public void upStatus(String sFile)
   {
	   AssistProc.log("error",sFile);
	   DataManager FoData=new DataManager(this);
	   String sError=FoData.upStatus(sFile);
	   if(sError.length()>0) AssistProc.log("error",sError);
   }
   
   

}
