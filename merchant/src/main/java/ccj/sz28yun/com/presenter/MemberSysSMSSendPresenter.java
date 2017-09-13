package ccj.sz28yun.com.presenter;

import android.content.Context;

import org.json.JSONException;

import ccj.sz28yun.com.api.MemberSysAPI;
import ccj.sz28yun.com.api.VerifyAPI;
import ccj.sz28yun.com.bean.MemberSysSMSSendParams;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class MemberSysSMSSendPresenter extends AbsPresenter {
    private static final String TAG = "LoginPresenter";

    private MemberSysSMSSendView view;
    private Context context;
    private MemberSysAPI memberSysAPI;


    public MemberSysSMSSendPresenter(Context context, MemberSysSMSSendView view) {
        this.view = view;
        this.context = context;
        this.memberSysAPI = new MemberSysAPI();
    }


    public void send(MemberSysSMSSendParams params){
        try {
            this.memberSysAPI.sendSMS(params).subscribe(
              result -> { view.onSuccess(result);},
                    throwable -> { view.onError(-1, throwable.getMessage());}
            );
        } catch (JSONException e) {
            e.printStackTrace();
            view.onError(-1, "数据转换异常， 请联系相关人员");
        }
    }


    public interface MemberSysSMSSendView extends GearResultView<String> {

    }
}
