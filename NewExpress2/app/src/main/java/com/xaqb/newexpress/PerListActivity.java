package com.xaqb.newexpress;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xaqb.newexpress.Adapter.PerAdapter;
import com.xaqb.newexpress.entity.Person;
import com.xaqb.newexpress.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class PerListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private PerListActivity instance;
    private ListView mList;
    private List<Person> people;

    @Override
    public void initTitle() {
        setTitleB("快递员");
    }

    @Override
    public void initView() {
        instance = this;
        setContentView(R.layout.activity_per_list);
        setStatusColorB(instance.getResources().getColor(R.color.wirte), View.VISIBLE,true);
        mList = (ListView) findViewById(R.id.list_pl);
        mList.setDivider(new ColorDrawable(getResources().getColor(R.color.background)));
        mList.setDividerHeight(20);
    }

    @Override
    public void initAvailable() {
        Intent intent = getIntent();
        String com = intent.getStringExtra("com");
        String name = intent.getStringExtra("name");
        String ide = intent.getStringExtra("ide");
        String tel = intent.getStringExtra("tel");
        String org = intent.getStringExtra("org");
        LogUtil.i("快递员列表"+com+name+ide+tel+org);
    }

    @Override
    public void initData() {

        people = new ArrayList<>();
        for (int i = 0;i<6;i++){
            Person person = new Person();
            person.setName("任亚军"+i);
            person.setTel("133-1255-865"+i);
            person.setCom("顺丰控股(集团)股份有限公司"+i);
            person.setIde("154984564688764567"+i);
            people.add(person);
        }
    }

    @Override
    public void addEvent() {
        mList.setAdapter(new PerAdapter(instance,people));
        mList.setOnItemClickListener(instance);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        intentB(instance,PerDetailActivity.class);
    }
}
