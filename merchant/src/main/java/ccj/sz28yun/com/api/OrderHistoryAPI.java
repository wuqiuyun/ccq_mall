package ccj.sz28yun.com.api;

import java.util.HashMap;
import java.util.Map;

import ccj.sz28yun.com.api.base.BaseURL;
import ccj.sz28yun.com.api.base.ClerkObserver;
import ccj.sz28yun.com.bean.OrderCCJResult;
import ccj.sz28yun.com.bean.OrderConsumeBillResult;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.net.ApiConnectionFactory;
import per.sue.gear2.net.parser.GearParser;
import rx.Observable;

/**
 * Created by sue on 2016/12/7.
 */
public class OrderHistoryAPI {


    /**
     * 订单列表及筛选搜索
     * @param token
     * @param merchantId
     * @param date 查询时间 ‘2016-10-10’
     * @param page 页码 第一页为1
     * @return
     */
    public Observable<OrderCCJResult> getOrderHistoryForCCJ(String token, String merchantId, String date, int page) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_ORDER_HISTORY_CCJ;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("union_type", "1");//联盟商家订单类型 1 套餐 2先消费后买单
        params.put("mon", date);
        params.put("page", page + "");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<OrderCCJResult>(OrderCCJResult.class))
                .observeOnMainThread();
    }

    /**
     * 获取先消费后买单订单列表
     * @param token
     * @param merchantId
     * @param date
     * @param page 页码 第一页为1
     * @return
     */
    public Observable<OrderConsumeBillResult> getOrderHistoryForConsumeBill(String token, String merchantId, String date, int page) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_ORDER_HISTORY_CONSUME_BILL;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("mon", date);
        params.put("page", page + "");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<OrderConsumeBillResult>(OrderConsumeBillResult.class))
                .observeOnMainThread();
    }

    /**
     * 验证订单
     * @param token
     * @param merchantId
     * @param orderId
     * @return
     */
    public Observable<String> checkOrderBill(String token, String merchantId,  String orderId) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_VERIFY_BILL_CHECK;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("order_id", orderId);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

}
