package per.sue.gear2.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import java.lang.reflect.Method;

import per.sue.gear2.R;


public abstract class AbsWheelPop {

	private PopupWindow popupWindow;
	private Button OkBtn;

	public void showAsDropDown(View parent) {
		
		popupWindow = new PopupWindow(getContentView(),getWidth(parent),
				LayoutParams.WRAP_CONTENT);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAsDropDown(parent);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();
		
	}
	
	public void showAtBottom(View parent) {
		int paddingBittonm = getVrtualBtnHeight(parent.getContext());
		popupWindow = new PopupWindow(getContentView(),LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAtLocation(((Activity) parent.getContext()).getWindow().getDecorView(), Gravity.BOTTOM, 0, paddingBittonm);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		popupWindow.update();
		
	}
	public void showAtCenter(View parent) {
			popupWindow = new PopupWindow(getContentView(), getWidth(parent),
					LayoutParams.WRAP_CONTENT);
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
			//	popupWindow.showAsDropDown(parent);
			popupWindow.showAtLocation(((Activity) parent.getContext()).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
			popupWindow.setFocusable(true);
			popupWindow.setOutsideTouchable(true);
		   popupWindow.update();
		
	}
	
	public  void addOkButton(Context context,View.OnClickListener l)
	{
		OkBtn=new Button(context);
		OkBtn.setBackgroundResource(R.drawable.gear_shape_squ_r4_primary);
		OkBtn.setOnClickListener(l);
		
	}
	
	public int  getWidth(View parent)
	{
		return  parent.getWidth();
	}
	
	
	public PopupWindow getPopupWindow()
	{
		return popupWindow;
	}
	
   protected  abstract View getContentView();
	
	public void dismiss() {
		popupWindow.dismiss();
	}

	public  int getVrtualBtnHeight(Context poCotext) {
		int location[] = getScreenWH(poCotext);
		int realHeiht = getDpi((Activity) poCotext);
		int virvalHeight = realHeiht - location[1];
		return virvalHeight;
	}

	public  int getDpi(Activity activity) {
		Display display = activity.getWindowManager().getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		int height = 0;
		@SuppressWarnings("rawtypes")
		Class c;
		try {
			c = Class.forName("android.view.Display");
			@SuppressWarnings("unchecked")
			Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
			method.invoke(display, dm);
			height = dm.heightPixels;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return height;
	}
	public  int[] getScreenWH(Context poCotext) {
		WindowManager wm = (WindowManager) poCotext
				.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		return new int[] { width, height };
	}

}
