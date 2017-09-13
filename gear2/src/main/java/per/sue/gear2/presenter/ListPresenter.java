package per.sue.gear2.presenter;

import android.content.Context;


import per.sue.gear2.exception.GearException;
import per.sue.gear2.ui.IBaseView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by SUE on 2016/7/20 0020.
 */
public   class ListPresenter<T> extends AbsPresenter  {

    protected boolean isRefresh;
    private boolean isWithLoad;
    protected int pageNum;
    protected Subscription subscription;
    private Context context;
    private ListResultView<T> listResultView;
    private Observable observable;

    private Runnable questBeforeRunable;

    public ListPresenter(Context context, ListResultView<T>  listResultView) {
        this.context = context;
        this.listResultView = listResultView;
    }

    public void refreshWithLoading(){
        isRefresh = true;
        isWithLoad =  true;
        pageNum = 1;
        listResultView.showLoading();
        query();
    }

    public void refresh(){
        isRefresh = true;
        isWithLoad = false;
        pageNum = 1;
        query();
    }

    public void loadMore(){
        isRefresh = false;
        isWithLoad = false;
        pageNum ++;
        query();
    }

    public void cancelRequest(){
        if(null != subscription)subscription.unsubscribe();
    }

    public void query() {
        if(null != questBeforeRunable){
            questBeforeRunable.run();
        }
        if(null == observable )return;
        if(null != subscription)subscription.unsubscribe();
        subscription  = getObservable().subscribe(new Subscriber<T>() {
            @Override
            public void onCompleted() {
                listResultView .onCompleted();
            }
            @Override
            public void onError(Throwable e) {
                if(e instanceof GearException){
                    GearException gearException = (GearException)e;
                    listResultView.onError(gearException.getCode(), e.getMessage());
                }else{
                    listResultView.onError(-1, e.getMessage());
                }

                if(isWithLoad){
                    listResultView.onLoadFailed();
                }
            }

            @Override
            public void onNext(T recommendVideos) {
                if(isRefresh){
                    listResultView .onSuccessRefresh(recommendVideos);
                }else{
                    listResultView.onSuccessLoadModre(recommendVideos);
                }

            }
        });

        addSubscription(subscription);
    }


    public int getPageNum() {
        return pageNum;
    }

    public void setObservable(Observable observable) {
        this.observable = observable;
    }

    public Observable getObservable() {
        return observable;
    }

    public void setQuestBeforeRunable(Runnable questRunable) {
        this.questBeforeRunable = questRunable;
    }

    public interface ListResultView<T> extends IBaseView {
        void onSuccessRefresh(T result);
        void onSuccessLoadModre(T result);
    }

    public boolean isWithLoad() {
        return isWithLoad;
    }

    public boolean isRefresh() {
        return isRefresh;
    }
}
