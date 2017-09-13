package ccj.sz28yun.com.presenter;

import android.content.Context;

import java.util.ArrayList;

import ccj.sz28yun.com.api.FinanceAPI;
import ccj.sz28yun.com.bean.FinanceMonthBean;
import ccj.sz28yun.com.bean.FinanceStatisticBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;
import per.sue.gear2.utils.DoubleUtil;
import per.sue.gear2.utils.date.DateUtils;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class FinanceTypeMonthPresenter extends AbsPresenter {
    private static final String TAG = "LoginPresenter";

    private FinanceMonthView view;
    private Context context;
    private FinanceAPI financeAPI;
    private long currentTime;
    private int type = 1;
    public FinanceTypeMonthPresenter(Context context, FinanceMonthView view) {
        this.view = view;
        this.context = context;
        this.financeAPI = new FinanceAPI();
        this.currentTime = System.currentTimeMillis();
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void getFinanceTypeMonth(){
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        addSubscription( this.financeAPI.getFinanceTypeMonth(userBean.getToken(), userBean.getStoreId(), DateUtils.getDate(currentTime), type + "").subscribe(
                result -> {
                    view.onSuccess(result.getFinanceMonthBeanArrayList());
                    view.onSuccessTotal(DoubleUtil.formatPrice(result.sum ));
                    view.onSuccessStatistic(result.getFinanceStatisticBean());
                },
                throwable -> { view.onError(-1, throwable.getMessage());}
        ));

    }


    public interface FinanceMonthView extends GearResultView<ArrayList<FinanceMonthBean>> {
        void onSuccessStatistic(FinanceStatisticBean statisticBean);
        void onSuccessTotal(String sum);

    }
}
