package clerk.sz28yun.com.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import clerk.sz28yun.com.R;
import clerk.sz28yun.com.activity.MainActivity;
import clerk.sz28yun.com.activity.MerchantDetailActivity;
import clerk.sz28yun.com.activity.MerchantUpdateActivity;
import clerk.sz28yun.com.activity.MerchantUpdateDraftActivity;
import clerk.sz28yun.com.activity.StatisticMemberActivity;
import clerk.sz28yun.com.adapter.HomeMerchantListAdapter;
import clerk.sz28yun.com.base.CCJFragment;
import clerk.sz28yun.com.bean.HomeBean;
import clerk.sz28yun.com.bean.HomeMerchantBean;
import clerk.sz28yun.com.cache.MerchantDraftStorageCache;
import clerk.sz28yun.com.presenter.HomePresenter;
import butterknife.Bind;
import butterknife.ButterKnife;
import per.sue.gear2.utils.DoubleUtil;
import per.sue.gear2.utils.ToastUtils;
import per.sue.gear2.widget.PageStatusLayout;


/**
 * Created by sue on 2016/11/16.
 */
public class MainHomeFragment extends CCJFragment implements HomePresenter.HomeView, View.OnClickListener {

    HomeViewHolder homeViewHolder;
    @Bind(R.id.home_list_view)
    ListView newMerchantListView;

    @Bind(R.id.page_status_layout)
    PageStatusLayout pageStatusLayout;

    private HomePresenter homePresenter;
    private HomeMerchantListAdapter homeMerchantListAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragmen_home;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        getView().findViewById(R.id.business_merchant_add_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MerchantUpdateActivity.startIntent(getActivity()));
            }
        });
        homePresenter = new HomePresenter(getContext(), this);
        pageStatusLayout.showLoading();
        homePresenter.requestData();
        View topView = LayoutInflater.from(getActivity()).inflate(R.layout.fragmen_home_list_top, null);
        topView.findViewById(R.id.count_member_ll).setOnClickListener(this);
        topView.findViewById(R.id.count_vip_ll).setOnClickListener(this);
        topView.findViewById(R.id.ccj_bouns_ll).setOnClickListener(this);
        topView.findViewById(R.id.merchants_bouns_ll).setOnClickListener(this);
        topView.findViewById(R.id.generalize_bouns_ll).setOnClickListener(this);
        topView.findViewById(R.id.count_generalize_ll).setOnClickListener(this);
        topView.findViewById(R.id.count_nosubmit_merchant_ll).setOnClickListener(this);


        homeViewHolder = new HomeViewHolder(topView, getActivity());
        newMerchantListView.addHeaderView(topView);
        homeMerchantListAdapter = new HomeMerchantListAdapter(getActivity());
        newMerchantListView.setAdapter(homeMerchantListAdapter);

        newMerchantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomeMerchantBean bean = (HomeMerchantBean) parent.getAdapter().getItem(position);
//                ToastUtils.showError(bean.getStoreName(), getActivity());
                if (bean != null) {
                    if (bean.getJoininState() == 30) {
                        startActivity(MerchantUpdateActivity.startIntent(getActivity(), bean.getId()));
                    } else {
                        startActivity(MerchantDetailActivity.startIntent(getActivity(), bean.getStoreName(), bean.getId()));
                    }
                }
            }
        });
        homeViewHolder.countNosubmitMerchantTv.setText(MerchantDraftStorageCache.getInstance().getMerchantParamsArrayList(getActivity()).size() + "家");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.count_member_ll:
                startActivity(StatisticMemberActivity.startIntent(getActivity()));
                break;
            case R.id.count_vip_ll:
                startActivity(StatisticMemberActivity.startIntent(getActivity()));
                break;
            case R.id.ccj_bouns_ll:
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).toPerformance();
                }
                break;
            case R.id.merchants_bouns_ll:
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).toPerformance();
                }
                break;
            case R.id.generalize_bouns_ll:
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).toPerformance();
                }
                break;
            case R.id.count_generalize_ll:
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).toBusiness();
                }
                break;
            case R.id.count_nosubmit_merchant_ll:
                if (getActivity() instanceof MainActivity) {
//                    ((MainActivity)getActivity()).toBusiness();
                    startActivity(MerchantUpdateDraftActivity.startIntent(getActivity()));
                }
                break;
            default:
                break;
        }
    }

    private void bindView(HomeBean bean) {

        if (null == bean) return;

        homeViewHolder.momneyVipTv.setText(DoubleUtil.formatPrice(bean.getAccount().currentVip) + "");
        homeViewHolder.ccjBounsTv.setText(DoubleUtil.formatPrice(bean.getAccount().currentGoods) + "");
        homeViewHolder.merchantsBounsTv.setText(DoubleUtil.formatPrice(bean.getAccount().currentMerchants) + "");
        homeViewHolder.generalizeBounsTv.setText(DoubleUtil.formatPrice(bean.getAccount().currentPromote) + "");

        homeViewHolder.countMemberTv.setText(bean.getMemberNum() + "名");
        homeViewHolder.countVipTv.setText(bean.getMemberVipNum() + "名");
        homeViewHolder.countEnterMerchantTv.setText(bean.getMerchantNum() + "家");
//        homeViewHolder.countNosubmitMerchantTv.setText( (null == bean.getMerchantList() ? 0: bean.getMerchantList().size()) + "家");
        if (null != bean.getMerchantList()) {
            homeMerchantListAdapter.setList(bean.getMerchantList());
        }

    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        pageStatusLayout.showFailed(message, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageStatusLayout.showLoading();
                homePresenter.requestData();
            }
        });
    }

    @Override
    public void onSuccess(HomeBean result) {
        pageStatusLayout.showContent();
        bindView(result);
    }

    static class HomeViewHolder {
        @Bind(R.id.momney_vip_tv)
        TextView momneyVipTv;
        @Bind(R.id.count_member_tv)
        TextView countMemberTv;
        @Bind(R.id.count_vip_tv)
        TextView countVipTv;
        @Bind(R.id.ccj_bouns_tv)
        TextView ccjBounsTv;
        @Bind(R.id.merchants_bouns_tv)
        TextView merchantsBounsTv;
        @Bind(R.id.generalize_bouns_tv)
        TextView generalizeBounsTv;
        @Bind(R.id.count_enter_merchant_tv)
        TextView countEnterMerchantTv;
        @Bind(R.id.count_nosubmit_merchant_tv)
        TextView countNosubmitMerchantTv;

        private Context context;

        HomeViewHolder(View view, Context context) {
            ButterKnife.bind(this, view);
            this.context = context;
        }
    }

    @Override
    public void onDestroy() {
        homePresenter.destroy();
        super.onDestroy();

    }
}
