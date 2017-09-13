package ccj.sz28yun.com.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ccj.sz28yun.com.api.base.BaseURL;
import ccj.sz28yun.com.api.base.ClerkObserver;
import ccj.sz28yun.com.bean.FinanceApplyDepositBean;
import ccj.sz28yun.com.bean.FinanceDayBean;
import ccj.sz28yun.com.bean.FinanceDepositFreeDetailsBean;
import ccj.sz28yun.com.bean.FinanceDepositsBean;
import ccj.sz28yun.com.bean.FinanceFreezeBean;
import ccj.sz28yun.com.bean.FinanceMonthResult;
import ccj.sz28yun.com.bean.FinanceResult;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.net.ApiConnectionFactory;
import per.sue.gear2.net.parser.GearParser;
import rx.Observable;

/**
 * Created by sue on 2016/12/19.
 */
public class FinanceAPI {

    /**
     * 获取商家资金详情及银行卡信息
     * @return
     */
    public Observable<FinanceResult> getFundsAndBankCard(String token, String merchantId) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_FINANCE_FUNDS;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<FinanceResult>(FinanceResult.class))
                .observeOnMainThread();
    }

    /**
     *
     * @param token
     * @param merchantId
     * @param month 月份(格式2016-10)
     * @param type 资金类型(1账户余额 2vip会员费 3餐餐抢费 4招商奖金 5推广奖金)
     * @return
     */
    public Observable<FinanceMonthResult> getFinanceTypeMonth(String token, String merchantId, String month, String type) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_FINANCE_FUNDS_TYPE_MONTH;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("month", month);
        params.put("type", type);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<FinanceMonthResult>(FinanceMonthResult.class))
                .observeOnMainThread();
    }

    /**
     *获取商家提现记录列表
     * @param token
     * @param merchantId
     * @param month 时间(格式2016-11)
     * @param type 资金类型(1推广提成 2招商奖金 5账户余额 7vip会员费 8餐餐抢费)
     * @param page 页码
     * @return
     */
    public Observable<ArrayList<FinanceDepositsBean>> getFinanceTypeDeposits(String token, String merchantId, String month, String type, int page) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_FINANCE_FUNDS_DEPOSITS;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("month", month);
        params.put("type", type);
        params.put("page", page + "");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<FinanceDepositsBean>(FinanceDepositsBean.class))
                .observeOnMainThread();
    }

    /**
     * 获取商家冻结记录列表
     * @param token
     * @param merchantId
     * @param month
     * @param type
     * @param page
     * @return
     */
    public Observable<ArrayList<FinanceFreezeBean>> getFinanceTypeFreeze(String token, String merchantId, String month, String type, int page) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_FINANCE_FUNDS_FREEZE;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("ntype", "1"); //请传1
        params.put("month", month);
        params.put("type", type);
        params.put("page", page + "");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<FinanceFreezeBean>(FinanceFreezeBean.class))
                .observeOnMainThread();
    }

    /**
     * 获取财务每天统计
     * @param token
     * @param merchantId
     * @param type
     * @param page
     * @return
     */
    public Observable<ArrayList<FinanceDayBean>> getFinanceTypeDay(String token, String merchantId, String day, String type, int page) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_FINANCE_FUNDS_TYPE_DAY;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("day", day);
        params.put("type", type);
        params.put("page", page + "");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<FinanceDayBean>(FinanceDayBean.class))
                .observeOnMainThread();
    }

    /**
     * 商家提现记录/冻结记录详情
     * @param token
     * @param merchantId
     * @param id
     * @return
     */
    public Observable<FinanceDepositFreeDetailsBean> getFinanceDepositDetails(String token, String merchantId, String id) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_FINANCE_FUNDS_DEPOSITS_DETIAL;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("id", id);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<FinanceDepositFreeDetailsBean>(FinanceDepositFreeDetailsBean.class))
                .observeOnMainThread();
    }

    /**
     * 申请提现时账户详情
     * @param token
     * @param merchantId
     * @param type
     * @return
     */
    public Observable<FinanceApplyDepositBean> getFinanceApplyDepositAccount(String token, String merchantId, String type) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_FINANCE_FUNDS_DEPOSITS_APPLY_INFO;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("type", type);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<FinanceApplyDepositBean>(FinanceApplyDepositBean.class))
                .observeOnMainThread();
    }

    /**
     * 提现申请
     * @param token
     * @param amount
     * @param type 提现类型(1推广提成 2招商奖金 5账户余额 7vip会员费 8餐餐抢)
     * @param bank_name 开户银行
     * @param card_account 银行卡账号
     * @param name 开户名称
     * @return
     */
    public Observable<String> applyDeposit(String token,String merchantId, String amount, String type, String bank_name, String card_account, String name) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_FINANCE_FUNDS_DEPOSITS_APPLY;
        Map<String, String> params = new HashMap<>();
        params.put("amount", amount);
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("type", type);
        params.put("bank_name", bank_name);
        params.put("card_account", card_account);
        params.put("name", name);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

}
