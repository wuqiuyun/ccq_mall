package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/12/4.
 */
public class ConsumeBillStatisticsBean {

    @SerializedName(value = "num")
    private double num;
    @SerializedName(value = "title")
    private String title;

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
