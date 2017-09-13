package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.FinanceApplyDepositBean;
import ccj.sz28yun.com.presenter.FinanceDepositApplyPresenter;
import per.sue.gear2.utils.DoubleUtil;
import per.sue.gear2.utils.ToastUtils;

/**
 * 申请提现
 * Created by sue on 2017/1/4.
 */
public class FinanceApplyDepositActivity extends CCJActivity implements FinanceDepositApplyPresenter.FinanceDepositDetailsView {


    @Bind(R.id.funds_balance_tv)
    TextView fundsBalanceTv;
    @Bind(R.id.bank_name_tv)
    TextView bankNameTv;
    @Bind(R.id.bank_user_name_tv)
    TextView bankUserNameTv;
    @Bind(R.id.bank_account_tv)
    TextView bankAccountTv;
    @Bind(R.id.funds_et)
    EditText fundsEt;

    private FinanceDepositApplyPresenter financeDepositApplyPresenter;
   private FinanceApplyDepositBean financeApplyDepositBean;
    private int type;
    public static Intent startIntent(Context context, int type) {
        Intent intent = new Intent(context, FinanceApplyDepositActivity.class);
        intent.putExtra("type", type);
        return intent;
    }



    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_finance_apply_deposit;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {

        financeDepositApplyPresenter = new FinanceDepositApplyPresenter(getActivity(), this);
        type = getIntent().getIntExtra("type", 1);
        financeDepositApplyPresenter.getAccountInfo(type + "");
    }



    @OnClick(R.id.submit_btn)
    public void onClick() {
        String funds = fundsEt.getText().toString();
        if(TextUtils.isEmpty(funds)){
            fundsEt.requestFocus();
            fundsEt.setError("请填写金额");
        }else if(DoubleUtil.isDouble(funds)){
            fundsEt.requestFocus();
            fundsEt.setError("输入内容不能包含错误字符");
        }else {
            double amount = Double.parseDouble(funds);
            if(amount < 10){
                fundsEt.requestFocus();
                fundsEt.setError("提现金额最小10元");
            }else if(amount > financeApplyDepositBean.amount){
                fundsEt.requestFocus();
                fundsEt.setError("提现金额不能大于可提现金额");
            }else{
                financeDepositApplyPresenter.apply(amount + "", type + "", financeApplyDepositBean.settlementBankName, financeApplyDepositBean.settlementBankAccountNumber, financeApplyDepositBean.settlementBankAccountName );
            }

        }
    }



    @Override
    public void onSuccessApply(String message) {
        ToastUtils.showError("提交成功, 请等待审核", getApplication());
        finish();

    }

    @Override
    public void onSuccess(FinanceApplyDepositBean result) {
        financeApplyDepositBean = result;
        fundsBalanceTv.setText(String.format("%s元", DoubleUtil.formatPrice(result.amount)));
        bankNameTv.setText(result.settlementBankName);
        bankUserNameTv.setText(result.settlementBankAccountName);
        bankAccountTv.setText(result.settlementBankAccountName);
    }
}
