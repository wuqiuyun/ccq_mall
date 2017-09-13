package ccj.sz28yun.com.api;



import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ccj.sz28yun.com.api.base.BaseURL;
import ccj.sz28yun.com.api.base.ClerkObserver;
import ccj.sz28yun.com.bean.ImageUploadResult;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.net.ApiConnectionFactory;
import per.sue.gear2.net.bean.InputFile;
import per.sue.gear2.net.parser.GearParser;
import per.sue.gear2.net.progress.ProgressRequestListener;
import rx.Observable;

/**
 * Created by sue on 2016/11/15.
 */
public class ImageUploadAPI {


    /**
     * 上传图片参考例子
     * @param file 文件
     *@param type 上传图片类型(1商品图片 2套餐图片 3添加商家基本图片 4添加商家商家相片 5添加商家厨房相片 6商家缩略图)
     * @param progressRequestListener 上传监听， 不要可以为NULL
     * @return
     */
    public Observable<ImageUploadResult> uploadCacheFile(String token , File file, String type, ProgressRequestListener progressRequestListener) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().IMG_DOMAIN + BaseURL.URL_IMG_UPLOAD;
        ArrayList<InputFile> list = new ArrayList<>();
        InputFile inputFile = new InputFile("image", file);
        list.add(inputFile);
        Map<String, String> params = new HashMap<>();
        params.put("type",type );
        params.put("token",token );
        return new ClerkObserver(ApiConnectionFactory.getInstance().create(url,params, list, progressRequestListener ), new GearParser<ImageUploadResult>(ImageUploadResult.class))
                .observeOnMainThread();

    }

}
