package ccj.sz28yun.com.presenter;

import android.content.Context;

import ccj.sz28yun.com.api.FinanceAPI;
import ccj.sz28yun.com.bean.FinanceBankBean;
import ccj.sz28yun.com.bean.FinanceFundsBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class FinancePresenter extends AbsPresenter {
    private static final String TAG = "LoginPresenter";

    private FinanceView view;
    private Context context;
    private FinanceAPI financeAPI;


    public FinancePresenter(Context context, FinanceView view) {
        this.view = view;
        this.context = context;
        this.financeAPI = new FinanceAPI();
    }


    public void getFundsAndBankCards(){
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.financeAPI.getFundsAndBankCard(userBean.getToken(), userBean.getStoreId()).subscribe(
          result -> {
              view.onSuccess(result.getFinanceFundsBean());
              view.onSuceessBankCard(result.getFinanceBankBean());
          },
                throwable -> { view.onError(-1, throwable.getMessage());}
        );

    }


    public interface FinanceView extends GearResultView<FinanceFundsBean> {

        void onSuceessBankCard(FinanceBankBean bean);

    }
}
