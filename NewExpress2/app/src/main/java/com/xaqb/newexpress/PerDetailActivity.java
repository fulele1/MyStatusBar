package com.xaqb.newexpress;


import android.view.View;

public class PerDetailActivity extends BaseActivity {

    private PerDetailActivity instance;


    @Override
    public void initTitle() {
        setTitleB("人员详情");
    }

    @Override
    public void initView() {
        instance = this;
        setContentView(R.layout.activity_per_detail);
        setStatusColorB(instance.getResources().getColor(R.color.wirte), View.VISIBLE,true);

    }

    @Override
    public void initAvailable() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void addEvent() {

    }
}
