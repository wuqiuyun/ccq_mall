package clerk.sz28yun.com.presenter;

import android.content.Context;

import clerk.sz28yun.com.api.ChildAccountAPI;
import clerk.sz28yun.com.api.CommonAPI;
import clerk.sz28yun.com.bean.ChildAccountBean;
import clerk.sz28yun.com.bean.ShareConfigBean;
import clerk.sz28yun.com.bean.UserBean;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;

import java.util.ArrayList;

import per.sue.gear2.exception.GearException;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;
import rx.Subscriber;

/**
 * Created by sue on 2016/11/16.
 */
public class MinePresenter extends AbsPresenter {

    private Context context;
    private MineView mineView;
    private ChildAccountAPI childAccountAPI;


    public MinePresenter(Context context, MineView mineView) {
        this.context = context;
        this.mineView = mineView;
        this.childAccountAPI  =  new ChildAccountAPI();
    }

    /**
     * 获取子账号列表
     */
    public void requestData(){
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        String token = userBean.getToken();
        String menberId = userBean.getMemberId();
       addSubscription( this.childAccountAPI.getChildAccountList(token, menberId).subscribe(new Subscriber<ArrayList<ChildAccountBean>>() {
           @Override
           public void onCompleted() {
               mineView.onCompleted();
           }

           @Override
           public void onError(Throwable e) {
               if(e instanceof GearException){
                  if( ((GearException)e).getCode()  != 300){//300 是没数据的意思， 暂时不弹对话框
                      mineView.onError(-1, e.getMessage());
                  }
               }else{
                   mineView.onError(-1, e.getMessage());
               }
           }

           @Override
           public void onNext(ArrayList<ChildAccountBean> list) {
               mineView.onSuccess(list);
           }
       }));
    }

    public void getShareConfig(){
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        String menberId = userBean.getMemberId();
       addSubscription(new CommonAPI().getShareConfig(menberId).subscribe(
               shareConfigBean -> {
                   mineView.onSuccessShareConfig(shareConfigBean);
               },
               throwable -> {
                   mineView.onError(-2, throwable.getMessage());
               }
       ));
    }

    public interface MineView extends GearResultView<ArrayList<ChildAccountBean>> {

        void onSuccessShareConfig( ShareConfigBean configBean);

    }
}
