package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/12/19.
 */
public class FinanceMonthBean {

    @SerializedName(value = "val")
    private double val;
    @SerializedName(value = "date")
    private String date;

    public double getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
