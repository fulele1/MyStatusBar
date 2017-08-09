package com.fy8848.concealapp;

import java.io.File;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.fy8848.concealbase.BaseActivity;

public class MainMenuActivity extends BaseActivity {
   protected String FsPath="";
   protected DataManager FoData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		FsPath=Environment.getExternalStorageDirectory().getAbsolutePath() + "/projecVideo";
		this.readSys();
		iniButton();
		checkPath();
		FoData=new DataManager(this);
		FoData.FsPath=FsPath;
		FoData.FsUser=FoSys.get("user");
		String sRet=FoData.createTable();
		if(sRet.length()>0) showMess(sRet,true);
		startUpload();
		iniTitle();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	
	public void iniButton()
	{
		OnClickListener oOnClick=new OnClickListener()
		{
			@Override
			public void onClick(View oView)
			{
				switch(oView.getId())
				{
				 case R.id.ivvideolist:openList();break;
				 case R.id.ivvideocapture:openNew();break;
				 case R.id.ivvideolog:openLog();break;
				 case R.id.ivvideoupload:openUping();break;
				 case R.id.ivvideoset:sysSet();break;
				 case R.id.ivvideoquit:
					 FiDialogType=2;
					 showDialog("退出确认","将要退出系统，是否确认？","确定","取消",0);
				     break;
				 
				
				 
				 
				}
			}
		};
		int[] aID={R.id.ivvideolist,R.id.ivvideocapture,R.id.ivvideolog,R.id.ivvideoupload,R.id.ivvideoset,R.id.ivvideoquit};
		ImageView oBtn;
		int i;
		for(i=0;i<aID.length;i++)
		{
		 oBtn=(ImageView)findViewById(aID[i]);
		 if(oBtn!=null) oBtn.setOnClickListener(oOnClick);
		}
		
		
	}
	
	public void quit()
	{
		File oFile= new File(FsPath+"/upload");	
	    File[] oFiles =null;
		if(oFile!=null)
		{
			oFiles=oFile.listFiles();
			if(oFiles.length==0) stopUpload();
		}
		finish();
	}
	
	public void openLog()
	   {
		   Intent oInt=new Intent(this,UploadActivity.class);
		   startActivity(oInt);
	   }
	
	public void sysSet()
	{
		Intent oInt=new Intent();
		oInt.setClass(this, SetActivity.class);
		startActivityForResult(oInt,1);
	}
	
	protected void iniTitle()
	{
		TextView oTv=(TextView)findViewById(R.id.tvuser);
		if(oTv!=null)
			oTv.setText("用户："+FoSys.get("user"));
	}
	
	 public void startUpload()
	   {
	     Intent oInt=new Intent(this,SendService.class);
		 startService(oInt);
	   }
	   public void stopUpload()
	   {
	     Intent oInt=new Intent(this,SendService.class);
	      stopService(oInt);
	   }
	   
	   protected void checkPath()
		{
			try
			{
			String sPath=Environment.getExternalStorageDirectory().getAbsolutePath() + "/projecVideo";
			File oFile=new File(sPath);
			if(!oFile.exists())	oFile.mkdir();
			oFile=new File(sPath+"/upload");
			if(!oFile.exists())	oFile.mkdir();
			}
			catch(Exception E)
			{
				showMess("新建文件夹错误！",true);
			}
		}
	   
	   @Override
		protected  void dialogOk()
		{
			
			switch(FiDialogType)
			{
			  case 2:quit();break;
			
			
			}
		}
	   
	   public void openUping()
	   {
		   Intent oInt=new Intent(this,UpingActivity.class);
		   startActivity(oInt);
	   }
	   
	   public void openList()
	   {
		    Intent oInt=new Intent();
		    oInt.setClass(this, ListActivity.class);
		    startActivity(oInt);
	   }
	   
	   public void openNew()
	   {
		    Intent oInt=new Intent();
			oInt.setClass(this, NewItemActivity.class);
			startActivityForResult(oInt,0);
	   }

}
