package com.xaqb.newexpress;


import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xaqb.newexpress.impl.OnOkDataFinishedListener;
import com.xaqb.newexpress.util.GsonUtil;
import com.xaqb.newexpress.util.HttpUrlUtils;
import com.xaqb.newexpress.util.LogUtil;
import com.xaqb.newexpress.util.SPUtil;

import java.util.Map;

public class PerDetailActivity extends BaseActivity implements OnOkDataFinishedListener{

    private PerDetailActivity instance;


    @Override
    public void initTitle() {
        setTitleB("人员详情");
    }


    private TextView txt_com;
    private TextView txt_name;
    private TextView txt_tel;
    private TextView txt_six;
    private TextView txt_ide;
    private TextView txt_get;
    private TextView txt_post;
    @Override
    public void initView() {
        instance = this;
        setContentView(R.layout.activity_per_detail);
        setStatusColorB(instance.getResources().getColor(R.color.wirte), View.VISIBLE,true);
        txt_com = (TextView) findViewById(R.id.txt_com_pd);
        txt_name = (TextView) findViewById(R.id.txt_name_pd);
        txt_tel = (TextView) findViewById(R.id.txt_tel_pd);
        txt_six = (TextView) findViewById(R.id.txt_six_pd);
        txt_ide = (TextView) findViewById(R.id.txt_ide_pd);
        txt_get = (TextView) findViewById(R.id.txt_get_count_pd);
        txt_post = (TextView) findViewById(R.id.txt_post_count_pd);
    }
    private String id;
    private String access_token;

    @Override
    public void initAvailable() {
        Intent intent = getIntent();
        id = intent.getStringExtra("intentData");
        access_token = SPUtil.get(instance,"access_token","").toString();
        LogUtil.i("快递员详情"+"id"+id);
    }

    @Override
    public void initData() {

    }

    @Override
    public void addEvent() {
        setOnDataFinishedListener(instance);
        okPostConnectionB(HttpUrlUtils.getHttpUrl().detail_per(),
                "code",id, "access_token", access_token);
    }

    @Override
    public void okDataFinishedListener(String s) {
        LogUtil.i("快递员详情"+s);
        //{"state":"10","mess":"access_token验证失败"}
        s = "{\"state\":\"0\",\"mess\":\"success\"}";
        Map<String,Object> map= GsonUtil.JsonToMap(s);
        LogUtil.i("快递员详情"+map);
        if (map.get("state").toString().equals("1")){
            showToastB(map.get("mess").toString());
            return;
        }else if (map.get("state").toString().equals("10")){
            showToastB(map.get("mess").toString());
            return;
        }else if (map.get("state").toString().equals("0")){
//            txt_name.setText();
//            txt_com.setText();
//            txt_tel.setText();
//            txt_six.setText();
//            txt_ide.setText();
//            txt_get .setText();
//            txt_post.setText();

        }
    }
}
