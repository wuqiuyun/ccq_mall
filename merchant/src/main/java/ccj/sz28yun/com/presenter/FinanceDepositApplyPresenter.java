package ccj.sz28yun.com.presenter;

import android.content.Context;

import ccj.sz28yun.com.api.FinanceAPI;
import ccj.sz28yun.com.bean.FinanceApplyDepositBean;
import ccj.sz28yun.com.bean.FinanceDepositFreeDetailsBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class FinanceDepositApplyPresenter extends AbsPresenter {
    private static final String TAG = "FinanceDepositDetailsPresenter";

    private FinanceDepositDetailsView view;
    private Context context;
    private FinanceAPI financeAPI;


    public FinanceDepositApplyPresenter(Context context, FinanceDepositDetailsView view) {
        this.view = view;
        this.context = context;
        this.financeAPI = new FinanceAPI();
    }


    public void getAccountInfo(  String type){
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        addSubscription( this.financeAPI.getFinanceApplyDepositAccount(userBean.getToken(), userBean.getStoreId(), type).subscribe(
          result -> { view.onSuccess(result);},
                throwable -> { view.onError(-1, throwable.getMessage());}
        ));
    }

    public void apply( String amount, String type, String bank_name, String card_account, String name){
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        addSubscription( this.financeAPI.applyDeposit(userBean.getToken(),userBean.getStoreId(),  amount, type, bank_name, card_account, name ).subscribe(
                result -> { view.onSuccessApply(result);},
                throwable -> { view.onError(-1, throwable.getMessage());}
        ));
    }


    public interface FinanceDepositDetailsView extends GearResultView<FinanceApplyDepositBean> {
               void onSuccessApply(String message);
    }
}
