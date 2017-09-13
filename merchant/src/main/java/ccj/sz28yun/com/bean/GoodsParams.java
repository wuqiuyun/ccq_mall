package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/12/4.
 */
public class GoodsParams {

    @SerializedName(value = "token")
    public String token;
    @SerializedName(value = "store_id")
    public String merchatId;   //商家id
    @SerializedName(value = "goods_name")
    public String goodsName;  //商品名称
    @SerializedName(value = "goods_images")
    public String goodsImages;  //商品图片(请用上传的图片ID组合成JSON,demo[1,2,3,4,5]）
    @SerializedName(value = "gc_id_1")
    public String goodsCategoryFirst;  //一级分类id
    @SerializedName(value = "gc_id_2")
    public String goodsCategorySecond;  //二级分类id
    @SerializedName(value = "gc_id_3")
    public String goodsCategoryThird;  //三级分类id
    @SerializedName(value = "goods_costprice")
    public double goodsOriginalPrice;  //原价
    @SerializedName(value = "goods_salenum")
    public int goodsSaleCount;   //默认销售量(销售量不得超过100)
    @SerializedName(value = "spec_id")
    public String goodsStandard;  //商品规格id
    @SerializedName(value = "goods_union_id")
    public String goodsUnionId;  //商品id
    @SerializedName(value = "origin_images")
    public String originImages;  //旧商品图片id,格式同goods_images 修改必填

    public GoodsParams(String token, String merchatId) {
        this.token = token;
        this.merchatId = merchatId;
    }



}
