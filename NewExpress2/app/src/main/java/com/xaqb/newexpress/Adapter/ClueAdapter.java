package com.xaqb.newexpress.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xaqb.newexpress.R;
import com.xaqb.newexpress.entity.Clue;

import java.util.List;

/**
 * Created by lenovo on 2017/9/8.
 */

public class ClueAdapter extends BaseAdapter{

    private Context mContext;
    private List<Clue> mClues;
    public ClueAdapter(Context context, List<Clue> clues) {
        mContext = context;
        mClues = clues;
    }

    @Override
    public int getCount() {
        return mClues.size();
    }

    @Override
    public Object getItem(int i) {
        return mClues.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    MyViewHoldera holder;
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            holder = new MyViewHoldera();
            view = LayoutInflater.from(mContext).inflate(R.layout.list_clue,null);
            holder.tvMsg = (TextView) view.findViewById(R.id.txt_message_lcl);
            holder.tvCome = (TextView) view.findViewById(R.id.txt_come_lcl);
            holder.tvDate = (TextView) view.findViewById(R.id.txt_date_lcl);
            view.setTag(holder);
        }else{
            holder = (MyViewHoldera) view.getTag();
        }
        holder.tvMsg.setText(mClues.get(i).getMessage());
        holder.tvCome.setText(mClues.get(i).getCome_form());
        holder.tvDate.setText(mClues.get(i).getEmployee()+"/"+mClues.get(i).getDate());
        return view;
    }
}

class MyViewHoldera {
    TextView tvMsg;
    TextView tvCome;
    TextView tvDate;
}
