package ccj.sz28yun.com.presenter;

import android.content.Context;

import java.util.ArrayList;

import ccj.sz28yun.com.api.MemberSysAPI;
import ccj.sz28yun.com.bean.MemberSysSMSBean;
import ccj.sz28yun.com.bean.MemberSysStatusBean;
import ccj.sz28yun.com.bean.OrderCCJBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataCache;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.ListPresenter;

/**
 * Created by sue on 2016/12/15.
 */
public class MemberSysSMSPresenter extends ListPresenter<ArrayList<MemberSysSMSBean>> {

    private MemberSysSMSView memberSysSMSView;
    private MemberSysAPI memberSysAPI;
    UserBean userBean;
    public MemberSysSMSPresenter(Context context, MemberSysSMSView listResultView) {
        super(context, listResultView);
        this.memberSysSMSView = listResultView;
        this.memberSysAPI = new MemberSysAPI();
        userBean = GlobalDataStorageCache.getInstance().getUserData();
    }


    @Override
    public void query() {
        memberSysAPI.getSMSList(userBean.getToken(), userBean.getStoreId(), getPageNum()).subscribe(
                result -> {
                    if (isRefresh) {
                        getStatisticData();//因为获取到数据后才会得到
                        this.memberSysSMSView.onSuccessRefresh(result);
                        //this.memberSysSMSView.onSuccessStatistic(result.getStatisticBeanArrayList());
                    } else {
                        this.memberSysSMSView.onSuccessLoadModre(result);
                    }
                },
                throwable -> {
                    getStatisticData();
                    memberSysSMSView.onError(-1, throwable.getMessage());
                }
        );
    }

    public void getStatisticData(){
        GlobalDataCache globalDataCache = GlobalDataCache.getInstance();
        memberSysSMSView.onSuccessStatistic(globalDataCache.num, globalDataCache.cost);

    }



    public interface MemberSysSMSView extends ListPresenter.ListResultView<ArrayList<MemberSysSMSBean>> {

        public void onSuccessStatistic(int count, double payment);


    }
}
