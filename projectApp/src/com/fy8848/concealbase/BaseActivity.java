package com.fy8848.concealbase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fy8848.concealapp.R;
import com.fy8848.procunit.OnNetOver;
import com.fy8848.procunit.OnUpDownOver;
import com.fy8848.procunit.SendThread;
import com.fy8848.virtualtable.TvTable;





public class BaseActivity extends Activity {
	
	protected int FiDialogType=0;//�Ի���������
	protected String FsAppPath="";
	protected String[] FaDialogList={};//�Ի��б���б������
	protected EditText FoInput=null;
	protected HashMap<String,String> FoSys=new HashMap<String,String>();
	protected View FoDialogView=null;
	protected BroadcastReceiver FoReceiver;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ��������
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        FoSys.put("server", "");//��������ַ
        FoSys.put("user", "");//����
        FoSys.put("pass", "");//����
       
	}
	
	
	//��ȡϵͳ��������Ϣ  
	protected void readSys()
	{
		String[] aName=new String[FoSys.keySet().size()];
		FoSys.keySet().toArray(aName);
		String[] aValue=new String[aName.length];
		if(aName.length>0)
		{
			readConfig(aName,aValue);
			int i;
			for(i=0;i<aName.length;i++)
				FoSys.put(aName[i], aValue[i]);
		}
	}
	
	//дϵͳ��������Ϣ
	protected void writeSys()
	{
		String[] aName=new String[FoSys.keySet().size()];
		FoSys.keySet().toArray(aName);
		String[] aValue=new String[aName.length];
		int i;
		for(i=0;i<aName.length;i++)
			aValue[i]=FoSys.get(aName[i]);
		writeConfig(aName,aValue);
	}
	
	//��ȡ������Ϣ
	protected String readConfig(String sName)
	{
		SharedPreferences oConfig = getSharedPreferences("config", Activity.MODE_PRIVATE);
		return oConfig.getString(sName, "");
		
	}
	
	protected void readConfig(String[] aName,String[] aValue)
	{
		SharedPreferences oConfig = getSharedPreferences("config", Activity.MODE_PRIVATE);
		int i;
		for(i=0;i<aName.length;i++)
			aValue[i]=oConfig.getString(aName[i], "");
	}
	
	protected void writeConfig(String sName,String sValue)
	{
		SharedPreferences oConfig = getSharedPreferences("config", Activity.MODE_PRIVATE);
		Editor oEdit = oConfig.edit();//��ñ༭��   
		oEdit.putString(sName, sValue);     
		oEdit.commit();//�ύ���� 
	}
	
	protected void writeConfig(String[] aName,String[] aValue)
	{
		int i;
		SharedPreferences oConfig = getSharedPreferences("config", Activity.MODE_PRIVATE);
		Editor oEdit = oConfig.edit();//��ñ༭��
		for(i=0;i<aName.length;i++)
			oEdit.putString(aName[i], aValue[i]); 
		oEdit.commit();//�ύ���� 
	}
	
	//�Ի��򵥻�ȷ����ť����
	protected  void dialogOk()
	{
		
	}
	//�Ի��򵥻�ȡ����ť����
	protected void dialogCancel()
	{
		
	}
	//�Ի����б�ѡ���Ĵ���
	protected void dialogList(String sSelect)
	{
		
	}
	//�����Ĵ���
	protected void dialogInput(String sValue)
	{
		
	}
	
	//����Ի���
	protected AlertDialog inputDialog(String sCaption,String sValue)
	{
		Builder oBuilder = new Builder(this);
		oBuilder.setTitle(sCaption);
  	    FoInput=new EditText(this);
  	    oBuilder.setView(FoInput);
  	    FoInput.setText(sValue);
  	    oBuilder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {  
		  @Override
	     public void onClick(DialogInterface dialog, int which) {
			  String sInput=FoInput.getText().toString();
			  BaseActivity.this.dialogInput(sInput);
			  dialog.dismiss();
	       }
	     });
  	    oBuilder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {   
     	  @Override
	       public void onClick(DialogInterface dialog, int which) {
     		 BaseActivity.this.dialogCancel();
	         dialog.dismiss();
	      }
	    }); 
  	    AlertDialog oDialog=oBuilder.create();
	    oDialog.show();
	    return oDialog;
	}
	
	//�б�ѡ��Ի���
	protected AlertDialog showListDialog(String sCaption,String sList)
	{
		Builder oBuilder = new Builder(this);
		FaDialogList=sList.split("\u0001");
  	    oBuilder.setItems(FaDialogList, new DialogInterface.OnClickListener()
          {
              @Override
              public void onClick(DialogInterface oDialog, int iWhich)
              {
              	BaseActivity.this.dialogList(FaDialogList[iWhich]); 
              	oDialog.dismiss();
              }
          });
  	   oBuilder.setTitle(sCaption);
  	   AlertDialog oDialog=oBuilder.create();
	   oDialog.show();
	   return oDialog;
	}
	
	//�Ի���ĵ���
	protected AlertDialog showDialog(String sCaption,
			                  String sText,
			                  String sOk,
			                  String sCancel,
			                  int iLayout) {
		  Builder oBuilder = new Builder(this);
		  if(iLayout>0)
		  {
			  LayoutInflater oInflater = getLayoutInflater();
			  View oLayout = oInflater.inflate(iLayout,null,false);
			  oBuilder.setView(oLayout);
			  FoDialogView=oLayout;
		  }
		  else 
		    oBuilder.setMessage(sText); 
		  oBuilder.setTitle(sCaption);
		  if(sOk.length()>0)
		  {
		     oBuilder.setPositiveButton(sOk, new DialogInterface.OnClickListener() {  
			  @Override
		     public void onClick(DialogInterface dialog, int which) {
				  BaseActivity.this.dialogOk();
				  dialog.dismiss();
		       }
		     }); 
		  }
		  if(sCancel.length()>0)
		  {
		     oBuilder.setNegativeButton(sCancel, new DialogInterface.OnClickListener() {   
	     	  @Override
		       public void onClick(DialogInterface dialog, int which) {
	     		 BaseActivity.this.dialogCancel();
		        dialog.dismiss();
		      }
		  }); 
		  }
		  AlertDialog oDialog=oBuilder.create();
		  oDialog.show();
		  return oDialog;
		}
	
	protected void showMess(String sMess,boolean bLong)
	{
		Toast.makeText(this, sMess,bLong?Toast.LENGTH_LONG:Toast.LENGTH_SHORT).show();
	}
	
	protected String appPath()
	{
		if(FsAppPath.length()==0)
		{
		   Context oContext=this;//���ȣ���Activity���ȡcontext  
           File oFile=oContext.getFilesDir();  
           FsAppPath=oFile.getAbsolutePath();
		}
        return FsAppPath;
	}
	
	
	
	protected void saveExtraItem(Editor oEdit)
	{
		
	}
	
	protected void saveExtra()
	{
		String sName=this.getClass().getName();
		SharedPreferences oData = getSharedPreferences(sName, Activity.MODE_PRIVATE);
		Editor oEdit = oData.edit();//��ñ༭��   
		saveExtraItem(oEdit);
		oEdit.putString(sName, "yes");
		oEdit.commit();//�ύ���� 	
	}
	
	protected void clearExtra()
	{
		SharedPreferences oConfig = getSharedPreferences(this.getClass().getName(), Activity.MODE_PRIVATE);
		Editor oEdit = oConfig.edit();//��ñ༭��   
		oEdit.clear();
		oEdit.commit();//�ύ���� 	
	}
	
	protected void loadExtraItem(SharedPreferences oData)
	{
		
	}
	
	protected void loadExtra()
	{
		String sName=this.getClass().getName();
		SharedPreferences oData = getSharedPreferences(sName, Activity.MODE_PRIVATE);
		if(oData.getString(sName, "").compareTo("yes")==0) loadExtraItem(oData);
	}
	
	//���ڶԻ���Ĵ�����
	protected void dialogDate(Calendar oDate)
	{
		
	}
	
	protected void selectDate(String sDate)
	{
		
		
		  SimpleDateFormat oFormat = new SimpleDateFormat("yyyy-MM-dd");
		  Date oDate=new Date();
		  try
		  {
			 if(sDate.length()>0)  oDate=oFormat.parse(sDate);
		  }
		  catch(Exception E){}
		  Calendar oCal= Calendar.getInstance();
		  oCal.setTime(oDate);
		  DatePickerDialog oDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker oView, int iYear, int iMonthOfYear, int iDayOfMonth) {
            	Calendar oDate= Calendar.getInstance();
            	oDate.set(iYear,iMonthOfYear,iDayOfMonth);
                //et1.setText(DateFormat.format("yyy-MM-dd", oDate));
            	dialogDate(oDate);
            }
		  },oCal.get(Calendar.YEAR),oCal.get(Calendar.MONTH),oCal.get(Calendar.DAY_OF_MONTH));
          oDialog.show();
	}
	
	//�첽��ʽ����񷢳�����  sUrl:�����ַ   sData:��������  oOver:������ɺ�Ļص�
	protected  void asynData(String sUrl,String sData,boolean bWait,OnNetOver oOver)
	{
		SendThread oThread=new SendThread(this);
		oThread.FsUrl=sUrl;
		oThread.FsData=sData;
		if(bWait)
		{
		  oThread.FiWait_Layout=R.layout.dialog_wait;
		  oThread.FiProgress_Layout=R.layout.dialog_progress;
		  oThread.FiText=R.id.tvtitle;
		  oThread.FiProgress=R.id.pbprogress;
		}
		oThread.setOverListener(oOver);
		oThread.start();
	}
	
	protected void procMore(TvTable[] aTable,String sData)
	{
		
	}
	
	protected void doAction(String sUrl,String sData,boolean bWait)
	{
		asynData(sUrl,sData,bWait,new OnNetOver(){
			@Override
			public void onOver(TvTable[] aTable,String sData)
			{
				if(aTable.length>0)
				{
				  if(aTable[0].getStatus()==0)
				  {
					  procMore(aTable,sData);
				  }
				  else showMess(aTable[0].getError(),true);
				}
				else showMess("����ʧ��",true);
			}
		});
	}
	
	protected  String url(String sAsk,String sParam)
	{
		return FoSys.get("server")+"/open.ashx?action="+sAsk+"&user="+FoSys.get("user")+"&pass="+FoSys.get("pass")+"&"+sParam;
	}
	
	//�첽��ʽ�������ļ�  sFile:�ļ���   sPath:�ļ��洢  oOver:������ɺ�Ļص�
	protected void downFile(String sFile,String sPath,boolean bWait,OnUpDownOver oOver)
	{
		String sUrl=FoSys.get("server")+"/data/"+sFile;
		SendThread oThread=new SendThread(this);
		oThread.FsUrl=sUrl;
		oThread.FsData="";
		oThread.FsFileName=sPath;
		if(bWait)
		{
		  oThread.FiWait_Layout=R.layout.dialog_wait;
		  oThread.FiProgress_Layout=R.layout.dialog_progress;
		  oThread.FiText=R.id.tvtitle;
		  oThread.FiProgress=R.id.pbprogress;
		}
		oThread.setUpDownListener(oOver);
		oThread.start();
		
	}
	
	protected void setTitle(String sCaption)
	{
		TextView oText=(TextView)findViewById(R.id.tvtitle);
		if(oText!=null) oText.setText(sCaption);
	}
	
	protected void broadcast(String[] aName,String[] aValue)
	   {
		   Intent oInt=new Intent("com.fy8848.conceal.service");
		   int i,iMax=aName.length;
		   if(aValue.length<iMax) iMax=aValue.length;
		   for(i=0;i<aName.length;i++)
		     oInt.putExtra(aName[i], aValue[i]);
		   sendBroadcast(oInt);
	   }
	//�㲥���յĴ�����
	protected void procReceive(Context context,Intent intent)
	{
		
	}
	
	protected void reg()
	   {
		   FoReceiver=new  BroadcastReceiver(){
			   @Override
			   public void onReceive(Context context,Intent intent)
			   {
				   procReceive(context,intent); 
			   }
		   };
		   
		   IntentFilter oInt=new IntentFilter("com.fy8848.conceal.activity");
		   registerReceiver(FoReceiver,oInt);
		   
	   }
	   
	   protected void unReg()
	   {
		   unregisterReceiver(FoReceiver);
	   }
	
	
	
	
}
