package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sue on 2016/12/7.
 */
public class OrderCCJResult {

    @SerializedName(value = "data1")
    private ArrayList<OrderStatisticBean> statisticBeanArrayList;


    @SerializedName(value = "data2")
    private ArrayList<OrderCCJBean> orderCCJBeanArrayList;

    public ArrayList<OrderStatisticBean> getStatisticBeanArrayList() {
        return statisticBeanArrayList;
    }

    public void setStatisticBeanArrayList(ArrayList<OrderStatisticBean> statisticBeanArrayList) {
        this.statisticBeanArrayList = statisticBeanArrayList;
    }

    public ArrayList<OrderCCJBean> getOrderCCJBeanArrayList() {
        return orderCCJBeanArrayList;
    }

    public void setOrderCCJBeanArrayList(ArrayList<OrderCCJBean> orderCCJBeanArrayList) {
        this.orderCCJBeanArrayList = orderCCJBeanArrayList;
    }
}
