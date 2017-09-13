package ccj.sz28yun.com.presenter;

import android.app.Activity;
import android.content.Context;


import java.io.File;

import ccj.sz28yun.com.api.CommonAPI;
import ccj.sz28yun.com.api.MerchantAPI;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import ccj.sz28yun.com.help.AppFileStoreHepler;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;
import per.sue.gear2.utils.DataCleanManager;
import per.sue.gear2.utils.MD5Utils;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class SettingsNormalPresenter extends AbsPresenter {
    private static final String TAG = "LoginPresenter";

    private SettingsView view;
    private Context context;
    private MerchantAPI merchantAPI;


    public SettingsNormalPresenter(Context context, SettingsView view) {
        this.view = view;
        this.context = context;
        this.merchantAPI = new MerchantAPI();
    }


    /**
     * 重置密码
     * @param oldPaawrd
     * @param newPassword
     */
    public void resetPassword(  String oldPaawrd,   String newPassword){
        String md5oldPaawrd = MD5Utils.encrypt(oldPaawrd);
        String md5newPassword = MD5Utils.encrypt(newPassword);
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.merchantAPI.resetPassword(userBean.getToken(), userBean.getStoreId(), md5oldPaawrd,md5newPassword ).subscribe(
          result -> { view.onSuccess(result);},
                throwable -> { view.onError(-1, throwable.getMessage());}
        );
    }


    /**
     * 获取缓存， 如果卡，可以改成异步
     */
    public void  getCache(){
        String sizeStr = "0M";
        try {
            sizeStr = DataCleanManager.getCacheSize(new File(AppFileStoreHepler.getInstance().getAppCachePath()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.view.onSuccessGetCache(sizeStr);
    }

    /**
     * 情理缓存， 主要清理图片缓存可以了
     */
    public void cleanCache(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    DataCleanManager.cleanCustomCache(AppFileStoreHepler.getInstance().getAppCachePath());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(context instanceof Activity){
                   ( (Activity)context).runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           view.onSuccessCleanCache();
                       }
                   });
                }
            }
        }).start();




    }


    public interface SettingsView extends GearResultView<String> {

        void onSuccessGetCache(String sizeStr);
        void onSuccessCleanCache();

    }
}
