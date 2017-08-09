package com.fy8848.procunit;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Handler;
import android.os.Message;

import com.fy8848.virtualtable.TvTable;

public class SendOneFileThread extends Thread {
    public String FsUrl="";
    public String FsFile=""; 

    private boolean FbRun=false;
    public Handler FoHandler=null;
    @Override
	public void run()
	{
    	TvTable oTable;
		  try
			{
			    URL oUrl = new URL(FsUrl);  
			    HttpURLConnection oHttp = (HttpURLConnection) oUrl.openConnection();  
			    oHttp.setReadTimeout(60000);  
			    oHttp.setDoInput(true);// 允许输入  
			    oHttp.setDoOutput(true);// 允许输出  
			    oHttp.setUseCaches(false);  
			    oHttp.setRequestMethod("POST"); // Post方式
			    DataOutputStream oOutput = new DataOutputStream(oHttp.getOutputStream());
			    File oFile=new File(FsFile);
			    FileInputStream oInput = new FileInputStream(FsFile);
			    byte[] aBuf = new byte[1024];
		        int iLen = 0,iPercent=0;
		        long iTotal=oFile.length(),iCount=0;
		        try
		        {
		        while((iLen=oInput.read(aBuf))!=-1)
		        {
		            oOutput.write(aBuf, 0, iLen);
		            iCount+=iLen;
		            if(iTotal>0) iPercent=(int)(100*iCount/iTotal);
		            if(FoHandler!=null)
		            {
		            	Message oMess=Message.obtain();//进度
		            	oMess.what=10;
		            	oMess.arg1=iPercent;
		            	FoHandler.sendMessage(oMess);
		            }
		            if(!FbRun) 
		            {
		            	Message oMess=Message.obtain();//中断
		            	oMess.what=1;
		            	oMess.obj="用户中断";
		            	FoHandler.sendMessage(oMess);
		            	oOutput.close();
		            	oInput.close();
		            	return;
		            }
		            if(iTotal<1000)
		            Thread.sleep(3000);
		            else
		            	if(iTotal<10000)
		            		Thread.sleep(1000);
		            	
		        }
		        oInput.close();
		        oOutput.flush();
		        if(oHttp.getResponseCode()==200){  
		        	    String sRet=new String(NetProc.readStream(oHttp.getInputStream()));
		        		oTable=new TvTable();
		        		oTable.loadData(sRet, false);
		        		if(oTable.getStatus()==0) //正确完成
		        		{
		        			Message oMess=Message.obtain();
			            	oMess.what=0;
			            	FoHandler.sendMessage(oMess);
		        		}
		        		else 
		        			{
		        			   Message oMess=Message.obtain();
			            	   oMess.what=1;
			            	   oMess.obj=oTable.getError();
			                	FoHandler.sendMessage(oMess);
		        			}
		        } 
		        else
		        {
		        	   Message oMess=Message.obtain();
	            	   oMess.what=1;
	            	   oMess.obj=oHttp.getResponseMessage();
	                   FoHandler.sendMessage(oMess);
		        
		        }
		        }
		        finally
		        {
		        	oInput.close();
		        	oOutput.close();
		        }
			
			}
			catch(Exception E)
			{
				
				Message oMess=Message.obtain();
         	    oMess.what=1;
         	    oMess.obj=E.getMessage();
             	FoHandler.sendMessage(oMess);
				
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
	
	
	
	
}
