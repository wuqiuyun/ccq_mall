package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/12/4.
 */
public class NormalStatisticsBean {

    @SerializedName(value = "num")
    private double num;
    @SerializedName(value = "title")
    private String title;
    @SerializedName(value = "order_amount")
    private String order_amount;
    @SerializedName(value = "pay_amount")
    private String pay_amount;

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public String getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }
}
