package clerk.sz28yun.com.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import clerk.sz28yun.com.R;
import clerk.sz28yun.com.activity.PerformanceDetActivity;
import clerk.sz28yun.com.activity.PerformanceMerchantActivity;
import clerk.sz28yun.com.adapter.PerformanceDayAdapter;
import clerk.sz28yun.com.adapter.PerformanceMonAdapter;
import clerk.sz28yun.com.base.CCJFragment;
import clerk.sz28yun.com.bean.PerformanceDayBean;
import clerk.sz28yun.com.bean.PerformanceMonBean;
import clerk.sz28yun.com.presenter.PerformanceDayPresenter;
import clerk.sz28yun.com.widget.SelectableLinearLayout;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import per.sue.gear2.controll.GearDateViewController;
import per.sue.gear2.dialog.GearCustomDialog;
import per.sue.gear2.utils.ToastUtils;
import per.sue.gear2.utils.date.DateStyle;
import per.sue.gear2.utils.date.DateUtils;
import per.sue.gear2.widget.PageStatusLayout;
import per.sue.gear2.widget.header.HeaderScrollHelper;
import per.sue.gear2.widget.header.HeaderViewPager;

/**
 * 我的业绩
 * Created by sue on 2016/11/18.
 */
public class MainPerfermanceFragment extends CCJFragment implements PerformanceDayPresenter.PerformanceDayResultView {

    @Bind(R.id.performance_merchant_tv)
    TextView performanceMerchantTv;
    @Bind(R.id.performance_rg)
    RadioGroup performanceRg;
    @Bind(R.id.performance_money_vip_tv)
    TextView performanceMoneyVipTv;
    @Bind(R.id.performance_money_ccj_tv)
    TextView performanceMoneyCcjTv;
    @Bind(R.id.performance_money_merchant_tv)
    TextView performanceMoneyMerchantTv;
    @Bind(R.id.performance_money_expand_tv)
    TextView performanceMoneyExpandTv;
    @Bind(R.id.performance_list_view)
    ListView performanceListView;
    @Bind(R.id.performance_refresh_layout)
    MaterialRefreshLayout refreshLayout;
//    @Bind(R.id.performance_page_status_layout)
//    PageStatusLayout performancePageStatusLayout;
//    @Bind(R.id.performance_header_view)
//    HeaderViewPager headerViewPager;

    @Bind(R.id.day_head_value_tv)
    TextView dayHeadValueTV;

    @Bind(R.id.performance_month_onther_rb)
    RadioButton monthOtherRb;


    PerformanceDayPresenter performancePresenter;

    PerformanceDayAdapter performanceDayAdapter;
    PerformanceMonAdapter performanceMonAdapter;

    private String typeName = "获得VIP";
    private int typeFunds = 1;
    private String dateDescribe = "当天";
    private String day;

    private SelectableLinearLayout lastBottomLineLinearLayout;

//    private List<PerformanceMonBean> performanceMonlist;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_performance;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DATE) + "";

        showProgressDialog("加载中");
//        performanceMonlist = new ArrayList<PerformanceMonBean>();
        performancePresenter = new PerformanceDayPresenter(getActivity(), this);
        performancePresenter.setDay(day);

//        performancePresenter.refreshWithLoading();
        changeViewForType((SelectableLinearLayout) getView().findViewById(R.id.performance_money_vip_ll));

//        headerViewPager.setCurrentScrollableContainer(new HeaderScrollHelper.ScrollableContainer() {
//            @Override
//            public View getScrollableView() {
//                return performanceListView;
//            }
//        });

