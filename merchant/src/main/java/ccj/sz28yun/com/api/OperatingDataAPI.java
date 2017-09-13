package ccj.sz28yun.com.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ccj.sz28yun.com.api.base.BaseURL;
import ccj.sz28yun.com.api.base.ClerkObserver;
import ccj.sz28yun.com.bean.OperatingDataOrderBean;
import ccj.sz28yun.com.bean.OperatingMerchantFlowBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.net.ApiConnectionFactory;
import per.sue.gear2.net.parser.GearParser;
import rx.Observable;

/**
 * Created by sue on 2016/12/7.
 */
public class OperatingDataAPI {


    /**
     * 获取订单分析数据
     * @param token
     * @param merchantId 商家id
     * @param year 年
     * @param month 月 (不填则默认为上个月)
     * @return
     */
    public Observable<OperatingDataOrderBean> getOrderStatisticData(String token, String merchantId, String  year, String month) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_OPERATING_DATA_ORDER;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("year", year);
        params.put("month", month);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<OperatingDataOrderBean>(OperatingDataOrderBean.class))
                .observeOnMainThread();
    }

    /**
     * 商家浏览记录
     * @param token
     * @param merchantId
     * @return
     */
    public Observable<ArrayList<OperatingMerchantFlowBean>> getMercgantFlowStatisticData(String token, String merchantId) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_OPERATING_DATA_MERCHANT;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("type", "1");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<OperatingMerchantFlowBean>(OperatingMerchantFlowBean.class))
                .observeOnMainThread();
    }
}
