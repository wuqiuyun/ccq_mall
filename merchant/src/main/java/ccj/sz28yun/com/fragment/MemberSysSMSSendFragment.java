package ccj.sz28yun.com.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.activity.MemberSysSMSMemberSysActivity;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.base.CCJFragment;
import ccj.sz28yun.com.bean.GoodsSpecificationBean;
import ccj.sz28yun.com.bean.MemberSysSMSSendParams;
import ccj.sz28yun.com.bean.MemberSysStatusBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataCache;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import ccj.sz28yun.com.presenter.MemberSysSMSSendPresenter;
import per.sue.gear2.controll.GearDateViewController;
import per.sue.gear2.dialog.GearCustomDialog;
import per.sue.gear2.utils.ToastUtils;

/**
 * 会员系统 发送短信
 * Created by sue on 2016/12/18.
 */
public class MemberSysSMSSendFragment extends CCJFragment implements MemberSysSMSSendPresenter.MemberSysSMSSendView {


    @Bind(R.id.member_type_tv)
    TextView memberTypeTv;
    @Bind(R.id.sms_signtrue_tv)
    TextView smsSigntrueTv;
    @Bind(R.id.sms_content_et)
    EditText smsContentEt;

    @Bind(R.id.target_mobile_et)
    EditText targetMobileEt;

    @Bind(R.id.send_type_now)
    RadioButton sendTypeNow;
    @Bind(R.id.send_type_time)
    RadioButton sendTypeTime;
    @Bind(R.id.select_time_tv)
    TextView selectTimeTv;
    @Bind(R.id.send_type_rg)
    RadioGroup sendTypeRg;
    @Bind(R.id.member_count_tv)
    TextView memberCountTv;
    @Bind(R.id.sms_count_tv)
    TextView smsCountTv;
    @Bind(R.id.payment_tv)
    TextView paymentTv;
    @Bind(R.id.target_mobile_ll)
    LinearLayout targetMobileLl;

    private long currentTime;
    private GearCustomDialog gearCustomDialog;

    private MemberSysSMSSendParams memberSysSMSSendParams;
    private MemberSysStatusBean memberSysStatusBean;

