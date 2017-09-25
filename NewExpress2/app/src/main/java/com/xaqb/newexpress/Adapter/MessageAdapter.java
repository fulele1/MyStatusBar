package com.xaqb.newexpress.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xaqb.newexpress.R;
import com.xaqb.newexpress.entity.Message;

import java.util.List;

/**
 * Created by lenovo on 2017/9/8.
 */

public class MessageAdapter extends BaseAdapter {
    private Context mContext;
    private List<Message> mMessages;
    public MessageAdapter(Context context, List<Message> messages) {
        mContext = context;
        mMessages = messages;
    }

    @Override
    public int getCount() {
        return mMessages.size();
    }

    @Override
    public Object getItem(int i) {
        return mMessages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    MyViewHolderaa holder;
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            holder = new MyViewHolderaa();
            view = LayoutInflater.from(mContext).inflate(R.layout.list_clue,null);
            holder.tvMsg = (TextView) view.findViewById(R.id.txt_message_lcl);
            holder.tvCome = (TextView) view.findViewById(R.id.txt_come_lcl);
            holder.tvDate = (TextView) view.findViewById(R.id.txt_date_lcl);
            view.setTag(holder);
        }else{
            holder = (MyViewHolderaa) view.getTag();
        }
        holder.tvMsg.setText(mMessages.get(i).getMeg());
        holder.tvCome.setText(mMessages.get(i).getComForm());
        holder.tvDate.setText(mMessages.get(i).getDate());
        return view;
    }
}

class MyViewHolderaa {
    TextView tvMsg;
    TextView tvCome;
    TextView tvDate;
}
