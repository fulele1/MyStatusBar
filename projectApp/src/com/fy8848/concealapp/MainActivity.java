package com.fy8848.concealapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.fy8848.concealbase.BaseActivity;
import com.fy8848.procunit.CodeProc;


public class MainActivity extends BaseActivity {

	protected int FiError=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ImageView oLogin=(ImageView)findViewById(R.id.btnlogin);
		oLogin.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				login();
			}
		});
		String sUser=readConfig("user");
		String sPass=readConfig("password");
		EditText oText=(EditText)findViewById(R.id.etuser);
		oText.setText(sUser);
		oText=(EditText)findViewById(R.id.etpass);
		oText.setText(sPass);
	}
	
	protected void login()
	{
		this.readSys();
		EditText oText=(EditText)findViewById(R.id.etuser);
		String sUser=oText.getText().toString();
		oText=(EditText)findViewById(R.id.etpass);
		String sPass=oText.getText().toString();
		
		
		if(FoSys.get("user").length()==0&&FoSys.get("pass").length()==0)
		{
			FiDialogType=0;
			showDialog("提示信息","你好，你首次登录，请先进行系统设置！","确定","",0);
			return;
		}
		
		if(sUser.length()==0)
		{
			showMess("请输入用户称呼",true);
			return;
		}
		String sMd5Pass=CodeProc.md5(sPass).toLowerCase();
		
		if(sUser.compareTo(FoSys.get("user"))==0&&sMd5Pass.compareTo(FoSys.get("pass").toLowerCase())==0)
		{
			writeConfig("passwork",sPass);
		 	Intent oInt=new Intent();
		    oInt.setClass(this, MainMenuActivity.class);
		    startActivity(oInt);
		}
		else 
			{
			   FiDialogType=1;
			   FiError++;
			   showDialog("错误信息","登录信息错误！","确定","",0);
			}
		
		
	}
	@Override
	protected  void dialogOk()
	{
		Intent oInt;
		switch(FiDialogType)
		{
		case 0:oInt=new Intent();
	          oInt.setClass(this, SetActivity.class);
	          startActivityForResult(oInt,0);
	          break;
		case 1:if(FiError>=5) 
		       {
			    oInt=new Intent();
	            oInt.setClass(this, SetActivity.class);
	            startActivityForResult(oInt,0);
		       }
		break;
			
		}
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     
		//showMess(Integer.toString(requestCode)+"-"+Integer.toString(resultCode),true);
		if(resultCode==1)
		{
			Intent oInt=new Intent();
	        oInt.setClass(this, ListActivity.class);
	        startActivity(oInt);	
		}
	        
	}

}
