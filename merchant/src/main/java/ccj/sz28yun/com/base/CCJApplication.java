package ccj.sz28yun.com.base;

import android.app.Application;
import android.content.pm.PackageManager;


import com.umeng.socialize.PlatformConfig;

import java.util.HashMap;
import java.util.Map;

import ccj.sz28yun.com.BuildConfig;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import ccj.sz28yun.com.help.AppFileStoreHepler;
import per.sue.gear2.net.ApiConnectionFactory;
import per.sue.gear2.net.session.CookiesManager;
import per.sue.gear2.utils.GearLog;
import per.sue.gear2.utils.SHA1Utils;

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
        CookiesManager.getInstance().initialize(getApplicationContext());
        GlobalDataStorageCache.getInstance().initialize(getApplicationContext());
        AppFileStoreHepler.getInstance().initialize(getApplicationContext());
        // GearImageLoad.getSingleton(getApplicationContext()).setBaseUrl(BaseAPI.BASE_URL);
        initUmeng();
       // GearLog.e(TAG, SHA1Utils.sHA1(getApplicationContext()));获取shai码
    }

    private void initUmeng() {
        //微信 appid appsecret
        PlatformConfig.setWeixin("wx32b6f6a596f3eb6f", "00992cc0c8e7427e699ee1dff5f859ac");
        //新浪微博 appkey appsecret have been changed
        PlatformConfig.setSinaWeibo("3666319414", "938c8da9a55aa72a4f9c3f82c0380631");
        // qq qzone appid appkey have been changed
        PlatformConfig.setQQZone("1105608160", "Ar32E1dkYpVrgE6U");
    }

    private Map<String, String> getHeads(){
        Map<String, String> map = new HashMap<>();
        String sysVersionName = android.os.Build.VERSION.RELEASE;
//        map.put("sys", sysVersionName);
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
