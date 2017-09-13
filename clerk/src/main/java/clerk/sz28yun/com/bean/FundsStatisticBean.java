package clerk.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/12/21.
 */
public class FundsStatisticBean {

    @SerializedName(value = "current_goods")
    public String currentGoods;
    @SerializedName(value = "current_promote")
    public double currentPromote;
    @SerializedName(value = "current_vip")
    public double currentVip;
    @SerializedName(value = "current_merchants")
    public double currentMerchants;
}
