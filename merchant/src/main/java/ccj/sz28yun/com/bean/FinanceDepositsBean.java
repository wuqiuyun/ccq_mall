package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/12/19.
 */
public class FinanceDepositsBean {

    @SerializedName(value = "id")
    private String id;
    @SerializedName(value = "status")
    private int status;
    @SerializedName(value = "amount")
    private double amount;
    @SerializedName(value = "time")
    private String time;
    @SerializedName(value = "card_account")
    private String cardAccount;

    public int getStatus() {
        return status;
    }

    public String getStatusName() {
        String name  = "";
        switch(status){
            case -1:
                name = "失败";
                break;
            case 0:
                name = "审核中";
                break;
            case 2:
                name = "成功";
                break;

        }
        return name;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getCardAccount() {
        return cardAccount;
    }

    public void setCardAccount(String cardAccount) {
        this.cardAccount = cardAccount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
