package per.sue.gear2.presenter;

import java.util.ArrayList;

import per.sue.gear2.utils.GearLog;
import rx.Subscription;

/*
* 文件名：
* 描 述：
* 作 者：苏昭强
* 时 间：2016/4/22
*/
public class AbsPresenter implements Presenter {

    protected ArrayList<Subscription> subscriptionArrayList = new ArrayList<>();

    public void cancelRequest(){
        if(null != subscriptionArrayList){
            for(Subscription subscription : subscriptionArrayList){
                if(null != subscription && !subscription.isUnsubscribed() )
                subscription.unsubscribe();
            }
        }
        subscriptionArrayList = new ArrayList<>();
    }

    public void addSubscription(Subscription subscription){
        subscriptionArrayList.add(subscription);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        GearLog.e(this.getClass().getSimpleName(), "destroy()");
        cancelRequest();
    }
}
