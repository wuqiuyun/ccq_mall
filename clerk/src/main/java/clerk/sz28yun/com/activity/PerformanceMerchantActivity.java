package clerk.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import clerk.sz28yun.com.R;
import clerk.sz28yun.com.adapter.PerformanceMerchantAdapter;
import clerk.sz28yun.com.base.CCJActivity;
import clerk.sz28yun.com.bean.HomeMerchantBean;
import clerk.sz28yun.com.bean.PerformanceMerchantBean;
import clerk.sz28yun.com.bean.PerformanceMerchantResult;
import clerk.sz28yun.com.presenter.PerformanceMerchantPresenter;
import clerk.sz28yun.com.widget.SelectableLinearLayout;

import java.util.ArrayList;

import butterknife.Bind;
import per.sue.gear2.presenter.ListPresenter;
import per.sue.gear2.widget.PageStatusLayout;

/**
 * 商家业绩
 * Created by sue on 2016/11/21.
 */
public class PerformanceMerchantActivity extends CCJActivity implements ListPresenter.ListResultView<PerformanceMerchantResult>, View.OnClickListener {

    TextView performanceCountCcjTv;
    TextView performanceCountMemberTv;
    TextView describeFirstLabelTv;
    TextView describeFirstValueTv;
    TextView describeSecondLabelTv;
    TextView describeSecondtValueTv;
    @Bind(R.id.performance_list_view)
    ListView performanceListView;
    @Bind(R.id.performance_refresh_layout)
    MaterialRefreshLayout performanceRefreshLayout;
    @Bind(R.id.performance_page_status_layout)
    PageStatusLayout performancePageStatusLayout;


    private PerformanceMerchantPresenter performanceMerchantPresenter;
    private PerformanceMerchantAdapter performanceMerchantAdapter;

    private PerformanceMerchantResult.PerformanceMerchantStatistics performanceMerchantStatistics;

    private SelectableLinearLayout lastBottomLinearLayout;
    private boolean isShowCCJType = true;



    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, PerformanceMerchantActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_performance_merchant;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {

        performanceMerchantPresenter = new PerformanceMerchantPresenter(getActivity(), this);
        performanceMerchantAdapter = new PerformanceMerchantAdapter(getActivity());
        performanceListView.setAdapter(performanceMerchantAdapter);
        View topView = LayoutInflater.from(getActivity()).inflate(R.layout.view_performance_merchant_top, null);
        topView.findViewById(R.id.performance_count_ccj_ll).setOnClickListener(this);
        topView.findViewById(R.id.performance_count_member_ll).setOnClickListener(this);
        performanceCountCcjTv = (TextView) topView.findViewById(R.id.performance_count_ccj_tv);
        performanceCountMemberTv = (TextView) topView.findViewById(R.id.performance_count_member_tv);
        describeFirstLabelTv = (TextView) topView.findViewById(R.id.describe_first_label_tv);
        describeFirstValueTv = (TextView) topView.findViewById(R.id.describe_first_value_tv);
        describeSecondLabelTv = (TextView) topView.findViewById(R.id.describe_second_label_tv);
        describeSecondtValueTv = (TextView) topView.findViewById(R.id.describe_secondt_value_tv);
        performanceListView.addHeaderView(topView);

        performanceMerchantPresenter.refreshWithLoading();//请求数据
        performanceRefreshLayout.setLoadMore(true);
        performanceRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                performanceMerchantPresenter.refresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                performanceMerchantPresenter.loadMore();
            }
        });

        performanceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PerformanceMerchantBean bean = (PerformanceMerchantBean) parent.getAdapter().getItem(position);
                if (bean != null) {
                    startActivity(MerchantDetailActivity.startIntent(getActivity(), bean.getStoreName(), bean.getId()));
                }
            }
        });

        changeViewForType((SelectableLinearLayout) findViewById(R.id.performance_count_ccj_ll));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.performance_count_ccj_ll:
                changeViewForType((SelectableLinearLayout) view);
                this.isShowCCJType = true;
                bindViewForStatisticType();
                break;
            case R.id.performance_count_member_ll:
                changeViewForType((SelectableLinearLayout) view);
                this.isShowCCJType = false;
                bindViewForStatisticType();
                break;
        }
    }
    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        performanceRefreshLayout.finishRefreshLoadMore();
        performanceRefreshLayout.finishRefresh();
        if(performanceMerchantPresenter.isWithLoad()){
            performancePageStatusLayout.showFailed(message, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    performanceMerchantPresenter.refreshWithLoading();
                }
            });
        }
    }

    @Override
    public void showLoading() {
        performancePageStatusLayout.showLoading();
    }

    @Override
    public void onSuccessRefresh(PerformanceMerchantResult result) {
        performancePageStatusLayout.showContent();
        performanceRefreshLayout.finishRefreshLoadMore();
        performanceRefreshLayout.finishRefresh();
        if(null == result ){
            result = new PerformanceMerchantResult();
            result.setList(new ArrayList<>());
            result.initialize();
        }
        performanceMerchantAdapter.setList(result.getList());
        bindViewBySatisticData(result.getStatistics());
        bindViewForStatisticType();
    }

    @Override
    public void onSuccessLoadModre(PerformanceMerchantResult result) {
        performanceRefreshLayout.finishRefreshLoadMore();
        performanceRefreshLayout.finishRefresh();
        if(null == result ){
            result = new PerformanceMerchantResult();
            result.setList(new ArrayList<>());
            result.initialize();
        }
        performanceMerchantAdapter.addList(result.getList());
    }

    /**
     * 绑定数据
     * @param performanceMerchantStatistics
     */
    private void bindViewBySatisticData(PerformanceMerchantResult.PerformanceMerchantStatistics performanceMerchantStatistics){
        this.performanceMerchantStatistics = performanceMerchantStatistics;
        performanceCountCcjTv.setText(new StringBuilder(performanceMerchantStatistics.saleGoodsNum + "").append( "张").toString() );
        performanceCountMemberTv.setText(new StringBuilder(performanceMerchantStatistics.inviteMemberCount + "").append( "名").toString() );
    }

    private void bindViewForStatisticType(){
        if(isShowCCJType){
            describeFirstLabelTv.setText(new StringBuilder("未验证" ));
            describeFirstValueTv.setText(new StringBuilder(performanceMerchantStatistics.saleUncheckGoodsNum + "").append( "张") );
            describeSecondLabelTv.setText(new StringBuilder("已验证" ));
            describeSecondtValueTv.setText(new StringBuilder(performanceMerchantStatistics.saleCheckGoodsNum + "").append( "张") );
        }else{
            describeFirstLabelTv.setText(new StringBuilder("注册会员" ));
            describeFirstValueTv.setText(new StringBuilder(performanceMerchantStatistics.inviteMemberCount + "").append( "名") );
            describeSecondLabelTv.setText(new StringBuilder("VIP会员" ));
            describeSecondtValueTv.setText(new StringBuilder(performanceMerchantStatistics.sumInviteVipCount + "").append( "名") );
        }

    }

    private void changeViewForType(SelectableLinearLayout bottomLineLinearLayout){
        if(null != lastBottomLinearLayout){
            lastBottomLinearLayout.setUnSelectType();
        }
        lastBottomLinearLayout = bottomLineLinearLayout;
        lastBottomLinearLayout.setSelectType();
    }




}
