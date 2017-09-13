package ccj.sz28yun.com.api;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ccj.sz28yun.com.api.base.BaseURL;
import ccj.sz28yun.com.api.base.ClerkObserver;
import ccj.sz28yun.com.bean.EmployeeBean;
import ccj.sz28yun.com.bean.EmployeeDetailsBean;
import ccj.sz28yun.com.bean.EmployeeParams;
import ccj.sz28yun.com.bean.MerchantChainBean;
import ccj.sz28yun.com.bean.MerchantChainParams;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.net.ApiConnectionFactory;
import per.sue.gear2.net.parser.GearParser;
import per.sue.gear2.utils.JSONUtils;
import rx.Observable;

/**
 * Created by sue on 2017/1/3.
 */
public class EmployeeAPI {

    /**
     * 获取员工账号列表
     * @param token
     * @param merchantId
     * @param groupId  员工身份(1营业员 10财务专员 11运营专员) **不传则获取全部
     * @param page
     * @return
     */
    public Observable<ArrayList<EmployeeBean>> getEmployeeList(String token, String merchantId, String groupId,  int page) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_EMPLOYEE_LIST;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("group_id", groupId);
        params.put("page", page + "");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<EmployeeBean>(EmployeeBean.class))
                .observeOnMainThread();
    }

    /**
     * 删除员工账号
     * @param token
     * @param id
     * @return
     */
    public Observable<String> deleteEmployee(String token,  String id) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_EMPLOYEE_DELETE;
        Map<String, String> params = new HashMap<>();
        //params.put("store_id", merchantId);
        params.put("token", token);
        params.put("seller_id", id);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

    /**
     * 添加员工账号
     * @param employeeParams
     * @return
     * @throws JSONException
     */
    public Observable<String> addEmployee(EmployeeParams employeeParams) throws JSONException {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_EMPLOYEE_ADD;
        Map<String, String> params = new HashMap<>();
        params = JSONUtils.beanToMap(employeeParams);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

    /**
     * 修改员工账号
     * @param employeeParams
     * @return
     * @throws JSONException
     */
    public Observable<String> editEmployee(EmployeeParams employeeParams) throws JSONException {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_EMPLOYEE_EDIT;
        Map<String, String> params = new HashMap<>();
        params = JSONUtils.beanToMap(employeeParams);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

    /**
     * 获取员工信息
     * @param token
     * @param merchantId
     * @param tragetId
     * @return
     */
    public Observable<EmployeeDetailsBean> getEmployeeDetails(String token, String merchantId, String tragetId) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_EMPLOYEE_DETAILS;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("seller_id", tragetId);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<EmployeeDetailsBean>(EmployeeDetailsBean.class))
                .observeOnMainThread();
    }

}
