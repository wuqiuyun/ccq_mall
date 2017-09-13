package per.sue.gear2.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by 威朋行政 on 2016-08-03.
 */
public class NoScrollListView extends ListView{
    public NoScrollListView(Context context) {
        super(context);
    }

    public NoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >>2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
