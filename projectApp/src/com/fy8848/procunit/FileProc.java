package com.fy8848.procunit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileProc {
	
	public static String copyFile(String sFrom,String sTo)
	{
		File oFrom=new File(sFrom);
		File oTo=new File(sTo);
		if(oTo.exists()) oTo.delete();
		try {
    			FileInputStream oFS = new FileInputStream(oFrom);
    			FileOutputStream oTS = new FileOutputStream(oTo);
    		    byte aBuf[] = new byte[1024];
    			int i;
      			while ((i = oFS.read(aBuf)) > 0) oTS.write(aBuf, 0, i);
				oFS.close();
                oTS.close();
                return "";
			} 
		catch (Exception E)
			{
				return E.getMessage();
    		}
	}

}
