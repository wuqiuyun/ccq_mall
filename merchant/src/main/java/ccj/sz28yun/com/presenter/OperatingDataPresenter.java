package ccj.sz28yun.com.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;

import ccj.sz28yun.com.api.OperatingDataAPI;
import ccj.sz28yun.com.bean.OperatingDataOrderBean;
import ccj.sz28yun.com.bean.OperatingMerchantFlowBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;
import per.sue.gear2.utils.date.DateUtils;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class OperatingDataPresenter extends AbsPresenter {
    private static final String TAG = "LoginPresenter";

    private OperatingDataView view;
    private Context context;
    private OperatingDataAPI operatingDataAPI;
    private String year;
    private String month;


    public OperatingDataPresenter(Context context, OperatingDataView view) {
        this.view = view;
        this.context = context;
        this.operatingDataAPI = new OperatingDataAPI();

        this.year = DateUtils.getYear(new Date()) + "";
        //this.mon = DateUtils.getMonth(new Date()) + "";
        this.month = "10";
    }


    public void getOrderStatisticData(){
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.operatingDataAPI.getOrderStatisticData(userBean.getToken(), userBean.getStoreId(), year, month).subscribe(
          result -> { view.onSuccess(result);},
                throwable -> { view.onError(-1, throwable.getMessage());}
        );
    }

    public void getMercgantFlowStatisticData(){
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.operatingDataAPI.getMercgantFlowStatisticData(userBean.getToken(), userBean.getStoreId()).subscribe(
                result -> { view.onSuccessMerchantFlow(result);},
                throwable -> { view.onError(-2, throwable.getMessage());}
        );
    }


    public interface OperatingDataView extends GearResultView<OperatingDataOrderBean> {
        void onSuccessMerchantFlow(ArrayList<OperatingMerchantFlowBean> list);



    }


    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
