package clerk.sz28yun.com.presenter;

import android.content.Context;

import clerk.sz28yun.com.api.MerchantAPI;
import clerk.sz28yun.com.bean.MerchantCategoryBean;
import clerk.sz28yun.com.bean.UserBean;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;

import java.util.ArrayList;

import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;
import rx.Subscriber;

/**
 * Created by sue on 2016/11/16.
 */
public class MerchantCategoryPresenter extends AbsPresenter {

    private Context context;
    private MerchantCategoryView merchantCategoryView;
    private MerchantAPI merchantAPI;
    ArrayList<MerchantCategoryBean> list;

    public MerchantCategoryPresenter(Context context, MerchantCategoryView view) {
        this.context = context;
        this.merchantCategoryView = view;
        this.merchantAPI  =  new MerchantAPI();
    }

    public void syncData(){
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        String token = userBean.getToken();
        String menberId = userBean.getMemberId();
        this.merchantAPI.getMerchantCategory(token).subscribe(new Subscriber<ArrayList<MerchantCategoryBean>>() {
            @Override
            public void onCompleted() {
                merchantCategoryView.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                merchantCategoryView.onError(-1, e.getMessage());
            }

            @Override
            public void onNext(ArrayList<MerchantCategoryBean> result) {
                list = result;
                merchantCategoryView.onSuccess(result);
                loadSecondData(0);
            }
        });
    }

    public void loadSecondData(int index){
        if(null != list && list.size() > index){
            merchantCategoryView.loadSecondList(list.get(index).getChildList());
        }
    }

    public interface MerchantCategoryView extends GearResultView<ArrayList<MerchantCategoryBean>> {

        void loadSecondList(ArrayList<MerchantCategoryBean> list);


    }
}
