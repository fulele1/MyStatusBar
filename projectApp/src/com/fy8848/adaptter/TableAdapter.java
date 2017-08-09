package com.fy8848.adaptter;

import java.util.ArrayList;
import java.util.HashMap;

import com.fy8848.procunit.AssistProc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TableAdapter extends BaseAdapter {
	private LayoutInflater FoInfalter;//视图容器
	private Context FoContext;//运行上下文 
	protected String[] FaField;
	protected int FiField=0;//字段数量
	protected int FiLayout=0;//布局
	
	protected ArrayList<ListData> FoData=new ArrayList<ListData>();//列表数据
	protected ArrayList<Integer> FoViewID=new ArrayList<Integer>();//布局中控件的ID
	protected ArrayList<Integer> FoViewType=new ArrayList<Integer>();//布局中控件的类型
	protected HashMap<String, Integer> FoImage = new HashMap<String, Integer>();
	protected int FiP=0;
	protected int FiPageSize=20;
	protected int FiTotal=0;
	protected OnAdapterView OnViewProc=null;
	public int FiCheckBoxID=0;
	public int FiCheckImageID=0;
	public int FiNoID=0;
	
	
	public void setPageSize(int iSize)
	{
		if(iSize>0) FiPageSize=iSize;
	}
	
	//每一行的控件对象
	public void procDateTime()
	{
		
	}
	
	//把布局中的控件添加到适配器中
	public void addID(int iID,int iType)
	{
		FoViewID.add(iID);
		FoViewType.add(iType);
		FiField=FoViewID.size();
	}
	
	//获取查询需要的分页参数
	public String getPage()
	{
		if(FoData.size()==FiTotal) return "";
		else return Integer.toString(FoData.size())+","+Integer.toString(FiPageSize);
	}
	
	public int getNexPage()
	{
		if(FoData.size()==FiTotal&&FiTotal>0) return -1;
		else return FoData.size()/FiPageSize+1;
	}
	
	public void addImage(String sName,int iID)
	{
		if(FoImage.containsKey(sName)) FoImage.remove(sName);
	    FoImage.put(sName, iID);
	}
	
	protected int getImageID(String sName)
	{
		if(FoImage.containsKey(sName)) return FoImage.get(sName);
		return -1;
	}
	
	protected void setRowView(ListItem oItem)
	{
		if(FoData.size()>0) FoData.get(FiP).FoView=oItem;
	}
	
	public void selectAll(boolean bSelect)
	{
		int i;
		ListData oItem;
		for(i=0;i<FoData.size();i++)
		{
			oItem=FoData.get(i);
			oItem.FbSelect=bSelect;
			//Assist.log("error","all:"+Integer.toString(i)+" "+(bSelect?"true":"false"));
			//if(oItem.FoView.FoCheck!=null) oItem.FoView.FoCheck.setChecked(bSelect);
		}
		
		this.notifyDataSetChanged();
	}
	
	
	public boolean isAll()
	{
		return FiTotal<=FoData.size();
	}
	
	//设置总数据数量
	public void setTotal(int iTotal)
	{
		FiTotal=iTotal;
	}
	
	public void setViewListener(OnAdapterView oListener)
	{
		this.OnViewProc=oListener;
	}
	
	
	//当前行到指定位置
	public void go(int iPos)
	{
		if(iPos>=0&&iPos<FoData.size()) FiP=iPos;
	}
	
    public TableAdapter(Context oContent,int iLayout)
    {
    	FoContext=oContent;
    	FoInfalter = LayoutInflater.from(oContent);
    	FiLayout=iLayout;
    }
    
    public void clear()
    {
    	FoData.clear();
    }
    
    public void add(String sPk,String[] aData)
    {
    	ListData oItem=new ListData(aData.length);
    	int i;
    	for(i=0;i<aData.length;i++)
    	   oItem.FaData[i]=aData[i];
    	oItem.FsPk=sPk;
    	FoData.add(oItem);
    }
    
     
   
    
    public String[] getSelect()
    {    	
    	ArrayList<String> oSel=new ArrayList<String>();
    	int i;
    	ListData oItem;
    	for(i=0;i<FoData.size();i++)
    	{
    		oItem=FoData.get(i);
    		if(oItem.FbSelect) oSel.add(oItem.FsPk);
    	}
    	String[] aRet=new String[oSel.size()];
    	oSel.toArray(aRet);
    	return aRet;
    }
    
    public String getPk(int iPosition)
    {
    	if(iPosition>=0&&iPosition<FoData.size())
    		return FoData.get(iPosition).FsPk;
    	return "";
    }
    
    public String asString(int iNo)
    {
    	if(FoData.size()>0&&iNo<FoData.get(FiP).FaData.length&&iNo>=0)
    	 return FoData.get(FiP).FaData[iNo];
    	return "";
    }
    
    public boolean isChecked()
    {
    	if(FoData.size()>0) return FoData.get(FiP).FbSelect;
    	return false;
    }
    
    public boolean isChecked(int iPos)
    {
    	if(iPos<FoData.size()) return FoData.get(iPos).FbSelect;
    	return false;
    }
    
    public void setSelect(int iPos,boolean bSel)
    {
    	
    	if(iPos>=0&&iPos<FoData.size())
    		{
    		   FoData.get(iPos).FbSelect=bSel;
    		   //Assist.log("error","sel:"+Integer.toString(iPos)+" "+(bSel?"true":"false"));
    		}
    }
    
    @Override
	public int getCount() {
		return FoData.size();
		
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
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ListItem oItem;
		int i;
		// 判断当前条目是否为null
		if(convertView == null){
			oItem = new ListItem(FoViewID.size());
			//获取item布局文件的视图
			convertView = FoInfalter.inflate(FiLayout, null);//生成条目对象
			//获取控件对象
			if(FiCheckBoxID>0) oItem.FoCheck=(CheckBox)convertView.findViewById(FiCheckBoxID);
			if(FiCheckImageID>0) oItem.FoCheckImage=(ImageView)convertView.findViewById(FiCheckImageID);
			if(FiNoID>0) oItem.FoNo=(TextView)convertView.findViewById(FiNoID);
			for(i=0;i<FoViewID.size();i++)
				switch(FoViewType.get(i))
				{
				case 0:oItem.FaView[i]=(TextView)convertView.findViewById(FoViewID.get(i));break;
				case 1:oItem.FaView[i]=(ImageView)convertView.findViewById(FoViewID.get(i));break;
				case 2:oItem.FaView[i]=convertView.findViewById(FoViewID.get(i));break;
				}
			
			oItem.FiPosition=position;
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
			convertView.setTag(oItem);
		}
		else
			oItem = (ListItem) convertView.getTag();
		
		FiP=position;
		//setRowView(oItem);
		//Assist.log("error",Integer.toString(FiP)+":"+(isChecked()?"true":"false"));
		if(oItem.FoCheck!=null) oItem.FoCheck.setChecked(isChecked());
		if(oItem.FoCheckImage!=null) 
			{
			   if(isChecked()) oItem.FoCheckImage.setVisibility(View.VISIBLE);
			   else oItem.FoCheckImage.setVisibility(View.INVISIBLE);
			   
			}
		int iImageID=-1;
		if(oItem.FoNo!=null) oItem.FoNo.setText(Integer.toString(position+1));
		int iPercent=0;
		for(i=0;i<oItem.FaView.length;i++)
		{
			switch(FoViewType.get(i))
			{
			case 0:if(oItem.FaView[i]!=null)  ((TextView)oItem.FaView[i]).setText(asString(i));break;
			case 1:iImageID=getImageID(asString(i));
			       if(iImageID>0) ((ImageView)oItem.FaView[i]).setImageResource(iImageID);	
			       break;
			case 2:iPercent=Integer.parseInt(asString(i));
			       ((ProgressBar)oItem.FaView[i]).setMax(100);
			       ((ProgressBar)oItem.FaView[i]).setProgress(iPercent);
			       AssistProc.log("error", "adapter:"+Integer.toString(iPercent));
			       break;
			}
		}
		if(this.OnViewProc!=null) OnViewProc.onView(oItem, FoData.get(FiP));
		return convertView;
	}
	
	public ListData getData(int iPosition)
	{
		if(iPosition>=0&&iPosition<FoData.size())
			return FoData.get(iPosition);
		return null;
	}
	
	public ListData getData(String sPk)
	{
		int i;
		ListData oItem=null;
		for(i=0;i<this.FoData.size();i++)
		{
			oItem=(ListData)FoData.get(i);
			if(oItem.FsPk.compareTo(sPk)==0)
				return oItem;
		}
		return oItem;
	}
	
	public int find(String sID)
	{
		if(FoData.size()>2)
		{
			boolean bUp=FoData.get(0).FsPk.compareTo(FoData.get(1).FsPk)<0;
			int i;
			if(bUp)
			{
				for(i=0;i<FoData.size();i++)
					if(FoData.get(i).FsPk.compareTo(sID)>=0) return i;
					
			}
			else
			{
				for(i=0;i<FoData.size();i++)
					if(FoData.get(i).FsPk.compareTo(sID)<=0) return i;
			}
		}
		return -1;
			
	}
}
