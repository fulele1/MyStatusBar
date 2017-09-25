package com.xaqb.newexpress;


import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xaqb.newexpress.Adapter.MessageAdapter;
import com.xaqb.newexpress.entity.Message;

import java.util.ArrayList;
import java.util.List;

public class MsgListActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private MsgListActivity instance;
    private ListView mList;
    private List<Message> mMseeages;

    @Override
    public void initTitle() {
        setTitleB("线索信息");
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_message_list);
        instance = this;
        setStatusColorB(instance.getResources().getColor(R.color.wirte), View.VISIBLE,true);
        mList = (ListView) findViewById(R.id.list_ml);
        mList.setDivider(new ColorDrawable(getResources().getColor(R.color.background)));
        mList.setDividerHeight(20);
    }

    @Override
    public void initAvailable() {

    }

    @Override
    public void initData() {
        mMseeages = new ArrayList<>();
        for (int i = 1;i<6;i++){
            Message message = new Message();
            message.setMeg("对于白领阶级来说未来租房会代替买房吗"+i);
            message.setComForm("/天弘基金/"+i);
            message.setDate("09-1"+i);
            mMseeages.add(message);
        }

    }

    @Override
    public void addEvent() {
        mList.setAdapter(new MessageAdapter(instance,mMseeages));
        mList.setOnItemClickListener(instance);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        intentB(instance,MsgDetailActivity.class);

    }
}
