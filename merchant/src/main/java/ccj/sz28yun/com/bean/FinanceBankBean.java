package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/12/19.
 */
public class FinanceBankBean {

    @SerializedName(value = "settlement_bank_name")
    private String settlementBankName;
    @SerializedName(value = "settlement_bank_account_name")
    private String settlementBankAccountName;
    @SerializedName(value = "settlement_bank_account_number")
    private String settlementBankAccountNumber;

    public String getSettlementBankName() {
        return settlementBankName;
    }

    public void setSettlementBankName(String settlementBankName) {
        this.settlementBankName = settlementBankName;
    }

    public String getSettlementBankAccountName() {
        return settlementBankAccountName;
    }

    public void setSettlementBankAccountName(String settlementBankAccountName) {
        this.settlementBankAccountName = settlementBankAccountName;
    }

    public String getSettlementBankAccountNumber() {
        return settlementBankAccountNumber;
    }

    public void setSettlementBankAccountNumber(String settlementBankAccountNumber) {
        this.settlementBankAccountNumber = settlementBankAccountNumber;
    }
}
