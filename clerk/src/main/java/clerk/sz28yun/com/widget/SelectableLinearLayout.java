package clerk.sz28yun.com.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import clerk.sz28yun.com.R;


/**
 * Created by sue on 2016/12/1.
 */
public class SelectableLinearLayout extends LinearLayout {

    private boolean isSelected;
    private int bottomLineSelectedHeight = 5;
    private int bottomLineNormalHeight = 1;
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
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        final float left = 0;
        final float right = getWidth() ;
        float top = getHeight() ;
        final float bottom = getHeight();
        if(isSelected){
            mPaint.setColor(getResources().getColor(R.color.colorPrimary));
            top = top - bottomLineSelectedHeight;
        }else{
            mPaint.setColor(getResources().getColor(R.color.app_divider));
            top = top - bottomLineNormalHeight;
        }

        //RectF rectF=new RectF(left,top,right,bottom);
        canvas.drawRect(left, top, right, bottom, mPaint);
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
                textView.setTextColor(getResources().getColor(R.color.colorPrimary));
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
}
