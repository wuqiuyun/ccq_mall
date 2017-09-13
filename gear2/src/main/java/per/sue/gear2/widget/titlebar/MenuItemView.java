package per.sue.gear2.widget.titlebar;

import android.content.Context;
import android.widget.ImageView;




public class MenuItemView extends ImageView {

    private MenuItem menuItem;

    public MenuItemView(Context context) {
        super(context);
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public void setMenuItemImageResource(int resId) {
        setImageResource(resId);
    }
}
