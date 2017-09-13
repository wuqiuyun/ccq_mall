package ccj.sz28yun.com.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ccj.sz28yun.com.api.base.BaseURL;
import ccj.sz28yun.com.api.base.ClerkObserver;
import ccj.sz28yun.com.bean.EvaluateStatisticBean;
import ccj.sz28yun.com.bean.EvaluateMerchantBean;
import ccj.sz28yun.com.bean.EvaluateGoodsBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.net.ApiConnectionFactory;
import per.sue.gear2.net.parser.GearParser;
import rx.Observable;

/**
 * Created by sue on 2016/12/6.
 */
public class EvaluateAPI {

    /**
     * 获取评价统计数据
     * @param token
     * @param merchantId
     * @return
     */
    public Observable<EvaluateStatisticBean> getEvaluateStatistic(String token, String merchantId, int type) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_EVALUATE_STATISTIC;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("type", type + "");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<EvaluateStatisticBean>(EvaluateStatisticBean.class))
                .observeOnMainThread();
    }

    /**
     * 获取商家评价列表
     * @param token
     * @param merchantId
     * @param page
     * @return
     */
    public Observable<ArrayList<EvaluateMerchantBean>> getEvaluateMerchantList(String token, String merchantId, int retype, int page) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_EVALUATE_MERCHANT_LIST;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("page", page + "");
        params.put("type", "0");
        params.put("re_type", retype + "");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<EvaluateMerchantBean>(EvaluateMerchantBean.class))
                .observeOnMainThread();
    }

    /**
     * 获取商品评价列表
     * @param token
     * @param merchantId
     * @param page
     * @return
     */
    public Observable<ArrayList<EvaluateGoodsBean>> getEvaluateGoodsList(String token, String merchantId, int retype, int page) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_EVALUATE_GOODS_LIST;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("page", page + "");
        params.put("type", "1");
        params.put("re_type", retype + "");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<EvaluateGoodsBean>(EvaluateGoodsBean.class))
                .observeOnMainThread();
    }

    /**
     * 回复商品评价
     * @param token
     * @param sevalId
     * @param content
     * @return
     */
    public Observable<String> replyEvaluateGoods(String token, String sevalId, String  content) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_EVALUATE_GOODS_REPLY;
        Map<String, String> params = new HashMap<>();
        params.put("geval_id", sevalId);
        params.put("token", token);
        params.put("content", content);
        params.put("type", "1");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

    /**
     * 回复商家评价
     * @param token
     * @param gevalId
     * @param content
     * @return
     */
    public Observable<String> replyEvaluateMerchant(String token, String gevalId, String  content) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_EVALUATE_MERCHANT_REPLY;
        Map<String, String> params = new HashMap<>();
        params.put("seval_id", gevalId);
        params.put("token", token);
        params.put("content", content);
        params.put("type", "0");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

}
