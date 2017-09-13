package clerk.sz28yun.com.presenter;

import android.content.Context;

import clerk.sz28yun.com.api.ImageUploadAPI;
import clerk.sz28yun.com.api.MerchantAPI;
import clerk.sz28yun.com.bean.ImageUploadResult;
import clerk.sz28yun.com.bean.MerchantBean;
import clerk.sz28yun.com.bean.MerchantParams;
import clerk.sz28yun.com.bean.UserBean;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;

import org.json.JSONException;

import java.io.File;

import per.sue.gear2.exception.GearException;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;
import rx.Subscriber;

/**
 * 新增修改商家
 * Created by sue on 2016/11/24.
 */
public class MerchantUpdatePresenter extends AbsPresenter {

    private Context context;
    private MerchantUpdateView merchantUpdateView;
    private MerchantAPI merchantAPI;
    private ImageUploadAPI imageUploadAPI;
    String token;
    String menberId;
    public MerchantUpdatePresenter(Context context, MerchantUpdateView merchantUpdateView) {
        this.context = context;
        this.merchantUpdateView = merchantUpdateView;
        this.merchantAPI = new MerchantAPI();
        this.imageUploadAPI = new ImageUploadAPI();
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.token = userBean.getToken();
        this.menberId = userBean.getMemberId();
    }


    public void submit(MerchantParams params){
        try {
            this.merchantAPI.addMerchant(token, params).subscribe(
                    result -> {this.merchantUpdateView.onSuccessSubmit(result);},
                    throwable -> {this.merchantUpdateView.onError(-1, throwable.getMessage());}
            );
        } catch (JSONException e) {
            e.printStackTrace();
            this.merchantUpdateView.onError(-1, "参数转换出错");
        }
    }

    public void getData(String targetId){
        this.merchantAPI.getMerchant(token, menberId, targetId).subscribe(
                result -> {
                    this.merchantUpdateView.onSuccess(result);
                },
                throwable -> {this.merchantUpdateView.onError(-1, throwable.getMessage());}
        );
    }

    /**
     * 效验商家账号是否正确
     * @param account
     */
    public void verifyMemberAccount(String account){
        this.merchantAPI.verifyMemberAccount(account).subscribe(
                result -> {
                    this.merchantUpdateView.onAccountNeedRegist(result);//这里根据接口反过来， 没注册的一定是后台肯是反回错误码
                },
                throwable -> {
                    if(throwable instanceof GearException){
                        GearException exception = (GearException)throwable;
                        if(exception.getCode() == 300){
                            this.merchantUpdateView.onAccountNotNeedRegist(throwable.getMessage());
                        }else{
                            this.merchantUpdateView.onError(exception.getCode(), throwable.getMessage());
                        }

                    }else{
                        this.merchantUpdateView.onError(-1, throwable.getMessage());
                    }

                }
        );
    }

    public void uploadImage(ImageType key, File file, String type){
        imageUploadAPI.uploadCacheFile(token, file, type, null).subscribe(new Subscriber<ImageUploadResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                merchantUpdateView.onError(-1, e.getMessage());

            }

            @Override
            public void onNext(ImageUploadResult result) {
                merchantUpdateView.onSucessLoadImage(key, result);
            }
        });
    }

    public interface MerchantUpdateView   extends GearResultView<MerchantBean> {
        void onAccountNeedRegist( String message);
        void onAccountNotNeedRegist(String message);
        void onSuccessSubmit(String message);
        void onSucessLoadImage(ImageType type, ImageUploadResult  result);
    }




    public  enum ImageType{
        BASE_ID_FRONET, BASE_ID_BACK, BASE_LICENSE, BASE_ZIZI, MERCHANT_BANNER, MERCHANT_SUONUE, MERCHANT_FIRST, MERCHANT_SECOND, MERCHANT_THIRD, KITCHEN_FIRST, KITCHEN_SECOND, KITCHEN_THIRD
    }


}
