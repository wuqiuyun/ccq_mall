package ccj.sz28yun.com.presenter;

import android.content.Context;

import java.util.ArrayList;

import ccj.sz28yun.com.api.CouponAPI;
import ccj.sz28yun.com.bean.CouponMealsBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.exception.GearException;
import per.sue.gear2.presenter.ListPresenter;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class CouponOnePublistPresenter extends ListPresenter<ArrayList<CouponMealsBean>> {
    private static final String TAG = "FinanceTypeDepositsPresenter";

    private CouponDanGoodsView view;
    private Context context;
    private CouponAPI couponAPI;
    private String nameKey;
    private String type ;
    private String goods_id ;
    private String kc_num ;
    UserBean userBean ;
    public CouponOnePublistPresenter(Context context, CouponDanGoodsView view) {
        super(context, view);
        this.view = view;
        this.context = context;
        this.couponAPI = new CouponAPI();
        userBean = GlobalDataStorageCache.getInstance().getUserData();

        setQuestBeforeRunable(new Runnable() {
            @Override
            public void run() {
                setObservable(couponAPI.getCouponOnePublist(userBean.getToken(),userBean.getStoreId(),  type ,  getPageNum() ));
            }
        });
    }

    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
    }


    public void setType(String type) {
        this.type = type;
    }
    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }
    public void setKc_num(String kc_num) {
        this.kc_num = kc_num;
    }

    public interface CouponDanGoodsView extends ListResultView<ArrayList<CouponMealsBean>> {
        void onSuccessDeleteORXiajia(String result);
    }

    public void xiajia(){
        try {
            this.couponAPI.xiajiaCouponMeals(userBean.getStoreId(),goods_id).subscribe(
                    result -> { view.onSuccessDeleteORXiajia(result); },
                    throwable -> { view.onError(-2, throwable.getMessage()); }
            );
        } catch (Exception e) {
            e.printStackTrace();
            view.onError(-2, "提交数据异常");
        }
    }
    public void delete(){
        try {
            this.couponAPI.deleteCouponMeals(userBean.getStoreId(),goods_id).subscribe(
                    result -> { view.onSuccessDeleteORXiajia(result); },
                    throwable -> { view.onError(-2, throwable.getMessage()); }
            );
        } catch (Exception e) {
            e.printStackTrace();
            view.onError(-2, "提交数据异常");
        }
    }
    public void editKC(){
        try {
            this.couponAPI.editKCCouponMeals(userBean.getStoreId(),goods_id, kc_num).subscribe(
                    result -> { view.onSuccessDeleteORXiajia(result); },
                    throwable -> { view.onError(-2, throwable.getMessage()); }
            );
        } catch (Exception e) {
            e.printStackTrace();
            view.onError(-2, "提交数据异常");
        }
    }

    @Override
    public void query() {
//        super.query();

        subscription =  couponAPI.getCouponOnePublist(userBean.getToken(),userBean.getStoreId(),  type ,  getPageNum() ).subscribe(
                CouponMealsBean -> {
                    if(isRefresh){
                        view .onSuccessRefresh(CouponMealsBean);
                    }else{
                        view.onSuccessLoadModre(CouponMealsBean);
                    }
                },
                throwable -> {
                    if(throwable instanceof GearException){
                        GearException gearException = (GearException)throwable;
                        if(300 == gearException.getCode()){//300 为空数据
                            if(isRefresh){
                                view .onSuccessRefresh(new ArrayList<>());
                            }else{
                                view.onSuccessLoadModre(new ArrayList<>());
                            }
                            if (getPageNum() != 1){
                                view.onError(gearException.getCode(), throwable.getMessage());
                            }
                        }else{
                            if (getPageNum() != 1){
                                view.onError(gearException.getCode(), throwable.getMessage());
                            }
                        }

                    }else{
                            view.onError(-1, throwable.getMessage());
                    }
                }
        );
        addSubscription(subscription);
    }
}
