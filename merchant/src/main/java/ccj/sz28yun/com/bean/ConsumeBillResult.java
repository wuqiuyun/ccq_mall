package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sue on 2016/12/4.
 */
public class ConsumeBillResult {

    @SerializedName(value = "data1")
    private  ArrayList<NormalStatisticsBean> consumeStatisticsList;
    @SerializedName(value = "data2")
    private ArrayList<ConsumeBillBean> list;


    public ArrayList<NormalStatisticsBean> getConsumeStatisticsList() {
        return consumeStatisticsList;
    }

    public void setConsumeStatisticsList(ArrayList<NormalStatisticsBean> consumeStatisticsList) {
        this.consumeStatisticsList = consumeStatisticsList;
    }

    public ArrayList<ConsumeBillBean> getList() {
        return list;
    }

    public void setList(ArrayList<ConsumeBillBean> list) {
        this.list = list;
    }
}
