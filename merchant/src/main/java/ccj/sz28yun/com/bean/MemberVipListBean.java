package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by meihuali on 2017/6/16.
 */

public class MemberVipListBean {

    @SerializedName(value = "member_id")
    public String memberId;
    @SerializedName(value = "member_name")
    public String memberName;
    @SerializedName(value = "member_time")
    public String memberTime;
    @SerializedName(value = "order_num")
    public String orderNum;
    @SerializedName(value = "vip")
    public String vip;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberTime() {
        return memberTime;
    }

    public void setMemberTime(String memberTime) {
        this.memberTime = memberTime;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    @Override
    public String toString() {
        return "MemberVipListBean{" +
                "memberId='" + memberId + '\'' +
                ", memberName='" + memberName + '\'' +
                ", memberTime='" + memberTime + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", vip='" + vip + '\'' +
                '}';
    }
}
