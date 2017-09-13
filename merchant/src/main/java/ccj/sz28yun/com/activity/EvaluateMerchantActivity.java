package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.EvaluateStatisticBean;
import ccj.sz28yun.com.fragment.EvaluateMerchantFragment;
import ccj.sz28yun.com.presenter.EvaluateStatisicPresenter;
import per.sue.gear2.adapter.GearFragmentAdapter;
import per.sue.gear2.bean.GearFragmentBean;
import per.sue.gear2.widget.nav.GearTabTopPageIndicator;

/**
 * 商店评论
 * Created by sue on 2017/1/18.
 */
public class EvaluateMerchantActivity extends CCJActivity implements EvaluateStatisicPresenter.EvaluateStatisicView {

    @Bind(R.id.tab_pager_indicator)
    GearTabTopPageIndicator tabPagerIndicator;
    @Bind(R.id.main_viewpager)
    ViewPager mainViewpager;

    EvaluateStatisicPresenter evaluateStatisicPresenter;

    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, EvaluateMerchantActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_evaluate_merchant;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        evaluateStatisicPresenter = new EvaluateStatisicPresenter(getActivity(), this);
        evaluateStatisicPresenter.setType(1);
        evaluateStatisicPresenter.requestStatisticData();

    }

    private List<GearFragmentBean> getTextTabFragmentb(EvaluateStatisticBean bean) {
        List<GearFragmentBean> list = new ArrayList<>();
        list.add(new GearFragmentBean(EvaluateMerchantFragment.class, EvaluateMerchantFragment.getBundle(0), "全部", 0));
        list.add(new GearFragmentBean(EvaluateMerchantFragment.class, EvaluateMerchantFragment.getBundle(1), String.format("好评(%s)", bean.getEvaGoods()), 0));
        list.add(new GearFragmentBean(EvaluateMerchantFragment.class, EvaluateMerchantFragment.getBundle(2), String.format("中评(%s)", bean.getEvaPosi()), 0));
        list.add(new GearFragmentBean(EvaluateMerchantFragment.class, EvaluateMerchantFragment.getBundle(3),  String.format("差评(%s)", bean.getEvaBad()), 0));
        return list;
    }


    @Override
    public void onSuccessStatistic(EvaluateStatisticBean bean) {
        mainViewpager.setAdapter(new GearFragmentAdapter(getSupportFragmentManager(), getTextTabFragmentb(bean)));
        tabPagerIndicator.setViewPager(mainViewpager);
        tabPagerIndicator.setBorderRadius(100);
        tabPagerIndicator.setSelectBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }
}
