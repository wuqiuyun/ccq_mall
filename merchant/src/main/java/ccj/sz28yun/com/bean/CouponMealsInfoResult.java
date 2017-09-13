package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sue on 2017/1/5.
 */
public class CouponMealsInfoResult {

    @SerializedName(value = "data1")
    public CouponMealsParams couponMealsParams;

    @SerializedName(value = "data2")
    public ArrayList<CouponMealsImageBean> couponMealsImageArrayList;

    @SerializedName(value = "data3")
    public ArrayList<CouponMealsItemBean> couponMealsItemArrayList;
}
