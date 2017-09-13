package clerk.sz28yun.com.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import clerk.sz28yun.com.R;
import clerk.sz28yun.com.base.BroadcastAction;
import clerk.sz28yun.com.base.CCJActivity;
import clerk.sz28yun.com.base.UpdateManager;
import clerk.sz28yun.com.cache.APIParamsCache;
import clerk.sz28yun.com.fragment.MainBusinessFragment;
import clerk.sz28yun.com.fragment.MainHomeFragment;
import clerk.sz28yun.com.fragment.MainMineFragment;
import clerk.sz28yun.com.fragment.MainPerfermanceFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import butterknife.Bind;
import per.sue.gear2.adapter.GearFragmentAdapter;
import per.sue.gear2.bean.GearFragmentBean;
import per.sue.gear2.utils.GearLog;
import per.sue.gear2.widget.nav.GearTabPageIndicator;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class MainActivity extends CCJActivity  {

    private static final String TAG = "MainActivity";
    @Bind(R.id.tab_pager_indicator)
    GearTabPageIndicator tabPagerIndicator;
    @Bind(R.id.main_viewpager)
    ViewPager viewpagerMain;

    //声明mLocationOption对象
    public AMapLocationClientOption locationClientOption = null;
    private AMapLocationClient aMapLocationClient;


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(BroadcastAction.ACTION_MIAN_NAV_BUSNESS.equals(intent.getAction()) ){
                viewpagerMain.setCurrentItem(1, false);
            }

        }
    };

    public static Intent startIntent(Context context) {
        Intent intent;
        intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        hasTitleBar = false;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }


    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        viewpagerMain.setAdapter(new GearFragmentAdapter(getSupportFragmentManager(), getTextTabFragmentb()));
        tabPagerIndicator.setViewPager(viewpagerMain);
        registerReceiver(broadcastReceiver, new IntentFilter(BroadcastAction.ACTION_MIAN_NAV_BUSNESS));//注册定位到业务界面广播
        setLocation();

        UpdateManager.getInstance().checkUpdate(MainActivity.this);
    }


    private List<GearFragmentBean> getTextTabFragmentb() {
        List<GearFragmentBean> list = new ArrayList<>();
        list.add(new GearFragmentBean(MainHomeFragment.class, null, "首页", R.drawable.selector_main_nav_home));
        list.add(new GearFragmentBean(MainBusinessFragment.class, null, "业务", R.drawable.selector_main_nav_business));
        list.add(new GearFragmentBean(MainPerfermanceFragment.class, null, "业绩", R.drawable.selector_main_nav_performancered));
        list.add(new GearFragmentBean(MainMineFragment.class, null, "我的", R.drawable.selector_main_nav_mine));
        return list;
    }

    /**
     * 设置定位监听
     */
    private void setLocation() {
        aMapLocationClient = new AMapLocationClient(this);
        //初始化定位参数
        locationClientOption = new AMapLocationClientOption();
        //设置定位监听
        aMapLocationClient.setLocationListener(new AMapLocationListener(){
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        amapLocation.getLatitude();//获取纬度
                        amapLocation.getLongitude();//获取经度
                        amapLocation.getAccuracy();//获取精度信息
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date(amapLocation.getTime());
                        df.format(date);//定位时间
                        //GearLog.e(TAG, "location success" + amapLocation.getAddress());
                        APIParamsCache.getInstance().setaMapLocation(amapLocation);
                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        GearLog.e("AmapError","location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                    }
                }
            }
        });
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        locationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        locationClientOption.setInterval(10000); // 10秒定位一次
        //设置定位参数
        aMapLocationClient.setLocationOption(locationClientOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        aMapLocationClient.startLocation();

    }


    public void toPerformance(){
        viewpagerMain.setCurrentItem(2, false);
    }

    public void toBusiness(){
        viewpagerMain.setCurrentItem(1, false);
    }

}
