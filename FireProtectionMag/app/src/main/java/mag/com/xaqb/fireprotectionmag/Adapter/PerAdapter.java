package mag.com.xaqb.fireprotectionmag.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import mag.com.xaqb.fireprotectionmag.R;
import mag.com.xaqb.fireprotectionmag.entity.Person;


/**
 * Created by fl on 2017/3/15.
 */

public class PerAdapter extends BaseAdapter {
    private Context mContext;
    private List<Person> mPeople;
    public PerAdapter(Context context, List<Person> people) {
        mContext = context;
        mPeople = people;
    }

    @Override
    public int getCount() {
        return mPeople.size();
    }

    @Override
    public Object getItem(int i) {
        return mPeople.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    ViewHolder holder;
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.list_per,null);
//
            holder.tvName = (TextView) view.findViewById(R.id.txt_name_lp);
            holder.tvTel = (TextView) view.findViewById(R.id.txt_tel_lp);
            holder.tvCom = (TextView) view.findViewById(R.id.txt_com_lp);
            holder.tvIde = (TextView) view.findViewById(R.id.txt_ide_lp);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.tvName.setText(mPeople.get(i).getName());
        holder.tvTel.setText(mPeople.get(i).getTel());
        holder.tvIde.setText(mPeople.get(i).getIde());
        holder.tvCom.setText(mPeople.get(i).getCom());
        return view;
    }
}

class ViewHolder {
    TextView tvName;
    TextView tvSix;
    TextView tvTel;
    TextView tvIde;
    TextView tvCom;
}
