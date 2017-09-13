package per.sue.gear2.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by sue on 2016/11/15.
 */
public class GearView implements IBaseView {

    @Override
    public int getLayoutResId() {
        return 0;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public Activity getActivity() {
        return null;
    }

    @Override
    public void onError(int code, String message) {

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void onLoadFailed() {

    }
}
