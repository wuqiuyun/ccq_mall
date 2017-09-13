package ccj.sz28yun.com.presenter;

import android.content.Context;

import java.util.ArrayList;

import ccj.sz28yun.com.api.MemberSysAPI;
import ccj.sz28yun.com.bean.MemberVipDetailCCJBean;
import ccj.sz28yun.com.bean.MemberVipDetailCCJResult;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.exception.GearException;
import per.sue.gear2.presenter.ListPresenter;

/**
 * Created by meihuali on 2017/6/17.
 */

public class MemberVipDetailsPresenter extends ListPresenter<ArrayList<MemberVipDetailCCJBean>> {

    private UserBean userBean;
    private MemberSysAPI memberSysAPI;
    private MemberVipDetailsResultView resultView;
    private String memberid;

    public MemberVipDetailsPresenter(Context context, MemberVipDetailsResultView listResultView) {
        super(context, listResultView);
        this.resultView = listResultView;
        this.userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.memberSysAPI = new MemberSysAPI();
    }

    @Override
    public void query() {
        subscription = memberSysAPI.getMemberVipDetails(userBean.getToken(), memberid, userBean.getStoreId(), getPageNum()).subscribe(
                result -> {
                    if (isRefresh) {
                        this.resultView.onSuccessRefresh(result.getList());
                        this.resultView.onSuccessStatic(result.getOrderNum(), result.getMemberName());
                    } else {
                        this.resultView.onSuccessLoadModre(result.getList());
                        this.resultView.onSuccessStatic(result.getOrderNum(), result.getMemberName());
                    }

                },
                throwable -> {
                    if (throwable instanceof GearException) {
                        GearException gearException = (GearException) throwable;
                        if (300 == gearException.getCode()) {//300 为空数据
                            if (isRefresh) {
                                resultView.onSuccessRefresh(new ArrayList<>());
//                                    resultView.onSuccessStatic(result.getStatistics().currentVip,result.getStatistics().currentGoods,
//                                            result.getStatistics().currentMerchants,result.getStatistics().currentPromote);
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

    public void setMember(String memberid) {
        this.memberid = memberid;
    }

    public interface MemberVipDetailsResultView extends ListResultView<ArrayList<MemberVipDetailCCJBean>> {

        public void onSuccessStatic(String orderNum, String memberName);
    }
}
