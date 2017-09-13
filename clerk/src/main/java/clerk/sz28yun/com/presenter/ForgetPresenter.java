package clerk.sz28yun.com.presenter;

import android.content.Context;
import clerk.sz28yun.com.api.CommonAPI;
import clerk.sz28yun.com.cache.APIParamsCache;
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
     * @param mobile 手机号/用户名
     * @param pasword 密码
     * @param code 验证码
     */
    public void forgetPasswd(String mobile, String pasword, String code){
        pasword = MD5Utils.encrypt(pasword);
        commonAPI.forgetPassword(mobile,  pasword, code, APIParamsCache.getInstance().getLogId())
                .subscribe(
                           message -> forgetView.onSuccess(message)
                        , throwable -> forgetView.onError(-1, throwable.getMessage()));
    }

    /**
     * 发送验证码
     * @param mobile
     */
    public void sendCode(String mobile){
        commonAPI.sendCode(null, mobile, "3")
                .subscribe( message ->  forgetView.onCode(message), throwable ->  forgetView.onError(-2, throwable.getMessage()));
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
