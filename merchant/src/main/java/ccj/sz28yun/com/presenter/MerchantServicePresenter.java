package ccj.sz28yun.com.presenter;

import android.content.Context;

import org.json.JSONException;

import ccj.sz28yun.com.api.MerchantAPI;
import ccj.sz28yun.com.bean.MerchantInfoBean;
import ccj.sz28yun.com.bean.MerchantInfoParams;
import ccj.sz28yun.com.bean.MerchantServiceBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class MerchantServicePresenter extends AbsPresenter {
    private static final String TAG = "LoginPresenter";

    private MerchantServiceView view;
    private Context context;
    private MerchantAPI merchantAPI;
    UserBean userBean;

    public MerchantServicePresenter(Context context, MerchantServiceView view) {
        this.view = view;
        this.context = context;
        this.merchantAPI = new MerchantAPI();
        userBean  = GlobalDataStorageCache.getInstance().getUserData();
    }


    public void getMerchantService(){
        addSubscription(this.merchantAPI.getMerchantService(userBean.getToken(), userBean.getStoreId()).subscribe(
                result -> { view.onSuccess(result);},
                throwable -> { view.onError(-1, throwable.getMessage());}
        ));

    }

    public void editMerchantService(MerchantServiceBean bean){
        bean.merchantId = userBean.getStoreId();
        bean.token = userBean.getToken();
        try {
            addSubscription(this.merchantAPI.editMerchantService(bean).subscribe(
                    result -> { view.onSuccessEdit(result);},
                    throwable -> { view.onError(-1, throwable.getMessage());}
            ));
        } catch (JSONException e) {
            e.printStackTrace();
            view.onError(-1, "数据转换错误， 请联系相关人员");
        }
    }


    public interface MerchantServiceView extends GearResultView<MerchantServiceBean> {

        void onSuccessEdit(String message);

    }
}
