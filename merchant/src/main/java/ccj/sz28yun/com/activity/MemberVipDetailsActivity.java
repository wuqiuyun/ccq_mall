package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;

import butterknife.Bind;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.adapter.MemberVipDetailsAdapter;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.MemberVipDetailCCJBean;
import ccj.sz28yun.com.presenter.MemberVipDetailsPresenter;
import per.sue.gear2.utils.ToastUtils;

/**
 * 商家会员消费列表
 * Created by meihuali on 2017/6/17.
 */

public class MemberVipDetailsActivity extends CCJActivity implements MemberVipDetailsPresenter.MemberVipDetailsResultView {
    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;
    @Bind(R.id.member_name_tv)
    TextView memberNameTv;
    @Bind(R.id.member_count_tv)
    TextView memberCountTv;

    private MemberVipDetailsAdapter memberVipDetailsAdapter;
    private MemberVipDetailsPresenter memberVipDetailsPresenter;

    public static Intent startIntent(Context context) {
        Intent intent;
        intent = new Intent(context, MemberVipListActivity.class);
        return intent;
    }
    public static Intent startIntent(Context context, String memberId) {
        Intent intent = new Intent(context, MemberVipDetailsActivity.class);
        intent.putExtra("memberId", memberId);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_membervip_details;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        memberVipDetailsPresenter = new MemberVipDetailsPresenter(getActivity(), this);
        memberVipDetailsPresenter.setMember(getIntent().getStringExtra("memberId"));

        refreshLayout.setLoadMore(true);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                memberVipDetailsPresenter.refresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                memberVipDetailsPresenter.loadMore();
            }
        });

        memberVipDetailsAdapter = new MemberVipDetailsAdapter(getActivity());
        listView.setAdapter(memberVipDetailsAdapter);
        showLoading();
        memberVipDetailsPresenter.refresh();
    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        dismissProgressDialog();
        refreshLayout.finishRefreshLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    public void onSuccessRefresh(ArrayList<MemberVipDetailCCJBean> result) {
        dismissProgressDialog();
        if(result.size() == 0){
            ToastUtils.showError("没有数据",getActivity());
        }
        refreshLayout.finishRefresh();
        memberVipDetailsAdapter.setList(result);
    }

    @Override
    public void onSuccessLoadModre(ArrayList<MemberVipDetailCCJBean> result) {
        dismissProgressDialog();
        if(result.size() == 0){
            ToastUtils.showError("没有更多数据",getActivity());
        }
        refreshLayout.finishRefreshLoadMore();
        memberVipDetailsAdapter.addList(result);
    }

    @Override
    public void onSuccessStatic(String orderNum, String memberName) {
        dismissProgressDialog();
        memberNameTv.setText(memberName);
        memberCountTv.setText("消费频次：" + orderNum);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        memberVipDetailsPresenter.destroy();
    }
}
