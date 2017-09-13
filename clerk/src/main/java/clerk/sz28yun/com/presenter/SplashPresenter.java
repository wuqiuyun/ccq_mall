package clerk.sz28yun.com.presenter;

import android.content.Context;

import clerk.sz28yun.com.api.CommonAPI;
import clerk.sz28yun.com.bean.ConfigBean;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;
import java.util.Timer;
import java.util.TimerTask;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;
import per.sue.gear2.utils.GearLog;
import rx.Subscriber;

/**
 * Created by sue on 2016/11/15.
 */
public class SplashPresenter extends AbsPresenter {

    private static final String TAG = "SplashPresenter";
    private SplashView splashView;
    private Context context;

    private Timer timer = null;
    private int currentTimes = 3;


    public SplashPresenter(Context context, SplashView splashView) {
        this.splashView = splashView;
        this.context = context;
    }

    /**
     * 加载初始化数据
     */
    public void loadConfig(){
        ConfigBean configBean = GlobalDataStorageCache.getInstance().getConfigData();
        if(null != configBean){
            GearLog.i(TAG, "load config data on cache");
            //每次打开都要获取一遍， 这里的判断主要是为了解决第一次没配置时是先获取配置再显示启动页逻辑， 第二次启动后，先用缓存显示在获取
            splashView.onSuccess(configBean);
            new CommonAPI().getConfig().subscribe(
                    bean -> {
                        bean.API_DOMAIN = bean.HP +  bean.API_DOMAIN;
                        GlobalDataStorageCache.getInstance().storeConfigData(bean);
                    }
            );
        }else{
            GearLog.i(TAG, "load config data on net");
            new CommonAPI().getConfig().subscribe(new Subscriber<ConfigBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    splashView.onError(-1, e.getMessage());

                }

                @Override
                public void onNext(ConfigBean configBean) {
                    configBean.API_DOMAIN = configBean.HP +  configBean.API_DOMAIN;
                    splashView.onSuccess(configBean);
                    GlobalDataStorageCache.getInstance().storeConfigData(configBean);
                }
            });
        }
    }

    /**
     * 开始倒计时
     */
    public void startConuntDown(){
        if(null !=  this.timer){
            this.timer.cancel();
            this.timer = null;
        }
        this.timer = new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                GearLog.e(TAG, "count down " + currentTimes);
                splashView.onCountDown(currentTimes);
                currentTimes -- ;
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    public interface  SplashView extends GearResultView<ConfigBean>{
        void onCountDown(int times);
    }


    @Override
    public void destroy() {
        super.destroy();
        if(null != this.timer){
            this.timer.cancel();
        }
    }
}
