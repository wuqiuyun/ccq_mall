package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.ConsumBillSettingResult;
import ccj.sz28yun.com.presenter.ConsumeBillSettingPresenter;
import per.sue.gear2.utils.ToastUtils;

/**
 * Created by sue on 2016/12/6.
 */
public class ConsumeBillSettingActivity extends CCJActivity implements ConsumeBillSettingPresenter.ConsumeBillSettingView {


    @Bind(R.id.consume_ck)
    CheckBox consumeCk;
    @Bind(R.id.percent_et)
    EditText percentEt;
    private ConsumeBillSettingPresenter consumeBillSettingPresenter;

    private String unionPay = "0";

    public static Intent startIntent(Context context) {
        Intent intent;
        intent = new Intent(context, ConsumeBillSettingActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_consume_bill_setting;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        consumeBillSettingPresenter = new ConsumeBillSettingPresenter(getActivity(), this);
        consumeBillSettingPresenter.getConsumeBillSettingInfo();
        consumeCk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    unionPay = "1";
                }else{
                    unionPay = "0";
                }
            }
        });
    }



    @OnClick(R.id.submit_btn)
    public void onClick() {
        consumeBillSettingPresenter.setSetting(unionPay, percentEt.getText().toString());
    }

    @Override
    public void onSuccess(String result) {
        ToastUtils.showError("设置成功!", getApplication());
        finish();

    }

    @Override
    public void onSuccessSetting(ConsumBillSettingResult result) {

        if(result.unionPay == 1){
            consumeCk.setChecked(true);
        }

        percentEt.setText((int)result.unionPayDiscount + "");
    }
}
