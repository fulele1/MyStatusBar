package com.fy8848.concealapp;

import java.io.File;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fy8848.adaptter.ListData;
import com.fy8848.concealbase.BaseListActivity;
import com.fy8848.dict.Dict;
import com.fy8848.procunit.AssistProc;
import com.fy8848.procunit.BitmapProc;
import com.fy8848.procunit.FileProc;
import com.fy8848.virtualtable.TvTable;

public class ListActivity extends BaseListActivity {


	protected int FiAskType=0;
	protected Dict FoDict=new Dict();
	public String FsPath="";
	public AlertDialog FoDialog;
	public String FsRet="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		FsPath=Environment.getExternalStorageDirectory().getAbsolutePath() + "/projecVideo";
		this.readSys();
		iniButton();
		FoData.FsPath=FsPath;
		FoData.FsUser=FoSys.get("user");
		String sRet=FoData.createTable();
		if(sRet.length()>0) showMess(sRet,true);
		int[] aID={R.id.tvname,R.id.tvitem,R.id.tvdate,R.id.ivicon,R.id.tvtype,R.id.tvunit,R.id.tvpart,R.id.tvsub};
		int[] aType={0,0,0,1,0,0,0,0};
		iniList(R.layout.list_video,aID,aType,R.id.lvdata);
		FoAdapter.addImage("未上传", R.drawable.willup);
		FoAdapter.addImage("正在上传", R.drawable.uping);
		FoAdapter.addImage("已上传", R.drawable.haveup);
		FoAdapter.FiCheckImageID=R.id.ivsel;
		FbNetData=false;
		this.FsSelectSql="select vlid,vlname,vlitem,vldate,vlstatus,vltype,vlunit,vlpart,vlsub from videolist order by vldate desc";
		this.FsCountSql="select count(*) from videolist ";
		openView(true);
		//checkNoUpload();
		
