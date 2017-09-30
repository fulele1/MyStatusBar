package mag.com.xaqb.fireprotectionmag;

public class DailyMagActivity extends BaseActivity {
    private DailyMagActivity instance;


    @Override
    public void initTitle() {
        setTitleB("日志管理");
    }

    @Override
    public void initView() {
        instance = this;
        setContentView(R.layout.activity_daily_mag);
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
