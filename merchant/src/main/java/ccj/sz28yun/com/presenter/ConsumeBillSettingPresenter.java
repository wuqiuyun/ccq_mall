package ccj.sz28yun.com.presenter;

import android.content.Context;

import ccj.sz28yun.com.api.VerifyAPI;
import ccj.sz28yun.com.bean.ConsumBillSettingResult;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class ConsumeBillSettingPresenter extends AbsPresenter {
    private static final String TAG = "LoginPresenter";

    private ConsumeBillSettingView view;
    private Context context;
    private VerifyAPI verifyAPI;


    public ConsumeBillSettingPresenter(Context context, ConsumeBillSettingView view) {
        this.view = view;
        this.context = context;
        this.verifyAPI = new VerifyAPI();
    }


    public void setSetting(  String unionPay, String unionPayDiscount){
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.verifyAPI.setVerifyConsumeBill(userBean.getToken(), userBean.getStoreId(), unionPay, unionPayDiscount).subscribe(
          result -> { view.onSuccess(result);},
                throwable -> { view.onError(-1, throwable.getMessage());}
        );
    }

    public void getConsumeBillSettingInfo(){
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.verifyAPI.getConsumeBillSettingInfo(userBean.getToken(), userBean.getStoreId()).subscribe(
                result -> { view.onSuccessSetting(result);},
                throwable -> { view.onError(-1, throwable.getMessage());}
        );
    }


    public interface  ConsumeBillSettingView extends GearResultView<String> {

        void onSuccessSetting(ConsumBillSettingResult result);

    }
}
