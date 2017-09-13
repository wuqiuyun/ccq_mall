package ccj.sz28yun.com.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;


import butterknife.Bind;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.ConfigBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import ccj.sz28yun.com.presenter.SplashPresenter;
import per.sue.gear2.controll.GearImageLoad;

/**
 * 启动页 /首屏
 * Created by sue on 2016/11/15.
 */
public class SplashActivity extends CCJActivity implements SplashPresenter.SplashView {

    @Bind(R.id.bg_ad_view)
    ImageView bgAdView;
    @Bind(R.id.count_down_tv)
    TextView countDownTV;

    private SplashPresenter splashPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        hasTitleBar = false;
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        splashPresenter  = new SplashPresenter(getApplication(), this);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                splashPresenter.loadConfig();
//            }
//        }, 2000);
        splashPresenter.loadConfig();
    }


    @Override
    public void onCountDown(final int times) {
       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               if(times == 0){//秒小于0时就跳转
                   skip();
                   finish();
               }else if(times > 0){
                   countDownTV.setText(times + "");
//                   if(times == 2){
//                       bgAdView.setVisibility(View.VISIBLE);
//                   }
               }
           }
       });
    }

    @Override
    public void onSuccess(ConfigBean result) {
        GearImageLoad.getSingleton(getApplication()).load(result.STORE_FP, bgAdView, 0, new Callback() {
            @Override
            public void onSuccess() {
                //图片加载成功再显示
                bgAdView.setVisibility(View.VISIBLE);
                countDownTV.setVisibility(View.VISIBLE);
                splashPresenter.startConuntDown();
            }

            @Override
            public void onError() {
                countDownTV.setVisibility(View.VISIBLE);
                splashPresenter.startConuntDown();
            }
        });
    }

    private void skip(){
        if(null == GlobalDataStorageCache.getInstance().getUserData()){
            startActivity(AccountLoginActivity.startIntent(getActivity()));
        }else{
            startActivity(MainActivity.startIntent(getActivity()));
        }

    }

    @Override
    protected void onDestroy() {
        splashPresenter.destroy();
        super.onDestroy();
    }
}
