package per.sue.gear2.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.TextView;

/**
 * Created by SUE on 2016/7/22 0022.
 */
public class GearCheckTextView extends TextView implements Checkable {


    public GearCheckTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GearCheckTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setChecked(boolean checked) {
        setSelected(checked);
    }

    @Override
    public boolean isChecked() {
        return isSelected();
    }

    @Override
    public void toggle() {

    }
}
