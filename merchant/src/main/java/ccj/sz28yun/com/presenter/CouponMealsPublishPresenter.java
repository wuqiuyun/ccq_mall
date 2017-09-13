package ccj.sz28yun.com.presenter;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;

import ccj.sz28yun.com.api.CouponAPI;
import ccj.sz28yun.com.api.ImageUploadAPI;
import ccj.sz28yun.com.api.VerifyAPI;
import ccj.sz28yun.com.bean.CouponChainBean;
import ccj.sz28yun.com.bean.CouponMealsInfoResult;
import ccj.sz28yun.com.bean.CouponMealsParams;
import ccj.sz28yun.com.bean.ImageUploadResult;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class CouponMealsPublishPresenter extends AbsPresenter {
    private static final String TAG = "LoginPresenter";

    private CouponMealsPublishView view;
    private Context context;
    private CouponAPI couponAPI;
    private ImageUploadAPI imageUploadAPI;
    private UserBean userBean;

    public CouponMealsPublishPresenter(Context context, CouponMealsPublishView view) {
        this.view = view;
        this.context = context;
        this.couponAPI = new CouponAPI();
        this.imageUploadAPI = new ImageUploadAPI();
        userBean = GlobalDataStorageCache.getInstance().getUserData();
    }

    public void uploadImage(File file) {
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        //2 是接口需求， 貌似每个地方上传图片不一样
        this.imageUploadAPI.uploadCacheFile(userBean.getToken(), file, "2", null).subscribe(
                imageUploadResult -> {
                    view.onSuccessUploadImage(imageUploadResult);
                },
                throwable -> {
                    view.onError(-2, throwable.getMessage());
                }
        );
    }

    /**
     * 获取分店列表
     */
//    private void getCouponCouponList() {
//        this.couponAPI.getCouponCouponList(userBean.getToken(), userBean.getStoreId(), 1).subscribe(
//                result -> {
//                    view.onSuccessChainList(result);
//                },
//                throwable -> {
//                    view.onError(-2, throwable.getMessage());
//                }
//        );
//    }

    /**
     * 获取初始化数据
     *
     * @param couponId
     */
    public void getInitData(String couponId) {
//        if(TextUtils.isEmpty(couponId)){//获取分店
//            this.couponAPI.getCouponCouponList(userBean.getToken(), userBean.getStoreId(), 1).subscribe(
//                    result -> { view.onSuccessChainList(result ); },
//                    throwable -> { view.onError(-2, throwable.getMessage()); }
//            );
//        }else{
//            //先获取分店， 再获取详情
//            this.couponAPI.getCouponCouponList(userBean.getToken(), userBean.getStoreId(), 1).flatMap(
//                    result -> {
//                        view.onSuccessChainList(result );
//                        try {
//                          return  this.couponAPI.getCouponMealsInfp(userBean.getToken(), couponId);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            view.onError(-2, "获取数据失败");
//                            return null;
//                        }
//                    }
//            ).subscribe(
//                    result -> { view.onSuccessCouponInfo(result ); },
//                    throwable -> { view.onError(-2, throwable.getMessage()); }
//            );
//        }
        if (TextUtils.isEmpty(couponId)) {//获取分店
        } else {
            //获取详情
            try {
                this.couponAPI.getCouponMealsInfp(userBean.getToken(), couponId).subscribe(
                        result -> {
                            view.onSuccessCouponInfo(result);
                        },
                        throwable -> {
                            view.onError(-2, throwable.getMessage());
                        }
                );
            } catch (JSONException e) {
                e.printStackTrace();
                view.onError(-2, "提交数据异常");
            }
        }
    }

    public void add(CouponMealsParams couponMealsParams) {
        couponMealsParams.token = userBean.getToken();
        couponMealsParams.merchatId = userBean.getStoreId();
        try {
            this.couponAPI.addCouponMeals(couponMealsParams).subscribe(
                    result -> {
                        view.onSuccess(result);
                    },
                    throwable -> {
                        view.onError(-2, throwable.getMessage());
                    }
            );
        } catch (JSONException e) {
            e.printStackTrace();
            view.onError(-2, "提交数据异常");
        }
    }

    public void edit(CouponMealsParams couponMealsParams) {
        couponMealsParams.token = userBean.getToken();
        couponMealsParams.merchatId = userBean.getStoreId();
        try {
            this.couponAPI.editCouponMeals(couponMealsParams).subscribe(
                    result -> {
                        view.onSuccess(result);
                    },
                    throwable -> {
                        view.onError(-2, throwable.getMessage());
                    }
            );
        } catch (JSONException e) {
            e.printStackTrace();
            view.onError(-2, "提交数据异常");
        }
    }


    public interface CouponMealsPublishView extends GearResultView<String> {
        void onSuccessUploadImage(ImageUploadResult imageUploadResult);

//        void onSuccessChainList(ArrayList<CouponChainBean> result);

        void onSuccessCouponInfo(CouponMealsInfoResult result);
    }
}
