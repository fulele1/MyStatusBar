package com.xaqb.newexpress;


import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xaqb.newexpress.Adapter.FragmentAdapter;
import com.xaqb.newexpress.fragment.OneFragment;
import com.xaqb.newexpress.fragment.ThreeFragment;
import com.xaqb.newexpress.fragment.TwoFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private MainActivity instance;
    private ViewPager mVpg;
    private RadioGroup mRgp;
    private RadioButton mRbOne;
    private RadioButton mRbTwo;
    private RadioButton mRbThree;
    private List<Fragment> mFrags;
    private FragmentManager mFragmentManager;

    @Override
    public void initTitle() {
        setTitleBarVisibleB(View.GONE);
    }

    @Override
    public void initView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        setContentView(R.layout.activity_main);
        instance = this;
//        setStatusColorB(instance.getResources().getColor(R.color.main),View.VISIBLE,true);

        mFragmentManager = this.getSupportFragmentManager();
        mVpg = (ViewPager) findViewById(R.id.vpg_main);
        mRgp = (RadioGroup) findViewById(R.id.rgp_main);
        mRbOne = (RadioButton) findViewById(R.id.rb_one_main);
        mRbTwo = (RadioButton) findViewById(R.id.rb_two_main);
        mRbThree = (RadioButton) findViewById(R.id.rb_three_main);
    }

    @Override
    public void initAvailable() {
    }

    @Override
    public void initData() {

        mFrags = new ArrayList<>();
        mFrags.add(new OneFragment());
        mFrags.add(new TwoFragment());
        mFrags.add(new ThreeFragment());
    }

    @Override
    public void addEvent() {

        mVpg.setAdapter(new FragmentAdapter(mFragmentManager,mFrags));
        mRgp.setOnCheckedChangeListener(new CheckedChange());
        mVpg.setOnPageChangeListener(new pageChange());
    }

    /**
     * 页面滑动后设置当前为点击
     */
    class pageChange implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        @Override
        public void onPageSelected(int position) {
            switch (position){
                case 0:
                    mRbOne.setChecked(true);
                    mRbOne.setTextColor(instance.getResources().getColor(R.color.main));
                    mRbTwo.setTextColor(Color.BLACK);
                    mRbThree.setTextColor(Color.BLACK);
                    break;
                case 1:
                    mRbTwo.setChecked(true);
                    mRbTwo.setTextColor(instance.getResources().getColor(R.color.main));
                    mRbOne.setTextColor(Color.BLACK);
                    mRbThree.setTextColor(Color.BLACK);
                    break;
                case 2:
                    mRbThree.setChecked(true);
                    mRbThree.setTextColor(instance.getResources().getColor(R.color.main));
                    mRbOne.setTextColor(Color.BLACK);
                    mRbTwo.setTextColor(Color.BLACK);
                    break;
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }


    /**
     * 点击改变页面的监听事件
     */
    class CheckedChange implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_one_main:
                    mVpg.setCurrentItem(0);

                    break;
                case R.id.rb_two_main:
                    mVpg.setCurrentItem(1);


                    break;
                case R.id.rb_three_main:
                    mVpg.setCurrentItem(2);

                    break;
            }
        }
    }


    @Override
    public void onClick(View v) {
        }

    }