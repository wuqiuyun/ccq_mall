package per.sue.gear2.widget.titlebar;

import android.widget.AdapterView;


public class MenuItem {


    private int[] menuImgs;
    private String[] menuTexts;
    private AdapterView.OnItemClickListener itemClickListener;

    public MenuItem( int[] menuImgs, String[] menuTexts, AdapterView.OnItemClickListener itemClickListener) {
        super();

        this.menuImgs = menuImgs;
        this.menuTexts = menuTexts;
        this.itemClickListener = itemClickListener;
    }



    public int[] getMenuImgs() {
        return menuImgs;
    }

    public void setMenuImgs(int[] menuImgs) {
        this.menuImgs = menuImgs;
    }

    public AdapterView.OnItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(AdapterView.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public String[] getMenuTexts() {
        return menuTexts;
    }

    public void setMenuTexts(String[] menuTexts) {
        this.menuTexts = menuTexts;
    }
}
