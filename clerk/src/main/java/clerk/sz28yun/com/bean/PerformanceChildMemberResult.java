package clerk.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * 子账号业绩返回数据
 * Created by sue on 2016/11/18.
 */
public class PerformanceChildMemberResult {

    private ArrayList<PerformanceChildMemberBean> list;

    @SerializedName(value = "count")
    private PerformanceChildStatistic statistic;

    public void initialize(){
        if(null == list){
            list = new ArrayList<>();
        }
        if(null == statistic){
            statistic = new PerformanceChildStatistic();
        }
    }

    public PerformanceChildStatistic getStatistic() {
        return statistic;
    }

    public void setStatistic(PerformanceChildStatistic statistic) {
        this.statistic = statistic;
    }

    public ArrayList<PerformanceChildMemberBean> getList() {
        return list;
    }

    public void setList(ArrayList<PerformanceChildMemberBean> list) {
        this.list = list;
    }

    public class PerformanceChildStatistic{
        @SerializedName(value = "vip_member_num")
        public int vipMemberNum;
        @SerializedName(value = "member_num")
        public int memberNum;
        @SerializedName(value = "store_num")
        public int storeNum;
        @SerializedName(value = "add_store_num")
        public int addStoreNum;
    }
}
