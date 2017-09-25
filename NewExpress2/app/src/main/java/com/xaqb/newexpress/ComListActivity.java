package com.xaqb.newexpress;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.xaqb.newexpress.Adapter.ComAdapter;
import com.xaqb.newexpress.entity.Company;
import com.xaqb.newexpress.impl.OnOkDataFinishedListener;
import com.xaqb.newexpress.util.GsonUtil;
import com.xaqb.newexpress.util.HttpUrlUtils;
import com.xaqb.newexpress.util.LogUtil;
import com.xaqb.newexpress.util.SPUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ComListActivity extends BaseActivity implements OnOkDataFinishedListener,AdapterView.OnItemClickListener{
    private ComListActivity instance;
    private ListView mList;
    private TextView txt_size;
    private List<Company> mCompanys;

    @Override
    public void initTitle() {
        setTitleB("企业");
    }

    @Override
    public void initView() {
        instance = this;
        setStatusColorB(instance.getResources().getColor(R.color.wirte), View.VISIBLE,true);
        setContentView(R.layout.activity_com_list);
        mList = (ListView) findViewById(R.id.list_cl);
        txt_size = (TextView) findViewById(R.id.txt_size_cl);
        mList.setDivider(new ColorDrawable(getResources().getColor(R.color.background)));
        mList.setDividerHeight(20);
    }
    String brand;
    String org;
    String com;
    String access_token;
    @Override
    public void initAvailable() {
        Intent intent = getIntent();
        brand = intent.getStringExtra("brand");
        org = intent.getStringExtra("org");
        com = intent.getStringExtra("com");
        access_token = SPUtil.get(instance,"access_token","").toString();
        LogUtil.i("企业列表"+brand+org+com+access_token);
        setOnDataFinishedListener(instance);
        okPostConnectionB(HttpUrlUtils.getHttpUrl().query_com(),
                "comname",com,"bccode",brand,"socode",org,
                "access_token", access_token);
    }

    @Override
    public void initData() {
    }

    @Override
    public void addEvent() {
        mList.setOnItemClickListener(instance);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        intentB(ComListActivity.this,ComDetialActivity.class,mCompanys.get(position).getComID());
    }


    @Override
    public void okDataFinishedListener(String s) {
        LogUtil.i("企业列表"+s);
        //{"state":"10","mess":"access_token验证失败"}
        s = "{\n" +
                "  \"state\": 0, \n" +
                "  \"mess\": {\n" +
                "    \"page\": 1, \n" +
                "    \"curr\": 1\n" +
                "  }, \n" +
                "  \"table\": [\n" +
                "    {\n" +
                "      \"com_address\": \"西安高新区123号\", \n" +
                "      \"com_id\": 1, \n" +
                "      \"com_area\": \"快快快\", \n" +
                "      \"com_name\": \"德邦快递\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        Map<String,Object> map= GsonUtil.JsonToMap(s);
        LogUtil.i("企业列表 map"+map);
        if (map.get("state").toString().equals("1")){
            showToastB(map.get("mess").toString());
            return;
        }else if (map.get("state").toString().equals("10")){
            showToastB(map.get("mess").toString());
            return;
        }else if (map.get("state").toString().equals("0")){
            List<Map<String,Object>> data = GsonUtil.GsonToListMaps(GsonUtil.GsonString(map.get("table")));
            LogUtil.i("企业列表 date"+data);
            mCompanys = new ArrayList<>();
            for (int j = 0;j<data.size();j++){
                Company company = new Company();
                company.setComName(data.get(j).get("com_name").toString());
                company.setComTotalName(data.get(j).get("com_area").toString());
                company.setComAddress(data.get(j).get("com_address").toString());
                company.setComID(data.get(j).get("com_id").toString());
                mCompanys.add(company);
            }

            mList.setAdapter(new ComAdapter(instance,mCompanys));


            Message msg = mHandler.obtainMessage();
            msg.obj = data.size();
            msg.what = 0;
            mHandler.sendMessage(msg);
        }

    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                txt_size.setText(msg.obj.toString());
            }
        }
    };

}
