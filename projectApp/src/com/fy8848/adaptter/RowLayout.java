package com.fy8848.adaptter;


public class RowLayout {
	
	public int[] FaViewID;//�����пؼ���ID
	public int[] FaViewType;//�����пؼ�������
	public int FiLayout=0;//����id
	public int FiCheckBox=0;//��ѡ���Id
	public int FiIcon=0;//ָʾ����ͼ���UI�����ID
	public int FiClose=0;//������ͼ����Դid
	public int FiOpen=0;//չ����ͼ����Դ��id
	public int FiLeaf=0;//Ҷ�ӽڵ��ͼ����Դ��ID
	public int FiEmpty=0;//�հ������UI���
	
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
