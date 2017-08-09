package com.fy8848.dict;

import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fy8848.procunit.DateProc;
import com.fy8848.virtualtable.TvTable;

public class DictItem {
	
	protected String[]  FaName={"id","name","fieldName","type","subType","max","min","primaryKey","auto","length",
			                 "unit","showWidth","align","templet","showSize","parent","treeShow",
			                 "fKey","fKeyId","fKeyTable","fKeyField","fKeyShow","fKeyJoinName","fKeyCondition","fKeyVisible","fKeyWidth","fKeyHeight",
			                 "sort","sum","keyFind","secondKey","viewFilter","subData","link","linkTable","linkField",
			                 "outShow","outTable","calField","expression","default","null","readonly","reg","group","select","noInput","inputWidth","showOrder"};
	protected int[]  FaType={1,6,6,1,1,2,2,7,7,1,
			                 6,1,1,6,1,7,6,
			                 7,1,6,6,6,6,6,7,1,1,
			                 1,1,1,7,7,7,7,6,6,
			                 7,6,7,6,6,7,7,6,6,6,7,1,1};
	public static String fKeyValue(String sData)
	{
		int iPos=sData.indexOf("-");
		if(iPos>0) return sData.substring(0,iPos);
		else return sData;
	}
	
	public static String fKeyShow(String sData)
	{
		int iPos=sData.indexOf("-");
		if(iPos>0) return sData.substring(iPos+1);
		else return sData;
	}
	
	protected HashMap<String,String> FoData=new HashMap<String,String>(); 
	
	//public int FIIid = 0; //序号
	//public String FISname = ""; //中文名称
	//public String FISfieldName = ""; //英文名称
	//public int FIItype = 0; //数据类型 1:正数 2:小数 3:日期 4:时间 5：日期时间 6：字符串 7布尔 8长字符串 9图像数据10大数据 
	//public int FIIsubType = 0; //数据子类型 type=3:0:日期 1：月份  2:年份
	//public double FIFmax = -1; //最大取值
	//public double FIFmin = 0; //最小取值
	//public boolean FIBprimaryKey = false; //是否主关键字
	//public boolean FIBauto = false; //是否自动增量
	//public int FIIlength = 0; //数据长度：小数：小数的位数   字符串：长度
	/*
	* 1:整数       subType 0:(无) 1：颜色
	* 2：小数    subType 0:(无) 1：人民币  2：美元 
	* 6：字符串 subtype 0:(无) 1:电话号码   2：手机号码  3：邮政编码  4：电子邮件 
	*/
	//------------------------------------------------------------
	//-------------------------显示信息---------------------------
	/*
	public String FISunit = ""; //显示数据的单位
	public int FIIshowWidth = 0; //数据显示的宽度
	public int FIIalign = 0; //显示时候对齐方式：0：居中 1：左对齐  2：右对齐
	public String FIStemplet = ""; //模板字符串
	public int FIIshowSize = 0; //数据显示的个数
	//-------------------------------------------
	//--------------树型显示信息-----------------
	public boolean FIBparent = false; //是否是父字段
	public boolean FIBtreeShow = false; //是否是树显示的显示内容
	//---------------------------------------------
	//-------------外关键字信息---------------
	public boolean FIBfKey = false; //是否外关键字
	public int FIIfKeyId = 0; //外关键字的字典ID
	public String FISfKeyTable = ""; //外关键字表
	public String FISfKeyField = ""; //外关键字字段
	public String FISfKeyShow = ""; //外关键字显示内容
	public String FISfKeyJoinName = ""; //外关键字链接查询的别名
	public String FISfKeyCondition = ""; //外关键字查找的条件
	public boolean FIBfKeyVisible = false; //外关键字查找时候，是否显示
	public int FIIfKeyWidth = 0; //外键窗口显示宽度
	public int FIIfKeyHeight = 0; //外键窗口显示高度

	//-------------------------------------------------------------
	//-------sql查询的信息--------------------------
	public int FIIsort = 0; //默认排序 1 3 5：奇数是升序排序  2 4 6:偶数是降序排序
	public int FIIsum = 0; //汇总类型 0:不汇总  1:sum  2:count 3:average
	public int FIIkeyFind = 0; //进行快速检索查找类型 0：无类型   1:数值  2：英文字符  4：中文字符
	public boolean FIBsecondKey = false; //第二关键字
	//--------视图显示的信息----------------------------
	public boolean FIBviewFilter = false; //是否是视图的查询条件
	public boolean FIBsubData = false; //是否是子数据，显示子数据用

	//-----------外部联系字段信息---------------
	public boolean FIBlink = false; //是否外联系
	public String FISlinkTable = ""; //外联系表
	public String FISlinkField = ""; //外联系字段
	//------------显示外部字段信息-----------
	public boolean FIBoutShow = false; //是否是外联显示字段
	public String FISoutTable = ""; //外联显示对应的表
	//-------------计算字段信息------------------
	public boolean FIBcalField = false; //是否是计算字段
	public String FISexpression = ""; //计算字段的计算表达式

	//--------------输入界面信息----------------------------
	public String FISdefault = ""; //默认取值
	public boolean FIBnull = false; //是否检查数据为空    true:数据不能为空  false:数据可以为空
	public boolean FIBreadonly = false; //是否只读
	public String FISreg = ""; //正则表达式
	public String FISgroup = ""; //数据分组名称
	public String FISselect = ""; //可选值
	public boolean FIBnoInput = false; //不在输入的界面上不显示
	public int FIIinputWidth = 0; //输入界面显示宽度
	//----------------
	public int FIIshowOrder = 1000; //显示排序
	//-------dom对象的引用------------------
	//public Arraylist<object> FaDom = new ArrayList<object>(); //数据录入界面的Dom，如果是多行录入，为数据，如果是单行录入是第0个元素
	public int FiIndex = -1; //当前有效的dom
	public ArrayList<String> FaOld=new ArrayList<String>(); //数据录入界面的原数据
	//-------------------------------
		 */
	public String Fvalue = ""; //取值
	public String FoldValue = ""; //旧值
	public double FsumValue = 0; //汇总值
	public String FkeyShow = "";
	
