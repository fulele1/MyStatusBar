package com.fy8848.concealapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.fy8848.concealbase.BaseActivity;

public class WebActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		this.readSys();
		WebView oWeb=(WebView)findViewById(R.id.wvmain);
		if(oWeb!=null)
		{
			oWeb.setWebChromeClient(new WebChromeClient(){
				@Override
				public boolean onJsAlert(WebView view,String sUrl,String sMessage,final JsResult result)
				{
					if(sMessage.compareTo("上传影像")==0) UpVideo();
					if(sMessage.compareTo("未上传列表")==0) WillList();
					if(sMessage.compareTo("退出")==0) finish();
					result.confirm();
					return true;
				}
			});
		}
		WebSettings oSet=oWeb.getSettings();
		oSet.setJavaScriptEnabled(true);
		String sUrl=FoSys.get("server")+"/mmain.html?token="+FoSys.get("token")+"&user="+FoSys.get("user")
				+"&username="+FoSys.get("userName")+"&company="+FoSys.get("company")+"&department="+FoSys.get("department");
		oWeb.loadUrl(sUrl);
		showMess(sUrl,true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.web, menu);
		return true;
	}
	
	protected void UpVideo()
	{
		Intent oInt=new Intent();
		oInt.setClass(this, ListActivity.class);
		startActivity(oInt);
	}
	
	protected void WillList()
	{
		Intent oInt=new Intent();
		oInt.setClass(this, NewItemActivity.class);
		startActivity(oInt);
	}
	
	

}
