package mag.com.xaqb.fireprotectionmag;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.Map;

import mag.com.xaqb.fireprotectionmag.impl.OnOkDataFinishedListener;
import mag.com.xaqb.fireprotectionmag.util.GsonUtil;
import mag.com.xaqb.fireprotectionmag.util.HttpUrlUtils;
import mag.com.xaqb.fireprotectionmag.util.LogUtil;
import mag.com.xaqb.fireprotectionmag.util.SPUtil;

public class ComDetialActivity extends BaseActivity implements OnOkDataFinishedListener {


    private ComDetialActivity instance;

    @Override
    public void initTitle() {
        setTitleB("企业详情");
    }


    @Override
    public void initView() {
        instance = this;
        setContentView(R.layout.activity_com_detial);
        setStatusColorB(instance.getResources().getColor(R.color.wirte), View.VISIBLE,true);
        img_pic = (ImageView) findViewById(R.id.img_pic_cd);
        txt_name = (TextView) findViewById(R.id.txt_name_cd);
        txt_com = (TextView) findViewById(R.id.txt_com_cd);
        txt_add = (TextView) findViewById(R.id.txt_address_cd);
        txt_res_per = (TextView) findViewById(R.id.txt_responsible_Per_cd);
        txt_res_tel = (TextView) findViewById(R.id.txt_responsible_tel_cd);
        txt_emp_count = (TextView) findViewById(R.id.txt_employee_count_cd);
        txt_get_count = (TextView) findViewById(R.id.txt_get_count_cd);
        txt_post_count = (TextView) findViewById(R.id.txt_post_count_cd);
    }

    private String code;
    private String access_token;
    @Override
    public void initAvailable() {
        Intent intent = getIntent();
        code = intent.getStringExtra("intentData");
        access_token = SPUtil.get(instance,"access_token","").toString();
        LogUtil.i("企业详情"+code+access_token);
        setOnDataFinishedListener(instance);
        okPostConnectionB(HttpUrlUtils.getHttpUrl().detail_com(),
                "code",code, "access_token", access_token);
    }

    @Override
    public void initData() {

    }

    @Override
    public void addEvent() {

    }

    private ImageView img_pic;
    private TextView txt_name;
    private TextView txt_com;
    private TextView txt_add;
    private TextView txt_res_per;
    private TextView txt_res_tel;
    private TextView txt_emp_count;
    private TextView txt_get_count;
    private TextView txt_post_count;
    @Override
    public void okDataFinishedListener(String s) {
        LogUtil.i("企业详情"+s);
        //{"state":"10","mess":"access_token验证失败"}
        s = "{\"state\":\"0\",\"mess\":\"success\"}";
        Map<String,Object> map= GsonUtil.JsonToMap(s);
        LogUtil.i("企业详情"+map);
        if (map.get("state").toString().equals("1")){
            showToastB(map.get("mess").toString());
            return;
        }else if (map.get("state").toString().equals("10")){
            showToastB(map.get("mess").toString());
            return;
        }else if (map.get("state").toString().equals("0")){

//            txt_name.setText();
//            txt_com.setText();
//            txt_add.setText();
//            txt_res_per.setText();
//            txt_res_tel.setText();
//            txt_emp_count.setText();
//            txt_get_count.setText();
//            txt_post_count.setText();

        }
    }
}