	public String S(String sName)
	{
		return FoData.get(sName);
	}
	public int I(String sName)
	{
		String sData=FoData.get(sName);
		if(sData.length()==0) return 0;
		else return Integer.parseInt(sData);
	}
	public double D(String sName)
	{
		String sData=FoData.get(sName);
		if(sData.length()==0) return 0;
		else return Double.parseDouble(sData);
	}
	public boolean B(String sName)
	{
		String sData=FoData.get(sName).toLowerCase();
		if(sData.compareTo("1")==0||sData.compareTo("true")==0||sData.compareTo("√")==0) return true;
		return false;
	}
	public void setDefault()
	{
		int i;
		if(FoData.size()==0)
			for(i=0;i<FaName.length;i++) FoData.put(FaName[i],"");
		for(i=0;i<FaName.length;i++)
			switch(FaType[i])
			{
			case 1:
			case 2:
			case 7:FoData.put(FaName[i],"0");break;
			case 6:FoData.put(FaName[i],"");break;
			}
		Fvalue = ""; //取值
		FoldValue = ""; //旧值
		FsumValue = 0; //汇总值
		FkeyShow = "";
	}
	
	public void loadFrom(TvTable oTable) {
		int i;
		setDefault();
		for(i=0;i<FaName.length;i++)
			FoData.put(FaName[i], oTable.asString("F"+FaName[i]));
	};
	
	//-----------------从虚表中载入数据--------------------  
    public void loadDataFrom(TvTable oTable) {
			    String sField=S("fieldName");
			    int  iNo = oTable.getColIndex(sField);
				if (iNo == -1) iNo = oTable.getColIndex("f" + S("id"));
				if (iNo >= 0) Fvalue=oTable.asString(iNo);
				if (B("fKey")) {
					iNo = oTable.getColIndex(S("fKeyShow"));
					if (iNo == -1) iNo = oTable.getColIndex("ff" + S("id"));
					if (iNo >= 0) this.FkeyShow = oTable.asString(iNo);
				}
   }
    
