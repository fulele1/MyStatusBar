package com.fy8848.procunit;

import android.util.Log;

public class AssistProc {
	
	public static final boolean FbDebug=true;
	public static void log(String sTag,String sMess)
	{
		if(FbDebug)	Log.w(sTag,sMess);
	}

}
