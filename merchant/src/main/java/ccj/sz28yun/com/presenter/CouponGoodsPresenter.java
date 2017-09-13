package ccj.sz28yun.com.presenter;

import android.content.Context;

import java.util.ArrayList;

import ccj.sz28yun.com.api.CouponAPI;
import ccj.sz28yun.com.bean.CouponGoodsBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.ListPresenter;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class CouponGoodsPresenter extends ListPresenter<ArrayList<CouponGoodsBean>> {
    private static final String TAG = "FinanceTypeDepositsPresenter";

    private CouponGoodsView view;
    private Context context;
    private CouponAPI couponAPI;
    private String nameKey;
    private String categoryId ;
    UserBean userBean ;
    public CouponGoodsPresenter(Context context, CouponGoodsView view) {
        super(context, view);
        this.view = view;
        this.context = context;
        this.couponAPI = new CouponAPI();
        userBean = GlobalDataStorageCache.getInstance().getUserData();

        setQuestBeforeRunable(new Runnable() {
            @Override
            public void run() {
                setObservable(couponAPI.getCouponGoodsList(userBean.getToken(),userBean.getStoreId(),  nameKey, categoryId ,  getPageNum() ));
            }
        });
    }

    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public interface CouponGoodsView extends ListResultView<ArrayList<CouponGoodsBean>> {


    }
}
