package ccj.sz28yun.com.presenter;

import android.content.Context;

import java.util.ArrayList;

import ccj.sz28yun.com.api.FinanceAPI;
import ccj.sz28yun.com.api.MessageAPI;
import ccj.sz28yun.com.bean.FinanceDayBean;
import ccj.sz28yun.com.bean.MessageBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.ListPresenter;

/**
 * Created by sue on 2016/12/15.
 */
public class FinanceTypeDayPresenter extends ListPresenter<ArrayList<FinanceDayBean>> {

    private FinanceTypeDayView financeTypeDayView;
    private FinanceAPI financeAPI;
    UserBean userBean;
    private int type;
    private String date;
    public FinanceTypeDayPresenter(Context context, FinanceTypeDayView listResultView) {
        super(context, listResultView);
        this.financeTypeDayView = listResultView;
        this.financeAPI = new FinanceAPI();
        userBean = GlobalDataStorageCache.getInstance().getUserData();
    }

    public void init(int type,  String date){
        this.type = type;
        this.date = date;

    }


    @Override
    public void query() {
        financeAPI.getFinanceTypeDay(userBean.getToken(), userBean.getStoreId(), date, type + "",   getPageNum()).subscribe(
                result -> {
                    if (isRefresh) {
                        this.financeTypeDayView.onSuccessRefresh(result);
                    } else {
                        this.financeTypeDayView.onSuccessLoadModre(result);
                    }
                },
                throwable -> {
                    financeTypeDayView.onError(-1, throwable.getMessage());
                }
        );
    }







    public interface FinanceTypeDayView extends ListResultView<ArrayList<FinanceDayBean>> {

    }
}
