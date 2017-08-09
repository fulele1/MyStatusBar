package com.fy8848.dict;

import java.util.ArrayList;

public class DictSet {
	
	protected ArrayList<Dict> FoItem=new ArrayList<Dict>();
	public void add(Dict oItem) {
		FoItem.add(oItem);
	};

	public Dict load(String sData)//´Ó×Ö·û´®ÔØÈë×Öµä
	{
		
		    Dict oDict = new Dict();
			if (!oDict.load(sData)) oDict = null;
			if ( oDict.FiID > 0) {
				boolean bFind = false;
				int i;
				for (i = 0; i < FoItem.size(); i++)
					if (FoItem.get(i).FiID == oDict.FiID) {
					bFind = true;
					break;
				}
				if (!bFind) FoItem.add(oDict);
			}
		
		return oDict;

	};
	
	public Dict getById(int iID) {
		Dict oDict = null;
		int i;
			for (i = 0; i < FoItem.size(); i++)
				if (FoItem.get(i).FiID == iID) {
				oDict = FoItem.get(i);
				break;
			}
		return oDict;
	};

	
	

}
