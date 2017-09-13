package ccj.sz28yun.com.presenter;

import android.content.Context;

import ccj.sz28yun.com.api.VerifyAPI;
import ccj.sz28yun.com.bean.OrderCCJBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class VerifyPresenter extends AbsPresenter {
    private static final String TAG = "LoginPresenter";

    private VerifyView view;
    private Context context;
    private VerifyAPI verifyAPI;


    public VerifyPresenter( Context context, VerifyView view) {
        this.view = view;
        this.context = context;
        this.verifyAPI = new VerifyAPI();
    }


    public void verifyCode( String code){
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.verifyAPI.verifyCode(userBean.getToken(), userBean.getStoreId(), code).subscribe(
          result -> { view.onSuccess(result);},
                throwable -> { view.onError(-1, throwable.getMessage());}
        );

    }


    public interface VerifyView extends GearResultView<OrderCCJBean> {

    }
}
