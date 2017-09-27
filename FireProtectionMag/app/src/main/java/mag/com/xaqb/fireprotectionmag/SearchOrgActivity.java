package mag.com.xaqb.fireprotectionmag;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mag.com.xaqb.fireprotectionmag.Listview.BrandAdapter;
import mag.com.xaqb.fireprotectionmag.Listview.BrandBean;
import mag.com.xaqb.fireprotectionmag.Listview.LetterIndexView;
import mag.com.xaqb.fireprotectionmag.Listview.PinnedSectionListView;
import mag.com.xaqb.fireprotectionmag.impl.OnOkDataFinishedListener;
import mag.com.xaqb.fireprotectionmag.util.ChangeUtil;
import mag.com.xaqb.fireprotectionmag.util.GsonUtil;
import mag.com.xaqb.fireprotectionmag.util.HttpUrlUtils;
import mag.com.xaqb.fireprotectionmag.util.LogUtil;
import mag.com.xaqb.fireprotectionmag.util.SPUtil;


public class SearchOrgActivity extends BaseActivity implements OnOkDataFinishedListener {

    /**
     * item标识为0
     */
    public static final int ITEM = 0;
    /**
     * item标题标识为1
     */
    public static final int TITLE = 1;
    public HashMap<String, Integer> map_IsHead;
    private SearchOrgActivity instance;
    private EditText edit_search;
    private PinnedSectionListView listView;
    private LetterIndexView letterIndexView;
    private TextView txt_center;
    private ArrayList<BrandBean> list_all;
    private ArrayList<BrandBean> list_show;
    private BrandAdapter adapter;

