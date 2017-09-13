package clerk.sz28yun.com.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import clerk.sz28yun.com.R;
import clerk.sz28yun.com.adapter.PerformanceChildAdapter;
import clerk.sz28yun.com.adapter.PerformanceChildMerchantAdapter;
import clerk.sz28yun.com.adapter.PerformanceChildStoreAdapter;
import clerk.sz28yun.com.base.CCJActivity;
import clerk.sz28yun.com.bean.PerformanceChildMemberBean;
import clerk.sz28yun.com.bean.PerformanceChildMemberResult;
import clerk.sz28yun.com.bean.PerformanceChildMerchantBean;
import clerk.sz28yun.com.presenter.PerformanceChildPresenter;
import clerk.sz28yun.com.widget.SelectableLinearLayout;

import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;
import per.sue.gear2.controll.GearDateViewController;
import per.sue.gear2.dialog.GearCustomDialog;
import per.sue.gear2.utils.date.DateStyle;
import per.sue.gear2.utils.date.DateUtils;
import per.sue.gear2.widget.PageStatusLayout;
import per.sue.gear2.widget.header.HeaderScrollHelper;
import per.sue.gear2.widget.header.HeaderViewPager;

/**
 * 子账号业绩
 * Created by sue on 2016/11/21.
 */
public class PerformanceChildActivity extends CCJActivity implements PerformanceChildPresenter.PerformanceChildView {


    @Bind(R.id.performance_month_rb)
    RadioButton performanceMonthRb;
    @Bind(R.id.performance_month_onther_rb)
    RadioButton performanceMonthOntherRb;
    @Bind(R.id.performance_rg)
    RadioGroup performanceRg;
    @Bind(R.id.performance_count_member_tv)
    TextView performanceCountMemberTv;
    @Bind(R.id.performance_count_vip_tv)
    TextView performanceCountVipTv;

    @Bind(R.id.performance_count_merchant_submit_tv)
    TextView performanceCountMerchantSubmitTv;
    @Bind(R.id.performance_count_merchant_enther_tv)
    TextView performanceCountMerchantEntherTv;
    @Bind(R.id.performance_list_view)
    ListView performanceListView;
    @Bind(R.id.performance_refresh_layout)
    MaterialRefreshLayout performanceRefreshLayout;
    @Bind(R.id.performance_page_status_layout)
    PageStatusLayout performancePageStatusLayout;
    @Bind(R.id.performance_header_view)
    HeaderViewPager performanceHeaderView;
    private String childId;
    private PerformanceChildPresenter performanceChildPresenter;
    private PerformanceChildAdapter performanceChildAdapter;
    private PerformanceChildStoreAdapter performanceChildStoreAdapter;
    private PerformanceChildMerchantAdapter performanceChildMerchantAdapter;
    private PerformanceChildMemberResult.PerformanceChildStatistic performanceChildStatistic;
    private String type = "1";

    private SelectableLinearLayout lastBottomLineLinearLayout;

