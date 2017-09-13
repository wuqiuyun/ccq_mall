package ccj.sz28yun.com.presenter;

import android.content.Context;

import java.util.ArrayList;

import ccj.sz28yun.com.api.OrderHistoryAPI;
import ccj.sz28yun.com.bean.OrderCCJBean;
import ccj.sz28yun.com.bean.OrderConsumeBillBean;
import ccj.sz28yun.com.bean.OrderStatisticBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.exception.GearException;
import per.sue.gear2.presenter.ListPresenter;
import per.sue.gear2.utils.date.DateUtils;


/**
 * Created by sue on 2016/12/13.
 */
public class OrderHistoryCCJPresenter extends ListPresenter<ArrayList<OrderCCJBean>> {

    private static final String TAG = "LoginPresenter";

    private OrderHistoryView resultView;
    private OrderHistoryAPI orderHistoryAPI;
    private UserBean userBean;

    private long dateTime;

    public OrderHistoryCCJPresenter(Context context, OrderHistoryView listResultView) {
        super(context, listResultView);
        this.resultView = listResultView;
        this.orderHistoryAPI = new OrderHistoryAPI();
        this.userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.dateTime = 0;
    }


    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

//    @Override
//    public void query() {
//
////        subscription = orderHistoryAPI.getOrderHistoryForCCJ(userBean.getToken(),  userBean.getStoreId(), DateUtils.getDate(dateTime), getPageNum()).subscribe(
//        subscription = orderHistoryAPI.getOrderHistoryForCCJ(userBean.getToken(),  userBean.getStoreId(), DateUtils.getDate(dateTime), getPageNum()).subscribe(
//        result -> {
//                    if (isRefresh) {
//                        this.resultView.onSuccessRefresh(result.getOrderCCJBeanArrayList());
//                        this.resultView.onSuccessStatistic(result.getStatisticBeanArrayList());
//                    } else {
//                        this.resultView.onSuccessLoadModre(result.getOrderCCJBeanArrayList());
//                    }
//                },
//                throwable -> resultView.onError(-1, throwable.getMessage())
//        );
//
//        addSubscription(subscription);
//
//    }
    @Override
    public void query() {
//        super.query();
        subscription =  orderHistoryAPI.getOrderHistoryForCCJ(userBean.getToken(),  userBean.getStoreId(), DateUtils.getDate(dateTime), getPageNum()).subscribe(
                result -> {
                    if(isRefresh){
                        resultView .onSuccessRefresh(result.getOrderCCJBeanArrayList());
                        resultView.onSuccessStatistic(result.getStatisticBeanArrayList());
                    }else{
                        resultView.onSuccessLoadModre(result.getOrderCCJBeanArrayList());
                    }
                },
                throwable -> {
                    if(throwable instanceof GearException){
                        GearException gearException = (GearException)throwable;
                        if(300 == gearException.getCode()){//300 为空数据
                            if(isRefresh){
                                resultView .onSuccessRefresh(new ArrayList<>());
                            }else{
                                resultView.onSuccessLoadModre(new ArrayList<>());
                            }
                        }else{
                            resultView.onError(gearException.getCode(), throwable.getMessage());
                        }

                    }else{
                        resultView.onError(-1, throwable.getMessage());
                    }
                }
        );
        addSubscription(subscription);
    }


    public interface OrderHistoryView extends ListResultView<ArrayList<OrderCCJBean>> {
        public void onSuccessStatistic(ArrayList<OrderStatisticBean> bean);

    }


}
