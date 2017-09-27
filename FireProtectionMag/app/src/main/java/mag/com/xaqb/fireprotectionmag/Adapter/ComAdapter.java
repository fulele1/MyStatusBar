package mag.com.xaqb.fireprotectionmag.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import mag.com.xaqb.fireprotectionmag.R;
import mag.com.xaqb.fireprotectionmag.entity.Company;


/**
 * Created by fl on 2017/3/15.
 */

public class ComAdapter extends BaseAdapter {

    private Context mContext;
    private List<Company> mCompany;
    public ComAdapter(Context context, List<Company> company) {
        mContext = context;
        mCompany = company;
    }

    @Override
    public int getCount() {
        return mCompany.size();
    }

    @Override
    public Object getItem(int i) {
        return mCompany.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    MyViewHolder holder;
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            holder = new MyViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.list_com,null);
            holder.tvCom = (TextView) view.findViewById(R.id.txt_name_lc);
            holder.tvComTotal = (TextView) view.findViewById(R.id.txt_name_total_lc);
            holder.tvAddress = (TextView) view.findViewById(R.id.txt_address_lc);
            view.setTag(holder);
        }else{
            holder = (MyViewHolder) view.getTag();
        }
        holder.tvCom.setText(mCompany.get(i).getComName());
        holder.tvComTotal.setText(mCompany.get(i).getComTotalName());
        holder.tvAddress.setText(mCompany.get(i).getComAddress());
        return view;
    }
}

class MyViewHolder {
    TextView tvCom;
    TextView tvComTotal;
    TextView tvAddress;
}
