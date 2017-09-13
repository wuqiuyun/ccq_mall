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
import ccj.sz28yun.com.activity.OrderCCJDetailsActivity;
import ccj.sz28yun.com.activity.OrderConsumDetailsActivity;
import ccj.sz28yun.com.adapter.OrderCCJAdapter;
import ccj.sz28yun.com.adapter.OrderConsumeBillAdapter;
import ccj.sz28yun.com.base.CCJFragment;
import ccj.sz28yun.com.bean.OrderCCJBean;
import ccj.sz28yun.com.bean.OrderConsumeBillBean;
import ccj.sz28yun.com.presenter.OrderHistoryCCJPresenter;
import ccj.sz28yun.com.presenter.OrderHistoryConsumPresenter;

/**
 * Created by sue on 2017/1/18.
 */
public class OrderListConsumFragment extends CCJFragment implements OrderHistoryConsumPresenter.OrderHistoryView {


    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;

    OrderHistoryConsumPresenter presenter;
    private OrderConsumeBillAdapter adapter;



    @Override
    public int getLayoutResId() {
        return R.layout.fragment_evaluate_goods;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {

        presenter = new OrderHistoryConsumPresenter(getActivity(), this);

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
        adapter = new OrderConsumeBillAdapter(getActivity());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrderConsumeBillBean orderCCJBean = adapter.getItem(position);
                startActivity(OrderConsumDetailsActivity.startIntent(getActivity(), orderCCJBean));
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
    public void onSuccessRefresh(ArrayList<OrderConsumeBillBean> result) {
        refreshLayout.finishRefresh();
        adapter.setList(result);
    }

    @Override
    public void onSuccessLoadModre(ArrayList<OrderConsumeBillBean> result) {
        refreshLayout.finishRefreshLoadMore();
        adapter.addList(result);
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
