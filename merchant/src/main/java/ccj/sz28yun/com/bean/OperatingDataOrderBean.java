package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/12/7.
 */
public class OperatingDataOrderBean {

    @SerializedName(value = "id")
    private String id;
    @SerializedName(value = "pay_order")
    private int payOrder;
    @SerializedName(value = "ccq_order")
    private int ccqOrder;
    @SerializedName(value = "year")
    private int year;
    @SerializedName(value = "package_order")
    private int packageOrder;
    @SerializedName(value = "online_order")
    private int onlineOrder;
    @SerializedName(value = "member_id")
    private String memberId;
    @SerializedName(value = "store_id")
    private String storeId;
    @SerializedName(value = "month")
    private int month;
    @SerializedName(value = "day")
    private int day;

    @SerializedName(value = "pay_order_per")
    private String payOrderPer;
    @SerializedName(value = "package_order_per")
    private String packageOrderPer;
    @SerializedName(value = "ccq_order_per")
    private String ccqOrderPer;


    public String getPayOrderPer() {
        return payOrderPer;
    }

    public void setPayOrderPer(String payOrderPer) {
        this.payOrderPer = payOrderPer;
    }

    public String getPackageOrderPer() {
        return packageOrderPer;
    }

    public void setPackageOrderPer(String packageOrderPer) {
        this.packageOrderPer = packageOrderPer;
    }

    public String getCcqOrderPer() {
        return ccqOrderPer;
    }

    public void setCcqOrderPer(String ccqOrderPer) {
        this.ccqOrderPer = ccqOrderPer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPayOrder() {
        return payOrder;
    }

    public void setPayOrder(int payOrder) {
        this.payOrder = payOrder;
    }

    public int getCcqOrder() {
        return ccqOrder;
    }

    public void setCcqOrder(int ccqOrder) {
        this.ccqOrder = ccqOrder;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPackageOrder() {
        return packageOrder;
    }

    public void setPackageOrder(int packageOrder) {
        this.packageOrder = packageOrder;
    }

    public int getOnlineOrder() {
        return onlineOrder;
    }

    public void setOnlineOrder(int onlineOrder) {
        this.onlineOrder = onlineOrder;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
