package mag.com.xaqb.fireprotectionmag;


import android.view.View;

public class MsgDetailActivity extends BaseActivity {
    private MsgDetailActivity instance;

    @Override
    public void initTitle() {
        setTitleB("通知详情");
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_msg_detail);
        instance = this;
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
