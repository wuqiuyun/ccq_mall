package ccj.sz28yun.com.api;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ccj.sz28yun.com.api.base.BaseURL;
import ccj.sz28yun.com.api.base.ClerkObserver;
import ccj.sz28yun.com.bean.CouponChainBean;
import ccj.sz28yun.com.bean.CouponGoodsBean;
import ccj.sz28yun.com.bean.CouponMealsBean;
import ccj.sz28yun.com.bean.CouponMealsInfoResult;
import ccj.sz28yun.com.bean.CouponMealsParams;
import ccj.sz28yun.com.bean.MerchantChainParams;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.net.ApiConnectionFactory;
import per.sue.gear2.net.parser.GearParser;
import per.sue.gear2.utils.JSONUtils;
import rx.Observable;

/**
 * Created by sue on 2016/12/19.
 */
public class CouponAPI {

    /**
     * 获取单品列表
     * @param token
     * @param merchantId
     * @param namekey  //商品名称
     * @param categoryId //分类id(gc_id为0，则获取该商家的所有商品)
     * @param page //页码
     * @return
     */
    public Observable<ArrayList<CouponGoodsBean>> getCouponGoodsList(String token, String merchantId, String namekey, String categoryId, int page) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_COUPON_GOODS_LIST;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("goods_name", namekey);
        params.put("gc_id", categoryId);
        params.put("page", page + "");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<CouponGoodsBean>(CouponGoodsBean.class))
                .observeOnMainThread();
    }

    /**
     * 获取单品券列表
     * @param token
     * @param merchantId
     * @param type 1 待上线 2 已上线 3 已下线 不填则查询全部
     * @param page
     * @return
     */
    public Observable<ArrayList<CouponMealsBean>> getCouponOnePublist(String token, String merchantId, String type, int page) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_COUPON_DANPIN_LIST;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("type", type);
        params.put("page", page + "");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<CouponMealsBean>(CouponMealsBean.class))
                .observeOnMainThread();
    }

    /**
     * 发布单品券
     * @param token
     * @param merchantId
     * @param goodsIds  //单品id
     * @param discounPercent  折扣后价格
     * @param discountCount  发布折扣券数量
     * @return
     */
    public Observable<String> publishOneCoupon(String token, String merchantId, String goodsIds, String discounPercent, String discountCount){
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_COUPON_PUBLISH_ONE;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("goods_union_id", goodsIds);
        params.put("goods_costprice", discounPercent);
        params.put("goods_storage", discountCount + "");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

    public Observable<String> publishDoubleCoupon(String token, String merchantId, String goodsIds, String discounPercent, String discountCount){
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_COUPON_PUBLISH_DOUBLE;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("goods_union_arr", goodsIds);
        params.put("discount", discounPercent);
        params.put("goods_storage", discountCount + "");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }


    /**
     * 获取套餐券列表
     * @param token
     * @param merchantId
     * @param type 1 待上线 2 已上线 3 已下线 不填则查询全部
     * @param page
     * @return
     */
    public Observable<ArrayList<CouponMealsBean>> getCouponMealsList(String token, String merchantId, String type, int page) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_COUPON_MEALS_LIST;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("type", type);
        params.put("page", page + "");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<CouponMealsBean>(CouponMealsBean.class))
                .observeOnMainThread();
    }

    /**
     * 获取套餐分店列表
     * @param token
     * @param merchantId
     * @param page
     * @return
     */
    public Observable<ArrayList<CouponChainBean>> getCouponCouponList(String token, String merchantId,  int page) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_COUPON_CHAIN_LIST;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("type", "1");
        params.put("page", page + "");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<CouponMealsBean>(CouponChainBean.class))
                .observeOnMainThread();
    }


    /**
     * 下架套餐券
     * @param
     * @return
     * @throws JSONException
     */
    public Observable<String> xiajiaCouponMeals(String store_id, String goods_id) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_XIAJIA_DELETE_TAOCANQUAN;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", store_id);
        params.put("goods_id", goods_id);
        params.put("type", "1");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }
    /**
     * 删除套餐券
     * @param
     * @return
     * @throws JSONException
     */
    public Observable<String> deleteCouponMeals(String store_id, String goods_id) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_XIAJIA_DELETE_TAOCANQUAN;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", store_id);
        params.put("goods_id", goods_id);
        params.put("type", "2");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }
    /**
     * 修改库存
     * @param
     * @return
     * @throws JSONException
     */
    public Observable<String> editKCCouponMeals(String store_id, String goods_id, String kc_num) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_EDIT_KUCUN;
        Map<String, String> params = new HashMap<>();
        params.put("api_v", "v3");
        params.put("store_id", store_id);
        params.put("goods_id", goods_id);
        params.put("update_num", kc_num);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }
    /**
     * 新增套餐券
     * @param couponMealsParams
     * @return
     * @throws JSONException
     */
    public Observable<String> addCouponMeals(CouponMealsParams couponMealsParams) throws JSONException {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_COUPON_MEALS_ADD;
        Map<String, String> params = new HashMap<>();
        params = JSONUtils.beanToMap(couponMealsParams);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }


    /**
     * 修改套餐券
     * @param couponMealsParams
     * @return
     * @throws JSONException
     */
    public Observable<String> editCouponMeals(CouponMealsParams couponMealsParams) throws JSONException {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_COUPON_MEALS_EDIT;
        Map<String, String> params = new HashMap<>();
        params = JSONUtils.beanToMap(couponMealsParams);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

    /**
     * 获取套餐券详情
     * @param token
     * @param couponId 套餐ID
     * @return
     * @throws JSONException
     */
    public Observable<CouponMealsInfoResult> getCouponMealsInfp(String token, String couponId) throws JSONException {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_COUPON_MEALS_INFO;
        Map<String, String> params = new HashMap<>();
        params.put("goods_id", couponId);
        params.put("token", token);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<CouponMealsInfoResult>(CouponMealsInfoResult.class))
                .observeOnMainThread();
    }


}
