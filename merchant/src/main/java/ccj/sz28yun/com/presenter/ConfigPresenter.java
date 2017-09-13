package ccj.sz28yun.com.presenter;

import android.content.Context;

import java.util.ArrayList;


import ccj.sz28yun.com.api.CommonAPI;
import ccj.sz28yun.com.bean.ShareConfigBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;
import per.sue.gear2.ui.IBaseView;
import rx.Subscriber;

/**
 * Created by sue on 2016/11/16.
 */
public class ConfigPresenter extends AbsPresenter {

    private Context context;
    private ConfigView configView;
    private UserBean userBean;



    public ConfigPresenter(Context context, ConfigView configView) {
        this.context = context;
        this.configView = configView;
        userBean = GlobalDataStorageCache.getInstance().getUserData();
    }


    /**
     * 获取分析配置
     */
    public void getShareConfig(){
       addSubscription( new CommonAPI().getShareConfig(userBean.getMemberId()).subscribe(
               shareConfigBean -> {
                   configView.onSuccessShareConfig(shareConfigBean);
               },
               throwable -> {
                   configView.onError(-2, throwable.getMessage());
               }
       ));
    }

    public interface ConfigView extends IBaseView {

        void onSuccessShareConfig(ShareConfigBean configBean);

    }
}
