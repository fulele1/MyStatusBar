package com.fy8848.concealapp;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;

import com.fy8848.adaptter.ListData;
import com.fy8848.concealbase.BaseListActivity;
import com.fy8848.procunit.AssistProc;

public class UpingActivity extends BaseListActivity {
    protected String FsPath="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uping);
		int[] aID={R.id.tvfile,R.id.pbper,R.id.tvper};
		int[] aType={0,2,0};
		this.iniList(R.layout.list_data, aID, aType, R.id.lvdata);
		FsPath=Environment.getExternalStorageDirectory().getAbsolutePath() + "/projecVideo/upload";
		iniButton();
		openView(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.uping, menu);
		return true;
	}
	
	@Override
	public void openView(boolean bFirst)
	{
		File oFile= new File(FsPath);	
	    File[] oFiles =null;
	    String sFile="";
	    FoAdapter.clear();
	    String[] aData={"","0","0%"};
		if(oFile!=null) oFiles=oFile.listFiles();
		int i;
		for(i=0;i<oFiles.length;i++)
			if(oFiles[i].isFile())
		    {
			   sFile=oFiles[i].getName();
			   aData[0]=sFile;
			   FoAdapter.add(sFile, aData);
		    }
		FoList.setAdapter(FoAdapter);
		FoAdapter.notifyDataSetChanged();
		if(oFiles.length==0) showMess("没有未上传文件",true);
	}
	
	@Override
	protected void procReceive(Context context,Intent intent)
	{
		String sFile=intent.getStringExtra("file");
		int iPercent=intent.getIntExtra("percent", 0);
		ListData oItem=FoAdapter.getData(sFile);
		if(oItem!=null)
		{
			
			oItem.FaData[1]=Integer.toString(iPercent);
			oItem.FaData[2]=Integer.toString(iPercent)+"%";
			FoAdapter.notifyDataSetChanged();
		}
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		reg();
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		unReg();
		//FoBlue.close();
	}

}
