package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.FinanceStatisticBean;
import ccj.sz28yun.com.fragment.FinanceApplyDepositFragment;
import ccj.sz28yun.com.fragment.FinanceTypeDepositsFragment;
import ccj.sz28yun.com.fragment.FinanceTypeFreezeFragment;
import ccj.sz28yun.com.fragment.FinanceTypeMonthFragment;
import ccj.sz28yun.com.widget.SelectableLinearLayout;
import per.sue.gear2.TestFragment;
import per.sue.gear2.adapter.GearFragmentAdapter;
import per.sue.gear2.bean.GearFragmentBean;
import per.sue.gear2.utils.DoubleUtil;
import per.sue.gear2.utils.UnitUtils;

/**
 * Created by sue on 2016/12/19.
 */
public class FinanceTypeActivity extends CCJActivity {

    @Bind(R.id.bar_title_tv)
    TextView titleBarTV;
    @Bind(R.id.funds_total_tv)
    TextView fundsTotalTv;
    @Bind(R.id.funds_balance_tv)
    TextView fundsBalanceTv;
    @Bind(R.id.funds_balance_ll)
    SelectableLinearLayout fundsBalanceLl;
    @Bind(R.id.funds_deposits_tv)
    TextView fundsDepositsTv;
    @Bind(R.id.funds_deposits_ll)
    SelectableLinearLayout fundsDepositsLl;
    @Bind(R.id.funds_freeze_tv)
    TextView fundsFreezeTv;
    @Bind(R.id.funds_freeze_ll)
    SelectableLinearLayout fundsFreezeLl;


    private SelectableLinearLayout lastSelectableLinearLayout;

    private int type ;//资金类型(1推广提成 2招商奖金 5账户余额 7vip会员费 8餐餐抢费)

    public static Intent startIntent(Context context, int type) {
        Intent intent = new Intent(context, FinanceTypeActivity.class);
        intent.putExtra("type", type);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        hasTitleBar = false;
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_finance_type;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {



        findViewById(R.id.title_back_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        fundsBalanceLl.setBottomLineSelectedHeight(UnitUtils.px2dip(getActivity(), 200));
        fundsBalanceLl.setSelectTextColor(getResources().getColor(R.color.app_white));
        fundsFreezeLl.setBottomLineSelectedHeight(UnitUtils.px2dip(getActivity(), 200));
        fundsFreezeLl.setSelectTextColor(getResources().getColor(R.color.app_white));
        fundsDepositsLl.setBottomLineSelectedHeight(UnitUtils.px2dip(getActivity(), 200));
        fundsDepositsLl.setSelectTextColor(getResources().getColor(R.color.app_white));

        this.type = getIntent().getIntExtra("type", 1);
        switch (type){
            case 1:
                titleBarTV.setText("推广提成");

                break;
            case 2:
                titleBarTV.setText("招商奖金");
                break;
            case 5:
                titleBarTV.setText("账户余额");
                break;
            case 7:
                titleBarTV.setText("VIP会员费");
                break;
            case 8:
                titleBarTV.setText("餐餐抢费");
                break;
        }

        FinanceTypeMonthFragment fragment1 = new FinanceTypeMonthFragment();
        fragment1.setArguments(FinanceTypeMonthFragment.getBundle(type));
        replaceFragment(R.id.fragment_content, fragment1);
        changeViewForType(null);
    }


    @OnClick({R.id.funds_balance_ll, R.id.funds_deposits_ll, R.id.funds_freeze_ll, R.id.top_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_layout:
                FinanceTypeMonthFragment fragment = new FinanceTypeMonthFragment();
                fragment.setArguments(FinanceTypeMonthFragment.getBundle(type));
                replaceFragment(R.id.fragment_content, fragment);
                changeViewForType(null);
                break;

            case R.id.funds_balance_ll:
                FinanceApplyDepositFragment fragment1 = new FinanceApplyDepositFragment();
                fragment1.setArguments(FinanceApplyDepositFragment.getBundle(type));
                replaceFragment(R.id.fragment_content, fragment1);
                changeViewForType(fundsBalanceLl);
                break;
            case R.id.funds_deposits_ll:
                FinanceTypeDepositsFragment fragment2 = new FinanceTypeDepositsFragment();
                fragment2.setArguments(FinanceTypeDepositsFragment.getBundle(type));
                replaceFragment(R.id.fragment_content, fragment2);
                changeViewForType(fundsDepositsLl);
                break;
            case R.id.funds_freeze_ll:
                FinanceTypeFreezeFragment fragment3 = new FinanceTypeFreezeFragment();
                fragment3.setArguments(FinanceTypeFreezeFragment.getBundle(type));
                replaceFragment(R.id.fragment_content, fragment3);
                changeViewForType(fundsFreezeLl);
                break;
            default:
                break;
        }
    }

    public void changeToDepositsList(){
        FinanceTypeDepositsFragment fragment2 = new FinanceTypeDepositsFragment();
        fragment2.setArguments(FinanceTypeDepositsFragment.getBundle(type));
        replaceFragment(R.id.fragment_content, fragment2);
        changeViewForType(fundsDepositsLl);
    }

    /*
     * 设置统计数据， 因为数据来自可用资金界面，所以改成公共方法给别的界面设置
     */
    public void setStatisticData(FinanceStatisticBean statisticBean) {
        fundsTotalTv.setText(String.format("%s元", DoubleUtil.formatPrice(statisticBean.getTotal())));
        fundsBalanceTv.setText(String.format("%s元", DoubleUtil.formatPrice(statisticBean.getCurrent())));
        fundsDepositsTv.setText(String.format("%s元", DoubleUtil.formatPrice(statisticBean.getTixian())));
        fundsFreezeTv.setText(String.format("%s元", DoubleUtil.formatPrice(statisticBean.getFee())));
    }

    private void changeViewForType(SelectableLinearLayout bottomLineLinearLayout){
        if(null != lastSelectableLinearLayout){
            lastSelectableLinearLayout.setUnSelectType();
        }

        lastSelectableLinearLayout = bottomLineLinearLayout;
        if(null != lastSelectableLinearLayout){
            lastSelectableLinearLayout.setSelectType();
        }

    }

}
