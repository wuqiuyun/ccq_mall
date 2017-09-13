package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/12/19.
 */
public class FinanceStatisticBean {

    @SerializedName(value = "current")
    private double current;
    @SerializedName(value = "fee")
    private double fee;
    @SerializedName(value = "total")
    private double total;
    @SerializedName(value = "tixian")
    private int tixian;

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getTixian() {
        return tixian;
    }

    public void setTixian(int tixian) {
        this.tixian = tixian;
    }
}
