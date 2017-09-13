package clerk.sz28yun.com.api;

import clerk.sz28yun.com.bean.BusinessBean;
import clerk.sz28yun.com.bean.MerchantBean;
import clerk.sz28yun.com.bean.MerchantCategoryBean;
import clerk.sz28yun.com.bean.MerchantParams;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import per.sue.gear2.net.ApiConnectionFactory;
import per.sue.gear2.net.parser.GearParser;
import per.sue.gear2.utils.JSONUtils;
import rx.Observable;

/**
 * Created by sue on 2016/11/17.
 */
public class MerchantAPI {

    /**
     * 获取我的业务
     * @param token
     * @param memberId
     * @param page 页数 第一页为1
     * @param state 申请状态 10-已提交申请 30-审核失败 40-审核通过开店 (查询全部时不用提交这个字段)
     * @return
     */
    public Observable<ArrayList<BusinessBean>> getBusinessList(String token, String memberId, int page, String state) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MERCHANT_LIST;
        Map<String, String> params = new HashMap<>();
        params.put("member_id", memberId);
        params.put("token", token);
        params.put("page", page + "");
        params.put("joinin_state", state);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<BusinessBean>(BusinessBean.class))
                .observeOnMainThread();
    }

    /**
     * 添加商家
     * @param token
     * @param merchantParams
     * @return
     * @throws JSONException
     */
    public Observable<String> addMerchant(String token, MerchantParams merchantParams) throws JSONException {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MERCHANT_ADD;
        Map<String, String> params = new HashMap<>();
        params = JSONUtils.beanToMap(merchantParams);
        //params.put("member_id", memberId);
        params.put("token", token);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

    /**
     * 获取商家
     * @param token
     * @param memberId
     * @param id
     * @return
     */
    public Observable<MerchantBean> getMerchant(String token, String memberId, String id)  {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MERCHANT;
        Map<String, String> params = new HashMap<>();
        params.put("member_id", memberId);
        params.put("token", token);
        params.put("id", id);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<MerchantBean>(MerchantBean.class))
                .observeOnMainThread();
    }

    /**
     * 获取商家分类
     * @param token
     * @return
     */
    public Observable<ArrayList<MerchantCategoryBean>> getMerchantCategory(String token)  {
        String url =  GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MERCHANT_CATEGORY;
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<MerchantCategoryBean>(MerchantCategoryBean.class))
                .observeOnMainThread();
    }

    /**
     * 效验会员账号是否注册
     * @param account 用户名(app注册时即手机号)
     * @return
     */
    public Observable<String> verifyMemberAccount(String account)  {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN +  BaseURL.URL_MERCHANT_MEMBER_ACCOUNT_VERIFY;
        Map<String, String> params = new HashMap<>();
        params.put("member_name", account);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }


}
