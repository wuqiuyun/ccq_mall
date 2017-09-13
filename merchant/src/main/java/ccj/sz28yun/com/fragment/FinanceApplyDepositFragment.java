package ccj.sz28yun.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.activity.FinanceTypeActivity;
import ccj.sz28yun.com.base.CCJFragment;
import ccj.sz28yun.com.bean.FinanceApplyDepositBean;
import ccj.sz28yun.com.presenter.FinanceDepositApplyPresenter;
import per.sue.gear2.utils.DoubleUtil;
import per.sue.gear2.utils.ToastUtils;

/**
 * Created by sue on 2017/1/17.
 */
public class FinanceApplyDepositFragment extends CCJFragment implements FinanceDepositApplyPresenter.FinanceDepositDetailsView {


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

    public static Bundle getBundle(int type){
        Bundle bundle = new Bundle();
        bundle.putInt("type", type );
        return bundle;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_finance_apply_deposit;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        financeDepositApplyPresenter = new FinanceDepositApplyPresenter(getActivity(), this);
        type = getArguments().getInt("type", 1);
        financeDepositApplyPresenter.getAccountInfo(type + "");
    }

    @OnClick(R.id.submit_btn)
    public void onClick() {
        String funds = fundsEt.getText().toString();
        if(TextUtils.isEmpty(funds)){
            fundsEt.requestFocus();
            fundsEt.setError("请填写金额");
        }else if(!DoubleUtil.isDouble(funds)){
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
        ToastUtils.showError("提交成功, 请等待审核", getActivity().getApplication());
        if(getActivity() instanceof FinanceTypeActivity){
            ((FinanceTypeActivity)getActivity() ).changeToDepositsList();
        }

    }

    @Override
    public void onSuccess(FinanceApplyDepositBean result) {
        financeApplyDepositBean = result;
        fundsBalanceTv.setText(String.format("%s元", DoubleUtil.formatPrice(result.amount)));
        bankNameTv.setText(result.settlementBankName);
        bankUserNameTv.setText(result.settlementBankAccountName);
        bankAccountTv.setText(result.settlementBankAccountNumber);
    }
}
