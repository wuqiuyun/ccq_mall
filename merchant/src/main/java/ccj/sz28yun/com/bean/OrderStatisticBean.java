package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/12/7.
 */
public class OrderStatisticBean {

    @SerializedName(value = "num")
    private int num;
    @SerializedName(value = "title")
    private String title;

    @SerializedName(value = "order_amount")
    private String orderamount;

    public String getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(String orderamount) {

        this.orderamount = orderamount;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
