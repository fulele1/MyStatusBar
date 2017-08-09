package com.fy8848.concealapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.fy8848.adaptter.TableAdapter;
import com.fy8848.concealbase.BaseListActivity;

public class UploadActivity extends BaseListActivity {
	
	
	protected ListView FoList;
	protected String FsFile="";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload);
		
		int[] aID={R.id.tvname,R.id.tvtime};
		int[] aType={0,0};
		iniList(R.layout.list_upload,aID,aType,R.id.lvdata);
		this.FsCountSql="select count(*) from errorlog";
		this.FsSelectSql="select id,logtext,logtime from errorlog order by logtime desc";
		if(FoList!=null) FoList.setAdapter(FoAdapter);
		iniButton();
		openView(true);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.upload, menu);
		return true;
	}
	
	
	
	
	
	
   
   public void quit()
   {
	    finish();
   }

}
