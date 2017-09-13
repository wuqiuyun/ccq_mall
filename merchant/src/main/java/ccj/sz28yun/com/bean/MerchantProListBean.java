package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sue on 2016/12/19.
 */
public class MerchantProListBean {

    @SerializedName(value = "goods_id")
    public int goodsId;
    @SerializedName(value = "goods_name")
    public String goodsName;
    @SerializedName(value = "goods_price")
    public double goodsPrice;
    @SerializedName(value = "goods_image")
    public String goodsImage;
    @SerializedName(value = "discount")
    public double discount;
    @SerializedName(value = "store_id")
    public String storeId;

}
