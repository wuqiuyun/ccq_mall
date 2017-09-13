package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sue on 2016/12/19.
 */
public class FinanceMonthResult {

    public double sum;//月合计

    @SerializedName(value = "data1")
    private FinanceStatisticBean financeStatisticBean;
    @SerializedName(value = "data2")
    private ArrayList<FinanceMonthBean>  financeMonthBeanArrayList;

    public FinanceStatisticBean getFinanceStatisticBean() {
        return financeStatisticBean;
    }

    public void setFinanceStatisticBean(FinanceStatisticBean financeStatisticBean) {
        this.financeStatisticBean = financeStatisticBean;
    }

    public ArrayList<FinanceMonthBean> getFinanceMonthBeanArrayList() {
        return financeMonthBeanArrayList;
    }

    public void setFinanceMonthBeanArrayList(ArrayList<FinanceMonthBean> financeMonthBeanArrayList) {
        this.financeMonthBeanArrayList = financeMonthBeanArrayList;
    }
}
