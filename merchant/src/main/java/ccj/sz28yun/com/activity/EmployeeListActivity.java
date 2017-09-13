package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;

import butterknife.Bind;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.adapter.EmployeeAdapter;
import ccj.sz28yun.com.adapter.MerchantChainAdapter;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.EmployeeBean;
import ccj.sz28yun.com.bean.MerchantChainBean;
import ccj.sz28yun.com.presenter.EmployeeListPresenter;
import ccj.sz28yun.com.presenter.MerchantChainListPresenter;
import per.sue.gear2.utils.ToastUtils;

/**
 * Created by sue on 2017/1/3.
 */
public class EmployeeListActivity extends CCJActivity implements EmployeeListPresenter.EmployeeListView {


    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;

    private EmployeeListPresenter employeeListPresenter;
    EmployeeAdapter employeeAdapter;

    public static Intent startIntent(Context context) {
        Intent intent;
        intent = new Intent(context, EmployeeListActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_employee_list;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {

        getHeadBarView().addRightTextItem("新增", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(EmployeeUpdateActivity.startIntent(getActivity()));
            }
        });

        employeeListPresenter = new EmployeeListPresenter(getActivity(), this);
        refreshLayout.setLoadMore(true);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                employeeListPresenter.refresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                employeeListPresenter.loadMore();
            }
        });

        employeeAdapter = new EmployeeAdapter(getActivity());
        listView.setAdapter(employeeAdapter);


        employeeAdapter.setOnEmployeeAdapterListener(new EmployeeAdapter.OnEmployeeAdapterListener() {
            @Override
            public void onDelete(EmployeeBean bean, int position) {
                employeeListPresenter.delete(bean.getSellerId());
            }

            @Override
            public void onEdit(EmployeeBean bean, int position) {
                startActivity(EmployeeUpdateActivity.startIntent(getActivity(), bean.getSellerId()));

            }
        });


        employeeListPresenter.refresh();

    }



    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        refreshLayout.finishRefresh();
        refreshLayout.finishRefreshLoadMore();
    }

    @Override
    public void onSuccessRefresh(ArrayList<EmployeeBean> result) {
        refreshLayout.finishRefresh();
        employeeAdapter.setList(result);

    }

    @Override
    public void onSuccessLoadModre(ArrayList<EmployeeBean> result) {

        refreshLayout.finishRefreshLoadMore();
        employeeAdapter.addList(result);


    }


    @Override
    public void onSuccessDeleted(String message) {
        ToastUtils.showError("删除成功", getApplication());
        employeeListPresenter.refresh();
    }


}
