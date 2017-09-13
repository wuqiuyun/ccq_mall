package ccj.sz28yun.com.presenter;

import android.content.Context;

import java.util.ArrayList;

import ccj.sz28yun.com.api.MerchantAPI;
import ccj.sz28yun.com.api.MessageAPI;
import ccj.sz28yun.com.bean.MerchantChainBean;
import ccj.sz28yun.com.bean.MessageBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.ListPresenter;

/**
 * Created by sue on 2016/12/15.
 */
public class MerchantChainListPresenter extends ListPresenter<ArrayList<MerchantChainBean>> {

    private MerchantChainListView messageView;
    private MerchantAPI merchantAPI;
    UserBean userBean;
    public MerchantChainListPresenter(Context context, MerchantChainListView listResultView) {
        super(context, listResultView);
        this.messageView = listResultView;
        this.merchantAPI = new MerchantAPI();
        userBean = GlobalDataStorageCache.getInstance().getUserData();
    }


    @Override
    public void query() {
        merchantAPI.getMerchantChainList(userBean.getToken(), userBean.getStoreId(), getPageNum()).subscribe(
                result -> {
                    if (isRefresh) {
                        this.messageView.onSuccessRefresh(result);
                    } else {
                        this.messageView.onSuccessLoadModre(result);
                    }
                },
                throwable -> {
                    messageView.onError(-1, throwable.getMessage());
                }
        );
    }

    public  void delete(String targetId){
        this.merchantAPI.deleteMerchantChain(userBean.getToken(),  targetId).subscribe(
                result -> { messageView.onSuccessDeleted(result);},
                throwable -> { messageView.onError(-1, throwable.getMessage());}
        );
    }





    public interface MerchantChainListView extends ListResultView<ArrayList<MerchantChainBean>> {

        public void onSuccessDeleted(String message);


    }
}
