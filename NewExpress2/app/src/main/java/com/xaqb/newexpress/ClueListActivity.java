package com.xaqb.newexpress;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xaqb.newexpress.Adapter.ClueAdapter;
import com.xaqb.newexpress.entity.Clue;

import java.util.ArrayList;
import java.util.List;

public class ClueListActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private ClueListActivity instance;
    private ListView mList;
    private List<Clue> mClues;


    @Override
    public void initTitle() {
        setTitleB("线索信息");
    }

    @Override
    public void initView() {
        instance = this;
        setStatusColorB(instance.getResources().getColor(R.color.wirte), View.VISIBLE,true);
        setContentView(R.layout.activity_clue_list);
        mList = (ListView) findViewById(R.id.list_clc);
        mList.setDivider(new ColorDrawable(getResources().getColor(R.color.background)));
        mList.setDividerHeight(20);
    }

    @Override
    public void initAvailable() {

    }

    @Override
    public void initData() {
        mClues = new ArrayList<>();
        for (int i = 0;i<6;i++){
            Clue clue = new Clue();
            clue.setMessage("发现可疑人物，寄送物品既然不让打开，也不刘身份信息"+i);
            clue.setCome_form("陕西全峰快递有限公司"+i);
            clue.setDate("张三丰");
            clue.setEmployee("2天前");
            mClues.add(clue);
        }

    }

    @Override
    public void addEvent() {
        mList.setAdapter(new ClueAdapter(instance,mClues));
        mList.setOnItemClickListener(instance);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        intentB(instance,ClueDetialActivity.class);

    }
}
