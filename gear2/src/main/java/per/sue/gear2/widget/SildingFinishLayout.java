package per.sue.gear2.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Toast;

/**
 * 自定义可以滑动的RelativeLayout, 类似于IOS的滑动删除页面效果，当我们要使用
 * 此功能的时候，需要将该Activity的顶层布局设置为SildingFinishLayout，
 * 然后需要调用setTouchView()方法来设置需要滑动的View
 * 
 * @author xiaanming
 * 
 * @blog http://blog.csdn.net/xiaanming
 * 
 */
@SuppressLint("Recycle")
public class SildingFinishLayout extends RelativeLayout implements OnTouchListener  {
	/**
	 * SildingFinishLayout布局的父布局
	 */
	private ViewGroup mParentView;
	/**
	 * 处理滑动逻辑的View
	 */
	private View touchView;
	/**
	 * 滑动的最小距离
	 */
	private int mTouchSlop;
	/**
	 * 按下点的X坐标
	 */
	private int downX;
	/**
	 * 按下点的Y坐标
	 */
	private int downY;
	/**
	 * 临时存储X坐标
	 */
	private int tempX;
	/**
	 * 滑动类
	 */
	private Scroller mScroller;
	/**
	 * SildingFinishLayout的宽度
	 */
	private int viewWidth;
	/**
	 * 记录是否正在滑动
	 */
	private boolean isSilding;
	
	private OnSildingFinishListener onSildingFinishListener;
	private boolean isFinish;
	
	private int[]loaction = new int[2];
	
	public SildingFinishLayout(Context context) {
		this(context, null, 0);
	}
	public SildingFinishLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SildingFinishLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		mScroller = new Scroller(context);
	    getLocationOnScreen(loaction);
		setOnTouchListener(this);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			// 获取SildingFinishLayout所在布局的父布局
			mParentView = (ViewGroup) this.getParent();
			viewWidth = this.getWidth();
			Log.e("onLayout", "left=" + l);
		}
	}

	/**
	 * 设置OnSildingFinishListener, 在onSildingFinish()方法中finish Activity
	 * 
	 * @param onSildingFinishListener
	 */
	public void setOnSildingFinishListener(
			OnSildingFinishListener onSildingFinishListener) {
		this.onSildingFinishListener = onSildingFinishListener;
	}

	/**
	 * 设置Touch的View
	 * 
	 * @param touchView
	 */
	public void setTouchView(View touchView) {
		this.touchView = touchView;
		//touchView.setOnTouchListener(this);
	}

	public View getTouchView() {
		return touchView;
	}

	/**
	 * 滚动出界面
	 */
	private void scrollRight() {
		final int delta = (viewWidth + mParentView.getScrollX());
		// 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
		//startScroll(startX,startY,x,y)
		//(startX,startY)滚动起始点,startX正值向左滑动，startY正值向上滑动
		//(x,y)滑动距离，x正值向左滑动，y正值向上滑动
		mScroller.startScroll(mParentView.getScrollX(), 0, -delta + 1, 0,
				Math.abs(delta));
		postInvalidate();
	}

	/**
	 * 滚动到起始位置
	 */
	private void scrollOrigin() {
		int delta = mParentView.getScrollX();
		mScroller.startScroll(mParentView.getScrollX(), 0, -delta, 0,
				Math.abs(delta));
		postInvalidate();
	}

	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		Log.e("onInterceptTouchEvent = ", event.getAction()+"");
		boolean isIntercept = false;
		switch(event.getAction())
		{
		case  MotionEvent.ACTION_DOWN:
			downX = tempX = (int) event.getX();
			downY = (int) event.getY();
			isIntercept = false;
			break;
		case  MotionEvent.ACTION_MOVE:
			int moveX = (int) event.getRawX();
			int moveY = (int) event.getRawY();
			int deltaX = tempX - moveX;
			tempX = moveX;
			//int abs =Math.abs(moveX - downX) ;
			int abs =moveX - downX ;
			//Log.e("onInterceptTouchEvent move ", "moveX = "+moveX +"/downX = "+downX);
			//Log.e("onInterceptTouchEvent = ", "abs = "+abs +"/mTouchSlop = "+mTouchSlop);
			if (abs> mTouchSlop) {
				isIntercept = true;
			
				//Log.e("onInterceptTouchEvent = ", event.getAction()+"");
			}
			
			break;
		case  MotionEvent.ACTION_UP:
			downX = 0;
			downY = 0;
			isIntercept = false;
			break;
		
		}
		
		//Log.e("onInterceptTouchEvent = ", event.getAction()+"isIntercept = " +isIntercept);
		//Log.e("onInterceptTouchEvent = ", event.getAction()+"isSilding = " +isSilding);
		
		return isIntercept;
	}
	
	@SuppressLint("NewApi")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		
		
		switch(event.getAction())
		{
		case  MotionEvent.ACTION_DOWN:
			downX = tempX = (int) event.getX();
			downY = (int) event.getY();
			break;
		case  MotionEvent.ACTION_MOVE:
			int moveX = (int) event.getRawX();
			int moveY = (int) event.getRawY();
			
			int deltaX = tempX - moveX;
			tempX = moveX;
			///Log.e("onTouch = ",deltaX+"=deltaX");
	     	//	Log.e("onTouch = ","getScrollX="+mParentView.getScrollX());
			
		   if(mParentView.getScrollX()<=0)
		   {
			   isSilding = true;
				mParentView.scrollBy(deltaX, 0);
		   }
				
	
			
			
			break;
		case  MotionEvent.ACTION_UP:
			isSilding =  false;
			if (mParentView.getScrollX() <= -viewWidth / 2) {
				isFinish = true;
				scrollRight();
			} else {
				scrollOrigin();
				isFinish = false;
			}
			break;
		}
		
		
		return true;
	}




	@Override
	public void computeScroll() {
		// 调用startScroll的时候scroller.computeScrollOffset()返回true，
		if (mScroller.computeScrollOffset()) {
			mParentView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();

			if (mScroller.isFinished()) {

				if (onSildingFinishListener != null && isFinish) {
					onSildingFinishListener.onSildingFinish();
				}
			}
		}
	}
	

	public interface OnSildingFinishListener {
		public void onSildingFinish();
	}

}
