package ccj.sz28yun.com.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;



import java.util.List;

import ccj.sz28yun.com.api.VersionAPI;
import ccj.sz28yun.com.bean.ApkInfoBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.APIParamsCache;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import ccj.sz28yun.com.fragment.UpdateDialogFragment;
import per.sue.gear2.utils.ToastUtils;
import rx.Subscriber;

/**
 * Created by langgu on 16/5/19.
 */
public class UpdateManager {

    private static final String TAG = "DownloadManager";


    public static final String APK_DOWNLOAD_URL = "url";
    private static UpdateManager ourInstance = new UpdateManager();
    private boolean isRuning;


    private UpdateManager() {
    }

    public static synchronized UpdateManager getInstance() {
        return ourInstance;
    }


    public void checkUpdate(FragmentActivity context) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                checkAPI(context);
            }
        });

    }



    private synchronized  void checkAPI(FragmentActivity context){

        if(isRuning)return ;

        isRuning = true;
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        try {
            String versionName =  context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            new VersionAPI().checkApkVersion(userBean.getToken(),versionName ).subscribe(new Subscriber<ApkInfoBean>() {
                @Override
                public void onCompleted() {
                    isRuning = false;
                }

                @Override
                public void onError(Throwable e) {
//                    APIParamsCache.getInstance().setSj(false);
                    isRuning = false;
                }

                @Override
                public void onNext(ApkInfoBean apkInfoBean) {
                    isRuning = false;
//                    APIParamsCache.getInstance().setSj(true);
                    UpdateDialogFragment.newInstance(apkInfoBean).show(context.getSupportFragmentManager(), null);
                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }


    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName
     *            是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
