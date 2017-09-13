package clerk.sz28yun.com.presenter;

import android.content.Context;


import clerk.sz28yun.com.api.CommonAPI;
import clerk.sz28yun.com.bean.ShareConfigBean;
import clerk.sz28yun.com.bean.UserBean;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.ui.IBaseView;

/**
 * Created by sue on 2016/11/16.
 */
public class ConfigPresenter extends AbsPresenter {

    private Context context;
    private ConfigView configView;



    public ConfigPresenter(Context context, ConfigView configView) {
        this.context = context;
        this.configView = configView;
    }


    /**
     * 获取分析配置
     */
    public void getShareConfig(){
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        String menberId = userBean.getMemberId();
       addSubscription( new CommonAPI().getShareConfig(menberId).subscribe(
               shareConfigBean -> {
                   configView.onSuccessShareConfig(shareConfigBean);
               },
               throwable -> {
                   configView.onError(-2, throwable.getMessage());
               }
       ));;
    }

    public interface ConfigView extends IBaseView {

        void onSuccessShareConfig(ShareConfigBean configBean);

    }
}
