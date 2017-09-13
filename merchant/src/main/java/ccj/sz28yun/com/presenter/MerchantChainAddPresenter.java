package ccj.sz28yun.com.presenter;

import android.content.Context;

import org.json.JSONException;

import ccj.sz28yun.com.api.MerchantAPI;
import ccj.sz28yun.com.api.VerifyAPI;
import ccj.sz28yun.com.bean.MerchantChainParams;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class MerchantChainAddPresenter extends AbsPresenter {
    private static final String TAG = "LoginPresenter";

    private MerchantChainAddView view;
    private Context context;
    private MerchantAPI merchantAPI;


    public MerchantChainAddPresenter(Context context, MerchantChainAddView view) {
        this.view = view;
        this.context = context;
        this.merchantAPI = new MerchantAPI();
    }


    public void submit(MerchantChainParams params){
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        try {
            this.merchantAPI.addMerchantChain(params).subscribe(
              result -> { view.onSuccess(result);},
                    throwable -> { view.onError(-1, throwable.getMessage());}
            );
        } catch (JSONException e) {
            e.printStackTrace();
            view.onError(-1, "数据异常, 请联系相关人员");
        }

    }


    public interface MerchantChainAddView extends GearResultView<String> {

    }
}
