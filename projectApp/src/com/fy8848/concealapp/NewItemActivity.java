package com.fy8848.concealapp;

import java.io.File;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.fy8848.adaptter.ListData;
import com.fy8848.adaptter.RowLayout;
import com.fy8848.adaptter.TreeAdapter;
import com.fy8848.concealbase.BaseActivity;
import com.fy8848.concealbase.HisStr;
import com.fy8848.procunit.CodeProc;
import com.fy8848.procunit.DateProc;
import com.fy8848.procunit.FileProc;
import com.fy8848.virtualtable.TvTable;

public class NewItemActivity extends BaseActivity {

	protected int FiListType=0;//0:project  1:unit  2:part  3:item
	protected String FsSavePath=Environment.getExternalStorageDirectory().getAbsolutePath() + "/projecVideo";
	protected String FsFile="";
	protected boolean FbPhoto=false;
	protected DataManager FoData;
	protected HisStr FoUnit;
	protected AlertDialog FoDialog;
	protected String FsRet="";
	protected TreeAdapter FoTree;
	protected LinearLayout FoLyData;
    protected LinearLayout FoLyList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_item);
		this.readSys();
		FoLyData=(LinearLayout)findViewById(R.id.lydata);
	    FoLyList=(LinearLayout)findViewById(R.id.lylist);
	    FoData=new DataManager(this);
		FoData.FsPath=FsSavePath;
		FoUnit=new HisStr();
		FoUnit.load(readConfig("prounit"));
		OnClickListener oOnClick=new OnClickListener()
		{
			public void onClick(View oView)
			{
				switch(oView.getId())
				{
				case R.id.ivproject:listProject();break;
				case R.id.ivvideo:takeVideo();break;
				case R.id.ivphoto:takePhoto();break;
				case R.id.ivbook:SelectPhoto();break;
				case R.id.ivback:finish();break;
				case R.id.ivsave:upload();break;
				//case R.id.ivitem:selectItem();break;
				case R.id.ivitemback:
					FoLyData.setVisibility(View.VISIBLE);
					FoLyList.setVisibility(View.GONE);
					break;
				case R.id.ivunit:listUnit();break;
				case R.id.ivpart:listPart();break;
				case R.id.ivitem:listItem();break;
				case R.id.ivsub:listSub();break;
				}
			}
		};
		ImageView oBtn=(ImageView)findViewById(R.id.ivproject);
		oBtn.setOnClickListener(oOnClick);
		oBtn.setOnClickListener(oOnClick);
		oBtn=(ImageView)findViewById(R.id.ivvideo);
		oBtn.setOnClickListener(oOnClick);
		oBtn=(ImageView)findViewById(R.id.ivphoto);
		oBtn.setOnClickListener(oOnClick);
		oBtn=(ImageView)findViewById(R.id.ivbook);
		oBtn.setOnClickListener(oOnClick);
		oBtn=(ImageView)findViewById(R.id.ivback);
		oBtn.setOnClickListener(oOnClick);
		oBtn=(ImageView)findViewById(R.id.ivsave);
		oBtn.setOnClickListener(oOnClick);
		oBtn=(ImageView)findViewById(R.id.ivitem);
		oBtn.setOnClickListener(oOnClick);
		oBtn=(ImageView)findViewById(R.id.ivitemback);
		oBtn.setOnClickListener(oOnClick);
		oBtn=(ImageView)findViewById(R.id.ivunit);
		oBtn.setOnClickListener(oOnClick);
		oBtn=(ImageView)findViewById(R.id.ivpart);
		oBtn.setOnClickListener(oOnClick);
		oBtn=(ImageView)findViewById(R.id.ivsub);
		oBtn.setOnClickListener(oOnClick);
		iniTree();
	}
	
	protected void iniTree()
	{
		FoTree=new TreeAdapter(this);
		int[] aID={R.id.tvname,R.id.tvtype};
		int[] aType={0,0};
		RowLayout oLayout=new RowLayout(R.layout.list_tree_item,0,aID,aType);
		oLayout.FiEmpty=R.id.tvempty;
		oLayout.FiIcon=R.id.ivicon;
		oLayout.FiOpen=R.drawable.treeopen;
		oLayout.FiClose=R.drawable.treeclose;
		oLayout.FiLeaf=R.drawable.leaf;
		FoTree.addLayout(oLayout);
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.new_item, menu);
		return true;
	}
	
	@Override
	protected void procMore(TvTable[] aTable,String sData)
	{
		
			/*
			CheckBox oBox=(CheckBox)findViewById(R.id.cbrightnow);
			if(oBox.isChecked())
			{
				String sTo=FsSavePath+"/upload/"+aTable[0].getError();
				sRet=FileProc.copyFile(FsFile,sTo);
				String sImg=sTo+".jpg";
				BitmapProc.getOne(FsFile,sTo);
			}
			else 
			{
				String sTo=FsSavePath+"/will/"+aTable[0].getError();
				sRet=FileProc.copyFile(FsFile,sTo);
			}*/
				
		
	}
	
	
	
	
	@Override
	protected void dialogList(String sSelect)
	{
		EditText oText=null;
		switch(FiListType)
		{
		  case 0:oText=(EditText)findViewById(R.id.etproject);
		   break;
		  case 1:oText=(EditText)findViewById(R.id.etunit);
		   break;
		  case 2:oText=(EditText)findViewById(R.id.etpart);
		   break;
		  case 3:oText=(EditText)findViewById(R.id.etitem);
		   break;
		  case 4:oText=(EditText)findViewById(R.id.etsub);
		   break;
		    
		}
		if(oText!=null) oText.setText(sSelect);
	}
	
	
	
	protected void listProject()
	{
		String sData=FoData.getProject();
		String[] aData=sData.split("\u0001");
		if(aData.length==1)
		{
			String[] aItem=aData[0].split("-");
			EditText oText=(EditText)findViewById(R.id.etproject);
			oText.setText(aItem[0]);
			return;
		}
		FiListType=0;
		if(sData.length()>0) this.showListDialog("请选择", sData);
		else showMess("没有工程标段信息，请首先进行数据同步",true);
	}
	
	
	protected void listUnit()
	{
		EditText oText=(EditText)findViewById(R.id.etproject);
		if(oText.getText().length()==0)
		{
			showMess("请选择标段",true);
			return;
		}
		String sFrag=CodeProc.fKeyValue(oText.getText().toString());
		String sData=FoData.getItem("",sFrag,"单位工程");
		String[] aData=sData.split("\u0001");
		if(aData.length==1)
		{
			oText=(EditText)findViewById(R.id.etunit);
			oText.setText(aData[0]);
			return;
		}
		FiListType=1;
		if(sData.length()>0) this.showListDialog("请选择", sData);
		else showMess("没有单位工程信息，请首先进行数据同步",true);
	}
	
	protected void listPart()
	{
		EditText oText=(EditText)findViewById(R.id.etproject);
		String sFrag=CodeProc.fKeyValue(oText.getText().toString());
		oText=(EditText)findViewById(R.id.etunit);
		if(oText.getText().length()==0)
		{
			showMess("请选择单位工程",true);
			return;
		}
		String sID=CodeProc.fKeyValue(oText.getText().toString());
		String sData=FoData.getItem(sID,sFrag,"分部工程");
		String[] aData=sData.split("\u0001");
		if(aData.length==1)
		{
			
			oText=(EditText)findViewById(R.id.etpart);
			oText.setText(aData[0]);
			return;
		}
		FiListType=2;
		if(sData.length()>0) this.showListDialog("请选择", sData);
		else showMess("没有分部工程信息，请首先进行数据同步",true);
	}
	
	protected void listItem()
	{
		EditText oText=(EditText)findViewById(R.id.etproject);
		String sFrag=CodeProc.fKeyValue(oText.getText().toString());
		oText=(EditText)findViewById(R.id.etpart);
		if(oText.getText().length()==0)
		{
			showMess("请选择分部工程",true);
			return;
		}
		String sID=CodeProc.fKeyValue(oText.getText().toString());
		String sData=FoData.getItem(sID,sFrag,"分项工程");
		String[] aData=sData.split("\u0001");
		if(aData.length==1)
		{
			
			oText=(EditText)findViewById(R.id.etitem);
			oText.setText(aData[0]);
			return;
		}
		FiListType=3;
		if(sData.length()>0) this.showListDialog("请选择", sData);
		else showMess("没有分项工程信息，请首先进行数据同步",true);
	}
	
	protected void listSub()
	{
		EditText oText=(EditText)findViewById(R.id.etproject);
		String sFrag=CodeProc.fKeyValue(oText.getText().toString());
		oText=(EditText)findViewById(R.id.etitem);
		if(oText.getText().length()==0)
		{
			showMess("请选择分项工程",true);
			return;
		}
		String sID=CodeProc.fKeyValue(oText.getText().toString());
		String sData=FoData.getItem(sID,sFrag,"子分项工程");
		String[] aData=sData.split("\u0001");
		if(aData.length==1)
		{
			
			oText=(EditText)findViewById(R.id.etsub);
			oText.setText(aData[0]);
			return;
		}
		FiListType=4;
		if(sData.length()>0) this.showListDialog("请选择", sData);
		else showMess("没有子分项工程信息，请首先进行数据同步",true);
	}
	
	
	
	protected void takeVideo()
	{
		Intent oInt=new Intent();
		oInt.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
		startActivityForResult(oInt, 0);
		FbPhoto=false;
	}
	
	protected void takePhoto()
	{
		//Intent oInt=new Intent();
		//oInt.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
		//startActivityForResult(oInt, 3);
		//FbPhoto=false;
		
		File oFile=new File(FsSavePath+"/photo.jpg");
		if(oFile.exists()) oFile.delete();
		Uri oUri=Uri.fromFile(oFile);
	    Intent oInt = new Intent("android.media.action.IMAGE_CAPTURE");  
		oInt.putExtra(MediaStore.EXTRA_OUTPUT, oUri);
        startActivityForResult(oInt, 3);//0为拍照
	}
	
	protected void SelectPhoto()
	{
		//Intent intent = new Intent(Intent.ACTION_PICK);
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);  
	    intent.setType("video/*");//相片类型  
	    startActivityForResult(intent, 1); 
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     
		  if(resultCode!=0)
	        if (requestCode == 0||requestCode == 1) {
	             FsFile=getPath(data.getData(),"");
	             File oFile=new File(FsFile);
	             FbPhoto=oFile.exists();
	        }
	        else
	        if (requestCode == 3)
	        {
	        	FsFile=FsSavePath+"/photo.jpg";
	        	File oFile=new File(FsFile);
	            FbPhoto=oFile.exists();
	        }
	        
	}
	
	private  String getPath(Uri uri, String selection) {
        String sPath = "";
        //
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                sPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return sPath;
    }
	
	protected void upload()
	{
		int[] aID={R.id.etproject,R.id.etunit};
		String[] aName={"工程标段","单位工程"};
		EditText oText;
		int i;
		for(i=0;i<aID.length;i++)
			{
			  oText=(EditText)findViewById(aID[i]);
			  if(oText.getText().toString().length()==0)
			  {
				  showMess(aName[i]+"数据不能为空，请输入",true);
				  return;
			  }
				  
			}
		if(!FbPhoto)
		{
			showMess("还没有拍摄视频",true);
			return;
		}
		showDialog("确认信息","将要保存数据，是否确认？","确定","取消",0);
			
	}
	
	
	Handler FoHandler = new Handler() {  
		@Override
        public void handleMessage(Message msg) {   
             switch (msg.what) {   
                  case 0: //显示等待对话框，读取服务器
                	 if(FoDialog!=null) FoDialog.dismiss(); 
                	 //writeConfig("prounit",FoUnit.get(10));
                	 setResult(1);
                	 finish();
                	 break;
                  case 1:
                	  if(FoDialog!=null) FoDialog.dismiss(); 
                	  showMess(FsRet,true);
                	 break;
                  
                  
             }   
             super.handleMessage(msg);   
        }   
   }; 
	
	protected void saveData()
	{
		Thread oProc=new Thread(){
			public void send(int iNo)
			{
				Message oMess=new Message();
				oMess.what=iNo;
				FoHandler.sendMessage(oMess);
			}
			
			@Override
			public void run()
			{
				FsRet=FoData.addVideo();
				if(FsRet.length()>0) send(1);
				else
				{
					FsRet=FileProc.copyFile(FsFile, FsSavePath+"/"+FoData.FsFile);
					if(FsRet.length()>0) send(1);
					else send(0);
					
				}
			}
		};
		oProc.start();
		this.FoDialog=showDialog("请等待","","","",R.layout.dialog_wait);
	}
	
	@Override
	protected  void dialogOk()
	{
		StringBuilder oStr=new StringBuilder();
		EditText oText;
		oText=(EditText)findViewById(R.id.etproject);
		FoData.FsProject=oText.getText().toString();
		oText=(EditText)findViewById(R.id.etitem);
		FoData.FsItem=CodeProc.fKeyShow(oText.getText().toString());
		FoUnit.add(oText.getText().toString());
		oText=(EditText)findViewById(R.id.etunit);
		FoData.FsUnit=CodeProc.fKeyShow(oText.getText().toString());
		oText=(EditText)findViewById(R.id.etpart);
		FoData.FsPart=CodeProc.fKeyShow(oText.getText().toString());
		oText=(EditText)findViewById(R.id.etsub);
		FoData.FsSub=CodeProc.fKeyShow(oText.getText().toString());
		oText=(EditText)findViewById(R.id.etmark);
		FoData.FsMark=oText.getText().toString();
		
		FoData.FsUser=FoSys.get("user");
		FoData.FsDate=DateProc.getTimeStr("yyyy-MM-dd HH:mm:ss");
		FoData.FsFile=FoData.FsUser+"_"+DateProc.getTimeStr("yyyyMMddHHmmss")+this.FsFile.substring(FsFile.length()-4);
		FoData.FsStatus="未上传";
		saveData();
		
	}
	
	
	protected void setItemValue(String sPk)
	{
		  String sData=FoData.getItem(sPk);
		  String[] aData=sData.split("\u0001");
		  EditText oText;
		  if(aData.length>0)
		  {
			  oText=(EditText)findViewById(R.id.etunit);
			  oText.setText(aData[0]);
			  
		  }
		  if(aData.length>1)
		  {
			  oText=(EditText)findViewById(R.id.etpart);
			  oText.setText(aData[1]);
			  
		  }
		  
		  if(aData.length>2)
		  {
			  oText=(EditText)findViewById(R.id.etitem);
			  oText.setText(aData[2]);
			  
		  }
		  if(aData.length>3)
		  {
			  oText=(EditText)findViewById(R.id.etsub);
			  oText.setText(aData[3]);
			  
		  }
		  FoLyData.setVisibility(View.VISIBLE);
		  FoLyList.setVisibility(View.GONE);
	}
	
	protected void selectItem()
	{
		EditText oText=(EditText)findViewById(R.id.etproject);
	    String sPro=oText.getText().toString();
	    if(oText.length()==0) 
	    {
	    	showMess("请首先选择标段信息",true);
	    	return;
	    }
	    FoData.getItem(sPro,FoTree);
	    
	   
	    FoLyList.setVisibility(View.VISIBLE);
	    FoLyData.setVisibility(View.GONE);
	    ListView oList=(ListView)findViewById(R.id.lvtree);
	    
	    oList.setAdapter(FoTree);
	    FoTree.updateShow();
	    oList.setOnItemClickListener(new OnItemClickListener(){
    		public void onItemClick(AdapterView<?> av, View v, int iPosition, long iOther)
    		{
    			if(!FoTree.clickTree(iPosition))
    				{
    				  ListData oData=FoTree.getData(iPosition);
    				  setItemValue(oData.FsPk);
    					  
    				}
    				
    		}
    	});
	    
	    oList.setOnItemLongClickListener(new OnItemLongClickListener(){  
            @Override  
            public boolean onItemLongClick(AdapterView<?> aAdapter, View oView,  
                    int iPosition, long iID) {  
            	ListData oData=FoTree.getData(iPosition);
				setItemValue(oData.FsPk);
                return false;  
            }  
        });  
	    
	}

}
