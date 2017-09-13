package ccj.sz28yun.com.presenter;

import android.content.Context;

import org.json.JSONException;

import java.io.File;

import ccj.sz28yun.com.api.ImageUploadAPI;
import ccj.sz28yun.com.api.MerchantAPI;
import ccj.sz28yun.com.bean.ImageUploadResult;
import ccj.sz28yun.com.bean.MerchantAdsResult;
import ccj.sz28yun.com.bean.MerchantAllPicResult;
import ccj.sz28yun.com.bean.MerchantServiceBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;
import rx.Subscriber;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class MerchantHomeUpdatePresenter extends AbsPresenter {
    private static final String TAG = "LoginPresenter";

    private MerchantHomeUpdateView view;
    private Context context;
    private MerchantAPI merchantAPI;
    private ImageUploadAPI imageUploadAPI;
    UserBean userBean;
    private String addstore_image_01;
    private String addstore_image_02;
    private String addstore_image_03;
    private String addstore_image_04;
    private String addstore_image_05;

    public MerchantHomeUpdatePresenter(Context context, MerchantHomeUpdateView view) {
        this.view = view;
        this.context = context;
        this.merchantAPI = new MerchantAPI();
        imageUploadAPI = new ImageUploadAPI();
        userBean  = GlobalDataStorageCache.getInstance().getUserData();
    }


    public void getMerchantAds(){
        this.merchantAPI.getMerchantAds(userBean.getToken(), userBean.getStoreId()).subscribe(
          result -> { view.onSuccess(result);},
                throwable -> { view.onError(-1, throwable.getMessage());}
        );
    }
    public void getMerchantAllPic(){
        this.merchantAPI.getMerchantAllPic(userBean.getToken(), userBean.getStoreId()).subscribe(
          result -> { view.onSuccessAllPic(result);},
                throwable -> { view.onError(-1, throwable.getMessage());}
        );
    }

    public void editMerchantAdsImage( String imagOriginalId, String iamgeId){
        this.merchantAPI.editMerchantAds(userBean.getToken(), userBean.getStoreId(), imagOriginalId,  iamgeId).subscribe(
                result -> { view.onSuccessEditAdsImage(result);},
                throwable -> { view.onError(-1, throwable.getMessage());}
        );
    }

    public void deleteMerchantAdsImage(String iamgeId){
        this.merchantAPI.deleteMerchantAds(userBean.getToken(), userBean.getStoreId(), iamgeId ).subscribe(
                result -> { view.onSuccessDeleteAdsImage(result);},
                throwable -> { view.onError(-1, throwable.getMessage());}
        );
    }

    public void editMerchantAdsText(String ads){
            this.merchantAPI.setMerchantAdsText(userBean.getToken(), userBean.getStoreId(), ads ).subscribe(
                    result -> { view.onSuccessEditAdsText(result);},
                    throwable -> { view.onError(-1, throwable.getMessage());}
            );
    }

    public void uploadImage(File file){
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.imageUploadAPI.uploadCacheFile(userBean.getToken(), file, "6", null ).subscribe(
                imageUploadResult -> { view.onSuccessUploadImage(imageUploadResult ); },
                throwable -> { view.onError(-2, throwable.getMessage()); }
        );
    }

    public void alluploadImage(ImageType key, File file, String type){
        imageUploadAPI.uploadCacheFile(userBean.getToken(), file, type, null).subscribe(new Subscriber<ImageUploadResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.onError(-1, e.getMessage());

            }

            @Override
            public void onNext(ImageUploadResult result) {
                view.onSucessLoadImage(key, result);
            }
        });
    }

    public void editMerchantAllPic(){
        this.merchantAPI.editMerchantAllPic(userBean.getToken(), userBean.getMemberId(),userBean.getStoreId(),addstore_image_01,addstore_image_02,addstore_image_03,addstore_image_04,addstore_image_05).subscribe(
                result -> { view.onSuccessEditMerchantAllPic(result);},
                throwable -> { view.onError(-1, throwable.getMessage());}
        );
    }


    public interface MerchantHomeUpdateView extends GearResultView<MerchantAdsResult> {

        void onSuccessEditAdsText(String message);
        void onSuccessEditAdsImage(String message);
        void onSuccessAllPic(MerchantAllPicResult result);
        void onSuccessDeleteAdsImage(String message);
        void onSuccessUploadImage(ImageUploadResult imageUploadResult);
        void onSucessLoadImage(ImageType type, ImageUploadResult  result);
        void onSuccessEditMerchantAllPic(String message);

    }

    public enum ImageType{
        BASE_ID_FRONET, BASE_ID_BACK, BASE_LICENSE, BASE_ZIZI, MERCHANT_FIRST, MERCHANT_SECOND, MERCHANT_THIRD,MERCHANT_FOUR, MERCHANT_FIVE, MERCHANT_SIX, KITCHEN_FIRST, KITCHEN_SECOND, KITCHEN_THIRD, STORE_SN
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
}
