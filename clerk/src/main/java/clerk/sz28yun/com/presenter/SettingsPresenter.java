package clerk.sz28yun.com.presenter;

import android.app.Activity;
import android.content.Context;

import clerk.sz28yun.com.api.CommonAPI;
import clerk.sz28yun.com.bean.UserBean;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;
import clerk.sz28yun.com.help.AppFileStoreHepler;

import java.io.File;

import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;
import per.sue.gear2.utils.DataCleanManager;
import per.sue.gear2.utils.MD5Utils;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class SettingsPresenter extends AbsPresenter {
    private static final String TAG = "LoginPresenter";

    private SettingsView view;
    private Context context;
    private CommonAPI commonAPI;


    public SettingsPresenter(Context context, SettingsView view) {
        this.view = view;
        this.context = context;
        this.commonAPI = new CommonAPI();
    }


    public void resetPassword(  String oldPaawrd,   String newPassword){
        String md5oldPaawrd = MD5Utils.encrypt(oldPaawrd);
        String md5newPassword = MD5Utils.encrypt(newPassword);
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.commonAPI.resetPassword(userBean.getToken(), userBean.getMemberId(), md5oldPaawrd,md5newPassword ).subscribe(
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
