package ccj.sz28yun.com.presenter;

import android.content.Context;

import java.util.ArrayList;

import ccj.sz28yun.com.api.EvaluateAPI;
import ccj.sz28yun.com.bean.EvaluateGoodsBean;
import ccj.sz28yun.com.bean.EvaluateMerchantBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.ListPresenter;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class EvaluateGoodsPresenter extends ListPresenter<ArrayList<EvaluateGoodsBean>> {
    private static final String TAG = "LoginPresenter";

    private EvaluateGoodsView resultView;
    private EvaluateAPI evaluateAPI;
    private UserBean userBean;
    private int reType;



    public EvaluateGoodsPresenter(Context context, EvaluateGoodsView listResultView) {
        super(context, listResultView);
        this.resultView = listResultView;
        this.evaluateAPI = new EvaluateAPI();
        this.userBean = GlobalDataStorageCache.getInstance().getUserData();
    }


    public void setReType(int reType) {
        this.reType = reType;
    }

    @Override
    public void query() {

        subscription =  evaluateAPI.getEvaluateGoodsList(userBean.getToken(), userBean.getStoreId(), reType, getPageNum()).subscribe(
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

    }


    public interface EvaluateGoodsView extends  ListResultView<ArrayList<EvaluateGoodsBean>> {

    }
}
