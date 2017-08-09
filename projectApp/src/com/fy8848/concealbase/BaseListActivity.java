package com.fy8848.concealbase;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.fy8848.adaptter.TableAdapter;
import com.fy8848.concealapp.DataManager;
import com.fy8848.concealapp.R;
import com.fy8848.virtualtable.TvTable;

public class BaseListActivity extends BaseActivity  {
	protected TableAdapter FoAdapter; 
    protected DataManager FoData;
    protected ListView FoList;
    protected String FsCountSql="";
    protected String FsSelectSql="";
    protected int FiField=0;
    protected String FsUrl="";
    protected String FsSendData="";
    protected boolean FbNetData=false;
    
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FoSys.put("server", "");
		FoSys.put("user", "");
		FoSys.put("pass", "");
        FoData=new DataManager(this);
        
       }
    
    public void iniList(int iLayout,int[] aView,int[] aType,int iList)
    {
    	FoList=(ListView)findViewById(iList);
    	FoAdapter=new TableAdapter(this, iLayout);
    	int i;
    	for(i=0;i<aView.length;i++)
         FoAdapter.addID(aView[i], aType[i]);
    	FoList.setOnScrollListener(new OnScrollListener(){  
            @Override  
            public void onScrollStateChanged(AbsListView oList, int scrollState){  
                // 当不滚动时  
                if (scrollState == OnScrollListener.SCROLL_STATE_IDLE)
                	if (oList.getLastVisiblePosition() == oList.getCount() - 1)  
                		openView(false); 
                         
                   
            } 
            
            @Override
            public void onScroll(AbsListView oList, int arg1, int arg2, int arg3) {
            	
            }
        });
    	FoList.setOnItemClickListener(new OnItemClickListener(){
    		public void onItemClick(AdapterView<?> av, View v, int iPosition, long iOther)
    		{
    			actionOnItemClick(iPosition);
    		}
    	});
    	
    	FoList.setOnItemLongClickListener(new OnItemLongClickListener()
    	{
    		public boolean onItemLongClick(AdapterView<?> av, View v, int iPosition, long iOther)
    		{
    			return actionOnItemLongClick(iPosition);
    		}
    	});
        
    }
    
    public void actionOnItemClick(int iPosition)
    {
    	
    }
    
    public boolean actionOnItemLongClick(int iPosition)
    {
    	return false;
    }

    public void iniButton()
    {
    	OnClickListener oClick=new OnClickListener(){
    		
    		@Override
    		public void onClick(View v)
    		{
    			switch(v.getId())
    			{
    			case R.id.ivback:actionClose();break;
    			//case R.id.ivadd:actionAdd();break;
    			}
    		}
    	};
    	ImageView oBtn=(ImageView)findViewById(R.id.ivback);
    	if(oBtn!=null) oBtn.setOnClickListener(oClick);
    	//oBtn=(ImageView)findViewById(R.id.ivadd);
    	//if(oBtn!=null) oBtn.setOnClickListener(oClick);
    }
    
    
    protected void actionAdd()
    {
    	//Intent oInt=new Intent();
    	//oInt.setClass(this, TunnelDataActivity.class);
    	//startActivityForResult(oInt,0);
    }
    
    protected void actionClose()
    {
    	finish();
    }
    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.tunnel, menu);
        return true;
    }
    
    public void openView(boolean bFirst)
    {
    	if(FbNetData) openNetView(bFirst);
    	else
    	if(bFirst)
    	{
    		FoAdapter.clear();
    		String sRet=FoData.ListData(FoAdapter, FsCountSql, FsSelectSql);
    		if(sRet.length()==0) 
    			{
    			   FoList.setAdapter(FoAdapter);
    			   FoAdapter.notifyDataSetChanged();
    			}
    		else {
    			FoAdapter.notifyDataSetChanged();
    		    showMess(sRet,true);
    		}
    	}
    	else
    		if(!FoAdapter.isAll())
    		{
    			//showMess("加载新数据...",false);
    			String sRet=FoData.ListData(FoAdapter, FsCountSql, FsSelectSql);
        		if(sRet.length()==0) FoAdapter.notifyDataSetChanged();
        		else showMess(sRet,true);
    		}
    	
    }
    
    public void openNetView(boolean bFirst)
    {
    	if(bFirst) FoAdapter.clear();
    	int iPage=this.FoAdapter.getNexPage();
    	if(iPage==-1) return;
    	String sUrl=this.FsUrl+"&page="+Integer.toString(iPage);
    	this.doAction(sUrl, FsSendData, true);
    }
    
    protected void procMore(TvTable[] aTable,String sData)
	{
		int iPage=FoAdapter.getNexPage();
    	this.FoData.ListData(FoAdapter, aTable[0]);
    	if(iPage==1) FoList.setAdapter(FoAdapter);
    	else FoAdapter.notifyDataSetChanged();
	}
    
    

}
