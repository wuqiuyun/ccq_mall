package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2017/1/3.
 */
public class MerchantServiceBean {

    @SerializedName(value = "token")
    public String token;
    @SerializedName(value = "store_id")
    public String merchantId;

    @SerializedName(value = "service_end_time")
    private String serviceEndTime;
    @SerializedName(value = "is_child")
    private int isChild;
    @SerializedName(value = "is_ticket")
    private int isTicket;
//    @SerializedName(value = "adv")
//    private String adv;
    @SerializedName(value = "service_start_time")
    private String serviceStartTime;
    @SerializedName(value = "is_wifi")
    private int isWifi;
    @SerializedName(value = "is_smoke")
    private int isSmoke;
    @SerializedName(value = "is_order")
    private int isOrder;

    public String getServiceEndTime() {
        return serviceEndTime;
    }

    public void setServiceEndTime(String serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
    }

    public int getIsChild() {
        return isChild;
    }

    public void setIsChild(int isChild) {
        this.isChild = isChild;
    }

    public int getIsTicket() {
        return isTicket;
    }

    public void setIsTicket(int isTicket) {
        this.isTicket = isTicket;
    }

//    public String getAdv() {
//        return adv;
//    }
//    public void setAdv(String adv) {
//        this.adv = adv;
//    }

    public String getServiceStartTime() {
        return serviceStartTime;
    }

    public void setServiceStartTime(String serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

    public int getIsWifi() {
        return isWifi;
    }

    public void setIsWifi(int isWifi) {
        this.isWifi = isWifi;
    }

    public int getIsSmoke() {
        return isSmoke;
    }

    public void setIsSmoke(int isSmoke) {
        this.isSmoke = isSmoke;
    }

    public int getIsOrder() {
        return isOrder;
    }

    public void setIsOrder(int isOrder) {
        this.isOrder = isOrder;
    }
}
