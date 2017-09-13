package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.adapter.FinanceTypeDayAdapter;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.FinanceDayBean;
import ccj.sz28yun.com.presenter.FinanceTypeDayPresenter;

/**
 * Created by sue on 2017/1/4.
 */
public class FinanceTypeDayActivity extends CCJActivity implements FinanceTypeDayPresenter.FinanceTypeDayView {


    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;

    private FinanceTypeDayPresenter financeTypeDayPresenter;
    private FinanceTypeDayAdapter  financeTypeDayAdapter;

    public static Intent startIntent(Context context, int type, String date) {
        Intent intent = new Intent(context, FinanceTypeDayActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("date", date);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_finance_type_day;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        financeTypeDayPresenter = new FinanceTypeDayPresenter(getActivity(), this);
        financeTypeDayAdapter = new FinanceTypeDayAdapter(getActivity());
        listView.setAdapter(financeTypeDayAdapter);
        int type  = getIntent().getIntExtra("type", 1);
        String date = getIntent().getStringExtra("date");
        financeTypeDayPresenter.init(type, date);
        refreshLayout.setLoadMore(true);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                financeTypeDayPresenter.refresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                financeTypeDayPresenter.loadMore();
            }
        });
        financeTypeDayPresenter.refresh();

    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        refreshLayout.finishRefresh();
        refreshLayout.finishRefreshLoadMore();
    }

    @Override
    public void onSuccessRefresh(ArrayList<FinanceDayBean> result) {
        refreshLayout.finishRefresh();
        financeTypeDayAdapter.setList(result);
    }

    @Override
    public void onSuccessLoadModre(ArrayList<FinanceDayBean> result) {
        refreshLayout.finishRefreshLoadMore();
        financeTypeDayAdapter.addList(result);
    }
}
