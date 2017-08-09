package com.fy8848.dict;

import java.util.ArrayList;

import com.fy8848.virtualtable.TvTable;

public class Dict {
    protected ArrayList<DictItem> FoItem=new ArrayList<DictItem>();
    protected boolean FbSimple=true;
    protected int FiID=0;
    protected String FsName="";
    protected String FsTable="";
    protected String FsSub = "";
    protected String FsPrefix = "";
    public String FsData="";
    
    public void add(DictItem oItem)
    {
    	if(oItem!=null) FoItem.add(oItem);
    }
    
    public void clear()
    {
    	FoItem.clear();
    	FiID=0;
    	FsName="";
    	FsTable="";
    	FsSub="";
    	FsPrefix="";
    }
    
    public void loadName(TvTable oTable) {
			this.FiID = oTable.asInt("Fid");
			this.FsName = oTable.asString("Fname");
			this.FsTable = oTable.asString("FtableName");
			this.FsSub = oTable.asString("Fsub");
			this.FsPrefix = oTable.asString("Fprefix");
		
	}
    
    public void loadItem(TvTable oTable) {
		  DictItem oItem;
			oTable.first();
			while (!oTable.eof()) {
				oItem = new DictItem();
				oItem.loadFrom(oTable);
				add(oItem);
				oTable.next();
			}
	}
    
  //------------------载入数据-------------------------
  	public void loadData(TvTable oTable) {
  		int i;
  		if (oTable.recordCount() > 0)
  			for (i = 0; i < this.FoItem.size(); i++)
  			FoItem.get(i).loadDataFrom(oTable);

  	}
  	
  	public void loadSum (TvTable oTable) {
  		int i;
		if (oTable.recordCount() > 0)
			for (i = 0; i < FoItem.size(); i++)
			FoItem.get(i).loadSum(oTable);
	}
  	
  //---------------------获取字典项目-------------------
  	public DictItem getItem(String sName) {
  		    sName = sName.toLowerCase();
  		    int i;
  			for (i = 0; i < FoItem.size(); i++)
  				if (FoItem.get(i).who(sName)) return FoItem.get(i);
  		return null;
  	}
  	//-------------------------------------------
  	public DictItem getPk() {
  		int i;
  		for (i = 0; i < FoItem.size(); i++)
  			if (FoItem.get(i).B("primaryKey")) return FoItem.get(i);
  		return null;
  	};
  	//-----------------------
  	public DictItem[] getLink(String sTable) {
  		ArrayList<DictItem> aItem=new ArrayList<DictItem>();
  		sTable = sTable.toLowerCase();
  		int i;
  		DictItem oItem;
  		for (i = 0; i < FoItem.size(); i++)
  		{
  			oItem=FoItem.get(i);
  			if (oItem.B("link") && oItem.S("linkTable").toLowerCase().compareTo(sTable)==0) 
               aItem.add(oItem);
  		}
  		DictItem[] aRet=new DictItem[aItem.size()];
  		aItem.toArray(aRet);
  		return aRet;
  	}
  	//-------设置字段值-------------------
  	public boolean setValue(String sName, String sValue) {
  		    DictItem oItem = getItem(sName);
  			if (oItem!=null) {
  				oItem.setValue(sValue);
  				return true;
  			}
  		return false;
  	}
  	//-----------------按名称取字符串数据-------------
  	public String asString(String sName) {
  		DictItem oItem = this.getItem(sName);
  		if (oItem!=null)
  			if (this.FbSimple && oItem.B("fKey"))
  				return DictItem.fKeyShow(oItem.asString());
  			else return oItem.asString();
  		return "";
  	};
  	//-------------按数值取数值数据
  	public int asInt(String sName) {
  		
  			DictItem oItem = this.getItem(sName);
  			if (oItem!=null) return oItem.asInt();
  		
  		return 0;
  	}
  	public float asFloat(String sName) {
  		
			DictItem oItem = this.getItem(sName);
			if (oItem!=null) return oItem.asFloat();
		
		return 0;
	}
  	//---------------------------------------
  	public boolean asBoolean(String sName) {
  		
  			DictItem oItem = this.getItem(sName);
  			if(oItem!=null) return oItem.asBoolean();
  	  		return false;
  	}
  	
  	public String inName(String sName) {
			sName = sName.toLowerCase();
			int i;
			for (i = 0; i < FoItem.size(); i++)
				if (FoItem.get(i).who(sName)) return "F" + FoItem.get(i).S("id");
		return null;
	}
  	
  	public DictItem getParent() {
  		int i;
  		DictItem oItem;
		for (i = 0; i < FoItem.size(); i++)
		{
			oItem=FoItem.get(i);
			if(oItem.B("parent")) return oItem;
		}
		return null;
	}

	public DictItem getFK(int iNo) {
		int iCount = 0;
		int i;
		DictItem oItem;
		for (i = 0; i < FoItem.size(); i++)
		{
			oItem=FoItem.get(i);
			if(oItem.B("fKey"))
				if(iCount==iNo) return oItem;
			iCount++;
		}
		return null;
	}
	
	public DictItem getFkeyById( int iId) {
		int i;
		DictItem oItem;
		for (i = 0; i < FoItem.size(); i++)
		{
			oItem=FoItem.get(i);
			if(oItem.B("fKey")&&oItem.I("fKeyId")==iId) return oItem;
		}
		
		return null;

	};
	
	//从字符串载入字典	
	public boolean load(String sData) {
			this.FsData = sData;
			return this.reset();
		};
		//复位字典项目  
	public boolean reset() {
			if (this.FsData.length() > 0) {
				this.clear();
				String[] aData = this.FsData.split("\u0003");
				TvTable oName = new TvTable();
				TvTable oTable = new TvTable();
				if (aData.length > 0) oName.loadData(aData[0],false);
				if (aData.length > 1) oTable.loadData(aData[1],false);
				this.loadName(oName);
				this.loadItem(oTable);
				return true;
			}
			return false;
		}

		//把续表中的字典字段名  替换成  实际字段名
	public void 	replaceName(TvTable oTable) {
			 int i;
			 String sOld,sNew;
			 DictItem oItem;
			 for (i = 0; i < FoItem.size(); i++) {
				   oItem=FoItem.get(i);
					sOld = "F" + oItem.S("id");
					sNew = oItem.S("fieldName");
					oTable.replaceName(sOld, sNew);
					if (oItem.B("fKey"))
					{
						sOld = "FF" + oItem.S("id");
						sNew = oItem.S("fKeyShow");
						oTable.replaceName(sOld, sNew);
					}
				
			}
		}
		
	public int filterNum()
		{
		   int iNum=0,i;
		   for (i = 0; i < FoItem.size(); i++)
				if (FoItem.get(i).B("viewFilter")) iNum++;
		   return iNum;
		};
}
