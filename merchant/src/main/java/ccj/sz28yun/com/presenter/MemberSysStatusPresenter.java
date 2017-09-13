package ccj.sz28yun.com.presenter;

import android.content.Context;

import com.google.gson.Gson;

import ccj.sz28yun.com.api.MemberSysAPI;
import ccj.sz28yun.com.bean.BaseResult;
import ccj.sz28yun.com.bean.MemberSysStatusBean;
import ccj.sz28yun.com.bean.MemberSysStatusResult;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataCache;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import ccj.sz28yun.com.fragment.MemberSysAduitFragment;
import per.sue.gear2.exception.GearException;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class MemberSysStatusPresenter extends AbsPresenter {
    private static final String TAG = "LoginPresenter";

    private MemberSysStatusView view;
    private Context context;
    private MemberSysAPI  memberSysAPI;


    public MemberSysStatusPresenter(Context context, MemberSysStatusView view) {
        this.view = view;
        this.context = context;
        this.memberSysAPI = new MemberSysAPI();
    }


    public void getStatus(){
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.memberSysAPI.getMemberSysStatus(userBean.getToken(), userBean.getStoreId(), userBean.getMemberId()).subscribe(
          result -> {
              GlobalDataCache.getInstance().setMemberSysStatusBean(result);
              view.onSuccess(result);
              view.onSuccessStatus(4);//成功切换界面到短信列表
          },
                throwable -> {
                    if(throwable instanceof GearException){
                        GearException gearException = (GearException)throwable;
                        String json = gearException.getJson();
                        int code = gearException.getCode();
                        MemberSysStatusResult result = new Gson().fromJson(json, MemberSysStatusResult.class);
                        GlobalDataCache.getInstance().setMemberSysStatusBean(result.data);
                        switch(code){
                            case 301:
                                view.onSuccessStatus(MemberSysAduitFragment.STATUS_FIRST);
                                break;
                            case 302:
                                GlobalDataCache.getInstance().setMemberSysStatusBean(result.data);
                                view.onSuccessStatus(MemberSysAduitFragment.STATUS_PROCESS);
                                break;
                            case 303:
                                GlobalDataCache.getInstance().setMemberSysStatusBean(result.data);
                                view.onSuccessStatus(MemberSysAduitFragment.STATUS_FAILED);
                                break;
                            default:
                                view.onError(-1, throwable.getMessage());
                                break;
                        }
                    }else{
                        view.onError(-1, throwable.getMessage());
                    }

                }
        );
    }





    public interface MemberSysStatusView extends GearResultView<MemberSysStatusBean> {

        void onSuccessStatus(int status);

    }
}
