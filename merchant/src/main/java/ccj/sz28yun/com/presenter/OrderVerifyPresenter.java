package ccj.sz28yun.com.presenter;

import android.content.Context;

import ccj.sz28yun.com.api.OrderHistoryAPI;
import ccj.sz28yun.com.api.VerifyAPI;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;
import per.sue.gear2.ui.IBaseView;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class OrderVerifyPresenter extends AbsPresenter {
    private static final String TAG = "LoginPresenter";

    private OrderVerifyView view;
    private Context context;
    private OrderHistoryAPI orderHistoryAPI;


    public OrderVerifyPresenter(Context context, OrderVerifyView view) {
        this.view = view;
        this.context = context;
        this.orderHistoryAPI = new OrderHistoryAPI();
    }


    public void verifyCode(  String orderId){
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.orderHistoryAPI.checkOrderBill(userBean.getToken(), userBean.getStoreId(), orderId).subscribe(
          result -> { view.onSuccessVerify(result);},
                throwable -> { view.onError(-1, throwable.getMessage());}
        );

    }


    public interface OrderVerifyView extends IBaseView {

        void onSuccessVerify(String result);
    }
}
