package ccj.sz28yun.com.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJFragment;
import ccj.sz28yun.com.bean.MemberSysStatusBean;
import ccj.sz28yun.com.presenter.MemberSysAduitPresenter;
import per.sue.gear2.dialog.GearCustomDialog;
import per.sue.gear2.utils.ToastUtils;

/**
 * Created by sue on 2016/12/15.
 */
public class MemberSysAduitFragment extends CCJFragment implements MemberSysAduitPresenter.MemberSysAduitView {

    public final static int STATUS_FIRST = 0;
    public final static int STATUS_PROCESS = 1;
    public final static int STATUS_FAILED = 2;

    int memberSysStatus;
    @Bind(R.id.regist_count_tv)
    TextView registCountTv;
    @Bind(R.id.vip_count_tv)
    TextView vipCountTv;
    @Bind(R.id.reson_tv)
    TextView resonTv;
    @Bind(R.id.to_apply_btn)
    View  aduitFirstLl;
    @Bind(R.id.aduit_process_ll)
    LinearLayout aduitProcessLl;
    @Bind(R.id.aduit_result_ll)
    LinearLayout aduitResultLl;

    private MemberSysAduitPresenter memberSysAduitPresenter;

    public static Bundle getBundle(int status){
        Bundle bundle = new Bundle();
        bundle.putInt("status", status);
        return bundle;

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_member_aduit;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        memberSysAduitPresenter = new MemberSysAduitPresenter(getActivity(), this);
        memberSysStatus = getArguments().getInt("status");
        refreshViewByStatus();
        memberSysAduitPresenter.getStatisticData();
    }


    @OnClick({R.id.to_apply_btn, R.id.again_apply_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.to_apply_btn:
                showApplyDialog();
                break;
            case R.id.again_apply_btn:
                showApplyDialog();
                break;
        }
    }

    private void refreshViewByStatus(){
        switch(memberSysStatus){
            case STATUS_FIRST: //第一次
                aduitFirstLl.setVisibility(View.VISIBLE);
                aduitProcessLl.setVisibility(View.GONE);
                aduitResultLl.setVisibility(View.GONE);
                break;
            case STATUS_FAILED://失败
                aduitFirstLl.setVisibility(View.GONE);
                aduitProcessLl.setVisibility(View.GONE);
                aduitResultLl.setVisibility(View.VISIBLE);
                memberSysAduitPresenter.getReson();
                break;
            case STATUS_PROCESS: //过程
                aduitFirstLl.setVisibility(View.GONE);
                aduitProcessLl.setVisibility(View.VISIBLE);
                aduitResultLl.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }



    @Override
    public void onSuccess(String result) {
        ToastUtils.showError("已提交审核", getContext());
        memberSysStatus = 1;
        refreshViewByStatus();
    }

    private void showApplyDialog(){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_member_sys_apply, null);
        EditText contentET = (EditText)view.findViewById(R.id.content_et);
        GearCustomDialog gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .setTitle("开通增值服务")
                .setContentView(view)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (TextUtils.isEmpty(contentET.getText().toString().trim())){
                            ToastUtils.showError("签名不能为空",getActivity());
                        }else{
                            dialog.dismiss();
                            memberSysAduitPresenter.sendSigntrue(contentET.getText().toString());
                        }
                    }
                })
                .create();
        gearCustomDialog.show();

    }

    @Override
    public void onSuccessStatistic(int memberCount, int vipCount) {
        registCountTv.setText(String.format("%s 名", memberCount));
        vipCountTv.setText(String.format("%s 名", vipCount));

    }

    @Override
    public void onFaildedReson(String reson) {
        resonTv.setText(reson);

    }
}
