package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2017/1/4.
 */
public class FinanceApplyDepositBean {

    @SerializedName(value = "amount")
    public double amount;
    @SerializedName(value = "settlement_bank_name")
    public String settlementBankName;
    @SerializedName(value = "settlement_bank_account_number")
    public String settlementBankAccountNumber;
    @SerializedName(value = "settlement_bank_account_name")
    public String settlementBankAccountName;
}