    @Override
    public void initTitle() {
        setTitle("管辖机构列表");
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_search_org);
        instance = this;
        setStatusColorB(instance.getResources().getColor(R.color.wirte), View.VISIBLE,true);
        edit_search = (EditText) findViewById(R.id.edit_search_org);
        listView = (PinnedSectionListView) findViewById(R.id.phone_listview_org);
        letterIndexView = (LetterIndexView) findViewById(R.id.phone_LetterIndexView_org);
        txt_center = (TextView) findViewById(R.id.phone_txt_center_org);
    }



    @Override
    public void initAvailable() {

    }

    @Override
    public void initData() {
        list_all = new ArrayList<>();
        list_show = new ArrayList<>();
        map_IsHead = new HashMap<>();
        adapter = new BrandAdapter(this, list_show, map_IsHead);
        listView.setAdapter(adapter);

        // 开启异步加载数据
//        new Thread(runnable).start();
        okConnection();
    }



    public void okConnection() {
//    http://192.168.0.112:8888/v1/governor/common/data/securityorg?access_token=edd0923f1c233c1ae54c5688fe1bee9a
        LogUtil.i("管辖机构"+ SPUtil.get(instance,"access_token",null));
        okGetConnectionB(HttpUrlUtils.getHttpUrl().getOrg()+"?access_token="+ SPUtil.get(instance,"access_token",null));//进行网络连接
        this.setOnDataFinishedListener(instance);
    }
    /**
     * 获取数据并进行排序
     */
    public void getData() {

        //按拼音排序
        SearchOrgActivity.MemberSortUtil sortUtil = new SearchOrgActivity.MemberSortUtil();
        Collections.sort(list_all, sortUtil);

        // 初始化数据，顺便放入把标题放入map集合
        for (int i = 0; i < list_all.size(); i++) {
            BrandBean cityBean = list_all.get(i);
            if (!map_IsHead.containsKey(cityBean.getHeadChar())) {// 如果不包含就添加一个标题
                BrandBean cityBean1 = new BrandBean();
                // 设置名字
                cityBean1.setName(cityBean.getName());
                // 设置标题type
                cityBean1.setType(SearchOrgActivity.TITLE);
                list_show.add(cityBean1);

                // map的值为标题的下标
                map_IsHead.put(cityBean1.getHeadChar(), list_show.size() - 1);
            }
            list_show.add(cityBean);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void addEvent() {

        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                //显示和隐藏字母条
                if (!editable.toString().equals("")) {
                    letterIndexView.setVisibility(View.GONE);
                } else if (editable.toString().equals("")) {
                    letterIndexView.setVisibility(View.VISIBLE);
                }

                //重新获取需要现实的数据
                list_show.clear();
                map_IsHead.clear();
                //把输入的字符改成大写
                String search = editable.toString().trim().toUpperCase();

                if (TextUtils.isEmpty(search)) {
                    for (int i = 0; i < list_all.size(); i++) {
                        BrandBean bean = list_all.get(i);
                        //中文字符匹配首字母和英文字符匹配首字母
                        if (!map_IsHead.containsKey(bean.getHeadChar())) {// 如果不包含就添加一个标题
                            BrandBean bean1 = new BrandBean();
                            // 设置名字
                            bean1.setName(bean.getName());
                            // 设置标题type
                            bean1.setType(SearchOrgActivity.TITLE);
                            list_show.add(bean1);
                            // map的值为标题的下标
                            map_IsHead.put(bean1.getHeadChar(),
                                    list_show.size() - 1);
                        }
                        // 设置Item type
                        bean.setType(SearchOrgActivity.ITEM);
                        list_show.add(bean);
                    }
                } else {
                    for (int i = 0; i < list_all.size(); i++) {
                        BrandBean bean = list_all.get(i);
                        //中文字符匹配首字母和英文字符匹配首字母
                        if (bean.getName().indexOf(search) != -1 || bean.getName_en().indexOf(search) != -1) {
                            if (!map_IsHead.containsKey(bean.getHeadChar())) {// 如果不包含就添加一个标题
                                BrandBean bean1 = new BrandBean();
                                // 设置名字
                                bean1.setName(bean.getName());
                                // 设置标题type
                                bean1.setType(SearchOrgActivity.TITLE);
                                list_show.add(bean1);
                                // map的值为标题的下标
                                map_IsHead.put(bean1.getHeadChar(),
                                        list_show.size() - 1);
                            }
                            // 设置Item type
                            bean.setType(SearchOrgActivity.ITEM);
                            list_show.add(bean);
                        }
                    }
                }
                adapter.notifyDataSetChanged();

            }
        });


        // 右边字母竖排的初始化以及监听
        letterIndexView.init(new LetterIndexView.OnTouchLetterIndex() {
            //实现移动接口
            @Override
            public void touchLetterWitch(String letter) {
                // 中间显示的首字母
                txt_center.setVisibility(View.VISIBLE);
                txt_center.setText(letter);
                // 首字母是否被包含
                if (adapter.map_IsHead.containsKey(letter)) {
                    // 设置首字母的位置
                    listView.setSelection(adapter.map_IsHead.get(letter));
                }
            }

            //实现抬起接口 隐藏字母
            @Override
            public void touchFinish() {
                txt_center.setVisibility(View.GONE);
            }
        });


        /**子条目的点击事件 */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (list_show.get(i).getType() == SearchOrgActivity.ITEM) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("coms", list_show.get(i).getName());
                    bundle.putString("code", list_show.get(i).getCity_id());
                    intent.putExtras(bundle);
                    instance.setResult(RESULT_OK, intent);
                    SearchOrgActivity.this.finish();
                }
            }
        });
    }

    @Override
    public void okDataFinishedListener(String s) {

        if (s.startsWith("0")) {
            //响应成功
            String str = ChangeUtil.procRet(s);
            str = str.substring(1, str.length());
            List<Map<String, Object>> data = GsonUtil.GsonToListMaps(str);
            for (int i = 0; i < data.size(); i++) {
                BrandBean cityBean = new BrandBean();
                cityBean.setName(data.get(i).get("soname").toString());
                cityBean.setCity_id(data.get(i).get("socode").toString());
                list_all.add(cityBean);
            }
        } else {
            //响应失败
            Toast.makeText(instance, "未查询到有效数据", Toast.LENGTH_SHORT).show();
        }
//        loadingDialog.dismiss();

        getData();
    }


    public class MemberSortUtil implements Comparator<BrandBean> {
        /**
         * 按拼音排序
         */
        @Override
        public int compare(BrandBean lhs, BrandBean rhs) {
            Comparator<Object> cmp = Collator
                    .getInstance(java.util.Locale.CHINA);
            return cmp.compare(lhs.getName_en(), rhs.getName_en());
        }
    }
}
