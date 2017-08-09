package com.fy8848.concealapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.fy8848.concealbase.BaseActivity;
import com.fy8848.procunit.AssistProc;
import com.fy8848.procunit.CodeProc;
import com.fy8848.virtualtable.TvTable;

public class SetActivity extends BaseActivity {
    protected boolean FbSet=false;
    protected String FsCheck="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);
		FoSys.put("server", "");
		FoSys.put("user", "");
		FoSys.put("pass", "");
		FoSys.put("rightcode", "");
		
		this.readSys();
		if(FoSys.get("user").length()==0)
		{
	    	DataManager FoData=new DataManager(this);
		    FoData.createTable();
		}
		OnClickListener oClick=new OnClickListener(){
			@Override
			public void onClick(View oView)
			{
				switch(oView.getId())
				{
				case R.id.ivback:
					     if(FbSet) setResult(1);
					     finish();break;
				case R.id.ivok:saveSet();break;
				case R.id.ivsame:willSame();break;
				};
			}
		};
		ImageView oBtn=(ImageView)findViewById(R.id.ivok);
		oBtn.setOnClickListener(oClick);
		oBtn=(ImageView)findViewById(R.id.ivback);
		oBtn.setOnClickListener(oClick);
		oBtn=(ImageView)findViewById(R.id.ivsame);
		oBtn.setOnClickListener(oClick);
		EditText oText=(EditText)findViewById(R.id.etserver);
		oText.setText(FoSys.get("server"));
		oText=(EditText)findViewById(R.id.etuser);
		oText.setText(FoSys.get("user"));
		
	}
	
	
	public void saveSet()
	{
		String sServer="",sUser="",sPass="";
		EditText oText=(EditText)findViewById(R.id.etserver);
		sServer=oText.getText().toString();
		if(sServer.length()==0)
		{
			showMess("请输入服务器地址",true);
			return;
		}
		sServer=sServer.toLowerCase();
		if(sServer.substring(0,7).compareTo("http://")!=0) sServer="http://"+sServer;
		oText=(EditText)findViewById(R.id.etuser);
		sUser=oText.getText().toString();
		if(sUser.length()==0)
		{
			showMess("请输入用户名",true);
			return;
		}
		oText=(EditText)findViewById(R.id.etpass);
		sPass=CodeProc.md5(oText.getText().toString());
		
		FoSys.put("server",sServer);
		FoSys.put("user",sUser);
		FoSys.put("pass",sPass);
		FiDialogType=0;
		showDialog("确认信息","将要保存配置，是否确定？","确定","取消",0);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.set, menu);
		return true;
	}
	
	@Override
	protected  void dialogOk()
	{
		
		switch(FiDialogType)
		{
		case 0:
			this.writeSys();
			showMess("配置信息已经保存",true);
			FbSet=true;
			break;
		case 1:sameData();break;
		
		}
	}
	
	
	
	@Override
	protected void procMore(TvTable[] aTable,String sData)
	{
		String sRet="";
		if(aTable.length==2)
		{
			DataManager oData=new DataManager(this);
			sRet=oData.addProject(aTable[0]);
			if(sRet.length()>0)
			{
				showMess(sRet,true);
				return;
			}
			sRet=oData.addItem(aTable[1]);
			if(sRet.length()>0)
			{
				showMess(sRet,true);
				return;
			}
			showMess("数据同步完成",true);
			writeConfig("checkcode",FsCheck);
		}
		else showMess("返回数据错误",true);
	}
	
	public void willSame()
	{
		if(FoSys.get("server").length()==0)
		{
			showMess("服务器数据错误，不能同步数据",true);
			return;
		}
		if(FoSys.get("user").length()==0)
		{
			showMess("用户名错误，不能同步数据",true);
			return;
		}
		FiDialogType=1;
		showDialog("确认信息","将要同步服务器数据，是否确定？","确定","取消",0);
	}
	
	public void sameData()
	   {
		   FsCheck=CodeProc.RandomStr(10);
		   AssistProc.log("error",FsCheck);
		   String sPass=FoSys.get("pass");
		   String sUser=FoSys.get("user");
		   String sUrl=url("clientData","");
		   doAction(sUrl,"",true);
	   }

}
