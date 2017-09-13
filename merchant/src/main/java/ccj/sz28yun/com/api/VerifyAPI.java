package ccj.sz28yun.com.api;

import java.util.HashMap;
import java.util.Map;

import ccj.sz28yun.com.api.base.BaseURL;
import ccj.sz28yun.com.api.base.ClerkObserver;
import ccj.sz28yun.com.bean.ConsumBillSettingResult;
import ccj.sz28yun.com.bean.ConsumeBillResult;
import ccj.sz28yun.com.bean.OrderCCJBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.net.ApiConnectionFactory;
import per.sue.gear2.net.parser.GearParser;
import rx.Observable;

/**
 * Created by sue on 2016/12/4.
 */
public class VerifyAPI {

    /**
     * 验证验证码
     * @param token
     * @param merchantId 商家id
     * @param code 验证码
     * @return
     */
    public Observable<OrderCCJBean> verifyCode(String token, String merchantId, String code) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_VERIFY_CODE;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("code", code);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<OrderCCJBean>(OrderCCJBean.class))
                .observeOnMainThread();
    }


    /**
     * 设置显消费后买单
     * @param token
     * @param merchantId
     * @param unionPay 1 开启先消费后买单 0 关闭
     * @param unionPayDiscount 先消费后买单折扣 例如 98 %(不需要%)
     * @return
     */
    public Observable<String> setVerifyConsumeBill(String token, String merchantId, String unionPay, String unionPayDiscount) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_VERIFY_BILL_SET;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("union_pay", unionPay);
        params.put("union_pay_discount", unionPayDiscount);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

    /**
     *获取先消费后买单订单列表
     * @param token
     * @param merchantId
     * @param date 查询月份 例如 ‘2016-10-10’
     * @param keyword 订单编号或者是手机号码
     * @param page 分页 第一页是1
     * @return
     */
    public Observable<ConsumeBillResult> getConsumeBillList(String token, String merchantId, String date, String keyword, int page) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_VERIFY_BILL_LIST;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("mon", date);
        params.put("key", keyword);
        params.put("page", page + "");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<ConsumeBillResult>(ConsumeBillResult.class))
                .observeOnMainThread();
    }

    /**
     * 验证先消费后买单
     * @param token
     * @param merchantId
     * @param orderId
     * @return
     */
    public Observable<String> checkConsumeBill(String token, String merchantId,  String orderId) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_VERIFY_BILL_CHECK;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("order_id", orderId);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

    public Observable<ConsumBillSettingResult> getConsumeBillSettingInfo(String token, String merchantId) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_VERIFY_BILL_SETTING;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<ConsumBillSettingResult>(ConsumBillSettingResult.class))
                .observeOnMainThread();
    }

}
