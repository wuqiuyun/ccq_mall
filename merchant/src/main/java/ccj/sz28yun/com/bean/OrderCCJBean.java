package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sue on 2016/12/7.
 */
public class OrderCCJBean implements Serializable {

    @SerializedName(value = "image")
    private String image;
    @SerializedName(value = "order_amount")
    private String orderAmount;
    @SerializedName(value = "payment_time")
    private String paymentTime;
    @SerializedName(value = "store_address")
    private String address;
    @SerializedName(value = "order_state")
    private String orderState;
    @SerializedName(value = "add_time")
    private String addTime;
    @SerializedName(value = "order_sn")
    private String orderSn;
    @SerializedName(value = "discount")
    private String discount;
    @SerializedName(value = "finnshed_time")
    private String finnshedTime;
    @SerializedName(value = "goods_name")
    private String goodsName;
    @SerializedName(value = "order_id")
    private String orderId;
    @SerializedName(value = "check_number")
    public String checkNumber;
    @SerializedName(value = "user_name")
    public String userName;
    @SerializedName(value = "goods_marketprice")
    public String markPrice;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
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

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(double String) {
        this.discount = discount;
    }

    public String getFinnshedTime() {
        return finnshedTime;
    }

    public void setFinnshedTime(String finnshedTime) {
        this.finnshedTime = finnshedTime;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {

        this.userName = userName;
    }

    public String getMarkPrice() {
        return markPrice;
    }

    public void setMarkPrice(String markPrice) {
        this.markPrice = markPrice;
    }
}
