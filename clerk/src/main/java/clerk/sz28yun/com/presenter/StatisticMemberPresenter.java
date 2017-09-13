package clerk.sz28yun.com.presenter;

import android.content.Context;

import clerk.sz28yun.com.api.PerformanceAPI;
import clerk.sz28yun.com.bean.MemberStatisticBean;
import clerk.sz28yun.com.bean.UserBean;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;

import java.util.ArrayList;

import per.sue.gear2.presenter.ListPresenter;

/**
 * Created by sue on 2016/11/16.
 */
public class StatisticMemberPresenter extends ListPresenter<ArrayList<MemberStatisticBean>> {

    private Context context;
    private ListResultView businessView;
    private PerformanceAPI performanceAPI;
    String keyword;
    String menberId;
    public StatisticMemberPresenter(Context context, ListResultView<ArrayList<MemberStatisticBean>> listResultView) {
        super(context, listResultView);
        this.performanceAPI  =  new PerformanceAPI();
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.menberId = userBean.getMemberId();
//        this.menberId = "56399";
        setQuestBeforeRunable(new Runnable() {
            @Override
            public void run() {
                setObservable(performanceAPI.getStatisticMemberList( menberId, keyword, getPageNum()));
            }
        });
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
