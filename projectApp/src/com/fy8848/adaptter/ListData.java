package com.fy8848.adaptter;

public  class ListData{
	public String[] FaData;
	public boolean FbSelect=false;
	public String FsPk="";//����
	public String FsParent="";//���ڵ�����
	public ListData FoChild;//����
	public ListData FoNext;//ͬ������һ��
	public int FiLayout=0;//���ֵ����
	public boolean FbExpand=false;//�Ƿ�չ��
	public ListItem FoView=null;
	public int FiLevel=0;//���Ľڵ㼶��
	public ListData(int iField)
	{
		FaData=new String[iField];
	}
	
	public String[] getData()
	{
		String[] aData=new String[FaData.length+1];
		aData[0]=FsPk;
		int i;
		for(i=0;i<FaData.length;i++)
			aData[i+1]=FaData[i];
		return aData;
	}
}
