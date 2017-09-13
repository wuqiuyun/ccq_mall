package ccj.sz28yun.com.presenter;

import android.content.Context;

import java.util.ArrayList;

import ccj.sz28yun.com.api.EvaluateAPI;
import ccj.sz28yun.com.bean.EvaluateGoodsBean;
import ccj.sz28yun.com.bean.EvaluateMerchantBean;
import ccj.sz28yun.com.bean.EvaluateStatisticBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.ListPresenter;
import per.sue.gear2.ui.IBaseView;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class EvaluateStatisicPresenter extends AbsPresenter {
    private static final String TAG = "LoginPresenter";

    private EvaluateStatisicView resultView;
    private EvaluateAPI evaluateAPI;
    private UserBean userBean;


    private int  type ;

    public EvaluateStatisicPresenter(Context context, EvaluateStatisicView listResultView) {
        this.resultView = listResultView;
        this.evaluateAPI = new EvaluateAPI();
        this.userBean = GlobalDataStorageCache.getInstance().getUserData();
    }

    public void requestStatisticData(){
        evaluateAPI.getEvaluateStatistic(userBean.getToken(), userBean.getStoreId(), type).subscribe(
          bean -> {  this.resultView.onSuccessStatistic(bean); },
          throwable -> resultView.onError(-1 , throwable.getMessage())
        );
    }

    public void setType(int type) {
        this.type = type;
    }

    public interface EvaluateStatisicView extends IBaseView {
        public void onSuccessStatistic(EvaluateStatisticBean bean);
    }
}
