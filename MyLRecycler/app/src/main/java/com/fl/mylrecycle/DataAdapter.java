package com.fl.mylrecycle;

import android.content.Context;
import android.widget.TextView;

import mag.com.xaqb.fireprotectionmag.R;
import mag.com.xaqb.fireprotectionmag.entity.SignIn;


/**
 * Created by fl on 2016/12/30.
 */

public class DataAdapter extends ListBaseAdapter<SignIn> {

    public DataAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.list_signin;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        SignIn item = mDataList.get(position);

        TextView titleText = holder.getView(R.id.info_text);
        titleText.setText(item.title);
    }
}
