package mag.com.xaqb.fireprotectionmag;

import android.view.View;

public class MapNavgActivity extends BaseActivity {
    private MapNavgActivity instance;


    @Override
    public void initTitle() {
        setTitleB("地图导航");
    }

    @Override
    public void initView() {
        instance = this;
        setStatusColorB(instance.getResources().getColor(R.color.wirte), View.VISIBLE,true);
        setContentView(R.layout.activity_map_navg);
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
