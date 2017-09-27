package mag.com.xaqb.fireprotectionmag;


import android.view.View;

public class ClueDetialActivity extends BaseActivity {
    ClueDetialActivity instance;

    @Override
    public void initTitle() {
        setTitleB("线索详情");
    }

    @Override
    public void initView() {
        instance = this;
        setContentView(R.layout.activity_clue_detial);
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
