package ccj.sz28yun.com.presenter;

import android.content.Context;

import org.json.JSONException;

import ccj.sz28yun.com.api.MerchantAPI;
import ccj.sz28yun.com.api.VerifyAPI;
import ccj.sz28yun.com.bean.MerchantInfoBean;
import ccj.sz28yun.com.bean.MerchantInfoParams;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class MerchantSettingPresenter extends AbsPresenter {
    private static final String TAG = "LoginPresenter";

    private MerchantSettingView view;
    private Context context;
    private MerchantAPI merchantAPI;
    UserBean userBean;

    public MerchantSettingPresenter(Context context, MerchantSettingView view) {
        this.view = view;
        this.context = context;
        this.merchantAPI = new MerchantAPI();
        userBean  = GlobalDataStorageCache.getInstance().getUserData();
    }


    public void getMerchantInfo(){
       addSubscription(this.merchantAPI.getMerchantInfo(userBean.getToken(), userBean.getStoreId()).subscribe(
               result -> { view.onSuccess(result);},
               throwable -> { view.onError(-1, throwable.getMessage());}
       ));

    }

    public void editMerchantInfo(MerchantInfoParams merchantInfoParams){
        try {
            addSubscription( this.merchantAPI.editMerchantInfo(merchantInfoParams).subscribe(
                    result -> { view.onSuccessEdit(result);},
                    throwable -> { view.onError(-1, throwable.getMessage());}
            ));
        } catch (JSONException e) {
            e.printStackTrace();
            view.onError(-1, "数据转换错误， 请联系相关人员");
        }
    }


    public interface MerchantSettingView extends GearResultView<MerchantInfoBean> {

        void onSuccessEdit(String message);

    }
}
