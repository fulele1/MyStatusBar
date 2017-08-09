package com.fy8848.adaptter;


public class RowLayout {
	
	public int[] FaViewID;//布局中控件的ID
	public int[] FaViewType;//布局中控件的类型
	public int FiLayout=0;//布局id
	public int FiCheckBox=0;//多选框的Id
	public int FiIcon=0;//指示他的图标的UI组件的ID
	public int FiClose=0;//收缩的图标资源id
	public int FiOpen=0;//展开的图标资源的id
	public int FiLeaf=0;//叶子节点的图标资源的ID
	public int FiEmpty=0;//空白区域的UI组件
	
	public RowLayout(int iLayout,int iCheckBox,int[] aView,int[] aType)
	{
		int i;
		FaViewID=new int[aView.length];
		for(i=0;i<aView.length;i++) FaViewID[i]=aView[i];
		FaViewType=new int[aType.length];
		for(i=0;i<aType.length;i++) FaViewType[i]=aType[i];
		FiLayout=iLayout;
		FiCheckBox=iCheckBox;
	}

}
