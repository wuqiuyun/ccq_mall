package ccj.sz28yun.com.presenter;

import android.content.Context;

import java.util.ArrayList;

import ccj.sz28yun.com.api.MemberSysAPI;
import ccj.sz28yun.com.api.MessageAPI;
import ccj.sz28yun.com.bean.MemberSysSMSBean;
import ccj.sz28yun.com.bean.MessageBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataCache;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.presenter.ListPresenter;

/**
 * Created by sue on 2016/12/15.
 */
public class MessagePresenter extends ListPresenter<ArrayList<MessageBean>> {

    private MessageView messageView;
    private MessageAPI messageAPI;
    UserBean userBean;
    public MessagePresenter(Context context, MessageView listResultView) {
        super(context, listResultView);
        this.messageView = listResultView;
        this.messageAPI = new MessageAPI();
        userBean = GlobalDataStorageCache.getInstance().getUserData();
    }


    @Override
    public void query() {
      subscription =  messageAPI.getMessageList(userBean.getToken(), userBean.getStoreId(), getPageNum()).subscribe(
                result -> {
                    if (isRefresh) {
                        this.messageView.onSuccessRefresh(result);
                    } else {
                        this.messageView.onSuccessLoadModre(result);
                    }
                },
                throwable -> {
                    messageView.onError(-1, throwable.getMessage());
                }
        );

        addSubscription(subscription);
    }

    public  void setRead(String messageId){
        this.messageAPI.setReaded(userBean.getToken(),  messageId).subscribe(
                result -> { messageView.onSetReaded(result);},
                throwable -> { messageView.onError(-1, throwable.getMessage());}
        );
    }





    public interface MessageView extends ListResultView<ArrayList<MessageBean>> {

        public void onSetReaded(String message);


    }
}
