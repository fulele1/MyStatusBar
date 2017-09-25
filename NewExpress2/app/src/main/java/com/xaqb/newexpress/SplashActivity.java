package com.xaqb.newexpress;


import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

public class SplashActivity extends BaseActivity {

    private SplashActivity instance;

    @Override
    public void initTitle() {
        setTitleBarVisibleB(View.GONE);
    }

    @Override
    public void initView() {
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_leader);
        instance = this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                intentB(instance,LoginActivity.class);
                finish();
            }
        },1500);
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
