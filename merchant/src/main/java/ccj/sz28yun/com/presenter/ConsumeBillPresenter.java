package ccj.sz28yun.com.presenter;

import android.content.Context;

import java.util.ArrayList;

import ccj.sz28yun.com.api.VerifyAPI;
import ccj.sz28yun.com.bean.ConsumeBillBean;
import ccj.sz28yun.com.bean.NormalStatisticsBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.ListPresenter;
import per.sue.gear2.utils.date.DateUtils;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class ConsumeBillPresenter extends ListPresenter<ArrayList<ConsumeBillBean>> {
    private static final String TAG = "LoginPresenter";

    private ConsumeBillView resultView;
    private Context context;
    private VerifyAPI verifyAPI;
    private UserBean userBean;

    private String date;
    private String keyword;


    public ConsumeBillPresenter(Context context, ConsumeBillView listResultView) {
        super(context, listResultView);
        date = DateUtils.getCurrentDate();
        resultView = listResultView;
        verifyAPI = new VerifyAPI();
        userBean = GlobalDataStorageCache.getInstance().getUserData();


    }

    @Override
    public void query() {
        verifyAPI.getConsumeBillList(userBean.getToken(), userBean.getStoreId(),date,  keyword, getPageNum()).subscribe(
                consumeBillResult -> {
                    if(isRefresh){
                        this.resultView.onSuccessRefresh(consumeBillResult.getList());
                    }else{
                        this.resultView.onSuccessLoadModre(consumeBillResult.getList());
                    }

                    if(null != consumeBillResult.getConsumeStatisticsList()){
                        this.resultView.onSuccessStatistic(consumeBillResult.getConsumeStatisticsList());
                    }

                },
                throwable -> {
                    this.resultView.onError(-1, throwable.getMessage());
                }

        );
    }

    public void checkBill(String orderId){
        verifyAPI.checkConsumeBill(userBean.getToken(), userBean.getStoreId(), orderId).subscribe(
                s -> { this.resultView.onSuccessChecked();},
                throwable -> { this.resultView.onError(-1, throwable.getMessage());}
        );
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public interface ConsumeBillView extends ListResultView<ArrayList<ConsumeBillBean>> {

     void  onSuccessStatistic(ArrayList<NormalStatisticsBean> result);
        void  onSuccessChecked();
    }
}
