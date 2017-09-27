package mag.com.xaqb.fireprotectionmag;

import android.view.View;

public class DataDetailActivity extends BaseActivity {
    private DataDetailActivity instance;


    @Override
    public void initTitle() {
        setTitleB("数据统计详情");
        setTitleBarColor(this.getResources().getColor(R.color.main));
        setTitleBtnColor(this.getResources().getColor(R.color.wirte));
    }

    @Override
    public void initView() {
        instance = this;
        setContentView(R.layout.activity_data_detail);
        setStatusColorB(instance.getResources().getColor(R.color.main), View.VISIBLE,true);

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
