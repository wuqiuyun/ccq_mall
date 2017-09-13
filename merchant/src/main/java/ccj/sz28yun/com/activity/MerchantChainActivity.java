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
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.adapter.MerchantChainAdapter;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.MerchantChainBean;
import ccj.sz28yun.com.presenter.MerchantChainListPresenter;
import per.sue.gear2.utils.ToastUtils;

/**
 * Created by sue on 2017/1/3.
 */
public class MerchantChainActivity extends CCJActivity implements MerchantChainListPresenter.MerchantChainListView {


    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;

    private MerchantChainListPresenter merchantChainListPresenter;
    MerchantChainAdapter merchantChainAdapter;

    public static Intent startIntent(Context context) {
        Intent intent;
        intent = new Intent(context, MerchantChainActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_merchant_chain;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {

        getHeadBarView().addRightTextItem("新增", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MerchantChainAddActivity.startIntent(getActivity()));
            }
        });

        merchantChainListPresenter = new MerchantChainListPresenter(getActivity(), this);
        refreshLayout.setLoadMore(true);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                merchantChainListPresenter.refresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                merchantChainListPresenter.loadMore();
            }
        });

        merchantChainAdapter = new MerchantChainAdapter(getActivity());
        listView.setAdapter(merchantChainAdapter);
        merchantChainAdapter.setOnMerchantChainAdapterListener(new MerchantChainAdapter.OnMerchantChainAdapterListener() {
            @Override
            public void onDelete(MerchantChainBean bean, int position) {
                merchantChainListPresenter.delete(bean.getId());
            }
        });


        merchantChainListPresenter.refresh();

    }



    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        refreshLayout.finishRefresh();
        refreshLayout.finishRefreshLoadMore();
    }

    @Override
    public void onSuccessRefresh(ArrayList<MerchantChainBean> result) {
        refreshLayout.finishRefresh();
        merchantChainAdapter.setList(result);

    }

    @Override
    public void onSuccessLoadModre(ArrayList<MerchantChainBean> result) {

        refreshLayout.finishRefreshLoadMore();
        merchantChainAdapter.addList(result);


    }


    @Override
    public void onSuccessDeleted(String message) {
        ToastUtils.showError("删除成功", getApplication());
        merchantChainListPresenter.refresh();
    }


}
