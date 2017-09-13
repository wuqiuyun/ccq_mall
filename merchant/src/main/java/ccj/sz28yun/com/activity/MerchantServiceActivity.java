package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.MerchantServiceBean;
import ccj.sz28yun.com.presenter.MerchantServicePresenter;
import per.sue.gear2.controll.GearDateViewController;
import per.sue.gear2.dialog.GearCustomDialog;
import per.sue.gear2.utils.ToastUtils;
import per.sue.gear2.utils.date.DateStyle;
import per.sue.gear2.utils.date.DateUtils;

/**
 * 服务设置
 * Created by sue on 2017/1/3.
 */
public class MerchantServiceActivity extends CCJActivity implements MerchantServicePresenter.MerchantServiceView {

    @Bind(R.id.is_child_paradise_ck)
    CheckBox isChildParadiseCk;
    @Bind(R.id.is_child_wifi_ck)
    CheckBox isChildWifiCk;
    @Bind(R.id.is_smoke_ck)
    CheckBox isSmokeCk;
    @Bind(R.id.is_bill_ck)
    CheckBox isBillCk;
    @Bind(R.id.start_date_tv)
    TextView startDateTv;
    @Bind(R.id.end_date_tv)
    TextView endDateTv;

    private MerchantServicePresenter  merchantServicePresenter;
    MerchantServiceBean merchantServiceBean;

    public static Intent startIntent(Context context) {
        Intent intent;
        intent = new Intent(context, MerchantServiceActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_service;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {

        merchantServicePresenter = new MerchantServicePresenter(getApplicationContext(), this);
        merchantServicePresenter.getMerchantService();
    }




    @OnClick({R.id.start_date_tv, R.id.end_date_tv, R.id.is_child_paradise_ck, R.id.is_child_wifi_ck, R.id.is_smoke_ck, R.id.is_bill_ck})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_date_tv:
                chooseStartTime();
                break;
            case R.id.end_date_tv:
                chooseEndTime();
                break;

            case R.id.is_child_paradise_ck:
               // isChildParadiseCk.setChecked(!isChildParadiseCk.isChecked());
                merchantServiceBean.setIsChild(isChildParadiseCk.isChecked() ? 1: 0);
                merchantServicePresenter.editMerchantService(merchantServiceBean);
                break;
            case R.id.is_child_wifi_ck:
               // isChildWifiCk.setChecked(!isChildWifiCk.isChecked());
                merchantServiceBean.setIsWifi(isChildWifiCk.isChecked() ? 1: 0);
                merchantServicePresenter.editMerchantService(merchantServiceBean);
                break;
            case R.id.is_smoke_ck:
               // isSmokeCk.setChecked(!isSmokeCk.isChecked());
                merchantServiceBean.setIsSmoke(isSmokeCk.isChecked() ? 1: 0);
                merchantServicePresenter.editMerchantService(merchantServiceBean);
                break;
            case R.id.is_bill_ck:
                //isBillCk.setChecked(!isBillCk.isChecked());
                merchantServiceBean.setIsTicket(isBillCk.isChecked() ? 1: 0);
                merchantServicePresenter.editMerchantService(merchantServiceBean);
                break;
        }
    }

    @Override
    public void onSuccessEdit(String message) {
        ToastUtils.showShortMessage("修改成功", getApplicationContext());

    }

    @Override
    public void onSuccess(MerchantServiceBean result) {
        merchantServiceBean = result;
        bindView(result);
    }

    private void bindView(MerchantServiceBean result){
        isChildParadiseCk.setChecked(result.getIsChild() == 0 ? false : true);
        isChildWifiCk.setChecked(result.getIsWifi() == 0 ? false : true);
        isSmokeCk.setChecked(result.getIsSmoke() == 0 ? false : true);
        isBillCk.setChecked(result.getIsTicket() == 0 ? false : true);

        startDateTv.setText(result.getServiceStartTime());
        endDateTv.setText(result.getServiceEndTime());

    }

    /**
     * 选择日期
     */
    private void chooseStartTime(){
        String  str  = merchantServiceBean.getServiceStartTime();
        if(TextUtils.isEmpty(str)){
            str = "09:00";
        }
        str = "2016-10-12 " + str;
        long time = DateUtils.StringToDate(str).getTime();
        final GearDateViewController gearDateViewController = new GearDateViewController(getActivity(), time);
        gearDateViewController.setDateStyle(GearDateViewController.DateStyle.HOUR_MIN);
        GearCustomDialog gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .setContentView(gearDateViewController.getContentView())
                .setTitle("选择开始服务时间")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        long dateTime = gearDateViewController.getSelectDate().getTime();
                        startDateTv.setText(DateUtils.getDate(dateTime, DateStyle.HH_MM));
                        merchantServiceBean.setServiceStartTime(DateUtils.getDate(dateTime, DateStyle.HH_MM));
                        merchantServicePresenter.editMerchantService(merchantServiceBean);
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

    private void chooseEndTime(){
        String  str  = merchantServiceBean.getServiceEndTime();
        if(TextUtils.isEmpty(str)){
            str = "18:00";
        }
        str = "2016-10-12 " + str;
        long time = DateUtils.StringToDate(str).getTime();
        final GearDateViewController gearDateViewController = new GearDateViewController(getActivity(), time);
        gearDateViewController.setDateStyle(GearDateViewController.DateStyle.HOUR_MIN);
        GearCustomDialog gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .setContentView(gearDateViewController.getContentView())
                .setTitle("选择开始服务时间")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        long dateTime = gearDateViewController.getSelectDate().getTime();
                        endDateTv.setText(DateUtils.getDate(dateTime, DateStyle.HH_MM));
                        merchantServiceBean.setServiceEndTime(DateUtils.getDate(dateTime, DateStyle.HH_MM));
                        merchantServicePresenter.editMerchantService(merchantServiceBean);
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

    @Override
    protected void onDestroy() {
        merchantServicePresenter.destroy();
        super.onDestroy();

    }
}
