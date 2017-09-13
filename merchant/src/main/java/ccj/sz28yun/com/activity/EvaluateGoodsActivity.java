package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.EvaluateStatisticBean;
import ccj.sz28yun.com.fragment.EvaluateGoodsFragment;
import ccj.sz28yun.com.fragment.EvaluateMerchantFragment;
import ccj.sz28yun.com.presenter.EvaluateStatisicPresenter;
import per.sue.gear2.adapter.GearFragmentAdapter;
import per.sue.gear2.bean.GearFragmentBean;

/**
 * Created by sue on 2017/1/18.
 */
public class EvaluateGoodsActivity extends CCJActivity implements EvaluateStatisicPresenter.EvaluateStatisicView {

    @Bind(R.id.type_rg)
    RadioGroup typeRG;
    @Bind(R.id.type_all_rb)
    RadioButton typeAllRb;
    @Bind(R.id.type_goods_rb)
    RadioButton typeGoodsRb;
    @Bind(R.id.type_middle_rb)
    RadioButton typeMiddleRb;
    @Bind(R.id.type_bad_rb)
    RadioButton typeBadRb;
    @Bind(R.id.main_viewpager)
    ViewPager mainViewpager;
    EvaluateStatisicPresenter evaluateStatisicPresenter;

    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, EvaluateGoodsActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_evaluate_goods;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {

        typeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.type_all_rb:
                        if(mainViewpager.getCurrentItem() != 0)
                        mainViewpager.setCurrentItem(0, false);
                        break;
                    case R.id.type_goods_rb:
                        if(mainViewpager.getCurrentItem() != 1)
                        mainViewpager.setCurrentItem(1, false);
                        break;
                    case R.id.type_middle_rb:
                        if(mainViewpager.getCurrentItem() != 2)
                        mainViewpager.setCurrentItem(2, false);
                        break;
                    case R.id.type_bad_rb:
                        if(mainViewpager.getCurrentItem() != 3)
                        mainViewpager.setCurrentItem(3, false);
                        break;

                }
            }
        });

        evaluateStatisicPresenter = new EvaluateStatisicPresenter(getActivity(), this);
        evaluateStatisicPresenter.setType(2);
        evaluateStatisicPresenter.requestStatisticData();
    }
    private List<GearFragmentBean> getTextTabFragmentb(EvaluateStatisticBean bean) {
        List<GearFragmentBean> list = new ArrayList<>();
        list.add(new GearFragmentBean(EvaluateGoodsFragment.class, EvaluateGoodsFragment.getBundle(0), "全部", 0));
        list.add(new GearFragmentBean(EvaluateGoodsFragment.class, EvaluateGoodsFragment.getBundle(1), String.format("好评(%s)", bean.getEvaGoods()), 0));
        list.add(new GearFragmentBean(EvaluateGoodsFragment.class, EvaluateGoodsFragment.getBundle(2), String.format("中评(%s)", bean.getEvaPosi()), 0));
        list.add(new GearFragmentBean(EvaluateGoodsFragment.class, EvaluateGoodsFragment.getBundle(3),  String.format("差评(%s)", bean.getEvaBad()), 0));
        return list;
    }

    @Override
    public void onSuccessStatistic(EvaluateStatisticBean bean) {
        mainViewpager.setAdapter(new GearFragmentAdapter(getSupportFragmentManager(), getTextTabFragmentb(bean)));
        mainViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        typeRG.check(R.id.type_all_rb);
                        break;
                    case 1:
                        typeRG.check(R.id.type_goods_rb);
                        break;
                    case 2:
                        typeRG.check(R.id.type_middle_rb);
                        break;
                    case 3:
                        typeRG.check(R.id.type_bad_rb);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
