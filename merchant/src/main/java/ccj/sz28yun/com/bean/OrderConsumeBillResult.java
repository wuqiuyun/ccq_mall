package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sue on 2016/12/7.
 */
public class OrderConsumeBillResult {

    @SerializedName(value = "data1")
    private ArrayList<OrderStatisticBean> statisticBeanArrayList;


    @SerializedName(value = "data2")
    private ArrayList<OrderConsumeBillBean> orderConsumeBillBeanArrayList;

    public ArrayList<OrderStatisticBean> getStatisticBeanArrayList() {
        return statisticBeanArrayList;
    }

    public void setStatisticBeanArrayList(ArrayList<OrderStatisticBean> statisticBeanArrayList) {
        this.statisticBeanArrayList = statisticBeanArrayList;
    }

    public ArrayList<OrderConsumeBillBean> getOrderConsumeBillBeanArrayList() {
        return orderConsumeBillBeanArrayList;
    }

    public void setOrderConsumeBillBeanArrayList(ArrayList<OrderConsumeBillBean> orderConsumeBillBeanArrayList) {
        this.orderConsumeBillBeanArrayList = orderConsumeBillBeanArrayList;
    }
}
