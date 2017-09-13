package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.FinanceBankBean;
import ccj.sz28yun.com.bean.FinanceFundsBean;
import ccj.sz28yun.com.presenter.FinancePresenter;

/**
 * Created by sue on 2016/12/19.
 */
public class FinanceActivity extends CCJActivity implements FinancePresenter.FinanceView {


    @Bind(R.id.payment_total_tv)
    TextView paymentTotalTv;
    @Bind(R.id.payment_ccj_tv)
    TextView paymentCcjTv;
    @Bind(R.id.payment_vip_tv)
    TextView paymentVipTv;
    @Bind(R.id.payment_business_tv)
    TextView paymentBusinessTv;
    @Bind(R.id.payment_business_ll)
    LinearLayout paymentBusinessLl;
    @Bind(R.id.payment_expand_tv)
    TextView paymentExpandTv;
    @Bind(R.id.payment_expand_ll)
    LinearLayout paymentExpandLl;
    @Bind(R.id.bank_name_tv)
    TextView bankNameTv;
    @Bind(R.id.bank_user_name_tv)
    TextView bankUserNameTv;
    @Bind(R.id.bank_account_tv)
    TextView bankAccountTv;

    private FinancePresenter financePresenter;

    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, FinanceActivity.class);
        return intent;
    }


    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_finance;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        financePresenter = new FinancePresenter(getActivity(), this);
        financePresenter.getFundsAndBankCards();
    }



    @OnClick({R.id.payment_ccj_ll, R.id.payment_vip_ll, R.id.payment_business_ll, R.id.payment_expand_ll, R.id.payment_total_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.payment_total_ll:
                startActivity(FinanceTypeActivity.startIntent(getActivity(), 5));

                break;
            case R.id.payment_ccj_ll:
                startActivity(FinanceTypeActivity.startIntent(getActivity(), 8));
                break;
            case R.id.payment_vip_ll:
                startActivity(FinanceTypeActivity.startIntent(getActivity(), 7));
                break;
            case R.id.payment_business_ll:
                startActivity(FinanceTypeActivity.startIntent(getActivity(), 2));
                break;
            case R.id.payment_expand_ll:
                startActivity(FinanceTypeActivity.startIntent(getActivity(), 1));
                break;
            default:
                break;
        }
    }



    @Override
    public void onSuceessBankCard(FinanceBankBean bean) {
        if(null != bean){
            bankNameTv.setText(bean.getSettlementBankName());
            bankUserNameTv.setText(bean.getSettlementBankAccountName());
            bankAccountTv.setText(bean.getSettlementBankAccountNumber());
            findViewById(R.id.bank_info_ll).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.bank_info_ll).setVisibility(View.GONE);
        }

    }

    @Override
    public void onSuccess(FinanceFundsBean result) {

        paymentTotalTv.setText(new StringBuilder(result.getTotalBalance() + "").append("元"));
        paymentCcjTv.setText(new StringBuilder(result.getTotalChan() + "").append("元"));
        paymentVipTv.setText(new StringBuilder(result.getTotalVip() + "").append("元"));
        paymentBusinessTv.setText(new StringBuilder(result.getTotalMerchants() + "").append("元"));
        paymentExpandTv.setText(new StringBuilder(result.getTotalPromote() + "").append("元"));


    }
}
