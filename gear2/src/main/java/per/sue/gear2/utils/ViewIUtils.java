package per.sue.gear2.utils;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by SUE on 2016/8/4 0004.
 */
public class ViewIUtils {

    public static  <T extends View> T findViewById(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}
