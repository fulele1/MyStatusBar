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
	
	//public int FIIid = 0; //���
	//public String FISname = ""; //��������
	//public String FISfieldName = ""; //Ӣ������
	//public int FIItype = 0; //�������� 1:���� 2:С�� 3:���� 4:ʱ�� 5������ʱ�� 6���ַ��� 7���� 8���ַ��� 9ͼ������10������ 
	//public int FIIsubType = 0; //���������� type=3:0:���� 1���·�  2:���
	//public double FIFmax = -1; //���ȡֵ
	//public double FIFmin = 0; //��Сȡֵ
	//public boolean FIBprimaryKey = false; //�Ƿ����ؼ���
	//public boolean FIBauto = false; //�Ƿ��Զ�����
	//public int FIIlength = 0; //���ݳ��ȣ�С����С����λ��   �ַ���������
	/*
	* 1:����       subType 0:(��) 1����ɫ
	* 2��С��    subType 0:(��) 1�������  2����Ԫ 
	* 6���ַ��� subtype 0:(��) 1:�绰����   2���ֻ�����  3����������  4�������ʼ� 
	*/
	//------------------------------------------------------------
	//-------------------------��ʾ��Ϣ---------------------------
	/*
	public String FISunit = ""; //��ʾ���ݵĵ�λ
	public int FIIshowWidth = 0; //������ʾ�Ŀ��
	public int FIIalign = 0; //��ʾʱ����뷽ʽ��0������ 1�������  2���Ҷ���
	public String FIStemplet = ""; //ģ���ַ���
	public int FIIshowSize = 0; //������ʾ�ĸ���
	//-------------------------------------------
	//--------------������ʾ��Ϣ-----------------
	public boolean FIBparent = false; //�Ƿ��Ǹ��ֶ�
	public boolean FIBtreeShow = false; //�Ƿ�������ʾ����ʾ����
	//---------------------------------------------
	//-------------��ؼ�����Ϣ---------------
	public boolean FIBfKey = false; //�Ƿ���ؼ���
	public int FIIfKeyId = 0; //��ؼ��ֵ��ֵ�ID
	public String FISfKeyTable = ""; //��ؼ��ֱ�
	public String FISfKeyField = ""; //��ؼ����ֶ�
	public String FISfKeyShow = ""; //��ؼ�����ʾ����
	public String FISfKeyJoinName = ""; //��ؼ������Ӳ�ѯ�ı���
	public String FISfKeyCondition = ""; //��ؼ��ֲ��ҵ�����
	public boolean FIBfKeyVisible = false; //��ؼ��ֲ���ʱ���Ƿ���ʾ
	public int FIIfKeyWidth = 0; //���������ʾ���
	public int FIIfKeyHeight = 0; //���������ʾ�߶�

	//-------------------------------------------------------------
	//-------sql��ѯ����Ϣ--------------------------
	public int FIIsort = 0; //Ĭ������ 1 3 5����������������  2 4 6:ż���ǽ�������
	public int FIIsum = 0; //�������� 0:������  1:sum  2:count 3:average
	public int FIIkeyFind = 0; //���п��ټ����������� 0��������   1:��ֵ  2��Ӣ���ַ�  4�������ַ�
	public boolean FIBsecondKey = false; //�ڶ��ؼ���
	//--------��ͼ��ʾ����Ϣ----------------------------
	public boolean FIBviewFilter = false; //�Ƿ�����ͼ�Ĳ�ѯ����
	public boolean FIBsubData = false; //�Ƿ��������ݣ���ʾ��������

	//-----------�ⲿ��ϵ�ֶ���Ϣ---------------
	public boolean FIBlink = false; //�Ƿ�����ϵ
	public String FISlinkTable = ""; //����ϵ��
	public String FISlinkField = ""; //����ϵ�ֶ�
	//------------��ʾ�ⲿ�ֶ���Ϣ-----------
	public boolean FIBoutShow = false; //�Ƿ���������ʾ�ֶ�
	public String FISoutTable = ""; //������ʾ��Ӧ�ı�
	//-------------�����ֶ���Ϣ------------------
	public boolean FIBcalField = false; //�Ƿ��Ǽ����ֶ�
	public String FISexpression = ""; //�����ֶεļ�����ʽ

	//--------------���������Ϣ----------------------------
	public String FISdefault = ""; //Ĭ��ȡֵ
	public boolean FIBnull = false; //�Ƿ�������Ϊ��    true:���ݲ���Ϊ��  false:���ݿ���Ϊ��
	public boolean FIBreadonly = false; //�Ƿ�ֻ��
	public String FISreg = ""; //������ʽ
	public String FISgroup = ""; //���ݷ�������
	public String FISselect = ""; //��ѡֵ
	public boolean FIBnoInput = false; //��������Ľ����ϲ���ʾ
	public int FIIinputWidth = 0; //���������ʾ���
	//----------------
	public int FIIshowOrder = 1000; //��ʾ����
	//-------dom���������------------------
	//public Arraylist<object> FaDom = new ArrayList<object>(); //����¼������Dom������Ƕ���¼�룬Ϊ���ݣ�����ǵ���¼���ǵ�0��Ԫ��
	public int FiIndex = -1; //��ǰ��Ч��dom
	public ArrayList<String> FaOld=new ArrayList<String>(); //����¼������ԭ����
	//-------------------------------
		 */
	public String Fvalue = ""; //ȡֵ
	public String FoldValue = ""; //��ֵ
	public double FsumValue = 0; //����ֵ
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
		if(sData.compareTo("1")==0||sData.compareTo("true")==0||sData.compareTo("��")==0) return true;
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
		Fvalue = ""; //ȡֵ
		FoldValue = ""; //��ֵ
		FsumValue = 0; //����ֵ
		FkeyShow = "";
	}
	
	public void loadFrom(TvTable oTable) {
		int i;
		setDefault();
		for(i=0;i<FaName.length;i++)
			FoData.put(FaName[i], oTable.asString("F"+FaName[i]));
	};
	
	//-----------------���������������--------------------  
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
		
		
		String sE1 =S("name") + "�Ƿ�����";
		String sE2 = S("name") + "����С����Сȡֵ";
		String sE3 = S("name") + "���ݴ������ȡֵ";
		String sE4 = S("name") + "���ݳ��ȳ�����󳤶�";
		String sE5 = S("name") + "���ݲ���Ϊ��";
		String sShow="";
		if (sValue.length() == 0 && (B("primaryKey") || B("null"))) return sE5;
		if (B("fKey")) {
				sShow = DictItem.fKeyShow(sValue);
				sValue = DictItem.fKeyValue(sValue);
				if ((B("null")) && (sShow.length() == 0)) return sE5; //�ж������Ƿ�Ϊ��

		}
		String sReg=S("reg");
		if (sReg.length() > 0)//��������ʽУ��
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
				   String sFormat="yyyy-MM-dd��HH:mm:ss";
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
					
						if ((sValue.compareTo("true")==0) || (sValue.compareTo("1")==0) || (sValue.compareTo("��")==0)) Fvalue="1";
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
		
	//----------------------����������ĿΪĬ��ȡֵ---------------------------  
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
				case 7:if(Fvalue.compareTo("1")==0||Fvalue.compareTo("true")==0||Fvalue.compareTo("��")==0) return "��";
				else return "��";
		 	}
			return Fvalue;
		};
		public String sumAsString() {
			String sSum = "";
			switch (I("sum")) {
				case 1: { sSum = "�ϼ�:"; break; }
				case 2: { sSum = "����:"; break; }
				case 3: { sSum = "ƽ��:"; break; }
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
			if (Fvalue.compareTo("1")==0||Fvalue.compareTo("true")==0||Fvalue.compareTo("��")==0) return true;
			else return false;
		};
		
		public boolean who(String sField) {
			sField = sField.toLowerCase();
			return ((sField.compareTo("f" + S("id"))==0) || (sField.compareTo(S("name").toLowerCase())==0) || (sField.compareTo(S("fieldName").toLowerCase())==0));
		};
}
