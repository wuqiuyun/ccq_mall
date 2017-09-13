package clerk.sz28yun.com.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import clerk.sz28yun.com.R;
import clerk.sz28yun.com.activity.MerchantDetailActivity;
import clerk.sz28yun.com.activity.MerchantUpdateActivity;
import clerk.sz28yun.com.adapter.BusinessListAdapter;
import clerk.sz28yun.com.base.CCJFragment;
import clerk.sz28yun.com.bean.BusinessBean;
import clerk.sz28yun.com.bean.MerchantParams;
import clerk.sz28yun.com.presenter.BusinessPresenter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import per.sue.gear2.dialog.GearCustomDialog;
import per.sue.gear2.presenter.ListPresenter;
import per.sue.gear2.utils.ToastUtils;
import per.sue.gear2.widget.PageStatusLayout;

/**
 * Created by sue on 2016/11/17.
 */
public class MainBusinessFragment extends CCJFragment implements ListPresenter.ListResultView<ArrayList<BusinessBean>> {

    @Bind(R.id.business_merchant_add_tv)
    TextView businessMerchantAddTv;
    @Bind(R.id.business_status_rg)
    RadioGroup businessStatusRg;
    @Bind(R.id.business_list_view)
    ListView businessListView;
    @Bind(R.id.business_page_status_layout)
    PageStatusLayout pageStatusLayout;
    @Bind(R.id.business_refresh_layout)
    MaterialRefreshLayout refreshLayout;
    private BusinessListAdapter businessListAdapter;
    private BusinessPresenter businessPresenter;
    private String memberId ;

    public static Bundle getBundle(String childId){
        Bundle bundle = new Bundle();
        bundle.putString("childId", childId);
        return bundle;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_busniess;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        if(null !=  getArguments()){
            memberId = getArguments().getString("childId", "");
        }

        businessListAdapter = new BusinessListAdapter(getActivity());
        businessListView.setAdapter(businessListAdapter);
        businessPresenter = new BusinessPresenter(getActivity(),  this);

        if(!TextUtils.isEmpty(memberId)){
            businessPresenter.setMenberId(memberId);
            getView().findViewById(R.id.business_title_bar_ll).setVisibility(View.GONE);
        }

        businessListAdapter.setOnBusinessListAdapterListener(new BusinessListAdapter.OnBusinessListAdapterListener() {
            @Override
            public void onClickCheckReson(BusinessBean bean) {
                new GearCustomDialog.Builder(getActivity()).setTitle("审核拒绝原因")
                        .setMessage(bean.getJoininMessage())
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                startActivity(MerchantUpdateActivity.startIntent(getActivity(), bean.getId()));
                            }
                        }).create().show();

            }

            @Override
            public void onClicCheck(BusinessBean bean) {
                startActivity(MerchantDetailActivity.startIntent(getActivity(), bean.getStoreName(), bean.getId()));
            }
        });

        refreshLayout.setLoadMore(true);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                businessPresenter.refresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                businessPresenter.loadMore();
            }
        });

        businessStatusRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.business_status_all_rb:
                        businessPresenter.setState(null);
                        businessPresenter.refreshWithLoading();
                        break;
                    case R.id.business_status_process_rb:
                        businessPresenter.setState("10");//神秘数字， 看接口文档，偷下懒
                        businessPresenter.refreshWithLoading();
                        break;
                    case R.id.business_status_refused_rb:
                        businessPresenter.setState("30");
                        businessPresenter.refreshWithLoading();
                        break;
                    case R.id.business_status_passed_rb:
                        businessPresenter.setState("40");
                        businessPresenter.refreshWithLoading();
                        break;
                    default:
                        break;
                }

            }
        });
    }

    @OnClick({R.id.business_merchant_add_tv})
    public void onClick(View view) {
        startActivity(MerchantUpdateActivity.startIntent(getActivity()));

    }

    @Override
    public void onError(int code, String message) {
        refreshLayout.finishRefreshLoadMore();
        refreshLayout.finishRefresh();
        super.onError(code, message);
        if(businessPresenter.isWithLoad()){
            pageStatusLayout.showFailed(message, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    businessPresenter.refreshWithLoading();
                }
            });
        }
    }

    @Override
    public void showLoading() {
        super.showLoading();
        pageStatusLayout.showLoading();

    }

    @Override
    public void onSuccessRefresh(ArrayList<BusinessBean> result) {
        refreshLayout.finishRefreshLoadMore();
        refreshLayout.finishRefresh();

        if(null != result && result.size() > 0){
            businessListAdapter.setList(result);
            pageStatusLayout.showContent();
        }else{
            businessListAdapter.setList(new ArrayList<BusinessBean>());
            pageStatusLayout.showEmpty();
        }

    }

    @Override
    public void onSuccessLoadModre(ArrayList<BusinessBean> result) {
        refreshLayout.finishRefreshLoadMore();
        refreshLayout.finishRefresh();
        if(null != result && result.size() > 0){
            businessListAdapter.addList(result);
        }else{
            //ToastUtils.showError("已没更多数据", getActivity());
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        businessPresenter.destroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        businessPresenter.refreshWithLoading();
    }
}
