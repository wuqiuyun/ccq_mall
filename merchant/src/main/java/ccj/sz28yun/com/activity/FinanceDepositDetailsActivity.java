package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.FinanceDepositFreeDetailsBean;
import ccj.sz28yun.com.presenter.FinanceDepositDetailsPresenter;

/**
 * Created by sue on 2017/1/4.
 */
public class FinanceDepositDetailsActivity extends CCJActivity implements FinanceDepositDetailsPresenter.FinanceDepositDetailsView {


    @Bind(R.id.bank_name_tv)
    TextView bankNameTv;
    @Bind(R.id.deposit_account_tv)
    TextView depositAccountTv;
    @Bind(R.id.bank_account_tv)
    TextView bankAccountTv;
    @Bind(R.id.deposit_funds_tv)
    TextView depositFundsTv;
    @Bind(R.id.deposit_apply_date_tv)
    TextView depositApplyDateTv;
    @Bind(R.id.aduit_date_tv)
    TextView aduitDateTv;
    @Bind(R.id.status_tv)
    TextView statusTv;

    private FinanceDepositDetailsPresenter financeDepositDetailsPresenter;

    public static Intent startIntent(Context context, String id, String title) {
        Intent intent = new Intent(context, FinanceDepositDetailsActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_finance_deposit_details;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        financeDepositDetailsPresenter = new FinanceDepositDetailsPresenter(getActivity(), this);
        String id = getIntent().getStringExtra("id");
        String title = getIntent().getStringExtra("title");
        setBarTitle(title);
        financeDepositDetailsPresenter.getDetails(id);
    }


    @Override
    public void onSuccess(FinanceDepositFreeDetailsBean result) {
        bankNameTv.setText(result.getBankName());
        depositAccountTv.setText(result.getName());
        bankAccountTv.setText(result.getCardAccount());
        depositFundsTv.setText(String.format("%s元",   result.getAmount()));
        depositApplyDateTv.setText(result.getTime());
        aduitDateTv.setText(result.getVerifyTime());
        statusTv.setText(getStatusName(result.getStatus()) );

    }

    //状态(-1失败,0未处理,1处理中,2成功)
    private String getStatusName(int status){
        String statusName = "";
        switch(status){
            case -1:
                statusName = "失败,";
                break;
            case 0:
                statusName = "未处理";
                break;
            case 1:
                statusName = "处理中";
                break;
            case 2:
                statusName = "成功";
                break;

        }
        return statusName;
    }
}
