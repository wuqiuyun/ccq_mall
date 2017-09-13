package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.OrderCCJBean;
import ccj.sz28yun.com.presenter.VerifyPresenter;
import per.sue.gear2.controll.GearImageLoad;
import per.sue.gear2.utils.ToastUtils;
import per.sue.gear2.widget.CleanableEditText;

/**
 * 输入验证界面
 * Created by sue on 2016/12/4.
 */
public class VerifyInputActivity extends CCJActivity implements VerifyPresenter.VerifyView {

    @Bind(R.id.code_et)
    CleanableEditText codeEt;
    @Bind(R.id.submit_btn)
    Button submitBtn;
    @Bind(R.id.yz_success_line)
    LinearLayout yzSuccessLine;
    VerifyPresenter verifyPresenter ;

    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, VerifyInputActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_verify_input;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        verifyPresenter = new VerifyPresenter(getActivity(), this);
        submitBtn.setEnabled(false);
        codeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(codeEt.getText())){
                    submitBtn.setEnabled(false);
                }else{
                    submitBtn.setEnabled(true);
                }

            }
        });
    }


    @OnClick(R.id.submit_btn)
    public void onClick() {

        if(TextUtils.isEmpty(codeEt.getText())){
            codeEt.requestFocus();
            codeEt.setError("请输入验证码");
        }else{
            verifyPresenter.verifyCode(codeEt.getText().toString());
        }

    }

    @Bind(R.id.order_num_tv)
    TextView orderNumTv;
    @Bind(R.id.date_tv)
    TextView dateTv;
    @Bind(R.id.avatar_iv)
    ImageView avatarIv;
    @Bind(R.id.percent_tv)
    TextView percentTv;
    @Bind(R.id.user_name_tv)
    TextView userNameTv;
    @Bind(R.id.goods_name_tv)
    TextView goodsNameTv;
    @Bind(R.id.ccj_code_tv)
    TextView ccjCodeTv;
    @Bind(R.id.price_sale_tv)
    TextView priceSaleTv;
    @Bind(R.id.collect_cash_tv)
    TextView collectCashTv;
    @Bind(R.id.address_tv)
    TextView addressTv;
    @Bind(R.id.status_tv)
    TextView statusTv;

    @Override
    public void onSuccess(OrderCCJBean result) {

        yzSuccessLine.setVisibility(View.VISIBLE);
        orderNumTv.setText(new StringBuilder("订单号: ").append(result.getOrderSn()));
        dateTv.setText(result.getAddTime() + "");
        userNameTv.setText(new StringBuilder("用户: ").append(result.getUserName()));
        goodsNameTv.setText(new StringBuilder("商品名称: ").append(result.getGoodsName()));
        ccjCodeTv.setText(new StringBuilder("餐餐券码: ").append(result.getCheckNumber()));
        priceSaleTv.setText(new StringBuilder("销售价格: ").append(result.getMarkPrice()) + "");
        collectCashTv.setText(new StringBuilder("收取现金: ").append(result.getOrderAmount()) + "");
        addressTv.setText(new StringBuilder("").append(result.getAddress()) + "");
        percentTv.setText(result.getDiscount()  + "折");
        GearImageLoad.getSingleton(getActivity()).load( result.getImage(), avatarIv);
        statusTv.setVisibility(View.GONE);

        ToastUtils.showError("验证成功", getApplication());

    }
}
