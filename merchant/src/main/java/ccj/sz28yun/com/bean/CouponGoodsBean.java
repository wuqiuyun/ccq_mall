package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/12/19.
 */
public class CouponGoodsBean {

    @SerializedName(value = "abs_goods_image")
    private String absGoodsImage;
    @SerializedName(value = "spec_name")
    private String specName;
    @SerializedName(value = "goods_costprice")
    private double goodsCostprice;
    @SerializedName(value = "goods_union_id")
    private String goodsUnionId;
    @SerializedName(value = "goods_name")
    private String goodsName;

    public int countChoose;//用来做套餐里面的逻辑用

    public String getAbsGoodsImage() {
        return absGoodsImage;
    }

    public void setAbsGoodsImage(String absGoodsImage) {
        this.absGoodsImage = absGoodsImage;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public double getGoodsCostprice() {
        return goodsCostprice;
    }

    public void setGoodsCostprice(double goodsCostprice) {
        this.goodsCostprice = goodsCostprice;
    }

    public String getGoodsUnionId() {
        return goodsUnionId;
    }

    public void setGoodsUnionId(String goodsUnionId) {
        this.goodsUnionId = goodsUnionId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
