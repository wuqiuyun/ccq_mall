package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/12/4.
 */
public class ConsumeBillBean {

    @SerializedName(value = "order_amount")
    private double orderAmount;
    @SerializedName(value = "add_time")
    private String addTime;
    @SerializedName(value = "order_sn")
    private String orderSn;
    @SerializedName(value = "member_mobile")
    private int memberMobile;
    @SerializedName(value = "order_id")
    private String orderId;
    @SerializedName(value = "goods_amount")
    private double goodsAmount;
    @SerializedName(value = "order_state")
    private String orderState;

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public int getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(int memberMobile) {
        this.memberMobile = memberMobile;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(double goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }
}
