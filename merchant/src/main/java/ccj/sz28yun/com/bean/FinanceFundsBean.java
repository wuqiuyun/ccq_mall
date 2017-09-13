package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/12/19.
 */
public class FinanceFundsBean {

    @SerializedName(value = "current_balance")
    private double totalBalance;
    @SerializedName(value = "current_merchants")
    private double totalMerchants;
    @SerializedName(value = "current_vip")
    private double totalVip;
    @SerializedName(value = "current_chan")
    private double totalChan;
    @SerializedName(value = "current_promote")
    private double totalPromote;

    public double getTotalPromote() {
        return totalPromote;
    }

    public void setTotalPromote(double totalPromote) {
        this.totalPromote = totalPromote;
    }

    public double getTotalMerchants() {
        return totalMerchants;
    }

    public void setTotalMerchants(double totalMerchants) {
        this.totalMerchants = totalMerchants;
    }

    public double getTotalVip() {
        return totalVip;
    }

    public void setTotalVip(double totalVip) {
        this.totalVip = totalVip;
    }

    public double getTotalChan() {
        return totalChan;
    }

    public void setTotalChan(double totalChan) {
        this.totalChan = totalChan;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }
}
