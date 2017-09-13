package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2017/1/3.
 */
public class MemberSysStatusBean {

    @SerializedName(value = "reason")
    private String reason;
    @SerializedName(value = "store_sms_sign")
    private String storeSmsSign;
    @SerializedName(value = "member_num")
    private int memberNum;
    @SerializedName(value = "vip_member_num")
    private int vipMemberNum;
    @SerializedName(value = "unit_price")
    private double unitPrice;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStoreSmsSign() {
        return storeSmsSign;
    }

    public void setStoreSmsSign(String storeSmsSign) {
        this.storeSmsSign = storeSmsSign;
    }

    public int getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(int memberNum) {
        this.memberNum = memberNum;
    }

    public int getVipMemberNum() {
        return vipMemberNum;
    }

    public void setVipMemberNum(int vipMemberNum) {
        this.vipMemberNum = vipMemberNum;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
