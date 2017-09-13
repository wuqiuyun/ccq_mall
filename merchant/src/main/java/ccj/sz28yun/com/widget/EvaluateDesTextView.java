package ccj.sz28yun.com.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

import ccj.sz28yun.com.R;

/**
 * Created by SUE on 2016/8/20 0020.
 */
public class EvaluateDesTextView extends TextView {

    private Paint mBgPaint = new Paint();
    private int color = Color.RED;
    int  radius = 10;
    public EvaluateDesTextView(Context context) {
        super(context);
    }

    public EvaluateDesTextView(Context context, int color) {
        super(context);
        this.color = color;
    }

    public EvaluateDesTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EvaluateDesTextView);
        color = a.getColor(R.styleable.EvaluateDesTextView_iconColor, Color.RED);
    }

    public EvaluateDesTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EvaluateDesTextView);
        color = a.getColor(R.styleable.EvaluateDesTextView_iconColor, Color.RED);
    }

    private int newPaddingLeft = 0;

    @Override
    protected void onDraw(Canvas canvas) {

        mBgPaint .setColor(color);
        int padingLeft  = getPaddingLeft();
        if(newPaddingLeft == 0){
            newPaddingLeft = padingLeft + radius *2 + 4;
            setPadding(newPaddingLeft,getTotalPaddingTop() , getPaddingRight(), getPaddingBottom());
        }

        float textWidth = getPaint().measureText(getText().toString());
        int y = getHeight()/2;
        canvas.drawCircle(  getWidth()/2  -  textWidth/2 - radius, y ,radius, mBgPaint);
      /*  //画圆角矩形
        mBgPaint.setStyle(Paint.Style.FILL);//充满
        mBgPaint.setColor(Color.LTGRAY);
        mBgPaint.setAntiAlias(true);// 设置画笔的锯齿效果
        canvas.drawText("画圆角矩形:", 10, 260, mBgPaint);
        RectF oval3 = new RectF(80, 260, 200, 300);// 设置个新的长方形
        canvas.drawRoundRect(oval3, 20, 15, mBgPaint);//第二个参数是x半径，第三个参数是y半径*/
        // canvas.setDrawFilter(pfd);//给Canvas加上抗锯齿标志

        super.onDraw(canvas);
    }



    @Override
       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
      /*  int measuredWidth = getMeasuredWidth();
       int measuredHeight = getMeasuredHeight();
       int max = Math.max(measuredWidth, measuredHeight);
       setMeasuredDimension(max, max);*/
       }
}