    private MemberSysSMSSendPresenter memberSysSMSSendPresenter;

//    public static Intent startIntent(Context context) {
//        Intent intent;
//        intent = new Intent(context, MemberSysSMSSendActivity.class);
//        return intent;
//    }

//    @Override
//    public boolean showBackView() {
//        return true;
//    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_member_sys_sms_send;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        memberSysSMSSendParams = new MemberSysSMSSendParams();
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        memberSysSMSSendParams.token = userBean.getToken();
        memberSysSMSSendParams.merchatId = userBean.getStoreId();
        memberSysSMSSendParams.sysType = 1;
        smsSigntrueTv.setText("28云-餐餐抢");
        memberSysStatusBean = GlobalDataCache.getInstance().getMemberSysStatusBean();
        currentTime = System.currentTimeMillis();
        memberSysSMSSendPresenter = new MemberSysSMSSendPresenter(getActivity(), this);
        sendTypeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.send_type_now) {
                    selectTimeTv.setVisibility(View.GONE);
                    memberSysSMSSendParams.sendType = 0;

                } else {
                    selectTimeTv.setVisibility(View.VISIBLE);
                    memberSysSMSSendParams.sendType = 1;
                }
            }
        });
    }


    @OnClick({R.id.member_type_ll, R.id.sms_signtrue_ll, R.id.select_time_tv, R.id.submit_btn, R.id.yi_send_sms})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.member_type_ll:
                chooseTargetMember();
                break;
            case R.id.sms_signtrue_ll:
                chooseSigtrue();
                break;
            case R.id.select_time_tv:
                chooseTime();
                break;
            case R.id.submit_btn:
                if (verify()) {
                    showLoading();
                    submit();
                }
                break;
            case R.id.yi_send_sms:
                startActivity(MemberSysSMSMemberSysActivity.startIntent(getActivity()));
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        dissDialog();
    }

    @Override
    public void onSuccess(String result) {
        dissDialog();
        ToastUtils.showError("提交成功等待审核", getActivity());
        getActivity().finish();
    }


    /**
     * 选择日期
     */
    private void chooseTime() {
        final GearDateViewController gearDateViewController = new GearDateViewController(getActivity(), currentTime);
        gearDateViewController.setDateStyle(GearDateViewController.DateStyle.YEAR_MONTH_DAY_HOUR_MIN);
        GearCustomDialog gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .setContentView(gearDateViewController.getContentView())
                .setTitle("选择定时发送时间")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        long dateTime = gearDateViewController.getSelectDate().getTime();
                        currentTime = dateTime;
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(dateTime);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String yymmdd = simpleDateFormat.format(dateTime);
                        selectTimeTv.setText(yymmdd);
                    /*    operatingDataPresenter.setYear(calendar.get(Calendar.YEAR) + "");
                        operatingDataPresenter.setMonth(calendar.get(Calendar.MONTH) + "");
                        operatingDataPresenter.getOrderStatisticData();*/

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        gearCustomDialog.show();
    }

    /**
     * 选择签名
     */
    private void chooseSigtrue() {
        ArrayList<String> data = new ArrayList<>();
        data.add("28云-餐餐抢");
        if (null != memberSysStatusBean || !TextUtils.isEmpty(memberSysStatusBean.getStoreSmsSign())) {
            data.add(0, memberSysStatusBean.getStoreSmsSign());
        }
        gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .addListView(data, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        gearCustomDialog.dismiss();
                        gearCustomDialog = null;
                        if (null != memberSysStatusBean || !TextUtils.isEmpty(memberSysStatusBean.getStoreSmsSign())) {
                            memberSysSMSSendParams.sysType = position;
                            smsSigntrueTv.setText(data.get(position));
                        } else {
                            memberSysSMSSendParams.sysType = 1;
                            smsSigntrueTv.setText("28云-餐餐抢");
                        }

                    }
                })
                .setTitle("请选择短信签名")
                .create();
        gearCustomDialog.show();
    }

    /**
     * 选择目标会员
     */
    private void chooseTargetMember() {
        ArrayList<String> data = new ArrayList<>();
        data.add("全部");
        data.add("VIP");
        data.add("指定");
        gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .addListView(data, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        gearCustomDialog.dismiss();
                        gearCustomDialog = null;
                        memberSysSMSSendParams.serviceType = position;
                        memberTypeTv.setText(data.get(position));

                        if (position == 0) {
                            memberCountTv.setText(memberSysStatusBean.getMemberNum() + "名");
                            smsCountTv.setText(memberSysStatusBean.getMemberNum() + "条");
                            paymentTv.setText((memberSysStatusBean.getMemberNum() * memberSysStatusBean.getUnitPrice()) + "元");
                            targetMobileLl.setVisibility(View.GONE);
                        } else if (position == 1) {
                            memberCountTv.setText(memberSysStatusBean.getVipMemberNum() + "名");
                            smsCountTv.setText(memberSysStatusBean.getVipMemberNum() + "条");
                            paymentTv.setText((memberSysStatusBean.getVipMemberNum() * memberSysStatusBean.getUnitPrice()) + "元");
                            targetMobileLl.setVisibility(View.GONE);
                        } else if (position == 2) {
                            memberCountTv.setText("1名");
                            smsCountTv.setText("1条");
                            paymentTv.setText((memberSysStatusBean.getUnitPrice()) + "元");
                            targetMobileLl.setVisibility(View.VISIBLE);
                        }
                    }
                })
                .setTitle("请选择目标会员")
                .create();
        gearCustomDialog.show();
    }

    private boolean verify() {
        boolean canSubmit = true;

        if (TextUtils.isEmpty(memberTypeTv.getText().toString())) {
            ToastUtils.showError("请选择目标会员", getActivity());
            canSubmit = false;

        } else if (memberSysSMSSendParams.serviceType == 2 && TextUtils.isEmpty(targetMobileEt.getText().toString())) {
            targetMobileEt.requestFocus();
            targetMobileEt.setError("请填写指定目标");
            canSubmit = false;
        } else if (TextUtils.isEmpty(smsContentEt.getText().toString())) {
            smsContentEt.requestFocus();
            smsContentEt.setError("请填写短信内容");
            canSubmit = false;
        } else if (memberSysSMSSendParams.sendType == 1 && "选择时间".equals(selectTimeTv.getText().toString().trim())) {
            selectTimeTv.requestFocus();
            ToastUtils.showError("定时发送请选择时间", getActivity());
            canSubmit = false;
        }
        if (memberSysSMSSendParams.serviceType == 2) {
            memberSysSMSSendParams.designate = targetMobileEt.getText().toString().trim();
        }
        memberSysSMSSendParams.smsContent = smsContentEt.getText().toString().trim();
        memberSysSMSSendParams.sendTime = currentTime/1000 + "";
        return canSubmit;

    }

    // 加载中动画
    private Dialog loadingDialog;

    // 加载动画
    public void showLoading() {

        View view = LayoutInflater.from(getActivity()).inflate(
                R.layout.loading, null);
        ImageView image = (ImageView) view.findViewById(R.id.iv_loading);
        Animation animation = AnimationUtils.loadAnimation(
                getActivity(), R.anim.loading_anim);
        image.startAnimation(animation);
        loadingDialog = new Dialog(getActivity(),
                R.style.mDialogStyle);
        loadingDialog.setContentView(view);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();
    }

    // 关闭加载dialog
    private void dissDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    private void submit() {
        memberSysSMSSendPresenter.send(memberSysSMSSendParams);
    }
}
