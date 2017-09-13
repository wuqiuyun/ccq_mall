package clerk.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 子账号业绩
 * Created by sue on 2016/11/21.
 */
public class PerformanceChildMemberBean {

    @SerializedName(value = "sum_vip_member")
    private int sumVipMember;
    @SerializedName(value = "sum_member")
    private int sumMember;
    @SerializedName(value = "date")
    private String date;
    @SerializedName(value = "sum_store")//进驻商家数量
    private String sum_store;
    @SerializedName(value = "sum_store_joinin")//提交商家数量
    private String sum_store_joinin;

    public int getSumVipMember() {
        return sumVipMember;
    }

    public void setSumVipMember(int sumVipMember) {
        this.sumVipMember = sumVipMember;
    }

    public int getSumMember() {
        return sumMember;
    }

    public void setSumMember(int sumMember) {
        this.sumMember = sumMember;
    }

    public String getSum_store() {
        return sum_store;
    }

    public void setSum_store(String sum_store) {
        this.sum_store = sum_store;
    }

    public String getSum_store_joinin() {
        return sum_store_joinin;
    }

    public void setSum_store_joinin(String sum_store_joinin) {
        this.sum_store_joinin = sum_store_joinin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
