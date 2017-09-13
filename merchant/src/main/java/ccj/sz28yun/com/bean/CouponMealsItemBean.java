package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sue on 2017/1/5.
 */
public class CouponMealsItemBean implements Serializable{

    @SerializedName(value = "sum")
    public int sum;
    @SerializedName(value = "choose_num")
    public int chooseNum;
    @SerializedName(value = "title")
    public String title;
    @SerializedName(value = "content")
    public String content;
    @SerializedName(value = "id")
    public String id;
}
