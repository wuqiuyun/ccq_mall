package ccj.sz28yun.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;

import butterknife.Bind;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.activity.EvaluateReplyActivity;
import ccj.sz28yun.com.adapter.EvaluateGoodsAdapter;
import ccj.sz28yun.com.adapter.EvaluateMerchantAdapter;
import ccj.sz28yun.com.base.CCJFragment;
import ccj.sz28yun.com.bean.EvaluateGoodsBean;
import ccj.sz28yun.com.bean.EvaluateMerchantBean;
import ccj.sz28yun.com.presenter.EvaluateGoodsPresenter;
import ccj.sz28yun.com.presenter.EvaluateMerchantPresenter;

/**
 * Created by sue on 2017/1/18.
 */
public class EvaluateGoodsFragment extends CCJFragment implements EvaluateGoodsPresenter.EvaluateGoodsView {


    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;

    EvaluateGoodsPresenter evaluatePresenter;
    private EvaluateGoodsAdapter evaluateGoodsAdapter;

    private int reType;

    public static Bundle getBundle(int reType){
        Bundle bundle = new Bundle();
        bundle.putInt("reType", reType );
        return bundle;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_evaluate_goods;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        reType = getArguments().getInt("reType");
        evaluatePresenter = new EvaluateGoodsPresenter(getActivity(), this);
        evaluatePresenter.setReType(reType);
        refreshLayout.setLoadMore(true);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                evaluatePresenter.refresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                evaluatePresenter.loadMore();
            }
        });
        evaluateGoodsAdapter = new EvaluateGoodsAdapter(getActivity());
        evaluateGoodsAdapter.setEvaluateGoodsAdapterListener(
                (bean, position) -> { startActivity(EvaluateReplyActivity.startIntent(getActivity(), bean)); }
        );
        listView.setAdapter(evaluateGoodsAdapter);
        evaluatePresenter.refresh();
    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        dismissProgressDialog();
        refreshLayout.finishRefreshLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    public void onSuccessRefresh(ArrayList<EvaluateGoodsBean> result) {
        refreshLayout.finishRefresh();
        evaluateGoodsAdapter.setList(result);
    }

    @Override
    public void onSuccessLoadModre(ArrayList<EvaluateGoodsBean> result) {
        refreshLayout.finishRefreshLoadMore();
        evaluateGoodsAdapter.addList(result);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        evaluatePresenter.destroy();
    }
}
