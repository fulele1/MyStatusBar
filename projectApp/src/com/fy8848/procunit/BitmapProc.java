package com.fy8848.procunit;

import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

public class BitmapProc {

	
	public static Bitmap getOne(String sFrom) {
      	
        return ThumbnailUtils.createVideoThumbnail(sFrom, MediaStore.Images.Thumbnails.MICRO_KIND);
               
    }
	
	public static Bitmap getFrame(String sFrom)
	{
		
		MediaMetadataRetriever oRetriever = new MediaMetadataRetriever(); 
		try
		{
			oRetriever.setDataSource(sFrom);  
            Bitmap oBmp= oRetriever.getFrameAtTime();
            return oBmp;
            
		}
		catch(Exception E)
		{
			return null;
		}
		finally
		{
			oRetriever.release(); 
		
		}
		
	}
	
	//图片的缩放
		public static Bitmap scalePhoto(Bitmap oSrc,float fRate)
		{
			  Matrix oMatrix = new Matrix(); 
			  oMatrix.postScale(fRate,fRate); //长和宽放大缩小的比例
			  return Bitmap.createBitmap(oSrc,0,0,oSrc.getWidth(),oSrc.getHeight(),oMatrix,true);
		}
		
		//按最大边长缩放
			public static  Bitmap scalePhoto(Bitmap oSrc,int iMax)
			{
				int iWidth=oSrc.getWidth();
				int iHeight=oSrc.getHeight();
				float fRate=0;
				if(iWidth>iHeight) fRate=(float)iMax/(float)iWidth;
				else fRate=(float)iMax/(float)iHeight;
				return scalePhoto(oSrc,fRate);
		     }
		
	//图片缩放
		public static Bitmap scalePhoto(Bitmap oSrc,int iWidth,int iHeight)
		{
			int iSrcW=oSrc.getWidth();
			int iSrcH=oSrc.getHeight();
			if(iSrcW==0||iSrcH==0) return null;
			int iW=iWidth,iH=(int)iSrcH*iWidth/iSrcW;
			if(iH==iHeight)
			    return scalePhoto(oSrc,iWidth>iH?iWidth:iH);
			else
				if(iH>iHeight)
				{
					iH=iHeight;
					iW=(int)iSrcW*iHeight/iSrcH;
				}
			Matrix oMatrix = new Matrix();
			oMatrix.postScale((float)iW/(float)iSrcW,(float)iH/(float)iSrcH); //长和宽放大缩小的比例
			return Bitmap.createBitmap(oSrc,0,0,iSrcW,iSrcH,oMatrix,true);
			
		}
		
		public static String bitmpSaveToJpg(Bitmap oBmp,String sFile)
		{
			if(oBmp==null) return "图片不存在";
			try
			{
				File oFile=new File(sFile);
	        	FileOutputStream oFS = new FileOutputStream(oFile);
	        	oBmp.compress(Bitmap.CompressFormat.JPEG, 90, oFS);
	        	oFS.flush();
	        	oFS.close();
	        	return "";
			}
			catch(Exception E)
			{
				return E.getMessage();
			}
		}
	
}
