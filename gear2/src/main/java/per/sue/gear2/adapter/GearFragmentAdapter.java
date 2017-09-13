package per.sue.gear2.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.List;

import per.sue.gear2.bean.GearFragmentBean;
import per.sue.gear2.widget.nav.IconPagerAdapter;

/**
 * Created by SUE on 2016/7/21 0021.
 */
public class GearFragmentAdapter extends FragmentStatePagerAdapter implements IconPagerAdapter {

    private static final String TAG = "GearFragmentAdapter";

    private List<GearFragmentBean> list;

    public GearFragmentAdapter(FragmentManager fm, List<GearFragmentBean> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        Log.e(TAG, "getItem()" + "position=" + position);
        Fragment fragment = null;

        try {
            fragment   = (Fragment) this.list.get(position).cls.newInstance();
            fragment.setArguments(this.list.get(position).bundle);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fragment;
    }



    @Override
    public int getIconResId(int index) {
        return this.list.get(index).imageResId;
    }

    public int getCount() {
        return this.list.size();
    }



    @Override
    public CharSequence getPageTitle(int position) {
        return this.list.get(position).title;
    }


}