		ListView oList=(ListView)findViewById(R.id.lvdata);
		oList.setOnItemClickListener(new OnItemClickListener(){
    		public void onItemClick(AdapterView<?> av, View v, int iPosition, long iOther)
    		{
    			boolean bSel=FoAdapter.isChecked(iPosition);
    			bSel=!bSel;
    			FoAdapter.setSelect(iPosition, bSel);
    			FoAdapter.notifyDataSetChanged();
    				
    		}
    	});
		
	}
	
	protected boolean checkSysData()
	{
		if(FoSys.get("server").length()==0||FoSys.get("user").length()==0||FoSys.get("pass").length()==0)
			{
			  showMess("请首先设置系统基本参数",true);
			  return false;
			}
		return true;
	}
	
	
	
	protected void checkNoUpload()
	{
		File oFile= new File(FsPath+"/upload");	
		if(oFile.exists())
		{
	      File[] oFiles=oFile.listFiles();
	      if(oFiles.length>0)
	      {
	         String sMess="有"+Long.toString(oFiles.length)+"个文件未上传,请检查用户授权。";
	         showMess(sMess,true);
	      }
		}
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
				 case R.id.ivupload:if(!checkSysData()) return;
					            upload();
					            break;
				 case R.id.ivdelete:if(!checkSysData()) return;
					                delete();
					                break;
				 case R.id.ivpreview:if(!checkSysData()) return;
					                 preview();
					                 break;
				 case R.id.ivvideo:if(!checkSysData()) return;
					             newOne();
					             break;
				 case R.id.ivback:finish();break;
				}
			}
		};
		int[] aID={R.id.ivvideo,R.id.ivdelete,R.id.ivpreview,R.id.ivback,R.id.ivupload};
		ImageView oBtn;
		int i;
		for(i=0;i<aID.length;i++)
		{
		 oBtn=(ImageView)findViewById(aID[i]);
		 if(oBtn!=null) oBtn.setOnClickListener(oOnClick);
		}
	}
	
	
	protected void upload()
	{
		
		String[] aSel=getSelectToUp();
		
		if(aSel.length==0)
		{
			showMess("请选择将要上传的未上传的数据",true);
			return;
		}
		FiDialogType=1;
		showDialog("确认信息","有"+Integer.toString(aSel.length)+"条数据将要进入上传队列，是否确定？","确定","取消",0);
	}
	
	protected String getFrame(String sFile,String sTo)
	{
		try
		{
			Bitmap oBmp=BitmapProc.getFrame(sFile);
			if(oBmp==null) return "获取视频截图错误";
			oBmp=BitmapProc.scalePhoto(oBmp, 320, 240);
			if(oBmp==null) return "获取视频缩略图错误";
			return BitmapProc.bitmpSaveToJpg(oBmp, sTo);
			
		}
		catch(Exception E)
		{
			return E.getMessage();
		}
	}
	
	protected String saveOther(String sFile,String sTo)
	{
		try
		{
			Bitmap oBmp = BitmapFactory.decodeFile(sFile);
			oBmp=BitmapProc.scalePhoto(oBmp, 300);
			BitmapProc.bitmpSaveToJpg(oBmp, sTo);
			return "";
			
		}
		catch(Exception E)
		{
			return E.getMessage();
		}
	}
	
	protected void doUpload()
	{
		
		Thread oUp=new Thread()
		{
			public void procError()
			{
				Message oMess=new Message();
				oMess.what=1;
				FoHandler.sendMessage(oMess);
			}
			
			@Override
			public void run()
			{
				
				String[] aSel=getSelectToUp();
				int i;
				for(i=0;i<aSel.length;i++)
				{
					FsRet=FoData.getVideo(Integer.parseInt(aSel[i]));
					if(FsRet.length()>0) {procError();return;}
					
					String sExt=FoData.FsFile.substring(FoData.FsFile.length()-3).toLowerCase();
					String sFrom=FsPath+"/"+FoData.FsFile;
					String sTo=FsPath+"/upload/"+FoData.FsFile;
					if(sExt.compareTo("jpg")==0)
						sTo=FsPath+"/upload/"+FoData.FsFile.substring(0,FoData.FsFile.length()-4)+".tmp";
					
					FsRet=FileProc.copyFile(sFrom, sTo);
					if(FsRet.length()>0) {procError();return;}
					
					
					
					
					if(sExt.compareTo("mp4")==0||sExt.compareTo("3gp")==0)
					{
					  sTo=sTo.substring(0,sTo.length()-4)+".jpg";
					  FsRet=getFrame(sFrom,sTo);
					  if(FsRet.length()>0) {procError();return;}
					}
					
					if(sExt.compareTo("jpg")==0)
					{
					   sTo=FsPath+"/upload/"+FoData.FsFile;
					   FsRet=saveOther(sFrom,sTo);
						  if(FsRet.length()>0) {procError();return;}
					}
					
					FsRet=FoData.createPackage();
					if(FsRet.length()>0) {procError();return;}
					
					FsRet=FoData.setUp(aSel[i]);
					if(FsRet.length()>0) {procError();return;}
					
				}
				Message oMess=new Message();
				oMess.what=0;
				FoHandler.sendMessage(oMess);
				
			}
		};
		oUp.start();
		FoDialog=showDialog("请等待","","","",R.layout.dialog_wait);
	}
	
	protected void delete()
	{
		String[] aSel=FoAdapter.getSelect();
		if(aSel.length==0)
		{
			showMess("请选择将要删除的数据",true);
			return;
		}
		this.showDialog("删除确认", "将要删除数据，是否确认？", "确定", "取消", 0);
		FiDialogType=0;
	}
	
	public void preview()
	{
		String[] aSel=FoAdapter.getSelect();
		if(aSel.length!=1) 
			{
			   showMess("请选择一条数据预览",true);
			   return;
			}
		String sRet=FoData.getVideo(Integer.parseInt(aSel[0]));
		if(sRet.length()>0)
		{
			showMess(sRet,true);
			return;
		}
		else
		{
			String sFile=FoData.FsPath+"/"+FoData.FsFile;
			String sExt=FoData.FsFile.substring(FoData.FsFile.length()-3).toLowerCase();
			File oFile=new File(sFile);
			AssistProc.log("error",sFile);
			if(oFile.exists()&oFile.isFile())
			{
				Uri uri = Uri.parse("file://"+sFile);
				Intent intent = new Intent(Intent.ACTION_VIEW);  
				if(sExt.compareTo("mp4")==0||sExt.compareTo("3gp")==0)
		          intent.setDataAndType(uri, "video/*");
		        else intent.setDataAndType(uri, "image/*");
		        startActivity(intent);
			}
			else showMess("视频文件不存在",true);
		}
	}
	
	
	
	public void newOne()
	{
		Intent oInt=new Intent();
		oInt.setClass(this, NewItemActivity.class);
		startActivityForResult(oInt,0);
	}
	
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.list, menu);
		return true;
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
		}
		else showMess("返回数据错误",true);
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     
		//showMess(Integer.toString(requestCode)+"-"+Integer.toString(resultCode),true);
		if(resultCode==1)
	        switch(requestCode)
	        {
	        case 0: this.openView(true);break;
	        
	        }
	        
	}
	@Override
	protected  void dialogOk()
	{
		String sRet="";
		switch(FiDialogType)
		{
		case 0:
			sRet=FoData.delVideo(FoAdapter.getSelect());
			if(sRet.length()==0) openView(true);
			else showMess(sRet,true);
			break;
		case 1:doUpload();break;
		
		
		}
	}
	
	Handler FoHandler = new Handler() {  
		@Override
        public void handleMessage(Message msg) {   
             switch(msg.what) {   
                  case 0: //显示等待对话框，读取服务器
                	  if(FoDialog!=null) FoDialog.dismiss();
                	  openView(true);
                	  FoDialog=null;
                	 break;
                  case 1:
                	  if(FoDialog!=null) FoDialog.dismiss();
                	  showMess(FsRet,true);
                	  FoDialog=null;
                	 break;
                  
                  
             }   
             super.handleMessage(msg);   
        }   
   }; 
   
  
   
   public void sameData()
   {
	   String sUrl=url("clientData","");
	   doAction(sUrl,"",true);
   }
   
   
   
   
   
   public String[] getSelectToUp()
   {
   	
   	ArrayList<String> oSel=new ArrayList<String>();
   	int i;
   	String[] aSel=FoAdapter.getSelect();
   	ListData oItem=null;
   	for(i=0;i<aSel.length;i++)
   	{
   	  oItem=FoAdapter.getData(aSel[i]);
   	  if(oItem!=null)
   		  if(oItem.FaData[3].compareTo("未上传")==0) oSel.add(aSel[i]);
   	}
   	String[] aRet=new String[oSel.size()];
	oSel.toArray(aRet);
	return aRet;
   	
   }

}
