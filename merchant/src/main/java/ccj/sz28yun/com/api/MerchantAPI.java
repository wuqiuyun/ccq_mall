package ccj.sz28yun.com.api;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ccj.sz28yun.com.api.base.BaseURL;
import ccj.sz28yun.com.api.base.ClerkObserver;
import ccj.sz28yun.com.bean.MerchantAdsResult;
import ccj.sz28yun.com.bean.MerchantAllPicResult;
import ccj.sz28yun.com.bean.MerchantChainBean;
import ccj.sz28yun.com.bean.MerchantChainParams;
import ccj.sz28yun.com.bean.MerchantInfoBean;
import ccj.sz28yun.com.bean.MerchantInfoParams;
import ccj.sz28yun.com.bean.MerchantProListBean;
import ccj.sz28yun.com.bean.MerchantServiceBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.net.ApiConnectionFactory;
import per.sue.gear2.net.parser.GearParser;
import per.sue.gear2.utils.JSONUtils;
import rx.Observable;

/**
 * Created by sue on 2017/1/3.
 */
public class MerchantAPI {

    /**
     * 获取商家信息
     * @param token
     * @param merchantId
     * @return
     */
    public Observable<MerchantInfoBean> getMerchantInfo(String token, String merchantId) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MERCHANT_INFO;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<MerchantInfoBean>(MerchantInfoBean.class))
                .observeOnMainThread();
    }

    /**
     * 编辑商家信息
     * @param memberSysParams
     * @return
     * @throws JSONException
     */
    public Observable<String> editMerchantInfo(MerchantInfoParams memberSysParams) throws JSONException {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MERCHANT_INFO_EDIT;
        Map<String, String> params = new HashMap<>();
        params = JSONUtils.beanToMap(memberSysParams);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }


    /**
     * 获取服务信息
     * @param token
     * @param merchantId
     * @return
     */
    public Observable<MerchantServiceBean> getMerchantService(String token, String merchantId) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MERCHANT_SERVICE;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<MerchantServiceBean>(MerchantServiceBean.class))
                .observeOnMainThread();
    }

    /**
     * 修改服务信息
     * @param bean
     * @return
     * @throws JSONException
     */
    public Observable<String> editMerchantService(MerchantServiceBean bean) throws JSONException {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MERCHANT_SERVICE_EDIT;
        Map<String, String> params = new HashMap<>();
        params = JSONUtils.beanToMap(bean);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

    /**
     * 获取商家商品信息
     * @param token
     * @param merchantId
     * @return
     */
    public Observable<ArrayList<MerchantProListBean>> getProList(String token, String merchantId,  int page) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MERCHANT_PROLIST;
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("store_id", merchantId);
        params.put("page", page + "");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<MerchantProListBean>(MerchantProListBean.class))
                .observeOnMainThread();
    }

    /**
     * 获取商家所有图片
     * @param token
     * @param merchantId
     * @return
     */
    public Observable<MerchantAllPicResult> getMerchantAllPic(String token, String merchantId) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MERCHANT_ALLPIC;
        Map<String, String> params = new HashMap<>();
        params.put("api_v", "v3");
        params.put("store_id", merchantId);
        params.put("token", token);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<MerchantAllPicResult>(MerchantAllPicResult.class))
                .observeOnMainThread();
    }
    /**
     * 获取商家广告信息
     * @param token
     * @param merchantId
     * @return
     */
    public Observable<MerchantAdsResult> getMerchantAds(String token, String merchantId) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MERCHANT_ADS;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<MerchantAdsResult>(MerchantAdsResult.class))
                .observeOnMainThread();
    }

    /**
     * 更新商家广告图片
     * @param token
     * @param merchantId
     * @param imagOriginalId 轮播图id(没有则不填)
     * @param iamgeId 商家id
     * @return
     */
    public Observable<String> editMerchantAds(String token, String merchantId, String imagOriginalId, String iamgeId) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MERCHANT_ADS_EDIT;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("store_image_id", imagOriginalId);
        params.put("image_id", iamgeId);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

    /**
     * 删除轮播图
     * @param token
     * @param merchantId
     * @param iamgeId
     * @return
     */
    public Observable<String> deleteMerchantAds(String token, String merchantId, String iamgeId) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MERCHANT_ADS_DELETE;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("store_image_id", iamgeId);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }
    /**
     * 商家设置-商家信息图片处理
     * @param addstore_image_01
     * @param addstore_image_02
     * @param token
     * @param merchantId   @return
     */
    public Observable<String> editMerchantAllPic(String token,String memberId, String merchantId , String addstore_image_01, String addstore_image_02 , String addstore_image_03, String addstore_image_04, String addstore_image_05) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MERCHANT_EDIT_ALLPIC;
        Map<String, String> params = new HashMap<>();
        params.put("api_v", "v3");
        params.put("token", token);
        params.put("member_id", memberId);
        params.put("store_id", merchantId);
        params.put("addstore_image_01", addstore_image_01);
        params.put("addstore_image_02", addstore_image_02);
        params.put("addstore_image_03", addstore_image_03);
        params.put("addstore_image_04", addstore_image_04);
        params.put("addstore_image_05", addstore_image_05);
        params.put("type", "0");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

    /**
     * 设置商家广告文字
     * @param token
     * @param merchantId
     * @param adsContent
     * @return
     */
    public Observable<String> setMerchantAdsText(String token, String merchantId, String adsContent) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MERCHANT_ADS_TEXT_SET;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("adv", adsContent);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }


    /**
     * 获取连锁店列表
     * @param token
     * @param merchantId
     * @param page
     * @return
     */
    public Observable<ArrayList<MerchantChainBean>> getMerchantChainList(String token, String merchantId, int page) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MERCHANT_CHAIN_LIST;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("page", page + "");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<MerchantChainBean>(MerchantChainBean.class))
                .observeOnMainThread();
    }

    /**
     * 删除连锁店
     * @param token
     * @param id
     * @return
     */
    public Observable<String> deleteMerchantChain(String token,  String id) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MERCHANT_CHAIN_DELETE;
        Map<String, String> params = new HashMap<>();
        //params.put("store_id", merchantId);
        params.put("token", token);
        params.put("id", id);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

    /**
     * 新增连锁店
     * @param merchantChainParams
     * @return
     * @throws JSONException
     */
    public Observable<String> addMerchantChain(MerchantChainParams merchantChainParams) throws JSONException {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MERCHANT_CHAIN_ADD;
        Map<String, String> params = new HashMap<>();
        params = JSONUtils.beanToMap(merchantChainParams);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();


    }

    /**
     * 重设密码
     * @param token token
     * @param memberId 会员id
     * @param oldpasswd 旧密码
     * @param newPasswd 新密码
     * @return
     */
    public Observable<String> resetPassword(String token, String memberId, String oldpasswd, String newPasswd) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MERCHANT_SETTING_RESET_PASSWORD;
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("store_id", memberId);
        params.put("old_pwd", oldpasswd);
        params.put("new_pwd", newPasswd);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }


}
