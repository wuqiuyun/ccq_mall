package clerk.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/11/18.
 */
public class PerformanceDayBean  extends  PerformanceBean{

    @SerializedName(value = "datetime")
    private long datetime;
    @SerializedName(value = "order_sn")
    private String orderSn;
    @SerializedName(value = "member_name")
    private String memberName;
    @SerializedName(value = "value")
    private double value;


    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
