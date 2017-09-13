package ccj.sz28yun.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.activity.EvaluateReplyActivity;
import ccj.sz28yun.com.adapter.EvaluateMerchantAdapter;
import ccj.sz28yun.com.base.CCJFragment;
import ccj.sz28yun.com.bean.EvaluateGoodsBean;
import ccj.sz28yun.com.bean.EvaluateMerchantBean;
import ccj.sz28yun.com.bean.EvaluateStatisticBean;
import ccj.sz28yun.com.presenter.EvaluateMerchantPresenter;
import ccj.sz28yun.com.presenter.EvaluatePresenter;

/**
 * Created by sue on 2017/1/18.
 */
public class EvaluateMerchantFragment extends CCJFragment implements EvaluateMerchantPresenter.EvaluateMerchant {


    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;

    EvaluateMerchantPresenter evaluatePresenter;
    private EvaluateMerchantAdapter evaluateMerchantAdapter;

    private int reType;

    public static Bundle getBundle(int reType){
        Bundle bundle = new Bundle();
        bundle.putInt("reType", reType );
        return bundle;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_evaluate_merchant;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        reType = getArguments().getInt("reType");
        evaluatePresenter = new EvaluateMerchantPresenter(getActivity(), this);
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
        evaluateMerchantAdapter = new EvaluateMerchantAdapter(getActivity());
        evaluateMerchantAdapter.setEvaluateMerchantAdapterListener(
                (bean, position) -> { startActivity(EvaluateReplyActivity.startIntent(getActivity(), bean)); }
        );
        listView.setAdapter(evaluateMerchantAdapter);
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
    public void onSuccessRefresh(ArrayList<EvaluateMerchantBean> result) {
        refreshLayout.finishRefresh();
        evaluateMerchantAdapter.setList(result);

    }

    @Override
    public void onSuccessLoadModre(ArrayList<EvaluateMerchantBean> result) {
        refreshLayout.finishRefreshLoadMore();
        evaluateMerchantAdapter.addList(result);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        evaluatePresenter.destroy();
    }
}
