package ccj.sz28yun.com.api;



import java.util.HashMap;
import java.util.Map;

import ccj.sz28yun.com.api.base.BaseURL;
import ccj.sz28yun.com.api.base.ClerkObserver;
import ccj.sz28yun.com.bean.ConfigBean;
import ccj.sz28yun.com.bean.ShareConfigBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.net.ApiCache;
import per.sue.gear2.net.ApiConnectionFactory;
import per.sue.gear2.net.parser.GearParser;
import rx.Observable;

/**
 * 通用接口API， 接口分类跟服务端接口文档保持一致
 * Created by sue on 2016/11/15.
 */
public class CommonAPI {


    /**
     * 获取初始配置数据
     * @return
     */
    public Observable<ConfigBean> getConfig() {
        String url = BaseURL.URL_ACCOUNT_CONFIG;
        Map<String, String> params = new HashMap<>();
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<ConfigBean>(ConfigBean.class))
                .observeOnMainThread();
    }

    /**
     * 获取前置Token
     * @return
     */
    public Observable<String> getPrefixToken() {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_ACCOUNT_PRE_TOKEN;
        Map<String, String> params = new HashMap<>();
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

    /**
     * 登录
     * @param username 账号
     * @param password 密码 MD5 加密一次
     * @param imei 唯一标识
     * @param token 两次加密
     * @return
     */
    public Observable<UserBean> login(String username, String password, String imei, String token) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_ACCOUNT_LOGIN;
        Map<String, String> params = new HashMap<>();
        params.put("member_name", username);
        params.put("member_passwd", password);
        params.put("m_id", imei);
        params.put("ctype", "1"); //来源类型 1 安卓 2 ios 3 微信 4 5 6
        params.put("gtype", "2"); //登录类型 1 商城会员 member 2 卖家 store 3=业务员（运营商） marketer 4=代理商
        params.put("token", token);
        return new ClerkObserver( ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<UserBean>(UserBean.class))
                .observeOnMainThread();
    }

    /**
     * 发送验证码
     * @param memberId 用户id (注册时不需要member_id )
     * @param phone 手机号
     * @param log_type 默认为1 短信类型:1为注册,2为登录,3为找回密码,4为忘记支付密码 5.绑定手机
     * @return
     */
    public Observable<String> sendCode(String memberId, String phone, String log_type) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_ACCOUNT_SEND_CODE;
        Map<String, String> params = new HashMap<>();
        params.put("member_id", memberId);
        params.put("phone", phone);
        params.put("log_type", log_type);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

    /**
     * 忘记密码
     * @param memberpwd  用户密码
     * @param memberpwd 用户密码
     * @return
     */
    public Observable<String> forgetPassword(String memberpwd, String membernewpwd, String memberid) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_ACCOUNT_PASSWORD_FORGET;
        Map<String, String> params = new HashMap<>();
        params.put("member_id", memberid);
        params.put("new_pwd", memberpwd);
        params.put("re_pwd", membernewpwd);
//        params.put("log_id", logId);
//        params.put("type", "1"); //通过手机修改密码:1
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
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_ACCOUNT_PASSWORD_RESET;
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("member_id", memberId);
        params.put("oldpasswd", oldpasswd);
        params.put("passwd", newPasswd);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

    /**
     *注册账号
     * @param mobile
     * @param password
     * @param code 手机验证码(手机注册专用)
     * @param email
     * @param inviterId 邀请人id(没有不用填)
     * @return
     */
    public Observable<String> registMemberAccount(String mobile, String password, String code, String email, String inviterId)  {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN +  BaseURL.URL_MERCHANT_MEMBER_ACCOUNT_REGIST;
        Map<String, String> params = new HashMap<>();
        params.put("member_mobile", mobile);
        params.put("member_passwd", password);
        params.put("code", code);
        params.put("email", email);
        params.put("inviter_id", inviterId);
        params.put("reg_type", "1");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

    /**
     * 验证商家手机号是否存在
     * @return
     */
    public Observable<String> checkPhone(String member_mobile) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_ACCOUNT_CHECK_PHONE;
        Map<String, String> params = new HashMap<>();
        params.put("member_mobile", member_mobile);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }
    /**
     * 短信验证
     * @param logId 短信id
     * @param code 验证码
     * @return
     */
    public Observable<String> verifyCode(String logId, String code) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_ACCOUNT_VERIFY_CODE;
        Map<String, String> params = new HashMap<>();
        params.put("log_id", logId);
        params.put("code", code);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

    /**
     * 获取分享配置
     * @return
     */
    public Observable<ShareConfigBean> getShareConfig(String memberId) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_CONFIG_SHARE;
        Map<String, String> params = new HashMap<>();
        params.put("member_id", memberId);
        return ApiCache.getInstance().cache(url, new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<ShareConfigBean>(ShareConfigBean.class))
                .observeOnMainThread()) ;
    }


}
