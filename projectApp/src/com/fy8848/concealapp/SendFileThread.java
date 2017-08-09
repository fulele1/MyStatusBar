package com.fy8848.concealapp;

import java.io.File;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import com.fy8848.procunit.AssistProc;
import com.fy8848.procunit.NetProc;
import com.fy8848.procunit.ProgressListener;



public class SendFileThread extends Thread {
    public String FsUrl="";
    public String FsPath="";
    public String FsUser="";
    public String FsPass="";
    public String FsRight="";
    public String FsCheck="";
    private int FiCounter=600;
    private boolean FbRun=false;
    public Handler FoHandler=null;
    String FsUpFile="";
    public long FiTick=0;
    @Override
	public void run()
	{
		String sRet="";
		String sUrl="";
		
		while(FbRun)
		{
			try
			{
			    Thread.sleep(100);
				if(FiCounter>600)
				{
					
					FiCounter=0;
					File oFile= new File(FsPath);	
				    File[] oFiles =null;
					if(oFile!=null) oFiles=oFile.listFiles();
					int i;
				    
				    
					if(oFiles!=null)
					for(i=0;i<oFiles.length;i++)
					{
					  	sUrl=FsUrl+"/open.ashx?action=videoUpload&user="+FsUser+"&pass="+FsPass+"&check="+FsCheck+"&right="+FsRight+"&file="+oFiles[i].getName();	
						try
						{
							
						AssistProc.log("error",FsPath+"/"+oFiles[i].getName());	
					  	//sRet=NetProc.sendFile(sUrl, FsPath+"/"+oFiles[i].getName());
						
						FsUpFile=oFiles[i].getName();
						FiTick=SystemClock.uptimeMillis();
					  	sRet=NetProc.uploadFile(sUrl, FsPath+"/"+oFiles[i].getName(),new ProgressListener()
					  	{
					  		public void transferred(int iPercent)
					  		{
					  			long iTick=SystemClock.uptimeMillis();
					  			if(iTick-FiTick>1000||iPercent==0||iPercent>=100)
					  			{
					  			 Message oMess=Message.obtain();
					    		 oMess.what=2;
					    		 Bundle oData=new Bundle();
					    		 oData.putString("file", FsUpFile);
					    		 oData.putInt("percent", iPercent);
					    		 oMess.setData(oData);
					    		 FoHandler.sendMessage(oMess);
					    		 FiTick=iTick;
					  			}
					  		}
					  	});
						
						if(sRet!=null&&sRet.length()==0)
						{
						  oFiles[i].delete();
						  if(FoHandler!=null)
				    	    {
							  Message oMess=Message.obtain();
					    	  oMess.what=2;
					    	  Bundle oData=new Bundle();
					    	  oData.putString("file", FsUpFile);
					    	  oData.putInt("percent", 100);
					    	  oMess.setData(oData);
					    	  FoHandler.sendMessage(oMess);
					    	  Thread.sleep(200);	 
				    		  oMess=Message.obtain();
				    		  oMess.what=0;
				    		  oData=new Bundle();
				    		  oData.putString("file", oFiles[i].getName());
				    		  oMess.setData(oData);
				    		  FoHandler.sendMessage(oMess);
				    	    }
						}
				    	else
				    	{
				    		AssistProc.log("error",sRet);
				    		if(FoHandler!=null)
				    	    {
				    		 Message oMess=Message.obtain();
				    		 oMess.what=1;
				    		 Bundle oData=new Bundle();
				    		 oData.putString("error", oFiles[i].getName()+":"+sRet);
				    		 oMess.setData(oData);
				    		 FoHandler.sendMessage(oMess);
				    	    }
				    	}
						
					  }
					 catch(Exception E)
						{
							AssistProc.log("error",E.getMessage());
							if(FoHandler!=null)
				    	    {
				    		 Message oMess=Message.obtain();
				    		 oMess.what=1;
				    		 Bundle oData=new Bundle();
				    		 oData.putString("error", oFiles[i].getName()+":"+E.getMessage());
				    		 oMess.setData(oData);
				    		 FoHandler.sendMessage(oMess);
				    	    }
						}
					 if(!FbRun) break;	
									
					}//for
					
				}//FiCount>6000
			}
			catch(Exception E)
			{
				
			}
			finally
			{
			  FiCounter++;
			}
		}
	}
    
    
	
	
	public void over()
	{
		FbRun=false;
	}
	public void open()
	{
		if(!FbRun)
		{
			FbRun=true;
			this.start();
		}
	}
	
	protected void writeLog(String sFile)
	{
		if(FoHandler!=null)
		{
		Message oMess=new Message();
		oMess.what=0;
		oMess.obj=sFile;
		FoHandler.sendMessage(oMess);
		}
	}
	
	protected void writeExpire(String sExpire)
	{
		if(FoHandler!=null&&sExpire.length()>10)
		{
		 Message oMess=new Message();
		 oMess.what=1;
		 oMess.obj=sExpire;
		 FoHandler.sendMessage(oMess);
		}
	}
	
	
}
