package ccj.sz28yun.com.presenter;

import android.content.Context;

import ccj.sz28yun.com.api.EvaluateAPI;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class EvaluateReplyPresenter extends AbsPresenter {
    private static final String TAG = "LoginPresenter";

    private EvaluateReplyView view;
    private Context context;
    private EvaluateAPI evaluateAPI;


    public EvaluateReplyPresenter(Context context, EvaluateReplyView view) {
        this.view = view;
        this.context = context;
        this.evaluateAPI = new EvaluateAPI();
    }


    public void replyMerchant( String sevalId, String content){
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.evaluateAPI.replyEvaluateMerchant(userBean.getToken(), sevalId, content).subscribe(
          result -> { view.onSuccessReplyMerchant();},
                throwable -> { view.onError(-1, throwable.getMessage());}
        );
    }

    public void replyGoods( String gevalId, String content){
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.evaluateAPI.replyEvaluateGoods(userBean.getToken(), gevalId, content).subscribe(
                result -> { view.onSuccessReplyGoods();},
                throwable -> { view.onError(-1, throwable.getMessage());}
        );
    }


    public interface EvaluateReplyView extends GearResultView<String> {

        void onSuccessReplyGoods();
        void onSuccessReplyMerchant();

    }
}
