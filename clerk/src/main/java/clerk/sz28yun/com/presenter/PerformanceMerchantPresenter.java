package clerk.sz28yun.com.presenter;

import android.content.Context;

import clerk.sz28yun.com.api.PerformanceAPI;
import clerk.sz28yun.com.bean.PerformanceMerchantResult;
import clerk.sz28yun.com.bean.UserBean;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;

import per.sue.gear2.presenter.ListPresenter;

/**
 * Created by sue on 2016/11/16.
 */
public class PerformanceMerchantPresenter extends ListPresenter<PerformanceMerchantResult> {

    private Context context;
    private ListResultView businessView;
    private PerformanceAPI performanceAPI;
    String token;
    String menberId;
    public PerformanceMerchantPresenter(Context context, ListResultView<PerformanceMerchantResult> listResultView) {
        super(context, listResultView);
        this.performanceAPI  =  new PerformanceAPI();
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.token = userBean.getToken();
        this.menberId = userBean.getMemberId();
//        this.menberId = "56399";

        setQuestBeforeRunable(new Runnable() {
            @Override
            public void run() {
                setObservable(performanceAPI.getPerformanceMerchant(token, menberId, getPageNum()));
            }
        });
    }

}
