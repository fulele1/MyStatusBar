package com.fy8848.procunit;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fy8848.virtualtable.TvTable;

public class SendThread extends Thread {
	
	protected Context FoContext;
	public String FsUrl="";//请求地址
	public String FsData="";//发送的数据
	public String FsFileName="";//文件路径
	protected AlertDialog FoDialog;//当前显示的对话框
	protected ProgressBar FoBar;
	protected TextView FoText;
	public int FiWait_Layout=0;
	public int FiProgress_Layout=0;
	public int FiText=0;
	public int FiProgress=0;
	protected OnNetOver FoNetOver;
	protected OnUpDownOver FoUpDown;
	protected int FiMode=0;//数据请求模式 0：一般数据请求  1：数据下载    2：数据上传
	protected TvTable[] FaRet;//返回的虚表
	protected String FsRet="";//返回字符串
	public SendThread(Context oContext)
	{
		FoContext=oContext;
	}
	
	public void setOverListener(OnNetOver oOver){FoNetOver=oOver;}//设置数据请求后的监听器
	public void setUpDownListener(OnUpDownOver oOver){FoUpDown=oOver;}//设置数据下载后的监听器
	
	Handler FoHandler = new Handler() {  
		@Override
        public void handleMessage(Message msg) {   
             switch (msg.what) {   
                  case 0: //显示等待对话框，读取服务器
                	  if(FiWait_Layout>0) showWait("正在请求服务器....",FiWait_Layout);
                   	  break;
                  case 1:
                	  if(FiProgress_Layout>0) showWait("正在读取...",FiProgress_Layout);
                	  if(FoText!=null) FoText.setText("已完成 0%");               	  
                  case 10://进度显示
                	  if(FoBar!=null)
                	    FoBar.setProgress(msg.arg1);
                	  if(FoText!=null) FoText.setText("已完成"+Integer.toString(msg.arg1)+"%");
                	  break;
                  case 11://进度完成
                	 if(FoDialog!=null) FoDialog.dismiss();
                	 if(FoUpDown!=null) FoUpDown.onOver(FsRet);
                	 break;
                  case 12:
                	  if(FoDialog!=null) FoDialog.dismiss();
                	  if(FoNetOver!=null) FoNetOver.onOver(FaRet,FsRet); 
                  
             }   
             super.handleMessage(msg);   
        }   
   }; 
	
	@Override
	public void run()
	{
	   Message oMess;
	   switch(FiMode)
	   {
	    case 0:
	    	oMess=new Message();
			oMess.what=0;
			FoHandler.sendMessage(oMess);
			FsRet=NetProc.httpRead(FsUrl, FsData);
	    	FaRet=NetProc.StrToTvTable(FsRet);
	    	oMess=new Message();
			oMess.what=12;
			FoHandler.sendMessage(oMess);
			break;
	    case 1:
	    	oMess=new Message();
			oMess.what=1;
			FoHandler.sendMessage(oMess);
			FsRet=NetProc.httpGetFile(FsUrl, FsFileName, FoHandler);
			oMess=new Message();
			oMess.what=11;
			FoHandler.sendMessage(oMess);
			break;
	    case 2:
	    	oMess=new Message();
			oMess.what=0;
			FoHandler.sendMessage(oMess);
			FsRet=NetProc.sendFile(FsUrl, FsFileName);
			oMess=new Message();
			oMess.what=11;
			FoHandler.sendMessage(oMess);
			break;
	   }
	}
	
	
   protected void showWait(String sCaption,int iLayout) {
       Builder oBuilder = new Builder(this.FoContext);
       
       LayoutInflater oInflater = ((Activity)this.FoContext).getLayoutInflater();
       View oLayout = oInflater.inflate(iLayout,null,false);
       oBuilder.setView(oLayout);
       oBuilder.setTitle(sCaption);
       AlertDialog oDialog=oBuilder.create();
       oDialog.show();
       FoDialog=oDialog;
   }
   
   protected void showProgress(String sCaption,int iLayout) {
       Builder oBuilder = new Builder(this.FoContext);
       LayoutInflater oInflater = ((Activity)this.FoContext).getLayoutInflater();
       View oLayout = oInflater.inflate(iLayout,null,false);
       oBuilder.setView(oLayout);
       oBuilder.setTitle(sCaption);
       AlertDialog oDialog=oBuilder.create();
       FoText=(TextView)oLayout.findViewById(FiText);
       FoBar=(ProgressBar)oLayout.findViewById(FiText);
       FoBar.setMax(100);
       oDialog.show();
       FoDialog=oDialog;
   }

}
