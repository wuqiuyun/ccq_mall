package per.sue.gear2.presenter;

import android.content.Context;

import rx.Observable;

/**
 * Created by SUE on 2016/8/3 0003.
 */
public class SelectListPresenter<T> extends ListPresenter<T> {


    private Observable<T> observable;

    public SelectListPresenter(Context context, ListResultView<T> listResultView ) {
        super(context, listResultView);
    }



   /* @Override
    public Observable<T> getObservable() {
        return observable;
    }*/
}
