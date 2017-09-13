package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2017/1/4.
 */
public class FinanceDayBean {

    @SerializedName(value = "order_sn")
    private String orderSn;
    @SerializedName(value = "value")
    private double value;
    @SerializedName(value = "datetime")
    private String datetime;
    @SerializedName(value = "user_name")
    private String userName;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
