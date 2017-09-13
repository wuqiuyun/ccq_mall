package ccj.sz28yun.com.presenter;

import android.content.Context;

import java.util.ArrayList;

import ccj.sz28yun.com.api.FinanceAPI;
import ccj.sz28yun.com.bean.FinanceDepositsBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.ListPresenter;
import per.sue.gear2.utils.date.DateStyle;
import per.sue.gear2.utils.date.DateUtils;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class FinanceTypeDepositsPresenter extends ListPresenter<ArrayList<FinanceDepositsBean>> {
    private static final String TAG = "FinanceTypeDepositsPresenter";

    private FinanceDepositsView view;
    private Context context;
    private FinanceAPI financeAPI;
    private long currentTime;
    private int type = 1;
    private int status;
    UserBean userBean ;
    public FinanceTypeDepositsPresenter(Context context, FinanceDepositsView view) {
        super(context, view);
        this.view = view;
        this.context = context;
        this.financeAPI = new FinanceAPI();
        this.currentTime = System.currentTimeMillis();
        userBean = GlobalDataStorageCache.getInstance().getUserData();

        setQuestBeforeRunable(new Runnable() {
            @Override
            public void run() {
                setObservable(financeAPI.getFinanceTypeDeposits(userBean.getToken(), userBean.getStoreId(),DateUtils.getDate(currentTime, DateStyle.YYYY_MM), type + "",  getPageNum() ));
            }
        });
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

    public void setStatus(int status) {
        this.status = status;
    }

    public interface FinanceDepositsView extends ListPresenter.ListResultView<ArrayList<FinanceDepositsBean>> {


    }
}
