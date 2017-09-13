package ccj.sz28yun.com.api;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ccj.sz28yun.com.api.base.BaseURL;
import ccj.sz28yun.com.api.base.ClerkObserver;
import ccj.sz28yun.com.bean.MemberSysSMSSendParams;
import ccj.sz28yun.com.bean.MemberSysSMSBean;
import ccj.sz28yun.com.bean.MemberSysStatusBean;
import ccj.sz28yun.com.bean.MemberSysVIPNumBean;
import ccj.sz28yun.com.bean.MemberVipDetailCCJBean;
import ccj.sz28yun.com.bean.MemberVipDetailCCJResult;
import ccj.sz28yun.com.bean.MemberVipListBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.net.ApiConnectionFactory;
import per.sue.gear2.net.parser.GearParser;
import per.sue.gear2.utils.JSONUtils;
import rx.Observable;

/**
 * Created by sue on 2017/1/3.
 */
public class MemberSysAPI {


    /**
     * 会员系统新首页
     * @param token
     * @param merchantId
     * @return
     */
    public Observable<MemberSysVIPNumBean> getMemberNum(String token, String merchantId) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MEMBER_SYSTEM_NEW_SY;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<MemberSysVIPNumBean>(MemberSysVIPNumBean.class))
                .observeOnMainThread();
    }

    /**
     * 会员列表
     * @param token
     * @param merchantId
     * @param searchMember
     * @param type
     * @param year
     * @param mon
     * @param day
     * @param page
     * @return
     */
    public Observable<ArrayList<MemberVipListBean>> getMemberCountList(String token, String merchantId, String searchMember, int type, String year, String mon, String day, int page) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MEMBER_SYSTEM_MEMBER_COUNT;
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("store_id", merchantId);
        params.put("search_member", searchMember);
        params.put("type", type + "");
        params.put("year", year);
        params.put("mon", mon);
        params.put("day", day);
        params.put("page", page + "");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<ArrayList<MemberVipListBean>>(MemberVipListBean.class))
                .observeOnMainThread();
    }

    /**
     * 会员详情
     * @param token
     * @param memberId
     * @param merchantId
     * @return
     */
    public Observable<MemberVipDetailCCJResult> getMemberVipDetails(String token, String memberId, String merchantId, int page) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MEMBER_SYSTEM_MEMBER_DETAIL;
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("member_id", memberId);
        params.put("store_id", merchantId);
        params.put("page", page + "");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<MemberVipDetailCCJResult>(MemberVipDetailCCJResult.class))
                .observeOnMainThread();
    }
    /**
     * 会员系统首页
     * @param token
     * @param merchantId
     * @return
     */
    public Observable<MemberSysStatusBean> getMemberSysStatus(String token, String merchantId, String memberId) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MEMBER_SYSTEM_STATUS;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("member_id", memberId);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<MemberSysStatusBean>(MemberSysStatusBean.class))
                .observeOnMainThread();
    }

    /**
     * 开启服务 添加签名
     * @param token
     * @param merchantId
     * @param signtrue
     * @return
     */
    public Observable<String> sendSigntrueForStartService(String token, String merchantId, String signtrue) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MEMBER_SYSTEM_SEND_SIGTRUE;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("sign", signtrue);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

    /**
     * 获取短信列表
     * @param token
     * @param merchantId
     * @param page
     * @return
     */
    public Observable<ArrayList<MemberSysSMSBean>> getSMSList(String token, String merchantId, int page) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MEMBER_SYSTEM_SMS_LIST;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("page", page + "");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<MemberSysSMSBean>(MemberSysSMSBean.class))
                .observeOnMainThread();
    }

    /**
     * 提交审核 发送短信
     * @param memberSysParams
     * @return
     * @throws JSONException
     */
    public Observable<String> sendSMS(MemberSysSMSSendParams memberSysParams) throws JSONException {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MEMBER_SYSTEM_SMS_SEND;
        Map<String, String> params = new HashMap<>();
        params = JSONUtils.beanToMap(memberSysParams);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

}
