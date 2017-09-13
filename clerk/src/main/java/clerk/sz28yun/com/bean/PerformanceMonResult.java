package clerk.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sue on 2016/11/18.
 */
public class PerformanceMonResult {

    @SerializedName(value = "list")
    private ArrayList<PerformanceMonBean> list;

    @SerializedName(value = "account")
    private PerformanceMerchantStatistics statistics;

    public ArrayList<PerformanceMonBean> getList() {
        return list;
    }

    public void setList(ArrayList<PerformanceMonBean> list) {
        this.list = list;
    }

    public PerformanceMerchantStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(PerformanceMerchantStatistics statistics) {
        this.statistics = statistics;
    }

    public class PerformanceMerchantStatistics {
        @SerializedName(value = "current_vip")
        public String currentVip; //vip奖金
        @SerializedName(value = "current_goods")
        public String currentGoods; // 餐餐抢奖金
        @SerializedName(value = "current_merchants")
        public String currentMerchants; //招商奖金
        @SerializedName(value = "current_promote")
        public String currentPromote; //推广奖金

    }
}