    public void loadSum(TvTable oTable) {
		if ( I("sum")> 0) {
			int iNo = oTable.getColIndex("S" + S("id"));
			if (iNo >= 0) this.FsumValue = oTable.asFloat(iNo);
		}
	};
	
	
	public String setValue(String sValue) {
		
		
		String sE1 =S("name") + "非法数据";
		String sE2 = S("name") + "数据小于最小取值";
		String sE3 = S("name") + "数据大于最大取值";
		String sE4 = S("name") + "数据长度超出最大长度";
		String sE5 = S("name") + "数据不能为空";
		String sShow="";
		if (sValue.length() == 0 && (B("primaryKey") || B("null"))) return sE5;
		if (B("fKey")) {
				sShow = DictItem.fKeyShow(sValue);
				sValue = DictItem.fKeyValue(sValue);
				if ((B("null")) && (sShow.length() == 0)) return sE5; //判断数据是否为空

		}
		String sReg=S("reg");
		if (sReg.length() > 0)//用正则表达式校验
			{
			     Pattern oReg = Pattern.compile(sReg);  
	             Matcher oMat= oReg.matcher(sValue);
	             if(!oMat.matches()) return sE1;
			}
		Date oDate;	
		int iType=I("type");
		double fValue=0;
		switch (iType) {
			case 1:
			case 2: 
				{
					sValue=sValue.replace(",","");
					try
					{
					if (iType == 1) 
						fValue = Integer.parseInt(sValue, 10);
					else
						if (iType == 2) {
						  fValue=Double.parseDouble(sValue);
					}
					}
					catch(Exception E)
					{
						return E.getMessage();
					}
					
					if (D("max") > D("min")) {
						if (fValue <D("min")) return sE2;
						if (fValue > D("max")) return sE3;
					}
					this.Fvalue = sValue;
					if (B("fKey")) this.FkeyShow = sShow;
					return "";
					
				}
			case 3:
		    case 4:
			case 5: 
				   String sFormat="yyyy-MM-dd　HH:mm:ss";
				   oDate=DateProc.strToDate(sValue,sFormat);
				   if(oDate==null) return sE1;
					if (D("max") > D("min")) {
						if (oDate.getTime() > D("max")) return sE3;
						else
							if (oDate.getTime() < D("min")) return sE2;
					}
					this.Fvalue = sValue;
					return "";
				
			case 7: 
				{
					
						if ((sValue.compareTo("true")==0) || (sValue.compareTo("1")==0) || (sValue.compareTo("√")==0)) Fvalue="1";
						else Fvalue="0";
						
						return "";
					
				}
			default: 
				{
					if(iType==6||iType==8)
					if ((I("length")>0) && (sValue.length() > I("length"))) return sE4;
					if (B("fKey")) this.FkeyShow = sShow;
				}
		} //swtich
		return "";
	};
		
	//----------------------设置数据项目为默认取值---------------------------  
	public void setDefaultData() {
			this.FkeyShow = "";
			switch (I("type")) {
				case 1:
				case 2: { if (S("default").length() > 0) this.setValue(S("default")); else Fvalue = "0"; break; }
				case 3:
				case 4:
				case 5: { if (S("default").length() > 0) this.setValue(S("default")); else Fvalue = DateProc.getTimeStr("yyyy-MM-dd HH:mm:ss"); break; }
				case 7: { if (S("default").length() > 0) this.setValue(S("default")); else this.Fvalue = ""; break; }
				case 6:
				case 8: { if (S("default").length() > 0) this.setValue(S("default")); else this.Fvalue = ""; break; }
				default: this.Fvalue = ""; ;
			}
			if ((I("type") > 2) && (I("type") < 6)) this.FoldValue = DateProc.getTimeStr("yyyy-MM-dd HH:mm:ss");
			else FoldValue = Fvalue;
			if (Fvalue == "") FkeyShow = "";

		}
		
		public String asString() {
			if(B("fKey")) return Fvalue+"-"+FkeyShow;
			Date oDate;
			int iType=I("type");
			switch(iType)
			{
				case 1:return Fvalue;
				case 2:if(I("length")>0) return String.format("%."+S("length")+"f", Double.parseDouble(Fvalue));
				else return Fvalue;
				case 3:
				case 4:
				case 5:
					oDate=DateProc.strToDate(Fvalue, "yyyy-MM-dd HH:mm:ss");
					if(oDate!=null)
					{
						if(iType==3) return DateProc.formatDate(oDate, "yyyy-MM-dd");
						if(iType==4) return DateProc.formatDate(oDate, "HH:mm:ss");
						if(iType==5) return DateProc.formatDate(oDate, "yyyy-MM-dd HH:mm:ss");
					}
					else return "";
				case 6:
				case 8:return Fvalue;
				case 7:if(Fvalue.compareTo("1")==0||Fvalue.compareTo("true")==0||Fvalue.compareTo("√")==0) return "√";
				else return "×";
		 	}
			return Fvalue;
		};
		public String sumAsString() {
			String sSum = "";
			switch (I("sum")) {
				case 1: { sSum = "合计:"; break; }
				case 2: { sSum = "数量:"; break; }
				case 3: { sSum = "平均:"; break; }
			}
			if (I("type") < 3 && I("length") >= 0)
				return sSum+String.format("%."+S("length")+"f",FsumValue);
			else return sSum + Double.toString(FsumValue);
		};
		
		public int asInt()
		{
			try
			{
			return Integer.parseInt(Fvalue);
			}
			catch(Exception E)
			{
				return 0;
			}
		}
		
		public float asFloat()
		{
			try
			{
			return Float.parseFloat(Fvalue);
			}
			catch(Exception E)
			{
				return 0;
			}
		}
		
		public boolean asBoolean() {
			if (Fvalue.compareTo("1")==0||Fvalue.compareTo("true")==0||Fvalue.compareTo("√")==0) return true;
			else return false;
		};
		
		public boolean who(String sField) {
			sField = sField.toLowerCase();
			return ((sField.compareTo("f" + S("id"))==0) || (sField.compareTo(S("name").toLowerCase())==0) || (sField.compareTo(S("fieldName").toLowerCase())==0));
		};
}
