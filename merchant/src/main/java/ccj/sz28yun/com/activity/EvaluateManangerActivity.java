package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.adapter.EvaluateGoodsAdapter;
import ccj.sz28yun.com.adapter.EvaluateMerchantAdapter;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.EvaluateGoodsBean;
import ccj.sz28yun.com.bean.EvaluateMerchantBean;
import ccj.sz28yun.com.bean.EvaluateStatisticBean;
import ccj.sz28yun.com.presenter.EvaluatePresenter;

/**
 * 评价管理
 * Created by sue on 2016/12/6.
 */
public class EvaluateManangerActivity extends CCJActivity implements EvaluatePresenter.EvaluateView {


    @Bind(R.id.evaluate_goods_count)
    TextView evaluateGoodsCountTV;
    @Bind(R.id.evaluate_middle_count)
    TextView evaluateMiddleCountTV;
    @Bind(R.id.evaluate_bad_count)
    TextView evaluateBadCountTV;
    @Bind(R.id.evaluate_reply_count)
    TextView evaluateReplyCountTV;
    @Bind(R.id.avaluate_merchant_count)
    RadioButton avaluateMerchantCountRB;
    @Bind(R.id.avaluate_goods_count)
    RadioButton avaluateGoodsCountRB;
    @Bind(R.id.evaluate_type_rg)
    RadioGroup evaluateTypeRg;
    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;

    private EvaluatePresenter evaluatePresenter;
    private EvaluateMerchantAdapter evaluateMerchantAdapter;
    private EvaluateGoodsAdapter evaluateGoodsAdapter;

    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, EvaluateManangerActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_evaluate_old;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        evaluatePresenter = new EvaluatePresenter(getActivity(), this);
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
        evaluateGoodsAdapter = new EvaluateGoodsAdapter(getActivity());
        evaluateMerchantAdapter.setEvaluateMerchantAdapterListener(
                (bean, position) -> {
                    startActivity(EvaluateReplyActivity.startIntent(getActivity(), bean));
                }
        );
        evaluateGoodsAdapter.setEvaluateGoodsAdapterListener(
                (bean, position) -> {
                    startActivity(EvaluateReplyActivity.startIntent(getActivity(), bean));
                }
        );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (evaluatePresenter.isQueryMerchant()) {
                    startActivity(EvaluateMerchantActivity.startIntent(getActivity()));
                } else {
                    startActivity(EvaluateGoodsActivity.startIntent(getActivity()));
                }
            }
        });

        evaluateTypeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.avaluate_merchant_count) {
                    evaluatePresenter.setQueryMerchant(true);
                } else {
                    evaluatePresenter.setQueryMerchant(false);
                }
                evaluatePresenter.refresh();
            }
        });

        evaluatePresenter.requestStatisticData();
        evaluatePresenter.refresh();
        //evaluatePresenter.refreshWithLoading();

    }


    @Override
    public void onSuccessStatistic(EvaluateStatisticBean bean) {
        evaluateGoodsCountTV.setText(String.format("(%s)条", bean.getEvaGoods()));
        evaluateMiddleCountTV.setText(String.format("(%s)条", bean.getEvaPosi()));
        evaluateBadCountTV.setText(String.format("(%s)条", bean.getEvaBad()));
        evaluateReplyCountTV.setText(String.format("(%s)条", bean.getEvaRep()));

        avaluateMerchantCountRB.setText(String.format("商家评价  (%s)", bean.getStoreEva()));
        avaluateGoodsCountRB.setText(String.format("商品评价  (%s)", bean.getGoodsEva()));
    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        dismissProgressDialog();
        refreshLayout.finishRefreshLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    public void onSuccessGoodsRefresh(ArrayList<EvaluateGoodsBean> result) {
        refreshLayout.finishRefreshLoadMore();
        refreshLayout.finishRefresh();
        evaluateGoodsAdapter.setList(result);
        listView.setAdapter(evaluateGoodsAdapter);
    }

    @Override
    public void onSuccessGoodsLoadModre(ArrayList<EvaluateGoodsBean> result) {
        refreshLayout.finishRefreshLoadMore();
        refreshLayout.finishRefresh();
        evaluateGoodsAdapter.addList(result);
    }

    @Override
    public void onSuccessRefresh(ArrayList<EvaluateMerchantBean> result) {
        refreshLayout.finishRefreshLoadMore();
        refreshLayout.finishRefresh();
        evaluateMerchantAdapter.setList(result);
        listView.setAdapter(evaluateMerchantAdapter);
    }

    @Override
    public void onSuccessLoadModre(ArrayList<EvaluateMerchantBean> result) {
        refreshLayout.finishRefreshLoadMore();
        refreshLayout.finishRefresh();
        evaluateMerchantAdapter.addList(result);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        evaluatePresenter.destroy();
    }

    @OnClick(R.id.to_repost_ll)
    public void onClick() {
        startActivity(EvaluateRepostActivity.startIntent(getActivity(),evaluatePresenter.isQueryMerchant() ));
    }
}
