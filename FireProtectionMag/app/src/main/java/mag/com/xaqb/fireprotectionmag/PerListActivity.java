package mag.com.xaqb.fireprotectionmag;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mag.com.xaqb.fireprotectionmag.Adapter.PerAdapter;
import mag.com.xaqb.fireprotectionmag.entity.Person;
import mag.com.xaqb.fireprotectionmag.impl.OnOkDataFinishedListener;
import mag.com.xaqb.fireprotectionmag.util.GsonUtil;
import mag.com.xaqb.fireprotectionmag.util.HttpUrlUtils;
import mag.com.xaqb.fireprotectionmag.util.LogUtil;
import mag.com.xaqb.fireprotectionmag.util.SPUtil;

public class PerListActivity extends BaseActivity implements AdapterView.OnItemClickListener,OnOkDataFinishedListener {

    private PerListActivity instance;
    private ListView mList;
    private TextView txt_size;
    private List<Person> people;

    @Override
    public void initTitle() {
        setTitleB("快递员");
    }

    @Override
    public void initView() {
        instance = this;
        setContentView(R.layout.activity_per_list);
        setStatusColorB(instance.getResources().getColor(R.color.wirte), View.VISIBLE,true);
        mList = (ListView) findViewById(R.id.list_pl);
        txt_size = (TextView) findViewById(R.id.txt_size_pl);
        mList.setDivider(new ColorDrawable(getResources().getColor(R.color.background)));
        mList.setDividerHeight(20);
    }

    String com;
    String name;
    String ide;
    String tel;
    String org;
    String access_token;
    @Override
    public void initAvailable() {
        Intent intent = getIntent();
        com = intent.getStringExtra("com");
        name = intent.getStringExtra("name");
        ide = intent.getStringExtra("ide");
        tel = intent.getStringExtra("tel");
        org = intent.getStringExtra("org");
        access_token = SPUtil.get(instance,"access_token","").toString();
        LogUtil.i("快递员列表"+"com"+com+"---name"+name+"---ide"+ide+"---tel"+tel+"---org"+org);

        okPostConnectionB(HttpUrlUtils.getHttpUrl().query_per(),
                "com",com,"name",name,"ide",ide,"org",org,
                "access_token", access_token);
    }

    @Override
    public void initData() {

    }

    @Override
    public void addEvent() {
        setOnDataFinishedListener(instance);
        mList.setOnItemClickListener(instance);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        intentB(instance,PerDetailActivity.class,mPeople.get(position).getId());
    }

    private List<Person> mPeople;

    @Override
    public void okDataFinishedListener(String s) {
        LogUtil.i("快递员列表"+s);
        //{"state":"10","mess":"access_token验证失败"}
        s = "{\n" +
                "  \"state\": 0, \n" +
                "  \"mess\": {\n" +
                "    \"page\": 1, \n" +
                "    \"curr\": 1\n" +
                "  }, \n" +
                "  \"table\": [\n" +
                "    {\n" +
                "      \"com_address\": \"张三\", \n" +
                "      \"com_id\": 1, \n" +
                "      \"com_area\": \"154515646\", \n" +
                "      \"com_name\": \"陕西省\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        Map<String,Object> map= GsonUtil.JsonToMap(s);
        LogUtil.i("企业列表 map"+map);
        if (map.get("state").toString().equals("1")){
            showToastB(map.get("mess").toString());
            return;
        }else if (map.get("state").toString().equals("10")){
            showToastB(map.get("mess").toString());
            return;
        }else if (map.get("state").toString().equals("0")){
            List<Map<String,Object>> data = GsonUtil.GsonToListMaps(GsonUtil.GsonString(map.get("table")));
            LogUtil.i("企业列表 date"+data);
            mPeople = new ArrayList<>();
            for (int j = 0;j<data.size();j++){
                Person person = new Person();
                person.setId(data.get(j).get("com_id").toString());
                person.setIde(data.get(j).get("com_area").toString());
                person.setName(data.get(j).get("com_address").toString());
                person.setTel(data.get(j).get("com_area").toString());
                person.setCom(data.get(j).get("com_name").toString());
                mPeople.add(person);
            }

            mList.setAdapter(new PerAdapter(instance,mPeople));


            Message msg = mHandler.obtainMessage();
            msg.obj = data.size();
            msg.what = 0;
            mHandler.sendMessage(msg);
        }

    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                txt_size.setText(msg.obj.toString());
            }
        }
    };
}
