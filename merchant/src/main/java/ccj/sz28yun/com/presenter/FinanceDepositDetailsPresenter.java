package ccj.sz28yun.com.presenter;

import android.content.Context;

import ccj.sz28yun.com.api.FinanceAPI;
import ccj.sz28yun.com.api.VerifyAPI;
import ccj.sz28yun.com.bean.FinanceDepositFreeDetailsBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class FinanceDepositDetailsPresenter extends AbsPresenter {
    private static final String TAG = "FinanceDepositDetailsPresenter";

    private FinanceDepositDetailsView view;
    private Context context;
    private FinanceAPI financeAPI;


    public FinanceDepositDetailsPresenter(Context context, FinanceDepositDetailsView view) {
        this.view = view;
        this.context = context;
        this.financeAPI = new FinanceAPI();
    }


    public void getDetails(  String id){
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.financeAPI.getFinanceDepositDetails(userBean.getToken(), userBean.getStoreId(), id).subscribe(
          result -> { view.onSuccess(result);},
                throwable -> { view.onError(-1, throwable.getMessage());}
        );

    }


    public interface FinanceDepositDetailsView extends GearResultView<FinanceDepositFreeDetailsBean> {

    }
}
