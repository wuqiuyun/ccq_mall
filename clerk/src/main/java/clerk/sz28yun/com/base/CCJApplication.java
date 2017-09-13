package clerk.sz28yun.com.base;

import android.app.Application;
import android.content.pm.PackageManager;

import clerk.sz28yun.com.BuildConfig;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;
import clerk.sz28yun.com.help.AppFileStoreHepler;
import com.umeng.socialize.PlatformConfig;

import java.util.HashMap;
import java.util.Map;

import per.sue.gear2.net.ApiConnectionFactory;
import per.sue.gear2.net.session.CookiesManager;
import per.sue.gear2.utils.GearLog;
import per.sue.gear2.utils.SHA1Utils;

//
//                                  _oo8oo_
//                                 o8888888o
//                                 88" . "88
//                                 (| -_- |)
//                                 0\  =  /0
//                               ___/'==='\___
//                             .' \\|     |// '.
//                            / \\|||  :  |||// \
//                           / _||||| -:- |||||_ \
//                          |   | \\\  -  /// |   |
//                          | \_|  ''\---/''  |_/ |
//                          \  .-\__  '-'  __/-.  /
//                        ___'. .'  /--.--\  '. .'___
//                     ."" '<  '.___\_<|>_/___.'  >' "".
//                    | | :  `- \`.:`\ _ /`:.`/ -`  : | |
//                    \  \ `-.   \_ __\ /__ _/   .-` /  /
//                =====`-.____`.___ \_____/ ___.`____.-`=====
//                                  `=---=`
//
//
//               ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//
//                          佛祖保佑         永不宕机/永无bug
/**
 * Created by sue on 2016/11/15.
 */
public class CCJApplication extends Application {

    private static final String TAG = "CCJApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        GearLog.LOG_DEBUG = BuildConfig.LOG_DEBUG;

        ApiConnectionFactory.getInstance().initialize(getApplicationContext(), getHeads());
        ApiConnectionFactory.getInstance().initialize(getApplicationContext());
        CookiesManager.getInstance().initialize(getApplicationContext());
        GlobalDataStorageCache.getInstance().initialize(getApplicationContext());
        AppFileStoreHepler.getInstance().initialize(getApplicationContext());
        initUmeng();
       // GearLog.e(TAG, SHA1Utils.sHA1(getApplicationContext()));
    }

    private void initUmeng() {
        //微信 appid appsecret
        PlatformConfig.setWeixin("wx5a45976f7ef126d0", "ae3a4544cffb8b8f6c66fbaba74bb5d4");
        //新浪微博 appkey appsecret have been changed
        PlatformConfig.setSinaWeibo("319735547", "91489195582760dc768537463a20607f");
        // qq qzone appid appkey have been changed
        PlatformConfig.setQQZone("1105483425", "rzVVdKSfYUwAi0Kp");
    }

    private Map<String, String> getHeads(){
        Map<String, String> map = new HashMap<>();
        String sysVersionName = android.os.Build.VERSION.RELEASE;
//        map.put("sys",  sysVersionName + "");
//        map.put("app_name", getPackageName());
        try {
            map.put("ccq", sysVersionName + "," + getPackageName() + "," + getPackageManager().getPackageInfo(getPackageName(), 0).versionName + "");
//            map.put("app_v", getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }
}