    public static Intent startIntent(Context context, String name, String childId) {
        Intent intent;
        intent = new Intent(context, PerformanceChildActivity.class);
        intent.putExtra("childId", childId);
        intent.putExtra("name", name);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_performance_child;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        this.childId = getIntent().getStringExtra("childId");

        String name = getIntent().getStringExtra("name");
        setBarTitle(name + "业绩");
        getHeadBarView().addRightTextItem("业务", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(BusinessChildActivity.startIntent(getActivity(), name, childId));
            }
        });

        performanceHeaderView.setCurrentScrollableContainer(new HeaderScrollHelper.ScrollableContainer() {
            @Override
            public View getScrollableView() {
                return performanceListView;
            }
        });

        performanceChildPresenter = new PerformanceChildPresenter(getActivity(), this);
        performanceChildPresenter.setMenberId(childId);
        performanceChildPresenter.setType(type);
        performanceChildAdapter = new PerformanceChildAdapter(getActivity());
        performanceChildStoreAdapter = new PerformanceChildStoreAdapter(getActivity());
        performanceChildMerchantAdapter = new PerformanceChildMerchantAdapter(getActivity());
        performanceListView.setAdapter(performanceChildAdapter);

        performanceChildStoreAdapter.setOnChildStoreDetailListener(new PerformanceChildStoreAdapter.OnChildStoreDetailListener() {
            @Override
            public void onClickDetail(PerformanceChildMemberBean bean) {

                findViewById(R.id.member_detailstore_ll).setVisibility(View.VISIBLE);
                findViewById(R.id.member_viphead_ll).setVisibility(View.GONE);
                findViewById(R.id.member_storehead_ll).setVisibility(View.GONE);
                performanceListView.setAdapter(performanceChildMerchantAdapter);
                performanceChildPresenter.setQueryMember(false);
                performanceChildPresenter.setData(bean.getDate());
                performanceChildPresenter.refresh();
            }
        });

        performanceRefreshLayout.setLoadMore(true);
        performanceRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                performanceChildPresenter.refresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                performanceChildPresenter.loadMore();
            }
        });

        performanceMonthOntherRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTime(v);
            }
        });
        performanceMonthRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performanceChildPresenter.setCurrentTime(System.currentTimeMillis());
                performanceChildPresenter.refresh();
            }
        });

        changeViewForType((SelectableLinearLayout)findViewById(R.id.select_member_count_ll));
        performanceChildPresenter.refreshWithLoading();//请求数据
    }


    @OnClick({R.id.statistic_count_member_ll, R.id.statistic_count_merchant_ll, R.id.member_detailstore_tv})
    public void onClick(View view){
        switch(view.getId()){
            case R.id.statistic_count_member_ll:
                findViewById(R.id.member_viphead_ll).setVisibility(View.VISIBLE);
                findViewById(R.id.member_storehead_ll).setVisibility(View.GONE);
                findViewById(R.id.member_detailstore_ll).setVisibility(View.GONE);

                performanceListView.setAdapter(performanceChildAdapter);
                type = "1";
                performanceChildPresenter.setType(type);
                performanceChildPresenter.setQueryMember(true);
                performanceChildPresenter.refresh();
                changeViewForType((SelectableLinearLayout)findViewById(R.id.select_member_count_ll));
                break;
            case  R.id.statistic_count_merchant_ll:

                findViewById(R.id.member_storehead_ll).setVisibility(View.VISIBLE);
                findViewById(R.id.member_viphead_ll).setVisibility(View.GONE);
                findViewById(R.id.member_detailstore_ll).setVisibility(View.GONE);
//                performanceListView.setAdapter(performanceChildMerchantAdapter);
                type = "2";
                performanceChildPresenter.setType(type);
                performanceChildPresenter.setQueryMember(true);
                performanceChildPresenter.refresh();
                performanceListView.setAdapter(performanceChildStoreAdapter);
                changeViewForType((SelectableLinearLayout)findViewById(R.id.select_merchant_count_ll));
                break;
            case  R.id.member_detailstore_tv:

                findViewById(R.id.member_storehead_ll).setVisibility(View.VISIBLE);
                findViewById(R.id.member_viphead_ll).setVisibility(View.GONE);
                findViewById(R.id.member_detailstore_ll).setVisibility(View.GONE);
//                performanceListView.setAdapter(performanceChildMerchantAdapter);
//                type = "2";
//                performanceChildPresenter.setType(type);
//                performanceChildPresenter.setQueryMember(true);
//                performanceChildPresenter.refresh();
                performanceListView.setAdapter(performanceChildStoreAdapter);
                changeViewForType((SelectableLinearLayout)findViewById(R.id.select_merchant_count_ll));
                break;
            default:
                break;
        }
    }

    private void changeViewForType(SelectableLinearLayout bottomLineLinearLayout){
        if(null != lastBottomLineLinearLayout){
            lastBottomLineLinearLayout.setUnSelectType();
        }
        lastBottomLineLinearLayout = bottomLineLinearLayout;
        lastBottomLineLinearLayout.setSelectType();
    }


    @Override
    public void showLoading() {
        performancePageStatusLayout.showLoading();
    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        performanceRefreshLayout.finishRefreshLoadMore();
        performanceRefreshLayout.finishRefresh();

        if(performanceChildPresenter.isWithLoad()){
            performancePageStatusLayout.showFailed(message, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    performanceChildPresenter.refreshWithLoading();
                }
            });
        }
    }
    @Override
    public void onSuccessStatistic(PerformanceChildMemberResult.PerformanceChildStatistic statistic) {
        bindViewBySatisticData(statistic);
    }

    @Override
    public void onSuccessMerchantRefresh(ArrayList<PerformanceChildMerchantBean> result) {
        performanceRefreshLayout.finishRefresh();
        performanceChildMerchantAdapter.setList(result);
    }

    @Override
    public void onSuccesssMerchantLoadModre(ArrayList<PerformanceChildMerchantBean> result) {
        performanceRefreshLayout.finishRefreshLoadMore();
        performanceChildMerchantAdapter.addList(result);
    }

    @Override
    public void onSuccessRefresh(ArrayList<PerformanceChildMemberBean> list) {

        performanceRefreshLayout.finishRefresh();
        if(performanceChildPresenter.isWithLoad()){
            performancePageStatusLayout.showContent();
        }

//        performanceChildAdapter.setList(list);
        if ("1".equals(type)){
            performanceChildAdapter.setList(list);
        }else if ("2".equals(type)){
            performanceChildStoreAdapter.setList(list);
        }
    }

    @Override
    public void onSuccessLoadModre(ArrayList<PerformanceChildMemberBean> list) {
        performanceRefreshLayout.finishRefreshLoadMore();

        if ("1".equals(type)){
            performanceChildAdapter.addList(list);
        }else if ("2".equals(type)){
            performanceChildStoreAdapter.addList(list);
        }
    }

    /**
     * 绑定数据
     * @param performanceMerchantStatistics
     */
    private void bindViewBySatisticData(PerformanceChildMemberResult.PerformanceChildStatistic performanceMerchantStatistics){
        this.performanceChildStatistic = performanceMerchantStatistics;
        performanceCountVipTv.setText(new StringBuilder("注册会员: ").append(performanceMerchantStatistics.vipMemberNum + "").append( "名").toString() );
        performanceCountMemberTv.setText(new StringBuilder("VIP会员: ").append(performanceMerchantStatistics.memberNum + "").append( "名").toString() );

        performanceCountMerchantSubmitTv.setText(new StringBuilder("提交商家: ").append(performanceMerchantStatistics.addStoreNum + "").append( "家").toString() );
        performanceCountMerchantEntherTv.setText(new StringBuilder("入驻商家: ").append(performanceMerchantStatistics.storeNum + "").append( "家").toString() );
    }


    /**
     * 选择时间
     * @param view
     */
    private void chooseTime(View view){
        long currernt = new Date().getTime();
        if(null != performanceMonthOntherRb.getTag() && (performanceMonthOntherRb.getTag()  instanceof  Long)){
            currernt = (long)performanceMonthOntherRb.getTag();
        }
        final GearDateViewController gearDateViewController = new GearDateViewController(getActivity(), currernt);
        gearDateViewController.setDateStyle(GearDateViewController.DateStyle.YEAR_MONTH);
        GearCustomDialog gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .setContentView(gearDateViewController.getContentView())
               // .setTitle("选择查询时间")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        long dateTime = gearDateViewController.getSelectDate().getTime();
                        performanceMonthOntherRb.setTag(dateTime);
                        performanceMonthOntherRb.setText(DateUtils.getDate(dateTime , DateStyle.YYYY_MM_EN));
                        performanceChildPresenter.setCurrentTime(dateTime);
                        performanceChildPresenter.refresh();
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


}
