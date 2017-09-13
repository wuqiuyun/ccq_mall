package per.sue.gear2.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by SUE on 2016/7/7 0007.
 */
public interface IBaseView {
    public abstract int getLayoutResId();
    public abstract void onInitialize(@Nullable Bundle savedInstanceState);
    public Activity getActivity();

    void onError(int code, String message);
    void onCompleted();
    void showLoading();
    void onLoadFailed();


}
