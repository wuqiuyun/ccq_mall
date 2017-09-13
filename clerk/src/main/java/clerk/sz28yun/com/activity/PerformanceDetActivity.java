package clerk.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import clerk.sz28yun.com.R;
import clerk.sz28yun.com.adapter.PerformanceDayAdapter;
import clerk.sz28yun.com.base.CCJActivity;
import clerk.sz28yun.com.bean.PerformanceDayBean;
import clerk.sz28yun.com.bean.PerformanceMonBean;
import clerk.sz28yun.com.presenter.PerformanceDayPresenter;

import java.util.ArrayList;

import butterknife.Bind;
import per.sue.gear2.utils.DoubleUtil;
import per.sue.gear2.utils.date.DateStyle;
import per.sue.gear2.utils.date.DateUtils;
import per.sue.gear2.widget.PageStatusLayout;

/**
 * 业绩详细
 * Created by sue on 2016/11/19.
 */
public class PerformanceDetActivity extends CCJActivity implements PerformanceDayPresenter.PerformanceDayResultView {


    @Bind(R.id.performance_day_rb)
    TextView performanceDayRb;
    @Bind(R.id.type_name_tv)
    TextView typeNameTv;
    @Bind(R.id.money_type_tv)
    TextView moneyTypeTv;
    @Bind(R.id.day_head_value_tv)
    TextView dayHeadValueTv;
    @Bind(R.id.performance_day_head)
    LinearLayout performanceDayHead;
    @Bind(R.id.performance_list_view)
    ListView performanceListView;
    @Bind(R.id.performance_refresh_layout)
    MaterialRefreshLayout performanceRefreshLayout;
    @Bind(R.id.performance_page_status_layout)
    PageStatusLayout performancePageStatusLayout;

    PerformanceDayPresenter performancePresenter;
    PerformanceDayAdapter performanceDayAdapter;

    private int typeFunds;

    public static Intent startIntent(Context context, String dateDescribe , String typeName, int typeFunds, String addTime ) {
        Intent intent = new Intent(context, PerformanceDetActivity.class);
        intent.putExtra("typeName", typeName);
        intent.putExtra("typeFunds", typeFunds);
        intent.putExtra("dateDescribe", dateDescribe);
        intent.putExtra("addTime", addTime);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_performance_det;
    }



    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {

        String typeName = getIntent().getStringExtra("typeName");
        typeFunds = getIntent().getIntExtra("typeFunds",1);
        String dateDescribe =  getIntent().getStringExtra("dateDescribe");
        typeNameTv.setText(typeName);
        dayHeadValueTv.setText(typeName);
        String addTime =  getIntent().getStringExtra("addTime");

//        moneyTypeTv.setText((DoubleUtil.formatPrice(typeFunds)) + "元");
        performanceDayRb.setText(dateDescribe);
        performancePresenter = new PerformanceDayPresenter(getActivity(), this);
        performancePresenter.setType(typeFunds);
        if (!TextUtils.isEmpty(addTime) || addTime != null){
            String[] data = addTime.split("/");
            performancePresenter.setDay(data[2]);
        }
        performanceDayAdapter = new PerformanceDayAdapter(getActivity());
        performanceListView.setAdapter(performanceDayAdapter);
        performancePresenter.refreshWithLoading();



        performanceRefreshLayout.setLoadMore(true);
        performanceRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
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
    }


    @Override
    public void showLoading() {
        //super.showLoading();
        performancePageStatusLayout.showLoading();
    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        performanceRefreshLayout.finishRefreshLoadMore();
        performanceRefreshLayout.finishRefresh();
        if(performancePresenter.isWithLoad()){
            performancePageStatusLayout.showFailed(message, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    performancePresenter.refreshWithLoading();
                }
            });
        }
    }

    @Override
    public void onSuccessRefresh(ArrayList<PerformanceDayBean> result) {
        if(performancePresenter.isWithLoad()){
            performancePageStatusLayout.showContent();
        }

        performanceRefreshLayout.finishRefreshLoadMore();
        performanceRefreshLayout.finishRefresh();
        if(null == result ){
            result = new ArrayList<>();
        }
        performanceDayAdapter.setList(result);

    }

    @Override
    public void onSuccessLoadModre(ArrayList<PerformanceDayBean> result) {
        performanceRefreshLayout.finishRefreshLoadMore();
        performanceRefreshLayout.finishRefresh();
        if(null == result ){
            result = new ArrayList<>();
        }
        performanceDayAdapter.addList(result);
    }

    @Override
    public void onSuccessMonRefresh(ArrayList<PerformanceMonBean> result) {

    }

    @Override
    public void onSuccessMonLoadModre(ArrayList<PerformanceMonBean> result) {

    }

    @Override
    public void onSuccessDayRefresh(ArrayList<PerformanceDayBean> result) {
        if(performancePresenter.isWithLoad()){
            performancePageStatusLayout.showContent();
        }

        performanceRefreshLayout.finishRefreshLoadMore();
        performanceRefreshLayout.finishRefresh();
        if(null == result ){
            result = new ArrayList<>();
        }
        performanceDayAdapter.setList(result);
    }

    @Override
    public void onSuccessDayLoadModre(ArrayList<PerformanceDayBean> result) {
        performanceRefreshLayout.finishRefreshLoadMore();
        performanceRefreshLayout.finishRefresh();
        if(null == result ){
            result = new ArrayList<>();
        }
        performanceDayAdapter.addList(result);
    }

    @Override
    public void onSuccessStatic(String currentVip, String currentGoods, String currentMerchants, String currentPromote) {
        if (typeFunds == 1){
        moneyTypeTv.setText((DoubleUtil.formatPrice(currentVip)) + "元");
        }else if(typeFunds == 2){
            moneyTypeTv.setText((DoubleUtil.formatPrice(currentGoods)) + "元");
        }else if(typeFunds == 3){
            moneyTypeTv.setText((DoubleUtil.formatPrice(currentMerchants)) + "元");
        }else if(typeFunds == 4){
            moneyTypeTv.setText((DoubleUtil.formatPrice(currentPromote)) + "元");
        }else{
            moneyTypeTv.setText("0.00" + "元");
        }
    }

}
