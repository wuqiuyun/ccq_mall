package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/12/7.
 */
public class OperatingMerchantFlowBean {

    @SerializedName(value = "stattime")
    private String stattime;
    @SerializedName(value = "sum")
    private int sum;

    public String getStattime() {
        return stattime;
    }

    public void setStattime(String stattime) {
        this.stattime = stattime;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
