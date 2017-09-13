package ccj.sz28yun.com.presenter;

import android.content.Context;

import com.google.gson.Gson;

import ccj.sz28yun.com.api.MemberSysAPI;
import ccj.sz28yun.com.api.VerifyAPI;
import ccj.sz28yun.com.bean.BaseResult;
import ccj.sz28yun.com.bean.MemberSysStatusBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataCache;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.exception.GearException;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class MemberSysAduitPresenter extends AbsPresenter {
    private static final String TAG = "LoginPresenter";

    private MemberSysAduitView view;
    private Context context;
    private MemberSysAPI  memberSysAPI;
    private UserBean userBean;


    public MemberSysAduitPresenter(Context context, MemberSysAduitView view) {
        this.view = view;
        this.context = context;
        this.memberSysAPI = new MemberSysAPI();
        userBean = GlobalDataStorageCache.getInstance().getUserData();
    }

    public void sendSigntrue(String content){

        memberSysAPI.sendSigntrueForStartService(userBean.getToken(), userBean.getStoreId(), content).subscribe(
                result -> { view.onSuccess(result);},
                throwable -> { view.onError(-1, throwable.getMessage());}
        );

    }

    public void getStatisticData(){
        MemberSysStatusBean memberSysStatusBean = GlobalDataCache.getInstance().getMemberSysStatusBean();
        view.onSuccessStatistic(memberSysStatusBean.getMemberNum(), memberSysStatusBean.getVipMemberNum());
    }

    public void getReson(){
        MemberSysStatusBean memberSysStatusBean = GlobalDataCache.getInstance().getMemberSysStatusBean();
        view.onFaildedReson(memberSysStatusBean.getReason());
    }

    public interface MemberSysAduitView extends GearResultView<String> {
        public void onSuccessStatistic(int memberCount, int vipCount);
        public void onFaildedReson(String reson);
    }
}
