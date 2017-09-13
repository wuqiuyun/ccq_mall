package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 商家提现记录/冻结记录详情
 * Created by sue on 2017/1/4.
 */
public class FinanceDepositFreeDetailsBean {

    @SerializedName(value = "name")
    private String name;
    @SerializedName(value = "bank_name")
    private String bankName;
    @SerializedName(value = "card_account")
    private String cardAccount;
    @SerializedName(value = "status")
    private int status;
    @SerializedName(value = "re_note")
    private String reNote;
    @SerializedName(value = "verify_time")
    private String verifyTime;
    @SerializedName(value = "amount")
    private double amount;
    @SerializedName(value = "time")
    private String time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardAccount() {
        return cardAccount;
    }

    public void setCardAccount(String cardAccount) {
        this.cardAccount = cardAccount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReNote() {
        return reNote;
    }

    public void setReNote(String reNote) {
        this.reNote = reNote;
    }

    public String getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(String verifyTime) {
        this.verifyTime = verifyTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
