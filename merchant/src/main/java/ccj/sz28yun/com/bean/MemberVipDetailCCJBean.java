package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by meihuali on 2017/6/17.
 */

public class MemberVipDetailCCJBean {

    @SerializedName(value = "order_id")
    private String orderId;
    @SerializedName(value = "store_name")
    private String storeName;
    @SerializedName(value = "order_sn")
    private String orderSn;
    @SerializedName(value = "store_id")
    private String storeId;
    @SerializedName(value = "check_number")
    public String checkNumber;
    @SerializedName(value = "member_name")
    private String memberName;
    @SerializedName(value = "goods_price")
    public String goodsPrice;
    @SerializedName(value = "goods_marketprice")
    public String markPrice;
    @SerializedName(value = "goods_name")
    public String goodsName;
    @SerializedName(value = "goods_image")
    private String goodsImage;
    @SerializedName(value = "status")
    private String status;
    @SerializedName(value = "time")
    private String time;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getMarkPrice() {
        return markPrice;
    }

    public void setMarkPrice(String markPrice) {
        this.markPrice = markPrice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }
}
