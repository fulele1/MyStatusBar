package com.fy8848.adaptter;

public  class ListData{
	public String[] FaData;
	public boolean FbSelect=false;
	public String FsPk="";//主键
	public String FsParent="";//父节点主键
	public ListData FoChild;//孩子
	public ListData FoNext;//同级的下一个
	public int FiLayout=0;//布局的序号
	public boolean FbExpand=false;//是否展开
	public ListItem FoView=null;
	public int FiLevel=0;//树的节点级别
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
