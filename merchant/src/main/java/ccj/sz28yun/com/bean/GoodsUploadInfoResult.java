package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meihuali on 2017/6/15.
 */

public class GoodsUploadInfoResult {

    @SerializedName(value = "goods_union_id")
    private String goodsUnionId;
    @SerializedName(value = "goods_name")
    private String goodsName;
    @SerializedName(value = "gc_id_1")
    private String gcId1;
    @SerializedName(value = "gc_id_2")
    private String gcId2;
    @SerializedName(value = "gc_id_3")
    private String gcId3;
    @SerializedName(value = "goods_costprice")
    private double goodsCostprice;
    @SerializedName(value = "goods_salenum")
    private int goodsSalenum;
    @SerializedName(value = "spec_id")
    private String specId;
    @SerializedName(value = "spec_name")
    private String specName;
    @SerializedName(value = "gc_name_3")
    private String gcName3;
    @SerializedName(value = "gc_name_2")
    private String gcName2;
    @SerializedName(value = "gc_name_1")
    private String gcName1;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    @SerializedName(value = "store_id")
    private String storeId;
    @SerializedName(value = "image")
    public ArrayList<GoodsUploadImageBean> goodsUploadImageBean;


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

    public String getGcId1() {
        return gcId1;
    }

    public void setGcId1(String gcId1) {
        this.gcId1 = gcId1;
    }

    public String getGcId2() {
        return gcId2;
    }

    public void setGcId2(String gcId2) {
        this.gcId2 = gcId2;
    }

    public String getGcId3() {
        return gcId3;
    }

    public void setGcId3(String gcId3) {
        this.gcId3 = gcId3;
    }

    public double getGoodsCostprice() {
        return goodsCostprice;
    }

    public void setGoodsCostprice(double goodsCostprice) {
        this.goodsCostprice = goodsCostprice;
    }

    public int getGoodsSalenum() {
        return goodsSalenum;
    }

    public void setGoodsSalenum(int goodsSalenum) {
        this.goodsSalenum = goodsSalenum;
    }

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getGcName3() {
        return gcName3;
    }

    public void setGcName3(String gcName3) {
        this.gcName3 = gcName3;
    }

    public String getGcName2() {
        return gcName2;
    }

    public void setGcName2(String gcName2) {
        this.gcName2 = gcName2;
    }

    public String getGcName1() {
        return gcName1;
    }

    public void setGcName1(String gcName1) {
        this.gcName1 = gcName1;
    }

    public ArrayList<GoodsUploadImageBean> getGoodsUploadImageBean() {
        return goodsUploadImageBean;
    }

    public void setGoodsUploadImageBean(ArrayList<GoodsUploadImageBean> goodsUploadImageBean) {
        this.goodsUploadImageBean = goodsUploadImageBean;
    }

}
