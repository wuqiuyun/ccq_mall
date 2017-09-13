package clerk.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sue on 2016/11/16.
 */
public class HomeBean {

    @SerializedName(value = "member_num")
    private int memberNum; //注册会员数量
    @SerializedName(value = "vip_member_num")
    private int memberVipNum; //VIP会员数量
    @SerializedName(value = "store_num")
    private int merchantNum; //商家数量
    @SerializedName(value = "account")
    private FundsStatisticBean account;
    @SerializedName(value = "add_list")
    private ArrayList<HomeMerchantBean> merchantList;

    public int getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(int memberNum) {
        this.memberNum = memberNum;
    }

    public int getMemberVipNum() {
        return memberVipNum;
    }

    public void setMemberVipNum(int memberVipNum) {
        this.memberVipNum = memberVipNum;
    }

    public int getMerchantNum() {
        return merchantNum;
    }

    public void setMerchantNum(int merchantNum) {
        this.merchantNum = merchantNum;
    }

    public FundsStatisticBean getAccount() {
        return account;
    }

    public void setAccount(FundsStatisticBean account) {
        this.account = account;
    }

    public ArrayList<HomeMerchantBean> getMerchantList() {
        return merchantList;
    }

    public void setMerchantList(ArrayList<HomeMerchantBean> merchantList) {
        this.merchantList = merchantList;
    }
}
