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
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.fragment.EvaluateGoodsFragment;
import ccj.sz28yun.com.fragment.EvaluateMerchantFragment;
import per.sue.gear2.adapter.GearFragmentAdapter;
import per.sue.gear2.bean.GearFragmentBean;

/**
 * Created by sue on 2017/1/18.
 */
public class EvaluateRepostActivity extends CCJActivity {

    @Bind(R.id.type_all_rb)
    RadioButton typeAllRb;
    @Bind(R.id.type_goods_rb)
    RadioButton typeGoodsRb;
    @Bind(R.id.type_rg)
    RadioGroup typeRg;
    @Bind(R.id.main_viewpager)
    ViewPager mainViewpager;
    private boolean isMerchant;

    public static Intent startIntent(Context context, boolean isMerchant) {
        Intent intent = new Intent(context, EvaluateRepostActivity.class);
        intent.putExtra("isMerchant", isMerchant);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        hasTitleBar = false;
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_evaluate_repost;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        isMerchant = getIntent().getBooleanExtra("isMerchant", true);
        mainViewpager.setAdapter(new GearFragmentAdapter(getSupportFragmentManager(), getTextTabFragment()));

        typeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.type_all_rb:
                        if (mainViewpager.getCurrentItem() != 0)
                            mainViewpager.setCurrentItem(0, false);
                        break;
                    case R.id.type_goods_rb:
                        if (mainViewpager.getCurrentItem() != 1)
                            mainViewpager.setCurrentItem(1, false);
                        break;


                }
            }
        });
        mainViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        typeRg.check(R.id.type_all_rb);
                        break;
                    case 1:
                        typeRg.check(R.id.type_goods_rb);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private List<GearFragmentBean> getTextTabFragment() {
        List<GearFragmentBean> list = new ArrayList<>();
        if (isMerchant) {
            list.add(new GearFragmentBean(EvaluateMerchantFragment.class, EvaluateMerchantFragment.getBundle(4), "已回复", 0));
            list.add(new GearFragmentBean(EvaluateMerchantFragment.class, EvaluateMerchantFragment.getBundle(5), "未回复", 0));
        } else {
            list.add(new GearFragmentBean(EvaluateGoodsFragment.class, EvaluateGoodsFragment.getBundle(4), "已回复", 0));
            list.add(new GearFragmentBean(EvaluateGoodsFragment.class, EvaluateGoodsFragment.getBundle(5), "未回复", 0));
        }

        return list;
    }


    @OnClick(R.id.back_btn)
    public void onClick() {
        finish();
    }
}
