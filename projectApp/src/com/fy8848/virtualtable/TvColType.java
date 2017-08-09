package com.fy8848.virtualtable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TvColType {
	public int FiDataType=0;//�������� 1������  2������  3������  4��ʱ�� 5������ʱ�� 6���ַ��� 7������  8�������ı� 9��ͼ�� 10:������
	public int FiLength=0;// �ַ������ȣ���������С��λ��
    public float FfMax=0;//��ֵ���͵����ȡֵ
    public float FfMin=0;//��ֵ���͵���Сȡֵ
    public String FsReg="";//������ʽ�����ݸ�ʽУ��
    
    public int FiId=0;//���ֵ�����,�ֵ���Ŀ��id���
    public String FsName="";//�ֶ���������  
    public String FsFieldName="";//�ֶ���
    public boolean checkData(String sValue) {//��������ʽ�������
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
      FiDataType=oHost.FiDataType;//�������� 1������  2������  3������  4��ʱ�� 5������ʱ�� 6���ַ��� 7������  8�������ı� 9��ͼ�� 10:������
      this.FiLength=oHost.FiLength;// �ַ������ȣ���������С��λ��
      this.FfMax=oHost.FfMax;//��ֵ���͵����ȡֵ
      this.FfMin=oHost.FfMin;//��ֵ���͵���Сȡֵ
      this.FsReg=oHost.FsReg;//������ʽ�����ݸ�ʽУ��
      this.FiId=oHost.FiId;//���ֵ�����,�ֵ���Ŀ��id���
      this.FsName=oHost.FsName;//�ֶ���������  
      this.FsFieldName=oHost.FsFieldName;
    }

}
