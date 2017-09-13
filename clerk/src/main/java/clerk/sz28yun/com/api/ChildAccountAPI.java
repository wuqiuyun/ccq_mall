package clerk.sz28yun.com.api;

import clerk.sz28yun.com.bean.ChildAccountBean;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import per.sue.gear2.net.ApiConnectionFactory;
import per.sue.gear2.net.parser.GearParser;
import rx.Observable;

/**
 * Created by sue on 2016/11/17.
 */
public class ChildAccountAPI {

    /**
     * 获取子账号列表
     * @param token
     * @param memberId  主账号id
     * @return
     */
    public Observable<ArrayList<ChildAccountBean>> getChildAccountList(String token, String memberId) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_CHILD_ACCOUNT_LIST;
        Map<String, String> params = new HashMap<>();
        params.put("member_id", memberId);
        params.put("token", token);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<ChildAccountBean>(ChildAccountBean.class))
                .observeOnMainThread();
    }

    /**
     * 修改子账号
     * @param token
     * @param memberId
     * @param fMemberId  主账号id
     * @param memberPasswd 密码 MD5加密
     * @param memberRealName 姓名
     * @param moblie 手机号
     * @return
     */
    public Observable<String> updateChildAccount(String token, String memberId, String fMemberId, String memberPasswd, String memberRealName, String moblie) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_CHILD_ACCOUNT_UPDATE;
        Map<String, String> params = new HashMap<>();
        params.put("member_id", fMemberId);
        params.put("token", token);
        params.put("f_member_id", memberId);
        params.put("member_passwd", memberPasswd);
        params.put("member_truename", memberRealName);
        params.put("member_mobile", moblie);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

    /**
     * 新增子账号
     * @param token
     * @param memberName 用户名
     * @param memberId 会员id(主账号id) 注意!!!
     * @param memberPasswd  密码 MD5加密
     * @param memberRealName 姓名
     * @param moblie 手机号
     * @return
     */
    public Observable<String> addChildAccount(String token, String memberId, String memberName, String memberPasswd, String memberRealName, String moblie) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_CHILD_ACCOUNT_ADD;
        Map<String, String> params = new HashMap<>();
        params.put("member_id", memberId);
        params.put("token", token);
        params.put("member_name", memberName);
        params.put("member_passwd", memberPasswd);
        params.put("member_truename", memberRealName);
        params.put("member_mobile", moblie);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

    /**
     * 获取子账号对象
     * @param token
     * @param memberId
     * @return
     */
    public Observable<ChildAccountBean> getChildAccount(String token, String memberId) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_CHILD_ACCOUNT;
        Map<String, String> params = new HashMap<>();
        params.put("member_id", memberId);
        params.put("token", token);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<ChildAccountBean>(ChildAccountBean.class))
                .observeOnMainThread();
    }


}
