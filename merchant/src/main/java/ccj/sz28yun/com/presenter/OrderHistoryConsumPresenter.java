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
public class OrderHistoryConsumPresenter extends ListPresenter<ArrayList<OrderConsumeBillBean>> {

    private static final String TAG = "LoginPresenter";

    private OrderHistoryView resultView;
    private OrderHistoryAPI orderHistoryAPI;
    private UserBean userBean;

    private long dateTime;

    public OrderHistoryConsumPresenter(Context context, OrderHistoryView listResultView) {
        super(context, listResultView);
        this.resultView = listResultView;
        this.orderHistoryAPI = new OrderHistoryAPI();
        this.userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.dateTime = 0;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public void query() {

       subscription = orderHistoryAPI.getOrderHistoryForConsumeBill(userBean.getToken(),  userBean.getStoreId(), DateUtils.getDate(dateTime), getPageNum()).subscribe(
                result -> {
                    if (isRefresh) {
                        this.resultView.onSuccessRefresh(result.getOrderConsumeBillBeanArrayList());
                    } else {
                        this.resultView.onSuccessLoadModre(result.getOrderConsumeBillBeanArrayList());
                    }
                },
                throwable -> resultView.onError(-1, throwable.getMessage())
        );

        addSubscription(subscription);
    }


    public interface OrderHistoryView extends ListResultView<ArrayList<OrderConsumeBillBean>> {


    }
}
