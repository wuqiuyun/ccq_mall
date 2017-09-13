package ccj.sz28yun.com.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RadioButton;

import ccj.sz28yun.com.R;

/**
 * Created by sue on 2017/1/2.
 */
public class BottomLineRadioButton extends RadioButton {

    private int bottomLineSelectedHeight = 5;
    private int bottomLineNormalHeight = 2;
    private int paddingOff = 0;
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    public BottomLineRadioButton(Context context) {
        super(context);
    }

    public BottomLineRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomLineRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(getResources().getColor(R.color.app_divider));
        float left = 0;
        float right = getWidth() ;
        float top =  0;
        float bottom = getHeight();
        top = getHeight() - bottomLineNormalHeight;
        canvas.drawRect(left, top, right, bottom, mPaint);

        if(isChecked()){
            mPaint.setColor(getResources().getColor(R.color.colorPrimary));
            left = (float) paddingOff;
            right = right - paddingOff;
            top = getHeight() - bottomLineSelectedHeight;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }

        //RectF rectF=new RectF(left,top,right,bottom);

    }
}
