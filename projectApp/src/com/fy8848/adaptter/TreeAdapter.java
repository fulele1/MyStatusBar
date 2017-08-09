package com.fy8848.adaptter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TreeAdapter extends BaseAdapter {
	private LayoutInflater FoInfalter;//视图容器
	private Context FoContext;//运行上下文 
	
	protected ArrayList<ListData> FoData=new ArrayList<ListData>();//列表数据所有
	protected ArrayList<ListData> FoShow=new ArrayList<ListData>();//列表数据显示
	
	protected ArrayList<RowLayout> FoLayout=new ArrayList<RowLayout>();
	
	protected OnAdapterView OnViewProc=null;
	public int FiGap=50;//不同级别的节点的空白区域
	
	public TreeAdapter(Context oContent)
    {
    	FoContext=oContent;
    	FoInfalter = LayoutInflater.from(oContent);
    }
	
	public void addLayout(RowLayout oLayout)
	{
		FoLayout.add(oLayout);
	}
	
	public void clearData()
	{
		FoData.clear();
		FoShow.clear();
	}
	
	
	//新增树的数据
	public void add(String sPk,String sParent,String[] aData,int iLayout)
    {
    	ListData oItem=new ListData(aData.length),oParent,oChild;
    	oItem.FiLayout=iLayout;
    	int i;
    	for(i=0;i<aData.length;i++)
    	   oItem.FaData[i]=aData[i];
    	oItem.FsPk=sPk;
    	oItem.FsParent=sParent;
    	if(sParent.length()>0)
    	for(i=0;i<FoData.size();i++)
    	{
    		if(FoData.get(i).FsPk.compareTo(sParent)==0)
    		{
    		   oParent=FoData.get(i);
    		   oItem.FiLevel=oParent.FiLevel+1;
    		   if(oParent.FoChild==null) oParent.FoChild=oItem;
    		   else
    		   {
    			   oChild=oParent.FoChild;
    			   while(oChild.FoNext!=null) oChild=oChild.FoNext;
    			   oChild.FoNext=oItem;
    		   }
       		  break;
    		}
    	}
    	else
    		for(i=0;i<FoData.size();i++)
    		{
    			if(FoData.get(i).FsParent.length()==0)
    			{
    				oParent=FoData.get(i);
    				if(oParent.FoNext==null) oParent.FoNext=oItem;
    				else
    				{
    					oChild=oParent.FoNext;
    					while(oChild.FoNext!=null) oChild=oChild.FoNext;
    					oChild.FoNext=oItem;
    				}
    				break;
    			}
    		}
    	FoData.add(oItem);
    }
	
	protected void showLevel(ListData oItem)
	{
		while(oItem!=null)
		{
			FoShow.add(oItem);
			if(oItem.FbExpand&&oItem.FoChild!=null) showLevel(oItem.FoChild); 
			oItem=oItem.FoNext;
		}
		
	}
	
	public void updateShow()
	{
		FoShow.clear();
		if(FoData.size()>0)	showLevel(FoData.get(0));
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return FoShow.size();
		
	}
    
    @Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		
		return null;
	}

	/*获取返回的id*/
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		//return positionId;
		return position;
	}
	
	protected void setSelect(int iPos,boolean bSel)
    {
    	
    	if(iPos>=0&&iPos<FoShow.size()) FoShow.get(iPos).FbSelect=bSel;
    }
	
	public ListData getData(int iPosition)
	{
		if(iPosition>=0&&iPosition<FoShow.size())
			return FoShow.get(iPosition);
		return null;
	}
	
	@Override
	public View getView(int iPosition, View oView, ViewGroup oParent) {
		// TODO Auto-generated method stub
		
		ListItem oItem=null;
		ListData oData=getData(iPosition);
		RowLayout oLayout=FoLayout.get(oData.FiLayout);
		int i;
		if(oView!=null) oItem = (ListItem) oView.getTag();
		// 判断当前条目是否为null
		if(oView == null||(oItem!=null&&oItem.FiLayout!=oData.FiLayout)){
			
			
			oItem = new ListItem( oLayout.FaViewID.length);
			//获取item布局文件的视图
			oView = FoInfalter.inflate(oLayout.FiLayout, null);//生成条目对象
			//获取控件对象
			oItem.FoCheck=(CheckBox)oView.findViewById(oLayout.FiCheckBox);
			for(i=0;i<oLayout.FaViewID.length;i++)
				switch(oLayout.FaViewType[i])
				{
				case 0:oItem.FaView[i]=(TextView)oView.findViewById(oLayout.FaViewID[i]);break;
				case 1:oItem.FaView[i]=(ImageView)oView.findViewById(oLayout.FaViewID[i]);break;
				}
			
			oItem.FiPosition=iPosition;
		    if(oItem.FoCheck!=null) 
		    	{
		    	    oItem.FoCheck.setTag(oItem);
		    	    oItem.FoCheck.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){

						@Override
						public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
							// TODO Auto-generated method stub
							//记录图片选中状态
							ListItem oItem=(ListItem)buttonView.getTag(); 
							setSelect(oItem.FiPosition,isChecked);
													
						}
						
					});
		    	}
		    if(oLayout.FiEmpty>0)
			{
				View oEmpty=oView.findViewById(oLayout.FiEmpty);
				LinearLayout.LayoutParams oParams=(LinearLayout.LayoutParams)oEmpty.getLayoutParams();
				oParams.setMargins(FiGap*(oData.FiLevel+1), 0,0, 0);
				oEmpty.setLayoutParams(oParams);
				
			}
			oView.setTag(oItem);
		}
		else
			oItem = (ListItem) oView.getTag();
		
		
		for(i=0;i<oItem.FaView.length;i++)
		{
			switch(oLayout.FaViewType[i])
			{
			case 0:if(oItem.FaView[i]!=null)  ((TextView)oItem.FaView[i]).setText(asString(iPosition,i));break;
			//case 1:oItem.FaView[i]=(ImageView)convertView.findViewById(FoViewID.get(i));break;
			}
		}
		if(oLayout.FiIcon>0)
		{
			ImageView oIcon=(ImageView)oView.findViewById(oLayout.FiIcon);
			if(oIcon!=null) 
			{
				if(oData.FoChild==null)
				{
					if(oLayout.FiLeaf>0) oIcon.setImageResource(oLayout.FiLeaf);
				}
				else
				if(oData.FbExpand&&oLayout.FiOpen>0) oIcon.setImageResource(oLayout.FiOpen);
				else
				if(!oData.FbExpand&&oLayout.FiClose>0) oIcon.setImageResource(oLayout.FiClose);
			}
		}
		
		if(this.OnViewProc!=null) OnViewProc.onView(oItem, oData);
		return oView;
	}
	
	public String asString(int iPos,int iNo)
    {
		if(iPos>=0&&iPos<FoShow.size())
		{
			ListData oData=FoShow.get(iPos);
			if(iNo>=0&&iNo<oData.FaData.length) return oData.FaData[iNo];
		}
		return "";
		
    }
	
	public void setViewListener(OnAdapterView oListener)
	{
		this.OnViewProc=oListener;
	}
	
	public String[] getSelect()
    {    	
    	ArrayList<String> oSel=new ArrayList<String>();
    	int i;
    	ListData oItem;
    	for(i=0;i<FoShow.size();i++)
    	{
    		oItem=FoShow.get(i);
    		if(oItem.FbSelect) oSel.add(oItem.FsPk);
    	}
    	String[] aRet=new String[oSel.size()];
    	oSel.toArray(aRet);
    	return aRet;
    }
	
	public boolean clickTree(int iPos)
	{
		ListData oData=FoShow.get(iPos);
		if(oData.FoChild!=null)
		{
			oData.FbExpand=!oData.FbExpand;
			updateShow();
			return true;
		}
		return false;
	}
	
	
}
