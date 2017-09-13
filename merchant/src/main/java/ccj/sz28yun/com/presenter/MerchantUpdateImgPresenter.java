package ccj.sz28yun.com.presenter;

import android.content.Context;

import java.io.File;

import ccj.sz28yun.com.api.ImageUploadAPI;
import ccj.sz28yun.com.api.MerchantAPI;
import ccj.sz28yun.com.bean.ImageUploadResult;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;

/**
 * Created by meihuali on 2017/7/11.
 */
public class MerchantUpdateImgPresenter extends AbsPresenter{

    private MerchantUpdateImgView view;
    private Context context;
    UserBean userBean;
    private ImageUploadAPI imageUploadAPI;
    private MerchantAPI merchantAPI;
    private String sctype;
    private String deletetype;
    private String addstore_image_01;
    private String addstore_image_02;
    private String addstore_image_03;
    private String addstore_image_04;
    private String addstore_image_05;

    public MerchantUpdateImgPresenter(Context context, MerchantUpdateImgView view) {
        this.view = view;
        this.context = context;
        this.imageUploadAPI = new ImageUploadAPI();
        this.merchantAPI = new MerchantAPI();
        userBean  = GlobalDataStorageCache.getInstance().getUserData();
    }

    public void uploadImage(File file) {
        //2 是接口需求， 貌似每个地方上传图片不一样
        this.imageUploadAPI.uploadCacheFile(userBean.getToken(), file, sctype, null).subscribe(
                imageUploadResult -> {
                    view.onSuccessUploadImage(imageUploadResult);
                },
                throwable -> {
                    view.onError(-2, throwable.getMessage());
                }
        );
    }
//    public void editMerchantAllPic(){
//        this.merchantAPI.editMerchantAllPic(userBean.getToken(), userBean.getMemberId(),userBean.getStoreId(),deletetype,addstore_image_01,addstore_image_02,addstore_image_03,addstore_image_04,addstore_image_05).subscribe(
//                result -> { view.onSuccess(result);},
//                throwable -> { view.onError(-1, throwable.getMessage());}
//        );
//    }

    public void setScType(String sctype) {
        this.sctype = sctype;
    }

    public void setDeleteType(String deletetype) {
        this.deletetype = deletetype;
    }

    public void setImage_01(String addstore_image_01) {
        this.addstore_image_01 = addstore_image_01;
    }
    public void setImage_02(String addstore_image_02) {
        this.addstore_image_02 = addstore_image_02;
    } public void setImage_03(String addstore_image_03) {
        this.addstore_image_03 = addstore_image_03;
    } public void setImage_04(String addstore_image_04) {
        this.addstore_image_04 = addstore_image_04;
    } public void setImage_05(String addstore_image_05) {
        this.addstore_image_05 = addstore_image_05;
    }

    public interface MerchantUpdateImgView extends GearResultView<String> {
        void onSuccessUploadImage(ImageUploadResult imageUploadResult);
    }
}
