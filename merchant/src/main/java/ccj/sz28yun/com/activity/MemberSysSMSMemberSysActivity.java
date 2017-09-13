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
import ccj.sz28yun.com.adapter.MemberSysSMSAdapter;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.MemberSysSMSBean;
import ccj.sz28yun.com.presenter.MemberSysSMSPresenter;
import per.sue.gear2.utils.DoubleUtil;

/**
 * Created by meihuali on 2017/6/13.
 */

public class MemberSysSMSMemberSysActivity extends CCJActivity implements MemberSysSMSPresenter.MemberSysSMSView {

    @Bind(R.id.sended_sms_count_tv)
    TextView sendedSmsCountTv;
    @Bind(R.id.sended_sms_payment_tv)
    TextView sendedSmsPaymentTv;
    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;

    MemberSysSMSPresenter memberSysSMSPresenter;
    MemberSysSMSAdapter memberSysSMSAdapter;

    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, MemberSysSMSMemberSysActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_member_sys_sms;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {

        memberSysSMSPresenter = new MemberSysSMSPresenter(getActivity(), this);
        memberSysSMSAdapter = new MemberSysSMSAdapter(getActivity());
        listView.setAdapter(memberSysSMSAdapter);

        refreshLayout.setLoadMore(true);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                memberSysSMSPresenter.refresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                memberSysSMSPresenter.loadMore();
            }
        });
        memberSysSMSPresenter.refresh();

    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        dismissProgressDialog();
        refreshLayout.finishRefreshLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    public void onSuccessRefresh(ArrayList<MemberSysSMSBean> result) {
        refreshLayout.finishRefresh();
        memberSysSMSAdapter.setList(result);
    }

    @Override
    public void onSuccessLoadModre(ArrayList<MemberSysSMSBean> result) {
        refreshLayout.finishRefreshLoadMore();
        memberSysSMSAdapter.addList(result);

    }

    @Override
    public void onSuccessStatistic(int count, double payment) {
        sendedSmsCountTv.setText(new StringBuilder(count + "").append("批次"));
        sendedSmsPaymentTv.setText(new StringBuilder(DoubleUtil.formatPrice(payment) + "").append("元"));
    }
}

