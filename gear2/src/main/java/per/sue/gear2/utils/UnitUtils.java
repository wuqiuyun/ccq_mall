package per.sue.gear2.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by su on 2015/8/31.
 * email 125906374@qq.com
 */
public class UnitUtils {



    public static int px2dip(Context context, int valuePx)
    {
        return  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valuePx, context.getResources().getDisplayMetrics());
    }
    public static int dip2px(Context context, int valueDp)
    {
        return  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, valueDp, context.getResources().getDisplayMetrics());
    }
}
