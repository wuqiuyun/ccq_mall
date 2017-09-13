package ccj.sz28yun.com.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import ccj.sz28yun.com.R;


/**
 * Created by sue on 2016/12/1.
 */
public class SelectableLinearLayout extends LinearLayout {

    private boolean isSelected;
    private int bottomLineSelectedHeight = 5;
    private int bottomLineNormalHeight = 1;

    private int selectTextColor;
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public SelectableLinearLayout(Context context) {
        super(context);
    }

    public SelectableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(getResources().getColor(R.color.app_divider));
        final float left = 0;
        final float right = getWidth() ;
        float top = 0 ;
        final float bottom = getHeight();
        top = getHeight() - bottomLineNormalHeight;
        Log.e("line height ", top + "");
        canvas.drawRect(left, top, right, bottom, mPaint);
        if(isSelected){
            mPaint.setColor(getResources().getColor(R.color.colorPrimary));
            top =  getHeight() - bottomLineSelectedHeight;
            Log.e("line height2 ", top + "");
            canvas.drawRect(left, top, right, bottom, mPaint);
        }

        //RectF rectF=new RectF(left,top,right,bottom);

    }

    public void setSelectType(){
        isSelected = true;
        int count = getChildCount();
        for(int i = 0 ; i < count; i++){
            View view = getChildAt(i);
            if(view instanceof TextView){
                TextView textView =   ( (TextView)view);
                if(null == textView.getTag(R.id.tag_key_text_color)){
                    textView.setTag(R.id.tag_key_text_color, textView.getTextColors());
                }
                if(0 == selectTextColor){
                    textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                }else {
                    textView.setTextColor(selectTextColor);
                }
            }else {

            }
        }

        invalidate();
    }

    public void setUnSelectType(){
        isSelected = false;
        int count = getChildCount();
        for(int i = 0 ; i < count; i++){
            View view = getChildAt(i);
            if(view instanceof TextView){
                TextView textView =   ( (TextView)view);
                if(null == textView.getTag(R.id.tag_key_text_color)){
                    textView.setTag(R.id.tag_key_text_color, textView.getTextColors());
                }
                textView.setTextColor((ColorStateList)textView.getTag(R.id.tag_key_text_color));
            }else {

            }
        }
        invalidate();
    }

    public void setBottomLineSelectedHeight(int bottomLineSelectedHeight) {
        this.bottomLineSelectedHeight = bottomLineSelectedHeight;
    }

    public void setSelectTextColor(int selectTextColor) {
        this.selectTextColor = selectTextColor;
    }
}
