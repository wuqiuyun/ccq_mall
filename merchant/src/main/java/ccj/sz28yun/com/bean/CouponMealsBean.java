package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/12/19.
 */
public class CouponMealsBean {

    @SerializedName(value = "goods_name")
    public String goodsName;
    @SerializedName(value = "discount_money")
    public String discountMoney;
    @SerializedName(value = "goods_price")
    public String goodsPrice;
    @SerializedName(value = "goods_storage")
    public String goodsStorage;
    @SerializedName(value = "goods_image")
    public String goodsImage;
    @SerializedName(value = "goods_id")
    public String goodsId;
    @SerializedName(value = "remark")
    public String remark;
    @SerializedName(value = "bunding_state")
    public String bundingState;
    @SerializedName(value = "state")
    public String state;
    @SerializedName(value = "discount")
    public String discount;
    @SerializedName(value = "goods_marketprice")
    public String goodsMarketprice;
    @SerializedName(value = "ischan")
    public String ischan;
    @SerializedName(value = "goods_verifyremark")
    public String goodsverifyremark;
}
