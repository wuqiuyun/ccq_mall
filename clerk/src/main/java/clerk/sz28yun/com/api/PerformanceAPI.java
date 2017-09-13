package clerk.sz28yun.com.api;

import clerk.sz28yun.com.bean.PerformanceChildMemberResult;
import clerk.sz28yun.com.bean.PerformanceChildMerchantBean;
import clerk.sz28yun.com.bean.PerformanceDayBean;
import clerk.sz28yun.com.bean.PerformanceDayResult;
import clerk.sz28yun.com.bean.PerformanceMerchantResult;
import clerk.sz28yun.com.bean.PerformanceMonResult;
import clerk.sz28yun.com.bean.MemberStatisticBean;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import per.sue.gear2.net.ApiConnectionFactory;
import per.sue.gear2.net.parser.GearParser;
import rx.Observable;

/**
 * Created by sue on 2016/11/18.
 */
public class PerformanceAPI {


    /**
     * 获取当天业绩
     * @param token
     * @param memberId
     * @param type  1 vip奖金 2 餐餐抢奖金 3 招商奖金 4 推广奖金
     * @param year  年
     * @param month 月
     * @param page 页码
     * @return
     */
//    public Observable<ArrayList<PerformanceDayBean>> getPerformanceDayList(String token, String memberId, int type, String year, String month,String day, int page) {
//        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_PERFORMANCE_MY;
//        Map<String, String> params = new HashMap<>();
//        params.put("member_id", memberId);
//        params.put("token", token);
//        params.put("page", page + "");
//        params.put("type", type + "");
//        params.put("year", year + "");
//        params.put("mon", month + "");
//        params.put("day", day + "");
//        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<PerformanceDayBean>(PerformanceDayBean.class))
//                .observeOnMainThread();
//    }
    public Observable<PerformanceDayResult> getPerformanceDayList(String token, String memberId, int type, String year, String month,String day, int page) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_PERFORMANCE_MY;
        Map<String, String> params = new HashMap<>();
        params.put("member_id", memberId);
        params.put("token", token);
        params.put("page", page + "");
        params.put("type", type + "");
        params.put("year", year + "");
        params.put("mon", month + "");
        params.put("day", day + "");
        return new NewClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<PerformanceDayResult>(PerformanceDayResult.class),true)
                .observeOnMainThread();
    }

    /**
     * 获取某月业绩
     * @param token
     * @param memberId
     * @param type
     * @param year
     * @param month
     * @param page
     * @return
     */
    public Observable<PerformanceMonResult> getPerformanceMonList(String token, String memberId, int type, String year, String month, int page) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_PERFORMANCE_MONTH;
        Map<String, String> params = new HashMap<>();
        params.put("member_id", memberId);
        params.put("token", token);
        params.put("page", page + "");
        params.put("type", type + "");
        params.put("year", year + "");
        params.put("mon", month + "");
        return new NewClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<PerformanceMonResult>(PerformanceMonResult.class))
                .observeOnMainThread();
    }

    /**
     * 获取商家业绩
     * @param token
     * @param memberId
     * @param page
     * @return
     */
    public Observable<PerformanceMerchantResult> getPerformanceMerchant(String token, String memberId, int page) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_PERFORMANCE_MERCHANT;
        Map<String, String> params = new HashMap<>();
        params.put("member_id", memberId);
        params.put("token", token);
        params.put("page", page + "");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<PerformanceMerchantResult>(PerformanceMerchantResult.class))
                .observeOnMainThread();
    }

    /**
     * 会员统计
     * @param memberId
     * @param keyword
     * @param page
     * @return
     */
    public Observable<ArrayList<MemberStatisticBean>> getStatisticMemberList(String memberId, String keyword, int page) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_STATISTIC_MEMBER;
        Map<String, String> params = new HashMap<>();
        params.put("member_id", memberId);
        params.put("search_member", keyword);
        params.put("page", page + "");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<MemberStatisticBean>(MemberStatisticBean.class))
                .observeOnMainThread();
    }

    /**
     * 获取子账号业绩数据
     * @param token
     * @param memberId
     * @param year
     * @param month
     * @param page
     * @return
     */
    public Observable<PerformanceChildMemberResult> getPerformanceChildList(String token, String memberId, String year, String month, int page,String type) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_CHILD_PERFORMANCE_MEMBER;
        Map<String, String> params = new HashMap<>();
        params.put("member_id", memberId);
        params.put("token", token);
        params.put("page", page + "");
        params.put("year", year + "");
        params.put("mon", month + "");
        params.put("type", type + "");
       // params.put("last_day", lastDay + "");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<PerformanceChildMemberResult>(PerformanceChildMemberResult.class))
                .observeOnMainThread();
    }

    /**
     * 子账号商家业绩
     * @param token
     * @param memberId
     * @param date 日期 例如 2016-10-29
     * @param page
     * @return
     */
    public Observable<ArrayList<PerformanceChildMerchantBean>> getPerformanceChildMerchantList(String token, String memberId, String date, int page) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_CHILD_PERFORMANCE_MERCHANT;
        Map<String, String> params = new HashMap<>();
        params.put("member_id", memberId);
        params.put("token", token);
        params.put("page", page + "");
        params.put("date", date + "");

        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<PerformanceChildMerchantBean>(PerformanceChildMerchantBean.class))
                .observeOnMainThread();
    }
}
