package ccj.sz28yun.com.presenter;

import android.content.Context;

import ccj.sz28yun.com.api.CouponAPI;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class CouponOnePublishPresenter extends AbsPresenter {
    private static final String TAG = "LoginPresenter";

    private CouponOnePublishView view;
    private Context context;
    private CouponAPI couponAPI;


    public CouponOnePublishPresenter(Context context, CouponOnePublishView view) {
        this.view = view;
        this.context = context;
        this.couponAPI = new CouponAPI();
    }


    public void submit( String goodsIds, String discounPercent, String discountCount){
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        if(goodsIds.contains("[")){
            this.couponAPI.publishDoubleCoupon(userBean.getToken(), userBean.getStoreId(), goodsIds, discounPercent, discountCount).subscribe(
                    result -> { view.onSuccess(result);},
                    throwable -> { view.onError(-1, throwable.getMessage());}
            );

        }else{
            this.couponAPI.publishOneCoupon(userBean.getToken(), userBean.getStoreId(), goodsIds, discounPercent, discountCount).subscribe(
                    result -> { view.onSuccess(result);},
                    throwable -> { view.onError(-1, throwable.getMessage());}
            );

        }


    }




    public interface CouponOnePublishView extends GearResultView<String> {



    }
}
