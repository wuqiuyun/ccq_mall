package ccj.sz28yun.com.presenter;

import android.content.Context;

import java.util.ArrayList;

import ccj.sz28yun.com.api.GoodsAPI;
import ccj.sz28yun.com.bean.GoodsCategoryBean;
import ccj.sz28yun.com.bean.GoodsUploadResult;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.exception.GearException;
import per.sue.gear2.presenter.ListPresenter;

/**
 * Created by Meihuali on 2017/6/14.
 */

public class GoodsUploadListPresenter extends ListPresenter<ArrayList<GoodsUploadResult>> {
    private GoodsUploadView view;
    private String gc_id_1 ;
    private String gc_id_2 ;
    private String gc_id_3 ;
    private String goods_union_id ;
    private GoodsAPI goodsAPI ;
    private UserBean userBean ;
    public GoodsUploadListPresenter(Context context, GoodsUploadView view) {
        super(context, view);
        this.view = view;
        goodsAPI = new GoodsAPI();
        userBean = GlobalDataStorageCache.getInstance().getUserData();

        setQuestBeforeRunable(new Runnable() {
            @Override
            public void run() {
                setObservable(goodsAPI.getGoodsUpload(userBean.getStoreId(), gc_id_1, gc_id_2, gc_id_3, getPageNum()));
            }
        });
    }

    @Override
    public void query() {
//        super.query();
        subscription =  goodsAPI.getGoodsUpload(userBean.getStoreId(), gc_id_1, gc_id_2, gc_id_3, getPageNum()).subscribe(
                GoodsUploadResult -> {
                    if(isRefresh){
                        view .onSuccessRefresh(GoodsUploadResult);
                    }else{
                        view.onSuccessLoadModre(GoodsUploadResult);
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
                            view.onError(gearException.getCode(), throwable.getMessage());
                        }else{
                            view.onError(gearException.getCode(), throwable.getMessage());
                        }

                    }else{
                        view.onError(-1, throwable.getMessage());
                    }
                }
        );
        addSubscription(subscription);
    }



    public interface GoodsUploadView extends ListResultView<ArrayList<GoodsUploadResult>> {
        void onSuccessDeleteOREdit(String result);
        void onSuccessCategoryFirst(ArrayList<GoodsCategoryBean> list);
        void onSuccessCategorySecond(ArrayList<GoodsCategoryBean> list);
        void onSuccessCategoryThird(ArrayList<GoodsCategoryBean> list);
    }

    public void delete(String goods_union_id){
        try {
            this.goodsAPI.deleteGoodsUpload(goods_union_id).subscribe(
                    result -> { view.onSuccessDeleteOREdit(result); },
                    throwable -> { view.onError(-2, throwable.getMessage()); }
            );
        } catch (Exception e) {
            e.printStackTrace();
            view.onError(-2, "提交数据异常");
        }
    }
    public void alldelete(){
        try {
            this.goodsAPI.alldeleteGoodsUpload(userBean.getStoreId()).subscribe(
                    result -> { view.onSuccessDeleteOREdit(result); },
                    throwable -> { view.onError(-2, throwable.getMessage()); }
            );
        } catch (Exception e) {
            e.printStackTrace();
            view.onError(-2, "提交数据异常");
        }
    }

    /**
     * 获取顶级分类
     */
    public void getCategoryFirstLv(){
        this.goodsAPI.getGoodsCategoryListByParentId(userBean.getToken(), "0").subscribe(
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
        this.goodsAPI.getGoodsCategoryListByParentId(userBean.getToken(), parentId).subscribe(
                goodsCategoryList -> {
                    view.onSuccessCategorySecond(goodsCategoryList);},
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
        this.goodsAPI.getGoodsCategoryListByParentId(userBean.getToken(), parentId).subscribe(
                goodsCategoryList -> {view.onSuccessCategoryThird(goodsCategoryList);},
                throwable -> {
                    view.onError(0, throwable.getMessage());
                }
        );
    }

    public void setGcId_1(String gc_id_1) {
        this.gc_id_1 = gc_id_1;
    }
    public void setGcId_2(String gc_id_2) {
        this.gc_id_2 = gc_id_2;
    }
    public void setGcId_3(String gc_id_3) {
        this.gc_id_3 = gc_id_3;
    }
    public void setGoodsUnionId(String goods_union_id) {
        this.goods_union_id = goods_union_id;
    }

}
