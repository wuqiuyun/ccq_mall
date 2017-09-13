package clerk.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sue on 2016/11/21.
 */
public class PerformanceMerchantResult {

    @SerializedName(value = "store_list")
    private ArrayList<PerformanceMerchantBean> list;
    @SerializedName(value = "yunying")
    private PerformanceMerchantStatistics statistics;

    public void initialize(){
        if(null == list){
            list = new ArrayList<>();
        }
        if(null == statistics){
            statistics = new PerformanceMerchantStatistics();
        }
    }

    public ArrayList<PerformanceMerchantBean> getList() {
        return list;
    }

    public void setList(ArrayList<PerformanceMerchantBean> list) {
        this.list = list;
    }

    public PerformanceMerchantStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(PerformanceMerchantStatistics statistics) {
        this.statistics = statistics;
    }

    public class PerformanceMerchantStatistics{
        @SerializedName(value = "sale_goods_num")
        public int saleGoodsNum; //餐餐抢券数量
        @SerializedName(value = "sum_invite_vip_count")
        public int sumInviteVipCount; // vip会员
        @SerializedName(value = "invite_member_count")
        public int inviteMemberCount; //注册会员
        @SerializedName(value = "sale_check_goods_num")
        public int saleCheckGoodsNum; //已验证餐餐抢券数量
        @SerializedName(value = "sale_uncheck_goods_num")
        public int saleUncheckGoodsNum; //未验证餐餐抢券数量

    }


}
