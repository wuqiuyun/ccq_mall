package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Meihuali on 2017/6/14.
 */

public class GoodsUploadResult {
    @SerializedName(value = "goods_union_id")
    public String goodsUnionId;//商品id
    @SerializedName(value = "goods_name")
    public String goodsName;//商品名称
    @SerializedName(value = "goods_image")
    public String goodsImage;//商品图片
    @SerializedName(value = "goods_salenum")
    public String goodsSalenum;//销量
    @SerializedName(value = "goods_costprice")
    public String goodsCostprice;//原价
    @SerializedName(value = "spec_name")
    public String specName;//规格
    @SerializedName(value = "goods_addtime")
    public String goodsAddtime;//上传时间
    @SerializedName(value = "is_shelves")
    public String isShelves;//是否已上架 用于判断删除和修改按钮颜色 1：已上架（灰色），0：未上架（红色）

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

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public String getGoodsSalenum() {
        return goodsSalenum;
    }

    public void setGoodsSalenum(String goodsSalenum) {
        this.goodsSalenum = goodsSalenum;
    }

    public String getGoodsCostprice() {
        return goodsCostprice;
    }

    public void setGoodsCostprice(String goodsCostprice) {
        this.goodsCostprice = goodsCostprice;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getGoodsAddtime() {
        return goodsAddtime;
    }

    public void setGoodsAddtime(String goodsAddtime) {
        this.goodsAddtime = goodsAddtime;
    }

    public String getIsShelves() {
        return isShelves;
    }

    public void setIsShelves(String isShelves) {
        this.isShelves = isShelves;
    }

    @Override
    public String toString() {
        return "GoodsUploadResult{" +
                "goodsUnionId='" + goodsUnionId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsImage='" + goodsImage + '\'' +
                ", goodsSalenum='" + goodsSalenum + '\'' +
                ", goodsCostprice='" + goodsCostprice + '\'' +
                ", specName='" + specName + '\'' +
                ", goodsAddtime='" + goodsAddtime + '\'' +
                ", isShelves='" + isShelves + '\'' +
                '}';
    }
}
