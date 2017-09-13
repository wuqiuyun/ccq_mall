package ccj.sz28yun.com.presenter;

import android.content.Context;


import ccj.sz28yun.com.api.CommonAPI;
import ccj.sz28yun.com.bean.AccountBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.APIParamsCache;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;
import per.sue.gear2.utils.DeviceUtils;
import per.sue.gear2.utils.MD5Utils;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class LoginPresenter extends AbsPresenter {
    private static final String TAG = "LoginPresenter";

    private LoginView loginView;
    private Context context;
    private CommonAPI commonAPI;

    private  String passwordMd5;

    public LoginPresenter(LoginView loginView, Context context) {
        this.loginView = loginView;
        this.context = context;
        this.commonAPI = new CommonAPI();
    }

    /**
     * 登录
     * @param account
     * @param password
     */
    public void login(final String account, final  String password){
        final String unqueId = DeviceUtils.getUniqueId(context);
        this.commonAPI.getPrefixToken().flatMap(new Func1<String, Observable<UserBean>>() {
            @Override
            public Observable<UserBean> call(String s) {
                passwordMd5 = MD5Utils.encrypt(password);
                String needMd5 = new StringBuilder(APIParamsCache.getInstance().getPrefixToken()).append("be56e0").append(unqueId).append(account).append(passwordMd5).toString();
                String token = MD5Utils.encrypt(needMd5);
               // GearLog.e(TAG, "PRE_TOKEN=" + ClerkObserver.PRE_TOKEN + "   be56e0   m_id =" + unqueId + "   account=" + account +  "  password=" + password +"   MD5Utils.encrypt(password)=" + MD5Utils.encrypt(password) );
               // GearLog.e(TAG, "md5 before=" + needMd5);
               // GearLog.e(TAG, "md5 after=" + passwordMd5);
                return  commonAPI.login(account, passwordMd5, unqueId,token );
            }
        }) .subscribe(new Subscriber<UserBean>() {
            @Override
            public void onCompleted() {
                loginView.onCompleted();

            }

            @Override
            public void onError(Throwable e) {
                loginView.onError(0, e.getMessage());
            }

            @Override
            public void onNext(UserBean userBean) {
                GlobalDataStorageCache.getInstance().storeAccountData(new AccountBean(account, passwordMd5));
                GlobalDataStorageCache.getInstance().storeUserData(userBean);
                loginView.onSuccess(userBean);
            }
        });
    }

    public void md5Password(){

    }

    public interface LoginView extends GearResultView<UserBean> {

    }
}
