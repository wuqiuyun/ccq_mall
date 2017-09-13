package ccj.sz28yun.com.presenter;

import android.content.Context;


import ccj.sz28yun.com.api.CommonAPI;
import ccj.sz28yun.com.cache.APIParamsCache;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;
import per.sue.gear2.utils.MD5Utils;

/*
* 文件名：
* 描 述 ：
* 作 者：苏昭强
* 时 间：2016/4/27
*/
public class ForgetPresenter extends AbsPresenter {

    private Context context;
    private ForgetView forgetView;
    private CommonAPI commonAPI;

    public ForgetPresenter(Context context, ForgetView registView) {
        this.context = context;
        this.forgetView = registView;
        commonAPI = new CommonAPI();
    }

    /**
     *忘记密码
     * @param pasword 密码
     */
    public void forgetPasswd(String pasword, String newpasword, String member_id){
        pasword = MD5Utils.encrypt(pasword);
        newpasword = MD5Utils.encrypt(newpasword);
        commonAPI.forgetPassword(pasword,  newpasword, member_id)
                .subscribe(
                           message -> forgetView.onSuccess(message)
                        , throwable -> forgetView.onError(-1, throwable.getMessage()));
    }

    /**
     *验证商家手机号是否存在
     * @param mobile 手机号/用户名
     */
    public void checkPhone(String mobile){
        commonAPI.checkPhone(mobile)
                .subscribe(
                           message -> forgetView.onSuccess(message)
                        , throwable -> forgetView.onError(-1, throwable.getMessage()));
    }



    /**
     * 发送验证码
     * @param mobile
     */
    public void sendCode(String mobile){
        commonAPI.sendCode(null, mobile, "4")
                .subscribe( message ->  forgetView.onCode(message), throwable ->  forgetView.onError(-1, throwable.getMessage()));
    }

    /**
     * 效验验证码
     * @param code
     */
    public void verifyCode(String code){
        String logId = APIParamsCache.getInstance().getLogId();
        commonAPI.verifyCode(logId, code)
                .subscribe( message ->  forgetView.onCodeCorrected(message), throwable ->  forgetView.onError(-3, throwable.getMessage()));
    }

    public interface ForgetView extends GearResultView<String> {
        void onCode(String code);
        void onCodeCorrected(String code);
    }
}
