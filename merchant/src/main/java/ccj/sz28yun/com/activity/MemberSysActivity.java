package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.MemberSysStatusBean;
import ccj.sz28yun.com.bean.MemberSysVIPNumBean;
import ccj.sz28yun.com.cache.GlobalDataCache;
import ccj.sz28yun.com.presenter.MemberSysPresenter;
import ccj.sz28yun.com.presenter.MemberSysStatusPresenter;
import per.sue.gear2.utils.GearLog;
import per.sue.gear2.utils.ToastUtils;

/**
 * Created by meihuali on 2017/6/13.
 */

public class MemberSysActivity extends CCJActivity implements MemberSysPresenter.MemberSysView {

    @Bind(R.id.all_count_tv)
    TextView allCountTv;
    @Bind(R.id.vip_count_tv)
    TextView vipCountTv;

    private MemberSysPresenter memberSysPresenter;

    public static Intent startIntent(Context context) {
        Intent intent;
        intent = new Intent(context, MemberSysActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }
    @Override
    public int getLayoutResId() {
        return R.layout.activity_member_sys;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        memberSysPresenter = new MemberSysPresenter(getActivity(), this);
        memberSysPresenter.getVipNum();
    }

    @OnClick({R.id.vip_look_ll, R.id.sms_yingxiao_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vip_look_ll:
                startActivity(MemberVipListActivity.startIntent(getActivity()));
                break;
            case R.id.sms_yingxiao_ll:
                startActivity(MemberSysSmsActivity.startIntent(getActivity()));
                break;
        }
    }

    @Override
    public void onSuccess(MemberSysVIPNumBean result) {
        allCountTv.setText(result.getaInviteMemberCount());
        vipCountTv.setText(result.getInviteVipCount());
    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        ToastUtils.showError(message, getActivity());
    }
}
