package com.xaqb.newexpress;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xaqb.newexpress.util.LogUtil;

public class PerQueryActivity extends BaseActivity {

    private PerQueryActivity instance;
    private Button btn_query;
    private EditText et_com;
    private EditText et_name;
    private EditText et_ide;
    private EditText et_tel;
    private EditText et_org;


    @Override
    public void initTitle() {
        setTitleB("人员查询");
    }

    @Override
    public void initView() {
        instance = this;
        setStatusColorB(instance.getResources().getColor(R.color.wirte), View.VISIBLE,true);
        setContentView(R.layout.activity_per_query);
        btn_query = (Button) findViewById(R.id.btn_query_pq);
        et_com = (EditText) findViewById(R.id.et_com_pq);
        et_name = (EditText) findViewById(R.id.et_name_pq);
        et_ide = (EditText) findViewById(R.id.et_ide_pq);
        et_tel = (EditText) findViewById(R.id.et_tel_pq);
        et_org = (EditText) findViewById(R.id.et_org_pq);
    }

    @Override
    public void initAvailable() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void addEvent() {
        btn_query.setOnClickListener(instance);
        et_org.setOnClickListener(instance);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_query_pq:
                query();
                break;
            case R.id.et_org_pq:
                intentB(instance,SearchOrgActivity.class);
                break;
        }
    }

    private void query() {
        String com = et_com.getText().toString();
        String name = et_name.getText().toString();
        String ide = et_ide.getText().toString();
        String tel = et_tel.getText().toString();
        String org = et_org.getText().toString();
        LogUtil.i("人员查询界面"+com+name+ide+tel+org);
        intentB(instance,PerListActivity.class,"com",com,"name",name,"ide",ide,"tel",tel,"org",org);
    }
}
