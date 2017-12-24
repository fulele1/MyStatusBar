package com.fl.mynavigate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Poi start = new Poi("三元桥", new LatLng(39.96087,116.45798), "");
/**终点传入的是北京站坐标,但是POI的ID "B000A83M61"对应的是北京西站，所以实际算路以北京西站作为终点**/
        Poi end = new Poi("北京站", new LatLng(39.904556, 116.427231), "B000A83M61");
        List<Poi> wayList = new ArrayList();//途径点目前最多支持3个。
        wayList.add(new Poi("团结湖", new LatLng(39.93413,116.461676), ""));
        wayList.add(new Poi("呼家楼", new LatLng(39.923484,116.461327), ""));
        wayList.add(new Poi("华润大厦", new LatLng(39.912914,116.434247), ""));
        AmapNaviPage.getInstance().showRouteActivity(this, new AmapNaviParams(start, wayList, end, AmapNaviType.DRIVER), new naviInfoCallback());

    }
    public class naviInfoCallback implements INaviInfoCallback{
        @Override
        public void onInitNaviFailure() {

        }

        @Override
        public void onGetNavigationText(String s) {

        }

        @Override
        public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

        }

        @Override
        public void onArriveDestination(boolean b) {

        }

        @Override
        public void onStartNavi(int i) {

        }

        @Override
        public void onCalculateRouteSuccess(int[] ints) {

        }

        @Override
        public void onCalculateRouteFailure(int i) {

        }

        @Override
        public void onStopSpeaking() {

        }
    }

}
