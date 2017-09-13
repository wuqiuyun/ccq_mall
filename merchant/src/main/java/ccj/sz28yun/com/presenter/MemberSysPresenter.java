package ccj.sz28yun.com.presenter;

import android.content.Context;

import org.json.JSONException;

import ccj.sz28yun.com.api.MemberSysAPI;
import ccj.sz28yun.com.bean.MemberSysSMSSendParams;
import ccj.sz28yun.com.bean.MemberSysVIPNumBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;

/**
 * Created by meihuali on 2017/6/16.
 */

public class MemberSysPresenter extends AbsPresenter {
    private Context context;
    private MemberSysView view;
    private MemberSysAPI memberSysAPI;
    private UserBean userBean;

    public MemberSysPresenter(Context context, MemberSysView view) {
        this.view = view;
        this.context = context;
        userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.memberSysAPI = new MemberSysAPI();
    }

    public void getVipNum() {
        this.memberSysAPI.getMemberNum(userBean.getToken(), userBean.getStoreId()).subscribe(
                result -> {
                    view.onSuccess(result);
                },
                throwable -> {
                    view.onError(-1, throwable.getMessage());
                }
        );
    }


    public interface MemberSysView extends GearResultView<MemberSysVIPNumBean> {

    }
}
