package per.sue.gear2.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import per.sue.gear2.R;
import per.sue.gear2.utils.GearLog;
import per.sue.gear2.utils.StatusBarUtils;
import per.sue.gear2.utils.ToastUtils;
import per.sue.gear2.utils.UnitUtils;
import per.sue.gear2.widget.PageStatusLayout;
import per.sue.gear2.widget.titlebar.HeadBarView;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public abstract class UIBaseActivity extends AppCompatActivity implements IBaseView {

    protected  ProgressDialog progressDialog;;
    protected  String mClassName = getClass().getSimpleName();
    private  final String TAG =  getClass().getSimpleName();

    private View mContentView;
    private PageStatusLayout mContentFrameLayout;
    private HeadBarView mHeadBarView;
    protected TextView mTitleTextView;
    protected View mLeftView;

    protected LinearLayout mBaseContainLayout;

    public   boolean hasTitleBar = true;

    private  long loadTimes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GearLog.i(TAG, " onCreate satrt loadTimes = " + (loadTimes = System.currentTimeMillis()));
        if(hasTitleBar){
            GearLog.i(TAG, "not title bar");
            setContentView(R.layout.activity_base);
            initComponentViews();
        }else{
            setContentView(getLayoutResId());
        }
        ButterKnife.bind(this);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.colorPrimary);
    }

    private void initComponentViews() {
        mBaseContainLayout = (LinearLayout) findViewById(R.id.app_base_contain_layout);
        mContentView = getLayoutInflater().inflate(getLayoutResId(), null);
        mContentFrameLayout = (PageStatusLayout) findViewById(R.id.app_content);
        mHeadBarView = (HeadBarView) findViewById(R.id.app_head_bar);
        mHeadBarView.setBackgroundColor(getResources().getColor( R.color.colorPrimary));
        mContentFrameLayout.addView(mContentView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        initTitleViews();
        mContentFrameLayout.showContent();
    }
    private void initTitleViews() {
        if (showBackView()) {
            mLeftView = mHeadBarView.addLeftItem(R.mipmap.ic_arrow_left_white, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
           // setMenuItemParams(mLeftView);
            if(mLeftView instanceof ImageView){
                (  (ImageView)mLeftView).setScaleType(ImageView.ScaleType.CENTER);
            }
        }

        setBarTitle(getTitle().toString());
    }

    public boolean showBackView(){
        return false;
    }

    public void setBarTitle(int resId) {
        setBarTitle(getResources().getString(resId));
    }

    public void setBarTitle(String title) {
        if (mTitleTextView == null) {
            mTitleTextView = mHeadBarView.addMiddlerTitle(title);
           mTitleTextView.setTextColor(getResources().getColor(R.color.gear_white));
            mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        } else {
            mTitleTextView.setText(title);
        }

    }

    public HeadBarView getHeadBarView() {
        return mHeadBarView;
    }

    public TextView getTitleTextView() {
        return mTitleTextView;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        onInitialize(savedInstanceState);
        GearLog.i(TAG, "onPostCreate  end loadTimes = " + (  System.currentTimeMillis() - loadTimes));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public Activity getActivity() {
        return this;
    }


    public void onError(int code, String message) {
        dismissProgressDialog();
        ToastUtils.showShortMessage(message, getApplication());
    }


    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void showLoading() {
        showProgressDialog("加载中...");
    }

    public void onLoadFailed(){

    }

    /**
     *
     * @param containerViewId
     * @param fragment
     */
    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    protected void addFragment(int containerViewId, Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment, tag);
        fragmentTransaction.commit();
    }

    protected void replaceFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    public  void showProgressDialog(String tip) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.setCancelable(true);
        }
        progressDialog.setMessage(tip);
        progressDialog.show();
    }

    protected void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public LinearLayout getBaseContainLayout() {
        return mBaseContainLayout;
    }

    public String getClassName() {
        return mClassName;
    }


    /**
     * 监听点击外面关闭小键盘
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText || v instanceof Button)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
