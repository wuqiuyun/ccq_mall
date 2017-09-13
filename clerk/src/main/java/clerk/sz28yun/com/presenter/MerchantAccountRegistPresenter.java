package clerk.sz28yun.com.presenter;

import android.content.Context;
import clerk.sz28yun.com.api.CommonAPI;
import clerk.sz28yun.com.bean.UserBean;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;

import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;
import per.sue.gear2.utils.MD5Utils;

/**
 * Created by sue on 2016/12/2.
 */
public class MerchantAccountRegistPresenter extends AbsPresenter {
    private Context context;
    private MerchantAccountRegistView merchantAccountRegistView;
    private CommonAPI commonAPI;

    public MerchantAccountRegistPresenter(Context context, MerchantAccountRegistView merchantAccountRegistView) {
        this.context = context;
        this.merchantAccountRegistView = merchantAccountRegistView;
        commonAPI = new CommonAPI();
    }


    public void regist(String mobile, String pasword, String code, String email){
        pasword = MD5Utils.encrypt(pasword);
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.commonAPI.registMemberAccount(mobile,  pasword, code, email, userBean.getMemberId())
                .subscribe(
                        message -> merchantAccountRegistView.onSuccess(message)
                        , throwable -> merchantAccountRegistView.onError(-1, throwable.getMessage()));
    }

    /**
     * 发送验证码
     * @param mobile
     */
    public void sendCode(String mobile){
        commonAPI.sendCode(null, mobile, "1")
                .subscribe( message ->  merchantAccountRegistView.onCode(message),
                        throwable ->  merchantAccountRegistView.onError(-1, throwable.getMessage()));
    }

    public interface MerchantAccountRegistView extends GearResultView<String> {
        void onCode(String code);
    }

}
