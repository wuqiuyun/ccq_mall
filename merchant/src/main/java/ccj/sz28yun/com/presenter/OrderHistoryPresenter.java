package ccj.sz28yun.com.presenter;

import android.content.Context;

import java.util.ArrayList;

import ccj.sz28yun.com.api.OrderHistoryAPI;
import ccj.sz28yun.com.bean.OrderCCJBean;
import ccj.sz28yun.com.bean.OrderConsumeBillBean;
import ccj.sz28yun.com.bean.OrderStatisticBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.ListPresenter;
import per.sue.gear2.utils.date.DateUtils;


/**
 * Created by sue on 2016/12/13.
 */
public class OrderHistoryPresenter extends ListPresenter<ArrayList<OrderCCJBean>> {

    private static final String TAG = "LoginPresenter";

    private OrderHistoryView resultView;
    private OrderHistoryAPI orderHistoryAPI;
    private UserBean userBean;

    private boolean isQueryCCJ = true;
    private String date;

    public OrderHistoryPresenter(Context context, OrderHistoryView listResultView) {
        super(context, listResultView);
        this.resultView = listResultView;
        this.orderHistoryAPI = new OrderHistoryAPI();
        this.userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.date = DateUtils.getCurrentDate();
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public void query() {

        if (isQueryCCJ) {
            orderHistoryAPI.getOrderHistoryForCCJ(userBean.getToken(),  userBean.getStoreId(), date, getPageNum()).subscribe(
                    result -> {
                        if (isRefresh) {
                            this.resultView.onSuccessRefresh(result.getOrderCCJBeanArrayList());
                            this.resultView.onSuccessStatistic(result.getStatisticBeanArrayList());
                        } else {
                            this.resultView.onSuccessLoadModre(result.getOrderCCJBeanArrayList());
                        }
                    },
                    throwable -> resultView.onError(-1, throwable.getMessage())
            );
        } else {
            orderHistoryAPI.getOrderHistoryForConsumeBill(userBean.getToken(),  userBean.getStoreId(), date, getPageNum()).subscribe(
                    result -> {
                        if (isRefresh) {
                            this.resultView.onSuccessOrderConsumeBillRefresh(result.getOrderConsumeBillBeanArrayList());
                        } else {
                            this.resultView.onSuccessOrderConsumeBillLoadModre(result.getOrderConsumeBillBeanArrayList());
                        }
                    },
                    throwable -> resultView.onError(-1, throwable.getMessage())
            );
        }

    }

    public void setQueryCCJ(boolean queryCCJ) {
        isQueryCCJ = queryCCJ;
    }

    public interface OrderHistoryView extends ListPresenter.ListResultView<ArrayList<OrderCCJBean>> {

        public void onSuccessStatistic(ArrayList<OrderStatisticBean> bean);

        public void onSuccessOrderConsumeBillRefresh(ArrayList<OrderConsumeBillBean> result);

        public void onSuccessOrderConsumeBillLoadModre(ArrayList<OrderConsumeBillBean> result);
    }
}
