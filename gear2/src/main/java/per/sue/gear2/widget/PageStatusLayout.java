package per.sue.gear2.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import per.sue.gear2.R;


/*
* 文件名：
* 描 述：
* 作 者：苏昭强
* 时 间：2016/3/16
*/
public class PageStatusLayout extends FrameLayout {

    private View loadView;
    private View failedView;
    private View emptyView;
    private long loadTime;

    private int loadViewResId;
    private int failedViewResId;
    private int emptyViewResId;


    public PageStatusLayout(Context context) {
        super(context);

    }

    public PageStatusLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.PageStatusLayout);
        loadViewResId = a.getResourceId(R.styleable.PageStatusLayout_loadViewResId, 0);
        failedViewResId = a.getResourceId(R.styleable.PageStatusLayout_failedViewResId, 0);
        emptyViewResId = a.getResourceId(R.styleable.PageStatusLayout_emptyViewResId, 0);
    }


    public void showLoading() {
        loadTime = System.currentTimeMillis();
        visibleLoading();
    }



    public void showEmpty() {
      visibleEmpty();
    }

    public void showFailed(String msg, OnClickListener l) {
        visibleFailed();
        if(null != failedView.findViewById(R.id.pager_tv_msg)){
            TextView msgTV = (TextView) failedView.findViewById(R.id.pager_tv_msg);
            msgTV.setText("重试");
            msgTV.setOnClickListener(l);
        }


    }

    public void showContent() {
        if(null != loadView){loadView.setVisibility(GONE);}
        if(null != failedView){failedView.setVisibility(GONE);}
        if(null != emptyView){emptyView.setVisibility(GONE);}

    }



    private void visibleFailed() {
        if (null == failedView) {
            if(0 != failedViewResId){
                failedView = LayoutInflater.from(getContext()).inflate(failedViewResId, null);
            }else{
                failedView = LayoutInflater.from(getContext()).inflate(R.layout.gear_view_pager_failed, null);
            }
            addView(failedView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        }
        failedView.setVisibility(View.VISIBLE);
        if(null != loadView){loadView.setVisibility(GONE);}
        if(null != emptyView){emptyView.setVisibility(GONE);}
    }


    private void visibleLoading() {
        if (null == loadView) {
            if(0 != loadViewResId){
                loadView = LayoutInflater.from(getContext()).inflate(loadViewResId, null);
            }else{
                loadView = LayoutInflater.from(getContext()).inflate(R.layout.gear_view_pager_loading, null);
            }
            addView(loadView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        }

        loadView.setVisibility(View.VISIBLE);
        if(null != failedView){failedView.setVisibility(GONE);}
        if(null != emptyView){emptyView.setVisibility(GONE);}
    }

    private void visibleEmpty() {
        if (null == emptyView) {
            if(0 != emptyViewResId){
                emptyView = LayoutInflater.from(getContext()).inflate(emptyViewResId, null);
            }else{
                emptyView = LayoutInflater.from(getContext()).inflate(R.layout.gear_view_pager_empty, null);
            }
            addView(emptyView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        }

        emptyView.setVisibility(View.VISIBLE);
        if(null != failedView){failedView.setVisibility(GONE);}
        if(null != loadView){loadView.setVisibility(GONE);}
    }

}
