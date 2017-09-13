package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.fragment.CouponMealsListFragment;
import per.sue.gear2.adapter.GearFragmentAdapter;
import per.sue.gear2.bean.GearFragmentBean;
import per.sue.gear2.widget.nav.GearTabTopPageIndicator;

/**
 * 发布套餐券
 * Created by sue on 2016/12/19.
 */
public class CouponMealsActivity extends CCJActivity {


    @Bind(R.id.tab_pager_indicator)
    GearTabTopPageIndicator tabPagerIndicator;
    @Bind(R.id.main_viewpager)
    ViewPager mainViewpager;

    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, CouponMealsActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_coupon_meals;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {

        getHeadBarView().addRightTextItem("新增", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CouponMealsPublishActivity.startIntent(getActivity()));
//                startActivityForResult(CouponMealsPublishActivity.startIntent(getActivity()),100);

            }
        });
        mainViewpager.setAdapter(new GearFragmentAdapter(getSupportFragmentManager(), getTextTabFragmentb()));
        tabPagerIndicator.setViewPager(mainViewpager);
        tabPagerIndicator.setBorderRadius(100);
        tabPagerIndicator.setSelectBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    private List<GearFragmentBean> getTextTabFragmentb() {
        List<GearFragmentBean> list = new ArrayList<>();
        list.add(new GearFragmentBean(CouponMealsListFragment.class, CouponMealsListFragment.getBundle(null), "全部", 0));
        list.add(new GearFragmentBean(CouponMealsListFragment.class, CouponMealsListFragment.getBundle("2"), "已上线", 0));
        list.add(new GearFragmentBean(CouponMealsListFragment.class, CouponMealsListFragment.getBundle("1"), "待上线", 0));
        list.add(new GearFragmentBean(CouponMealsListFragment.class, CouponMealsListFragment.getBundle("3"), "已下线", 0));
        return list;
    }

}
