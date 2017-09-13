package ccj.sz28yun.com.presenter;

import android.content.Context;

import java.util.ArrayList;

import ccj.sz28yun.com.api.MemberSysAPI;
import ccj.sz28yun.com.bean.MemberVipListBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.exception.GearException;
import per.sue.gear2.presenter.ListPresenter;

/**
 * Created by meihuali on 2017/6/16.
 */

public class MemberVipListPresenter extends ListPresenter<ArrayList<MemberVipListBean>> {

    private MemberVipListResultView resultView;
    private MemberSysAPI memberSysAPI;
    private UserBean userBean;
    private int type;// 0:全部会员 1:vip会员
    private String searchtext;
    private String year;
    private String mon;
    private String day;

    public MemberVipListPresenter(Context context, MemberVipListResultView resultView) {
        super(context, resultView);
        this.resultView = resultView;
        this.memberSysAPI = new MemberSysAPI();
        this.userBean = GlobalDataStorageCache.getInstance().getUserData();
    }

    @Override
    public void query() {
        subscription = memberSysAPI.getMemberCountList(userBean.getToken(), userBean.getStoreId(), searchtext, type, year, mon, day, getPageNum()).subscribe(
                MemberVipListBean -> {
                    if (isRefresh) {
                        resultView.onSuccessRefresh(MemberVipListBean);
                    } else {
                        resultView.onSuccessLoadModre(MemberVipListBean);
                    }
                },
                throwable -> {
                    if (throwable instanceof GearException) {
                        GearException gearException = (GearException) throwable;
                        if (300 == gearException.getCode()) {//300 为空数据
                            if (isRefresh) {
                                resultView.onSuccessRefresh(new ArrayList<>());
                            } else {
                                resultView.onSuccessLoadModre(new ArrayList<>());
                            }
                            resultView.onError(gearException.getCode(), throwable.getMessage());
                        } else {
                            resultView.onError(gearException.getCode(), throwable.getMessage());
                        }

                    } else {
                        resultView.onError(-1, throwable.getMessage());
                    }
                }
        );
        addSubscription(subscription);
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setSearchText(String searchtext) {
        this.searchtext = searchtext;
    }

    public void setDate(String year, String mon, String day) {
        this.year = year;
        this.mon = mon;
        this.day = day;
    }

    public interface MemberVipListResultView extends ListResultView<ArrayList<MemberVipListBean>> {

    }
}
