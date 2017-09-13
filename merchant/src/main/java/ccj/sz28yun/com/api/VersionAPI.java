package ccj.sz28yun.com.api;

import java.util.HashMap;
import java.util.Map;

import ccj.sz28yun.com.api.base.BaseURL;
import ccj.sz28yun.com.api.base.ClerkObserver;
import ccj.sz28yun.com.bean.ApkInfoBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.net.ApiConnectionFactory;
import per.sue.gear2.net.parser.GearParser;
import rx.Observable;

/**
 * Created by sue on 2017/1/20.
 */
public class VersionAPI {

    public Observable<ApkInfoBean> checkApkVersion(String token, String user_versions) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_VERSION_UPDATE;
        Map<String, String> params = new HashMap<>();
        params.put("app_type", "2");
        params.put("token", token);
        params.put("user_versions", user_versions);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<ApkInfoBean>(ApkInfoBean.class))
                .observeOnMainThread();
    }
}
