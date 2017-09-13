package ccj.sz28yun.com.presenter;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;

import ccj.sz28yun.com.api.GoodsAPI;
import ccj.sz28yun.com.api.ImageUploadAPI;
import ccj.sz28yun.com.bean.GoodsCategoryBean;
import ccj.sz28yun.com.bean.GoodsParams;
import ccj.sz28yun.com.bean.GoodsSpecificationBean;
import ccj.sz28yun.com.bean.GoodsUploadInfoResult;
import ccj.sz28yun.com.bean.ImageUploadResult;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class GoodsUploadPresenter extends AbsPresenter {
    private static final String TAG = "LoginPresenter";

    private GoodsUploadView view;
    private Context context;
    private GoodsAPI goodsAPI;
    private ImageUploadAPI imageUploadAPI;
    private String token;


    public GoodsUploadPresenter(Context context, GoodsUploadView view) {
        this.view = view;
        this.context = context;
        this.goodsAPI = new GoodsAPI();
        this.imageUploadAPI = new ImageUploadAPI();
        this.token = GlobalDataStorageCache.getInstance().getUserData().getToken();
    }


    public void submit(GoodsParams params) {
        try {
            this.goodsAPI.uploadGoods(params).subscribe(
                    result -> {
                        view.onSuccess(result);
                    },
                    throwable -> {
                        view.onError(-1, throwable.getMessage());
                    }
            );
        } catch (JSONException e) {
            e.printStackTrace();
            view.onError(-1, "上传失败, 请联系开发者");
        }
    }


    public void getInitializeData(String goodsUnionId) {
      /*  this.goodsAPI.getGoodsCategoryList().flatMap(
                goodsCategoryList -> {
                    view.onSuccessCategory(goodsCategoryList);
                    return  goodsAPI.getGoodsSpecificationList();
                }
        ).subscribe(
                goodsSpecificationList -> {view.onSuccessSpecification(goodsSpecificationList);},
                throwable -> {
                    view.onError(0, throwable.getMessage());
                }
        );*/
        goodsAPI.getGoodsSpecificationList().subscribe(
                goodsSpecificationList -> {
                    view.onSuccessSpecification(goodsSpecificationList);
                },
                throwable -> {
                    view.onError(0, throwable.getMessage());
                }
        );
        //获取详情
        if (!TextUtils.isEmpty(goodsUnionId)) {
                this.goodsAPI.getGoodsUploadInfo(goodsUnionId).subscribe(
                        result -> {
                            view.onSuccessGoodsUploadInfo(result);
                        },
                        throwable -> {
                            view.onError(0, throwable.getMessage());
                        }
                );
        }
    }

    public void uploadImage(File file) {
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.imageUploadAPI.uploadCacheFile(userBean.getToken(), file, "1", null).subscribe(
                imageUploadResult -> {
                    view.onSuccessUploadImage(imageUploadResult);
                },
                throwable -> {
                    view.onError(-2, throwable.getMessage());
                }
        );
    }

    /**
     * 获取顶级分类
     */
    public void getCategoryFirstLv() {
        this.goodsAPI.getGoodsCategoryListByParentId(token, "0").subscribe(
                goodsCategoryList -> {
                    view.onSuccessCategoryFirst(goodsCategoryList);
                },
                throwable -> {
                    view.onError(0, throwable.getMessage());
                }
        );
    }

    /**
     * 获取第二级分类
     *
     * @param parentId
     */
    public void getCategorySecondLv(String parentId) {
        this.goodsAPI.getGoodsCategoryListByParentId(token, parentId).subscribe(
                goodsCategoryList -> {
                    view.onSuccessCategorySecond(goodsCategoryList);
                },
                throwable -> {
                    view.onError(0, throwable.getMessage());
                }
        );
    }

    /**
     * 获取第三季分类
     *
     * @param parentId
     */
    public void getCategoryThirdLv(String parentId) {
        this.goodsAPI.getGoodsCategoryListByParentId(token, parentId).subscribe(
                goodsCategoryList -> {
                    view.onSuccessCategoryThird(goodsCategoryList);
                },
                throwable -> {
                    view.onError(0, throwable.getMessage());
                }
        );
    }


    public interface GoodsUploadView extends GearResultView<String> {

        void onSuccessUploadImage(ImageUploadResult imageUploadResult);

        void onSuccessCategoryFirst(ArrayList<GoodsCategoryBean> list);

        void onSuccessCategorySecond(ArrayList<GoodsCategoryBean> list);

        void onSuccessCategoryThird(ArrayList<GoodsCategoryBean> list);

        void onSuccessSpecification(ArrayList<GoodsSpecificationBean> list);

        void onSuccessGoodsUploadInfo(GoodsUploadInfoResult result);

    }
}
