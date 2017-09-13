package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 发布套餐提交的参数
 * Created by sue on 2017/1/4.
 */
public class CouponMealsParams {

    @SerializedName(value = "token")
    public String token;
    @SerializedName(value = "store_id")
    public String merchatId;   //商家id
    @SerializedName(value = "goods_id")
    public String goodsId;   //套餐ID
    @SerializedName(value = "origin_images")
    public String originalImages; //未修改图片id(格式见以下范例)


    @SerializedName(value = "goods_name")
    public String goodsName; //套餐名称
    @SerializedName(value = "images")
    public String images; //套餐图片
    @SerializedName(value = "goods_detail")
    public String goodsDetails; //套餐详情
    @SerializedName(value = "goods_marketprice")
    public String goodsPriceStroe; //门店销售价
    @SerializedName(value = "goods_costprice")
    public String goodsPriceCCJ; //餐餐抢价
    @SerializedName(value = "people")
    public int people; //默认用餐人数 (0单人餐 1双人餐 3多人餐)
    @SerializedName(value = "chain")
    public String chain; //分店(格式见以下范例)
    @SerializedName(value = "offer_start_time")
    public String offStartTime; //可使用时间起始时间
    @SerializedName(value = "offer_end_time")
    public String offEndTime; //可使用时间截至时间
    @SerializedName(value = "valid_start_time")
    public String effectStartTime; //套餐有效期起始时间
    @SerializedName(value = "valid_end_time")
    public String effectEndTime; //套餐有效期结束时间
    @SerializedName(value = "benefit")
    public String favorable; //同时享有优惠
    @SerializedName(value = "is_tuijian")
    public int isRecommend; //是否推荐产品 (0否 1是)
    @SerializedName(value = "is_sex")
    public int sexType = -1; //是否有性别要求 (0不要求 1 只限男性 2 只限女性)

    @SerializedName(value = "is_voucher")
    public int certificate = -1; //是否需要携带证件 (0不需要 1身份证)

    @SerializedName(value = "is_order")
    public int sfyuyue = -1; //是否需要预约 (0否 1是)

    @SerializedName(value = "goods_storage")
    public String discountLimitCount; //发布折扣券数量

    @SerializedName(value = "goods_salenum")
    public String discountDefult; //默认销售量

    @SerializedName(value = "remark")
    public String remark; //套餐描述


    @SerializedName(value = "goods_price")
    public String goodsPriceCCJ2; //餐餐抢价 返回时用
}
