package com.fy8848.procunit;

import com.fy8848.virtualtable.TvTable;
//网络访问完成后的回调函数
public interface OnNetOver {
	public void onOver(TvTable[] aTable,String sData);	
}
