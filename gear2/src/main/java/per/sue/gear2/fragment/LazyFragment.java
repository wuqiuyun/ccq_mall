package per.sue.gear2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import per.sue.gear2.ui.UIBaseFragment;
import per.sue.gear2.utils.GearLog;

/**
 * Created by SUE on 2016/8/1 0001.
 */
public abstract class LazyFragment extends UIBaseFragment {

    private Bundle savedInstanceState;
    public static final String INTENT_BOOLEAN_LAZYLOAD = "intent_boolean_lazyLoad";
    private boolean isLazyLoad = true;
    private boolean isInited;
    private boolean isLoaded;

    /** Fragment当前状态是否可见 */
    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        changeVisableHint();
    }

    private void changeVisableHint(){

        if(isLoaded)return ;

        if(isInited){
            if(getUserVisibleHint()) {
                onFragmentVisibleLoad(savedInstanceState);
                onFragmentVisible();
            } else {
                onFragmentInvisible();
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        this.isInited = true;
        this.savedInstanceState = savedInstanceState;
        super.onActivityCreated(savedInstanceState);
        changeVisableHint();
    }

    /**
     * 可见
     */
    protected void onFragmentVisible() {
        GearLog.e(TAG, "onFragmentVisible();");
    }

    protected void onFragmentVisibleLoad(@Nullable Bundle savedInstanceState) {
        if(isLoaded) return;
        isLoaded = true;
    }


    /**
     * 不可见
     */
    protected void onFragmentInvisible() {
        GearLog.e(TAG, "onFragmentInvisible();");

    }

    public boolean isLazyLoad() {
        return isLazyLoad;
    }

    public void setLazyLoad(boolean lazyLoad) {
        isLazyLoad = lazyLoad;
    }
}
