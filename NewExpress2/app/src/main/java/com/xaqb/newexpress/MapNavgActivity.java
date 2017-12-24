package com.xaqb.newexpress;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapPageType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.xaqb.newexpress.util.AmapTTSController;
import com.xaqb.newexpress.util.LogUtil;

import java.util.ArrayList;
import java.util.List;


public class MapNavgActivity extends BaseActivity implements INaviInfoCallback {
    private MapNavgActivity instance;
    private String[] examples = new String[]{"起终点算路", "无起点算路", "途径点算路", "直接导航"};
    private AmapTTSController amapTTSController;
    LatLng p1 = new LatLng(39.993266, 116.473193);//首开广场
    LatLng p2 = new LatLng(39.917337, 116.397056);//故宫博物院
    LatLng p3 = new LatLng(39.904556, 116.427231);//北京站
    LatLng p4 = new LatLng(39.773801, 116.368984);//新三余公园(南5环)
    LatLng p5 = new LatLng(40.041986, 116.414496);//立水桥(北5环)

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {
                LogUtil.e("0");
                AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), new AmapNaviParams(new Poi("北京站", p3, ""), null, new Poi("故宫博物院", p5, ""), AmapNaviType.DRIVER), MapNavgActivity.this);
            } else if (position == 1) {
                LogUtil.e("1");
                AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), new AmapNaviParams(null, null, new Poi("故宫博物院", p2, ""), AmapNaviType.DRIVER), MapNavgActivity.this);
            } else if (position == 2) {
                LogUtil.e("2");
                List<Poi> poiList = new ArrayList();
                poiList.add(new Poi("首开广场", p1, ""));
                poiList.add(new Poi("故宫博物院", p2, ""));
                poiList.add(new Poi("北京站", p3, ""));
                AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), new AmapNaviParams(new Poi("立水桥(北5环)", p5, ""), poiList, new Poi("新三余公园(南5环)", p4, ""), AmapNaviType.DRIVER), MapNavgActivity.this);
            } else if (position == 3) {
                LogUtil.e("3");
                Poi start = new Poi("立水桥(北5环)", p5, "");//起点
                //<editor-fold desc="途径点">
                List<Poi> poiList = new ArrayList();
                poiList.add(new Poi("首开广场", p1, ""));
                poiList.add(new Poi("故宫博物院", p2, ""));
                poiList.add(new Poi("北京站", p3, ""));
                //</editor-fold>

                Poi end = new Poi("新三余公园(南5环)", p4, "");//终点
                AmapNaviParams amapNaviParams = new AmapNaviParams(start, null, end, AmapNaviType.DRIVER, AmapPageType.NAVI);
                AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), amapNaviParams, MapNavgActivity.this);
            }
        }
    };



    @Override
    public void initTitle() {
        setTitleB("地图导航");
    }

    @Override
    public void initView() {
        instance = this;
        setStatusColorB(instance.getResources().getColor(R.color.wirte), View.VISIBLE,true);
        setContentView(R.layout.activity_map_navg);
        initViews();
        amapTTSController = AmapTTSController.getInstance(getApplicationContext());
//        amapTTSController.init();
    }
    private void initViews() {
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, examples));
        setTitle("导航SDK " + AMapNavi.getVersion());
        listView.setOnItemClickListener(mItemClickListener);
    }


    @Override
    public void initAvailable() {
        LogUtil.e("initAvailable");
    }

    @Override
    public void initData() {
        LogUtil.e("initData");
    }

    @Override
    public void addEvent() {
        LogUtil.e("addEvent");
    }

    @Override
    public void onInitNaviFailure() {
        LogUtil.e("onInitNaviFailure");
    }

    @Override
    public void onGetNavigationText(String s) {
        amapTTSController.onGetNavigationText(s);
    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {
        LogUtil.e("onLocationChange");
    }

    @Override
    public void onArriveDestination(boolean b) {
        LogUtil.e("onArriveDestination");
    }

    @Override
    public void onStartNavi(int i) {
        LogUtil.e("onStartNavi");
    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {
        LogUtil.e("onCalculateRouteSuccess");
    }

    @Override
    public void onCalculateRouteFailure(int i) {
        LogUtil.e("onCalculateRouteFailure");
    }

    @Override
    public void onStopSpeaking() {
        amapTTSController.stopSpeaking();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        amapTTSController.destroy();
    }

}
