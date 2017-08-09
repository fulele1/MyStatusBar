package com.fy8848.procunit;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.fy8848.virtualtable.TvTable;

public class NetProc {
	
	public static final int  FiProgress=10;//�ļ����ؽ�����Ϣ
	public static final int  FiDownOver=11;//�ļ��������
	private static ContentType contentType;
	
	
	
	
	//�ļ��ϴ���sUrl�ϴ��ĵ�ַ  sFile:�ļ�·��
	//���أ����ַ�������ʾ�ϴ��ɹ�   �ǿ�Ϊ�����ַ���
	
	public static String sendFile(String sUrl,String sFile) 
	{
	  try
	  {
		  /*
		URL oUrl = new URL(sUrl);  
	    HttpURLConnection oHttp = (HttpURLConnection) oUrl.openConnection();  
	    oHttp.setReadTimeout(60000);  
	    oHttp.setDoInput(true);// ��������  
	    oHttp.setDoOutput(true);// �������  
	    oHttp.setUseCaches(false);  
	    oHttp.setRequestMethod("POST"); // Post��ʽ
	    DataOutputStream oOutput = new DataOutputStream(oHttp.getOutputStream());
	    FileInputStream oFile = new FileInputStream(sFile);
	    byte[] aBuf = new byte[1024];
        int iLen = 0;
        while((iLen=oFile.read(aBuf))!=-1)
        {
            oOutput.write(aBuf, 0, iLen);
        }
        oFile.close();
        oOutput.flush();
        if(oHttp.getResponseCode()==200){  
        	    String sRet=new String(readStream(oHttp.getInputStream()));
        		TvTable oTable=new TvTable();
        		oTable.loadData(sRet, false);
        		if(oTable.getStatus()==0) return "";
        		else return oTable.getError();
        } 
		return "��������"+Integer.toString(oHttp.getResponseCode());
		*/
		  DefaultHttpClient client = new DefaultHttpClient();
		  //client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 120000);
		  client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 6000000);
		   HttpPost oPost = new HttpPost(sUrl);//�˴���URLΪhttp://..../path?arg1=value&....argn=value
		   File oFile = new File(sFile);
		   FileInputStream oFileStream = new FileInputStream(oFile);
		   InputStreamEntity reqEntity = new InputStreamEntity(oFileStream, oFile.length());
		   oPost.setEntity(reqEntity);
		   reqEntity.setContentType("binary/octet-stream");
		   HttpResponse oResponse = client.execute(oPost); //ģ������
		   int iCode = oResponse.getStatusLine().getStatusCode();//������Ӧ��
		   if(iCode==200)
			{//readStream()
			   String sRet=new String(readStream(oResponse.getEntity().getContent()));
       		   TvTable oTable=new TvTable();
       		   oTable.loadData(sRet, false);
       	       if(oTable.getStatus()==0) return "";
       		   else return oTable.getError();
			}
		   else return oResponse.getStatusLine().getReasonPhrase();  
	  }
	  catch(Exception E)
	  {
		  return E.getMessage();
	  }
	}
	
	public static byte[] readStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = -1;  
        while( (len=inStream.read(buffer)) != -1){  
            outSteam.write(buffer, 0, len);  
        }  
        outSteam.close();  
        inStream.close();  
        return outSteam.toByteArray();
       
    } 
	
	public static String readToFile(InputStream inStream,String sFile,long iAll,Handler oHandler)
	{
		try
		{
		File oFile=new File(sFile);
		FileOutputStream oStream=new FileOutputStream(oFile);
        byte[] buffer = new byte[1024];  
        int len = -1;  
        long iCount=0;
        int iProgress=0;
        int iSend=0;
        Message oMess=new Message();
        Log.w("error",Long.toString(iAll));
        while( (len=inStream.read(buffer)) != -1){ 
        	oStream.write(buffer, 0, len);
        	iCount+=len;
        	if(iAll>0) 
        		{
        		  iProgress = (int) (((float) iCount / iAll) * 100);
        		  if(iProgress>iSend)
        		  {
        		    oMess.what=FiProgress;
        		    oMess.arg1=iProgress;
        		    if(oHandler!=null)
        		      oHandler.sendMessage(oMess);
        		    iSend=iProgress;
        		  }
        		}
        }  
        oMess.what=FiDownOver;
	    if(oHandler!=null)
	      oHandler.sendMessage(oMess);
        oStream.close();  
        inStream.close();  
        return "";
		}
		catch(Exception E)
		{
			return E.getMessage();
		}
       
    } 
	
	
	//------------------------------------------------------------------
	/*
	 * �ӷ������϶�ȡ����  sUrlΪ�����������ַ  sData:Ϊ�ϴ�������
	 * �����������
	 */
	
	public static String httpRead(String sUrl,String sData) 
	{
		String sRet="";
		try
		{
		URL oUrl = new URL(sUrl); 
		HttpURLConnection oHttp = (HttpURLConnection) oUrl.openConnection();  
	    oHttp.setReadTimeout(60000);
	    oHttp.setDoInput(true);// ��������  
	    oHttp.setDoOutput(true);// �������
	    if(sData.length()==0)
	    {
	      oHttp.setRequestMethod("GET");
	      oHttp.connect();
	    }
	    else
	    {
	    	oHttp.setRequestMethod("POST");
	    	DataOutputStream oOutput = new DataOutputStream(oHttp.getOutputStream());
	    	byte[]  aData=sData.getBytes();
	    	oOutput.write(aData,0,aData.length);
	    	oOutput.flush();
	    }
	    
	    if(oHttp.getResponseCode()==200) 
	        sRet=new String(readStream(oHttp.getInputStream()));
        else 
        	sRet="1\u0001"+oHttp.getResponseMessage()+"\u00010\u00010\u00010";
        
	    return sRet;
		}
		catch(Exception E)
		{
		   return "1\u0001"+E.getMessage()+"\u00010\u00010\u00010";
     	}
	       
	}
	
	public static TvTable[] StrToTvTable(String sData)
	{
		String[] aRet=sData.split("\u0003");
        int i;
        TvTable oItem;
        ArrayList<TvTable> oTable=new ArrayList<TvTable>();
        for(i=0;i<aRet.length;i++)
        {
     	  oItem=new TvTable();
     	  oItem.loadData(aRet[i], false);
     	  oTable.add(oItem);
        }
        TvTable[] aTable=new TvTable[oTable.size()];
        oTable.toArray(aTable);
        return aTable;
	}
	
	
	
	//-------------------------------------------------------------------------------
	/*
	 * �����ļ�   sUrl��������ַ   sFile�����ļ�����·��   oHandler:������Ϣ���ݴ�����
	 */
	
	public static String httpGetFile(String sUrl,String sFile,Handler oHandler) 
	{
		try
		{
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet oGet = new HttpGet(sUrl);//�˴���URLΪhttp://..../path?arg1=value&....argn=value
			HttpResponse oResponse = client.execute(oGet); //ģ������
			int iCode = oResponse.getStatusLine().getStatusCode();//������Ӧ��
			if(iCode==200)
			{
				long iAll=oResponse.getEntity().getContentLength();
				String sRet=readToFile(oResponse.getEntity().getContent(),sFile,iAll,oHandler);//���������ص�����
				if(sRet.length()==0) return "";
				else return sRet;
			  
			}
			else return oResponse.getStatusLine().getReasonPhrase();
		
		}
		catch(Exception E)
		{
			return E.getMessage();
		}
	}
	
	public static String uploadFile(String sUrl,String sFile,ProgressListener oCall) {  
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		// �������ӳ�ʱʱ��
		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
		HttpPost httpPost = new HttpPost(sUrl);
		MultipartEntityBuilder entitys = MultipartEntityBuilder.create();
		entitys.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		entitys.setCharset(Charset.forName(HTTP.UTF_8));
		String sBoundary="2983754uekjsdfeiswruyhvweruvx";
		entitys.setBoundary(sBoundary);
		httpPost.addHeader("Content-Type", "multipart/form-data;boundary=" + sBoundary);
		
        
		File oFile = new File(sFile);
		//FileBody oBody=new FileBody(oFile);
		//entitys.addBinaryBody("name", oFile, ContentType.MULTIPART_FORM_DATA, sFile);
		entitys.addBinaryBody("file", oFile);
		
		
		HttpEntity httpEntity = entitys.build();
		long iTotalSize = httpEntity.getContentLength();
		ProgressOutHttpEntity progressHttpEntity = new ProgressOutHttpEntity(
				httpEntity, oCall,iTotalSize);
		httpPost.setEntity(progressHttpEntity);
		try {
			HttpResponse oResponse = httpClient.execute(httpPost);
			int iCode = oResponse.getStatusLine().getStatusCode();//������Ӧ��
			   if(iCode==200)
				{//readStream()
				   String sRet=new String(readStream(oResponse.getEntity().getContent()));
	       		   TvTable oTable=new TvTable();
	       		   oTable.loadData(sRet, false);
	       	       if(oTable.getStatus()==0) return "";
	       		   else return oTable.getError();
				}
			   else return oResponse.getStatusLine().getReasonPhrase();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpClient != null && httpClient.getConnectionManager() != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
		return "�ļ��ϴ�ʧ��";
        
	
}  

}
