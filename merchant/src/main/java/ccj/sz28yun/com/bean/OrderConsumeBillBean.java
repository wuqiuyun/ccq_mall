package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sue on 2016/12/7.
 */
public class OrderConsumeBillBean implements Serializable{
    @SerializedName(value = "goods_amount")
    private String goodsAmount;
    @SerializedName(value = "order_sn")
    private String orderSn;
    @SerializedName(value = "order_id")
    private String orderId;
    @SerializedName(value = "order_amount")
    private String orderAmount;
    @SerializedName(value = "add_time")
    private String addTime;
    @SerializedName(value = "member_mobile")
    private String memberMobile;
    @SerializedName(value = "order_state")
    private String orderState;
    @SerializedName(value = "store_address")
    private String address;
    @SerializedName(value = "user_name")
    public String userName;

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    @SerializedName(value = "discount")
    public String discount;

    public String getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(String goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
