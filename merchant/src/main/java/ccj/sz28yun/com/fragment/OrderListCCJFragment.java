package ccj.sz28yun.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;

import butterknife.Bind;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.activity.EvaluateReplyActivity;
import ccj.sz28yun.com.activity.OrderCCJDetailsActivity;
import ccj.sz28yun.com.activity.OrderHistoryActivity;
import ccj.sz28yun.com.adapter.EvaluateGoodsAdapter;
import ccj.sz28yun.com.adapter.OrderCCJAdapter;
import ccj.sz28yun.com.base.CCJFragment;
import ccj.sz28yun.com.bean.EvaluateGoodsBean;
import ccj.sz28yun.com.bean.OrderCCJBean;
import ccj.sz28yun.com.bean.OrderStatisticBean;
import ccj.sz28yun.com.presenter.EvaluateGoodsPresenter;
import ccj.sz28yun.com.presenter.OrderHistoryCCJPresenter;

/**
 * Created by sue on 2017/1/18.
 */
public class OrderListCCJFragment extends CCJFragment implements OrderHistoryCCJPresenter.OrderHistoryView {


    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;

    OrderHistoryCCJPresenter presenter;
    private OrderCCJAdapter adapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_evaluate_goods;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {

        presenter = new OrderHistoryCCJPresenter(getActivity(), this);

        refreshLayout.setLoadMore(true);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                presenter.refresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                presenter.loadMore();
            }
        });
        adapter = new OrderCCJAdapter(getActivity());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrderCCJBean orderCCJBean = adapter.getItem(position);
                startActivity(OrderCCJDetailsActivity.startIntent(getActivity(), orderCCJBean));
            }
        });

        presenter.refresh();
    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        dismissProgressDialog();
        refreshLayout.finishRefreshLoadMore();
        refreshLayout.finishRefresh();
    }



    @Override
    public void onSuccessRefresh(ArrayList<OrderCCJBean> result) {
        refreshLayout.finishRefresh();
        adapter.setList(result);
    }

    @Override
    public void onSuccessLoadModre(ArrayList<OrderCCJBean> result) {
        refreshLayout.finishRefreshLoadMore();
        adapter.addList(result);
    }

    @Override
    public void onSuccessStatistic(ArrayList<OrderStatisticBean> list) {
        if(getActivity() instanceof OrderHistoryActivity){
            ((OrderHistoryActivity)getActivity()).onSuccessStatistic(list);
        }
    }

    public void setDate(long time){
        presenter.setDateTime(time);
        presenter.refresh();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}
