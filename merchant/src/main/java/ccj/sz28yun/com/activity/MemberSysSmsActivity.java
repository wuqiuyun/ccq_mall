package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.MemberSysStatusBean;
import ccj.sz28yun.com.fragment.MemberSysAduitFragment;
import ccj.sz28yun.com.fragment.MemberSysSMSSendFragment;
import ccj.sz28yun.com.presenter.MemberSysStatusPresenter;
import per.sue.gear2.utils.ToastUtils;

/**
 * 会员系统短信营销界面
 * Created by sue on 2016/12/15.
 */
public class MemberSysSmsActivity extends CCJActivity implements MemberSysStatusPresenter.MemberSysStatusView {

    private MemberSysStatusPresenter memberSysStatusPresenter;

    public static Intent startIntent(Context context) {
        Intent intent;
        intent = new Intent(context, MemberSysSmsActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_member_syssms;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {

        memberSysStatusPresenter = new MemberSysStatusPresenter(getActivity(), this);
        memberSysStatusPresenter.getStatus();

    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        ToastUtils.showError(message, getActivity());
    }

    @Override
    public void onSuccessStatus(int status) {
        switch(status){
            case MemberSysAduitFragment.STATUS_FIRST:
            case MemberSysAduitFragment.STATUS_PROCESS:
            case  MemberSysAduitFragment.STATUS_FAILED:
                MemberSysAduitFragment fragment = new MemberSysAduitFragment();
                fragment.setArguments(MemberSysAduitFragment.getBundle(status));
                replaceFragment(R.id.content_fl, fragment);
                break;
            default:
                //短信列表
                replaceFragment(R.id.content_fl, new MemberSysSMSSendFragment());
                break;

        }
    }

    @Override
    public void onSuccess(MemberSysStatusBean result) {

    }
}
