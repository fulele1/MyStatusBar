package com.fy8848.adaptter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public  class ListItem{
	public View[] FaView;
	public CheckBox FoCheck;
	public ImageView FoCheckImage;
	public TextView FoNo;
	public int FiPosition=-1;
	public int FiLayout=-1;
	public ListItem(int iField)
	{
		FaView=new View[iField];
	}
	public View[] getViews()
	{
		View[] aView=new View[FaView.length+1];
		aView[0]=FoCheck;
		int i;
		for(i=0;i<FaView.length;i++)
			aView[i+1]=FaView[i];
		return aView;
	}
}
