package clerk.sz28yun.com.presenter;

import android.content.Context;

import clerk.sz28yun.com.api.HomeAPI;
import clerk.sz28yun.com.bean.HomeBean;
import clerk.sz28yun.com.bean.StatisticBean;
import clerk.sz28yun.com.bean.UserBean;
import clerk.sz28yun.com.cache.GlobalDataCache;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;

import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;
import rx.Subscriber;

/**
 * Created by sue on 2016/11/16.
 */
public class HomePresenter extends AbsPresenter {

    private Context context;
    private HomeView homeView;
    private HomeAPI homeAPI;


    public HomePresenter(Context context, HomeView homeView) {
        this.context = context;
        this.homeView = homeView;
        this.homeAPI  =  new HomeAPI();
    }

    public void requestData(){
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        String token = userBean.getToken();
        String menberId = userBean.getMemberId();
        addSubscription(this.homeAPI.getHomeData(token, menberId).subscribe(new Subscriber<HomeBean>() {
            @Override
            public void onCompleted() {
                homeView.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                homeView.onError(-1, e.getMessage());
            }

            @Override
            public void onNext(HomeBean homeBean) {
                GlobalDataCache.getInstance().setStatisticBean(StatisticBean.fromHomeBean(homeBean));//缓存一些数据
                homeView.onSuccess(homeBean);
            }
        }));
    }

    public interface HomeView extends GearResultView<HomeBean> {

    }
}
