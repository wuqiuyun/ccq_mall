package ccj.sz28yun.com.presenter;

import android.content.Context;

import java.util.ArrayList;

import ccj.sz28yun.com.api.CouponAPI;
import ccj.sz28yun.com.api.GoodsAPI;
import ccj.sz28yun.com.bean.GoodsCategoryBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class GoodsCategoryListPresenter extends AbsPresenter {
    private static final String TAG = "LoginPresenter";

    private GoodsCategoryListView view;
    private Context context;
    private CouponAPI couponAPI;
    private String token;
    private GoodsAPI goodsAPI;

    public GoodsCategoryListPresenter(Context context, GoodsCategoryListView view) {
        this.view = view;
        this.context = context;
        this.couponAPI = new CouponAPI();
        this.goodsAPI = new GoodsAPI();
        this.token =  GlobalDataStorageCache.getInstance().getUserData().getToken();
    }

    /**
     * 获取顶级分类
     */
    public void getCategoryFirstLv(){
        this.goodsAPI.getGoodsCategoryListByParentId(token, "0").subscribe(
                goodsCategoryList -> {view.onSuccessCategoryFirst(goodsCategoryList);},
                throwable -> {
                    view.onError(0, throwable.getMessage());
                }
        );
    }

    /**
     * 获取第二级分类
     * @param parentId
     */
    public void getCategorySecondLv(String parentId){
        this.goodsAPI.getGoodsCategoryListByParentId(token, parentId).subscribe(
                goodsCategoryList -> {view.onSuccessCategorySecond(goodsCategoryList);},
                throwable -> {
                    view.onError(0, throwable.getMessage());
                }
        );
    }

    /**
     * 获取第三季分类
     * @param parentId
     */
    public void getCategoryThirdLv(String parentId){
        this.goodsAPI.getGoodsCategoryListByParentId(token, parentId).subscribe(
                goodsCategoryList -> {view.onSuccessCategoryThird(goodsCategoryList);},
                throwable -> {
                    view.onError(0, throwable.getMessage());
                }
        );
    }


    public interface GoodsCategoryListView extends GearResultView<String> {
        void onSuccessCategoryFirst(ArrayList<GoodsCategoryBean> list);
        void onSuccessCategorySecond(ArrayList<GoodsCategoryBean> list);
        void onSuccessCategoryThird(ArrayList<GoodsCategoryBean> list);
    }
}
