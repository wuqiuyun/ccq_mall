package clerk.sz28yun.com.presenter;

import android.content.Context;

import clerk.sz28yun.com.api.ChildAccountAPI;
import clerk.sz28yun.com.bean.ChildAccountBean;
import clerk.sz28yun.com.bean.UserBean;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;

import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;
import per.sue.gear2.utils.MD5Utils;

/*
* 文件名：
* 描 述 ：
* 作 者：苏昭强
* 时 间：2016/4/27
*/
public class ChildAccountPresenter extends AbsPresenter {

    private Context context;
    private ChildAccountView childAccountView;
    private ChildAccountAPI childAccountAPI;
    String token;
    String menberId;

    public ChildAccountPresenter(Context context, ChildAccountView childAccountView) {
        this.context = context;
        this.childAccountView = childAccountView;
        childAccountAPI = new ChildAccountAPI();
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        this.token = userBean.getToken();
        this.menberId = userBean.getMemberId();
    }

    /**
     * 获取子账号
     */
    public void getChildAccount(String childId){
        childAccountAPI.getChildAccount(token, childId).subscribe(
                childAccountBean -> this.childAccountView.onSuccess(childAccountBean),
                throwable -> this.childAccountView.onError(-1, throwable.getMessage()));
    }

    /**
     * 新增子账号
     * @param memberName
     * @param passwd
     * @param nick
     * @param mobile
     */
    public void addChildAccount(String memberName,  String passwd, String nick, String mobile){
        if(  passwd.length() != 32){
            passwd = MD5Utils.encrypt(passwd);
        }

        childAccountAPI.addChildAccount(this.token, this.menberId, memberName, passwd, nick, mobile ).subscribe(
                message -> this.childAccountView.onSubmit(message),
                throwable -> this.childAccountView.onError(-2, throwable.getMessage())
        );
    }

    /**
     * 修改子账号
     * @param childId
     * @param passwd
     * @param nick
     * @param mobile
     */
    public void updateChildAccount(String childId,  String passwd, String nick, String mobile){
        if(  !"".equals(passwd)) {
            if (passwd.length() != 32) {//如果不是32位（修改密码后就不会是32位），就进行MD5加密。
                passwd = MD5Utils.encrypt(passwd);
            }
        }
        childAccountAPI.updateChildAccount(this.token, this.menberId, childId, passwd, nick, mobile ).subscribe(
                message -> this.childAccountView.onSubmit(message),
                throwable -> this.childAccountView.onError(-2, throwable.getMessage())
        );
    }



    public interface ChildAccountView extends GearResultView<ChildAccountBean> {
        void onSubmit(String message);
    }
}
