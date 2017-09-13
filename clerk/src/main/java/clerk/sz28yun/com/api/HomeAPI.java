package clerk.sz28yun.com.api;

import clerk.sz28yun.com.bean.HomeBean;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;

import java.util.HashMap;
import java.util.Map;

import per.sue.gear2.net.ApiConnectionFactory;
import per.sue.gear2.net.parser.GearParser;
import rx.Observable;

/**
 * Created by sue on 2016/11/16.
 */
public class HomeAPI {


    /**
     * 获取主页数据
     * @param token token
     * @param memberId 会员id
     * @return
     */
    public Observable<HomeBean> getHomeData(String token, String memberId) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_HOME_DATA;
        Map<String, String> params = new HashMap<>();
        params.put("member_id", memberId);
        params.put("token", token);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<HomeBean>(HomeBean.class))
                .observeOnMainThread();
    }
}
