package per.sue.gear2.bean;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by SUE on 2016/7/21 0021.
 */
public class GearFragmentBean {

    public Class cls;
    public String title;
    public int imageResId;
    public Bundle bundle;


    public GearFragmentBean(Class cls,Bundle bundle, String title, int imageResId) {
        this.cls = cls;
        this.bundle = bundle;
        this.title = title;
        this.imageResId = imageResId;
    }


}
