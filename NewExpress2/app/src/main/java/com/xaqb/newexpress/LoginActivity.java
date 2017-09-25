package com.xaqb.newexpress;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xaqb.newexpress.impl.OnOkDataFinishedListener;
import com.xaqb.newexpress.util.AppUtils;
import com.xaqb.newexpress.util.GsonUtil;
import com.xaqb.newexpress.util.HttpUrlUtils;
import com.xaqb.newexpress.util.LogUtil;
import com.xaqb.newexpress.util.SPUtil;

import java.util.Map;


public class LoginActivity extends BaseActivity implements OnOkDataFinishedListener {
    private Button mBtLogin;
    private TextView mTvFind;
    private LoginActivity instance;
    private EditText mEtPhone;
    private EditText mEtPsw;
    private String mPhone;
    private String mPsw;

    @Override
    public void initTitle() {
        setTitleBarVisibleB(View.GONE);
    }

    @Override
    public void initView() {


        if ((Boolean) SPUtil.get(this, "FirstIn", true) == true) {
            setContentView(R.layout.activity_login);
            instance = this;
            setStatusColorB(instance.getResources().getColor(R.color.main), View.VISIBLE, true);
        } else {
            instance = this;
            setStatusColorB(instance.getResources().getColor(R.color.main), View.VISIBLE, true);
            setContentView(R.layout.activity_login);
            intentB(instance, MainActivity.class);
            finish();
        }
        mBtLogin = (Button) findViewById(R.id.bt_login_login);
        mTvFind = (TextView) findViewById(R.id.tv_find_login);
        mEtPsw = (EditText) findViewById(R.id.et_psw_login);
        mEtPhone = (EditText) findViewById(R.id.et_phone_login);

    }

    @Override
    public void initAvailable() {
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!SPUtil.get(instance, "password", "").toString().equals("")) {
            mEtPhone.setText(SPUtil.get(instance, "loginNum", "").toString());
            mEtPsw.setText(SPUtil.get(instance, "password", "").toString());
        } else if (SPUtil.get(instance, "password", "").toString().equals("")) {//找回密码或者新注册时
            mEtPhone.setText(SPUtil.get(instance, "loginNum", "").toString());
            mEtPsw.setText("");
        }
    }

    @Override
    public void addEvent() {
        mBtLogin.setOnClickListener(instance);
        mTvFind.setOnClickListener(instance);
        setOnDataFinishedListener(instance);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login_login://登录按钮
                login();
                break;
            case R.id.tv_find_login://找回密码
                intentB(instance, FindActivity.class);
                break;
        }
    }

    /**
     * 登录
     */
    public void login() {
        mPhone = mEtPhone.getText().toString().trim();
        mPsw = mEtPsw.getText().toString().trim();
        if (mPhone.equals("")) {
            showToastB("请输入账号");
            return;
        } else if (mPsw.equals("")) {
            showToastB("请输入密码");
            return;
        } else if (AppUtils.getInfo(instance).equals("")) {
            showToastB("无法获取当前设备号");
            return;
        }

        //进行POST网络请求
        mLoadingDialog.show("正在登陆");
        okPostConnectionB(HttpUrlUtils.getHttpUrl().userLogin(), "user", mPhone, "pwd", mPsw);
    }

    @Override
    public void okDataFinishedListener(String s) {
        mLoadingDialog.dismiss();
        LogUtil.i(s.toString());
        Map<String, Object> map = GsonUtil.GsonToMaps(s);
        if (map.get("state").toString().equals("1.0")) {
            showToastB(map.get("mess").toString());

            return;
        } else if (map.get("state").toString().equals("0.0")) {
            Map<String, Object> data = GsonUtil.JsonToMap(GsonUtil.GsonString(map.get("table")));
            LogUtil.i(data.toString());


        /*
        {"state":0,"mess":"success","table":
            {"gn_id":1,"gn_name":"ceshi","gn_pwd":"e590a47ee74db85ee5943cefa289ff87",
            "gn_salt":"urfc","gn_headpic":"","gn_realname":"","gn_nickname":"","gn_mp":"",
            "gn_is_open":1,"gn_logintime":1505122165,"gn_logincount":90,"gn_loginip":"192.168.0.121",
            "gn_createtime":0,"gn_updatetime":0,"gn_is_real":1,"gn_securityorg":"610102434000","gn_role":0,
            "so_name":"中山门派出所","access_token":"8502e07170b80fea59fce1e9d44580eb",
            "refresh_token":"6f93ce466c86153fd5fc275359dc4e46","expire_in":7200}}
         */

            LogUtil.i(data.get("access_token").toString());

            SPUtil.put(instance, "access_token", data.get("access_token").toString());//access_token值
            SPUtil.put(instance, "loginNum", mPhone);
            SPUtil.put(instance, "password", mPsw);
            showToastB("登录成功");
            SPUtil.put(this, "FirstIn", false);
            intentB(instance, MainActivity.class);
            finish();
        } else {
            showToastB("请输入正确的用户名或者密码");
        }


    }


}