        refreshLayout.setLoadMore(true);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                showProgressDialog("加载中");
                performancePresenter.refresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                performancePresenter.loadMore();
            }
        });


        refreshLayout.setLoadMore(true);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                performancePresenter.refresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                performancePresenter.loadMore();
            }
        });

        performanceDayAdapter = new PerformanceDayAdapter(getActivity());
        performanceMonAdapter = new PerformanceMonAdapter(getActivity());
        performanceListView.setAdapter(performanceDayAdapter);

        performanceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if ("当天".equals(dateDescribe)) {
                } else {
                    PerformanceMonBean bean = (PerformanceMonBean) parent.getAdapter().getItem(position);
                    startActivity(PerformanceDetActivity.startIntent(getActivity(), dateDescribe, typeName, typeFunds, DateUtils.getDate(bean.getAddtime() * 1000, DateStyle.YYYY_MM_DD_EN)));
                }
            }
        });


        performanceRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.performance_day_rb://当天
                        showProgressDialog("加载中");
                        getView().findViewById(R.id.performance_mon_head).setVisibility(View.GONE);
                        getView().findViewById(R.id.performance_day_head).setVisibility(View.VISIBLE);
                        performanceListView.setAdapter(performanceDayAdapter);
                        performancePresenter.setQueryByMon(false);
                        performancePresenter.setDay(day);
                        performancePresenter.initialize();
                        performancePresenter.refresh();
                        dateDescribe = "当天";
                        break;
                    case R.id.performance_month_rb://本月
                        showProgressDialog("加载中");
                        getView().findViewById(R.id.performance_mon_head).setVisibility(View.VISIBLE);
                        getView().findViewById(R.id.performance_day_head).setVisibility(View.GONE);
                        performanceListView.setAdapter(performanceMonAdapter);
                        performancePresenter.setQueryByMon(true);
                        performancePresenter.initialize();
                        performancePresenter.refresh();
                        dateDescribe = "本月";
                        break;
                    case R.id.performance_month_onther_rb://其他月份
                        getView().findViewById(R.id.performance_mon_head).setVisibility(View.VISIBLE);
                        getView().findViewById(R.id.performance_day_head).setVisibility(View.GONE);
                        performanceListView.setAdapter(performanceMonAdapter);
                        //performancePresenter.setQueryByMon(true);
                        //performancePresenter.refresh();
                        break;
                    default:
                        break;
                }
            }
        });

        monthOtherRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTime(v);
            }
        });
        performancePresenter.refresh();
    }

    @OnClick({R.id.performance_money_vip_ll, R.id.performance_money_ccj_ll, R.id.performance_money_merchant_ll, R.id.performance_money_expand_ll, R.id.performance_merchant_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.performance_money_vip_ll:
                showProgressDialog("加载中");
                changeViewForType((SelectableLinearLayout) view);
                dayHeadValueTV.setText("获得VIP");
                typeName = "获得VIP";
                typeFunds = 1;
                performancePresenter.setType(1);
                performancePresenter.refresh();
                break;
            case R.id.performance_money_ccj_ll:
                showProgressDialog("加载中");
                changeViewForType((SelectableLinearLayout) view);
                dayHeadValueTV.setText("餐餐券");
                typeName = "餐餐抢券";
                typeFunds = 2;
                performancePresenter.setType(2);
                performancePresenter.refresh();
                break;
            case R.id.performance_money_merchant_ll:
                showProgressDialog("加载中");
                changeViewForType((SelectableLinearLayout) view);
                dayHeadValueTV.setText("获得招商");
                typeName = "获得招商";
                typeFunds = 3;
                performancePresenter.setType(3);
                performancePresenter.refresh();
                break;
            case R.id.performance_money_expand_ll:
                showProgressDialog("加载中");
                changeViewForType((SelectableLinearLayout) view);
                dayHeadValueTV.setText("推广奖金");
                typeName = "推广奖金";
                typeFunds = 4;
                performancePresenter.setType(4);
                performancePresenter.refresh();
                break;
            case R.id.performance_merchant_tv:
                startActivity(PerformanceMerchantActivity.startIntent(getActivity()));
                break;
            default:
                break;
        }
    }

    private void changeViewForType(SelectableLinearLayout bottomLineLinearLayout) {
        if (null != lastBottomLineLinearLayout) {
            lastBottomLineLinearLayout.setUnSelectType();
        }
        lastBottomLineLinearLayout = bottomLineLinearLayout;
        lastBottomLineLinearLayout.setSelectType();
    }

    @Override
    public void onSuccessLoadModre(ArrayList<PerformanceDayBean> result) {
        dismissProgressDialog();
        refreshLayout.finishRefreshLoadMore();
        refreshLayout.finishRefresh();
        if (null != result && result.size() > 0) {
            performanceDayAdapter.addList(result);
        }

    }

    @Override
    public void onSuccessMonRefresh(ArrayList<PerformanceMonBean> result) {
        dismissProgressDialog();
        refreshLayout.finishRefreshLoadMore();
        refreshLayout.finishRefresh();
        if (null == result) {
            result = new ArrayList<>();
        }
        performanceMonAdapter.setList(result);
//        performanceMonlist = result;

    }

    @Override
    public void onSuccessMonLoadModre(ArrayList<PerformanceMonBean> result) {
        dismissProgressDialog();
        refreshLayout.finishRefreshLoadMore();
        refreshLayout.finishRefresh();
        if (null != result && result.size() > 0) {
//            result = new ArrayList<>();
            performanceMonAdapter.addList(result);
//            performanceMonlist.addAll(result);
        }

    }

    @Override
    public void onSuccessDayRefresh(ArrayList<PerformanceDayBean> result) {
        dismissProgressDialog();
        refreshLayout.finishRefreshLoadMore();
        refreshLayout.finishRefresh();
        if (null == result) {
            result = new ArrayList<>();
        }
        performanceDayAdapter.setList(result);
    }

    @Override
    public void onSuccessDayLoadModre(ArrayList<PerformanceDayBean> result) {
        dismissProgressDialog();
        refreshLayout.finishRefreshLoadMore();
        refreshLayout.finishRefresh();
        if (null != result && result.size() > 0) {
            //result = new ArrayList<>();
            performanceDayAdapter.addList(result);
        }

    }


    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        dismissProgressDialog();
        ToastUtils.showError(message, getActivity());
        refreshLayout.finishRefreshLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    public void onSuccessRefresh(ArrayList<PerformanceDayBean> result) {
        dismissProgressDialog();
        refreshLayout.finishRefreshLoadMore();
        refreshLayout.finishRefresh();
        if (null == result) {
            result = new ArrayList<>();
        }
        performanceDayAdapter.setList(result);

    }

    @Override
    public void onSuccessStatic(String currentVip, String currentGoods, String currentMerchants, String currentPromote) {
        dismissProgressDialog();
        performanceMoneyVipTv.setText(currentVip);
        performanceMoneyCcjTv.setText(currentGoods);
        performanceMoneyMerchantTv.setText(currentMerchants);
        performanceMoneyExpandTv.setText(currentPromote);
    }


    /**
     * 选择时间
     *
     * @param view
     */
    private void chooseTime(View view) {
        long currernt = new Date().getTime();
        if (null != monthOtherRb.getTag() && (monthOtherRb.getTag() instanceof Long)) {
            currernt = (long) monthOtherRb.getTag();
        }
        final GearDateViewController gearDateViewController = new GearDateViewController(getActivity(), currernt);
        gearDateViewController.setDateStyle(GearDateViewController.DateStyle.YEAR_MONTH);
        GearCustomDialog gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .setContentView(gearDateViewController.getContentView())
                //.setTitle("选择查询时间")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        showProgressDialog("加载中");
                        long dateTime = gearDateViewController.getSelectDate().getTime();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(dateTime);
                        monthOtherRb.setTag(dateTime);
                        monthOtherRb.setText(DateUtils.getDate(dateTime, DateStyle.YYYY_MM_EN));
                        dateDescribe = DateUtils.getDate(dateTime, DateStyle.YYYY_MM_EN);
                        performancePresenter.setQueryByMon(true);
                        performancePresenter.setDate(calendar.get(Calendar.YEAR) + "", (calendar.get(Calendar.MONTH) + 1) + "");
                        performancePresenter.refresh();

                        // monthOtherRb.setSelected(true);
                        //getView().findViewById(R.id.performance_mon_head).setVisibility(View.VISIBLE);
                        //getView().findViewById(R.id.performance_day_head).setVisibility(View.GONE);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .create();

        gearCustomDialog.show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        performancePresenter.destroy();
    }
}
