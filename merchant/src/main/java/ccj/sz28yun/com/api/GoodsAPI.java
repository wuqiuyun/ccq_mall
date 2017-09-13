package ccj.sz28yun.com.api;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ccj.sz28yun.com.api.base.BaseURL;
import ccj.sz28yun.com.api.base.ClerkObserver;
import ccj.sz28yun.com.bean.GoodsCategoryBean;
import ccj.sz28yun.com.bean.GoodsParams;
import ccj.sz28yun.com.bean.GoodsSpecificationBean;
import ccj.sz28yun.com.bean.GoodsUploadInfoResult;
import ccj.sz28yun.com.bean.GoodsUploadResult;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.net.ApiCache;
import per.sue.gear2.net.ApiConnectionFactory;
import per.sue.gear2.net.parser.GearParser;
import per.sue.gear2.utils.JSONUtils;
import rx.Observable;

/**
 * Created by sue on 2016/12/4.
 */
public class GoodsAPI {

    /**
     * 获取商品列表
     * @return
     */
    public Observable<ArrayList<GoodsUploadResult>> getGoodsUpload(String store_id, String gc_id_1, String gc_id_2, String gc_id_3 ,int page) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_GOODS_UPLOAD_LIST;
        Map<String, String> params = new HashMap<>();
        params.put("api_v","v3");
        params.put("store_id",store_id);
        params.put("gc_id_1",gc_id_1);
        params.put("gc_id_2",gc_id_2);
        params.put("gc_id_3",gc_id_3);
        params.put("page",page+"");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<GoodsUploadResult>(GoodsUploadResult.class))
                .observeOnMainThread();
    }

    /**
     * 获取商品分类
     * @return
     */
    public Observable<ArrayList<GoodsCategoryBean>> getGoodsCategoryList() {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_GOODS_CATEGORY_LIST;
        Map<String, String> params = new HashMap<>();
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<GoodsCategoryBean>(GoodsCategoryBean.class))
                .observeOnMainThread();
    }

    /**
     * 获取商品规格
     * @return
     */
    public Observable<ArrayList<GoodsSpecificationBean>> getGoodsSpecificationList() {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_GOODS_SPECIFICATION;
        Map<String, String> params = new HashMap<>();
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<GoodsSpecificationBean>(GoodsSpecificationBean.class))
                .observeOnMainThread();
    }

    /**
     * 上传商品
     * @param goodsParams
     * @return
     * @throws JSONException
     */
    public Observable<String> uploadGoods(GoodsParams goodsParams) throws JSONException {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_GOODS_UPLOAD;
        Map<String, String> params = new HashMap<>();
        params = JSONUtils.beanToMap(goodsParams);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }

    public Observable<ArrayList<GoodsCategoryBean>> getGoodsCategoryListByParentId(String token, String parentId) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_GOODS_CATEGORY_LIST_BY_PARENTID;
        Map<String, String> params = new HashMap<>();
        params.put("token", token );
        params.put("gc_id", parentId );
        return ApiCache.getInstance().cache( new StringBuilder(url).append("?parentId=").append(parentId).toString(), new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<GoodsCategoryBean>(GoodsCategoryBean.class))
                .observeOnMainThread()) ;
    }


    /**
     * 删除商品
     * @param goods_union_id
     * @return
     */
    public Observable<String> deleteGoodsUpload(String goods_union_id){
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_GOODS_UPLOAD_DELETE;
        Map<String, String> params = new HashMap<>();
        params.put("api_v","v3");
        params.put("goods_union_id",goods_union_id);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }
    /**
     * 清空当前已下架商品
     * @param store_id
     * @return
     */
    public Observable<String> alldeleteGoodsUpload(String store_id){
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_GOODS_UPLOAD_CLEAR;
        Map<String, String> params = new HashMap<>();
        params.put("api_v","v3");
        params.put("store_id",store_id);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }
    /**
     * 商品详情
     * @param goods_union_id
     * @return
     */
    public Observable<GoodsUploadInfoResult> getGoodsUploadInfo(String goods_union_id){
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_GOODS_UPLOAD_DETAIL_INFO;
        Map<String, String> params = new HashMap<>();
        params.put("api_v","v3");
        params.put("goods_union_id",goods_union_id);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<GoodsUploadInfoResult>(GoodsUploadInfoResult.class))
                .observeOnMainThread();
    }
}
