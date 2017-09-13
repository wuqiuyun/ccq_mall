package ccj.sz28yun.com.presenter;

import android.content.Context;

import java.util.ArrayList;

import ccj.sz28yun.com.api.EvaluateAPI;
import ccj.sz28yun.com.bean.EvaluateMerchantBean;
import ccj.sz28yun.com.bean.EvaluateGoodsBean;
import ccj.sz28yun.com.bean.EvaluateStatisticBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.ListPresenter;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class EvaluatePresenter extends ListPresenter<ArrayList<EvaluateMerchantBean>> {
    private static final String TAG = "LoginPresenter";

    private EvaluateView resultView;
    private EvaluateAPI evaluateAPI;
    private UserBean userBean;

    private boolean isQueryMerchant = true;

    private int reType;

    public EvaluatePresenter(Context context,EvaluateView listResultView) {
        super(context, listResultView);
        this.resultView = listResultView;
        this.evaluateAPI = new EvaluateAPI();
        this.userBean = GlobalDataStorageCache.getInstance().getUserData();
    }

    public void requestStatisticData(){
        evaluateAPI.getEvaluateStatistic(userBean.getToken(), userBean.getStoreId(), 0).subscribe(
          bean -> {  this.resultView.onSuccessStatistic(bean); },
          throwable -> resultView.onError(-1 , throwable.getMessage())
        );
    }

    @Override
    public void query() {
        if(isQueryMerchant){
            subscription =  evaluateAPI.getEvaluateMerchantList(userBean.getToken(), userBean.getStoreId(), reType, getPageNum()).subscribe(
                    result -> {
                        if(isRefresh){
                            this.resultView.onSuccessRefresh(result);
                        }else{
                            this.resultView.onSuccessLoadModre(result);
                        }

                    },
                    throwable -> {
                        if(isRefresh){
                            this.resultView.onSuccessRefresh(new ArrayList<>());
                        }
                        resultView.onError(-1 , throwable.getMessage());
                    }
            );

            addSubscription(subscription);
        }else{
            subscription =  evaluateAPI.getEvaluateGoodsList(userBean.getToken(), userBean.getStoreId(),  0, getPageNum()).subscribe(
                    result -> {
                        if(isRefresh){
                            this.resultView.onSuccessGoodsRefresh(result);
                        }else{
                            this.resultView.onSuccessGoodsLoadModre(result);
                        }
                    },
                    throwable ->
                    {
                        if(isRefresh){
                            this.resultView.onSuccessRefresh(new ArrayList<>());
                        }
                        resultView.onError(-1 , throwable.getMessage());
                    }

            );
            addSubscription(subscription);
        }

    }

    public void setQueryMerchant(boolean queryMerchant) {
        isQueryMerchant = queryMerchant;
    }

    public boolean isQueryMerchant() {
        return isQueryMerchant;
    }

    public void setReType(int reType) {
        this.reType = reType;
    }

    public interface EvaluateView extends  ListResultView<ArrayList<EvaluateMerchantBean>> {

        public void onSuccessStatistic(EvaluateStatisticBean bean);

        public void onSuccessGoodsRefresh(ArrayList<EvaluateGoodsBean> result);
        public void onSuccessGoodsLoadModre(ArrayList<EvaluateGoodsBean> result) ;
    }
}
