package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2017/1/12.
 */
public class ConsumBillSettingResult {

    @SerializedName(value = "union_pay")
    public int unionPay;
    @SerializedName(value = "store_id")
    public int storeId;
    @SerializedName(value = "union_pay_discount")
    public double unionPayDiscount;
}
