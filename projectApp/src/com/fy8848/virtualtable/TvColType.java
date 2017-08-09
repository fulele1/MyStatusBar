package com.fy8848.virtualtable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TvColType {
	public int FiDataType=0;//数据类型 1：整数  2：浮点  3：日期  4：时间 5：日期时间 6：字符串 7：布尔  8：多行文本 9：图像 10:大数据
	public int FiLength=0;// 字符串长度，浮点数的小数位数
    public float FfMax=0;//数值类型的最大取值
    public float FfMin=0;//数值类型的最小取值
    public String FsReg="";//正则表达式，数据格式校验
    
    public int FiId=0;//从字典载入,字典项目的id编号
    public String FsName="";//字段中文名称  
    public String FsFieldName="";//字段名
    public boolean checkData(String sValue) {//用正则表达式检查数据
        if(this.FsReg.length()>0)
        {	
        	Pattern oPat = Pattern.compile(FsReg);  
            Matcher oMat = oPat.matcher(sValue);
            return oMat.find();
        }
        else return true;
    }
    
    public void assign(TvColType oHost)
    {
      FiDataType=oHost.FiDataType;//数据类型 1：整数  2：浮点  3：日期  4：时间 5：日期时间 6：字符串 7：布尔  8：多行文本 9：图像 10:大数据
      this.FiLength=oHost.FiLength;// 字符串长度，浮点数的小数位数
      this.FfMax=oHost.FfMax;//数值类型的最大取值
      this.FfMin=oHost.FfMin;//数值类型的最小取值
      this.FsReg=oHost.FsReg;//正则表达式，数据格式校验
      this.FiId=oHost.FiId;//从字典载入,字典项目的id编号
      this.FsName=oHost.FsName;//字段中文名称  
      this.FsFieldName=oHost.FsFieldName;
    }

}
