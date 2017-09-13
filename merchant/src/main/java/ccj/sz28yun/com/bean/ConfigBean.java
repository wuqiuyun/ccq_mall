package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 系统配置数据
 * Created by sue on 2016/11/15.
 */
public class ConfigBean implements Serializable {

    @SerializedName(value = "API_DOMAIN")
    public String API_DOMAIN; //api域名 例如 app.28yun.com/index.php/webapi_V2/
    @SerializedName(value = "IMG_DOMAIN")
    public String IMG_DOMAIN;  //图片服务器域名 例如 http://112.74.172.160
    @SerializedName(value = "USERAGREEMENT")
    public String USERAGREEMENT;  //用户协议
    @SerializedName(value = "HP")
    public String HP;   //http:// 和 https:// 协议可以做切换
    @SerializedName(value = "REG")
    public String REG;  //暂无说明
    @SerializedName(value = "CHECK_PHONE")
    public String CHECK_PHONE; //商家后台审核人员电话

    @SerializedName(value = "MALL_FX")
    public String MALL_FX;   //商城App分享图片地址
    @SerializedName(value = "MALL_FX_N")
    public String MALL_FX_N;  //
    @SerializedName(value = "MALL_QRCODE_IMG_IOS")
    public String MALL_QRCODE_IMG_IOS; //商城App二维码地址IOS版
    @SerializedName(value = "MALL_QRCODE_IMG_IOS_URL")
    public String MALL_QRCODE_IMG_IOS_URL;  //商城App二维码地址IOS版url
    @SerializedName(value = "MALL_SERVICE")
    public String MALL_SERVICE; //商城App客服电话
    @SerializedName(value = "MALL_QRCODE_IMG_AZ")
    public String MALL_QRCODE_IMG_AZ; //商城App二维码地址安卓版
    @SerializedName(value = "MALL_QRCODE_IMG_AZ_URL")
    public String MALL_QRCODE_IMG_AZ_URL; //商城App二维码地址安卓版url
    @SerializedName(value = "MALL_WITHDRAW_EXPLAIN")
    public String MALL_WITHDRAW_EXPLAIN;
    @SerializedName(value = "MALL_FP")
    public String MALL_FP;  //商城App首屏广告图
    @SerializedName(value = "MALL_HELP")
    public String MALL_HELP; //商城帮助中心
    @SerializedName(value = "MALL_RED_PACKET")
    public String MALL_RED_PACKET; //红包规则

    @SerializedName(value = "STORE_QRCODE_IMG_IOS_URL")
    public String STORE_QRCODE_IMG_IOS_URL; //商家后台App二维码地址IOS版url
    @SerializedName(value = "STORE_QRCODE_IMG_IOS")
    public String STORE_QRCODE_IMG_IOS;  //商城App二维码地址IOS版
    @SerializedName(value = "STORE_QRCODE_IMG_AZ")
    public String STORE_QRCODE_IMG_AZ;     //商家后台App二维码地址安卓版
    @SerializedName(value = "STORE_QRCODE_IMG_AZ_URL")
    public String STORE_QRCODE_IMG_AZ_URL; //商家后台App二维码地址安卓版url
    @SerializedName(value = "STORE_WITHDRAW_EXPLAIN")
    public String STORE_WITHDRAW_EXPLAIN;  //商家后台提现说明
    @SerializedName(value = "STORE_HELP")
    public String STORE_HELP;              //商家帮助中心
    @SerializedName(value = "STORE_FX")
    public String STORE_FX;               //商家后台分享图片地址
    @SerializedName(value = "STORE_FP")
    public String STORE_FP;             //商家后台App首屏广告图
    @SerializedName(value = "STORE_SERVICE")
    public String STORE_SERVICE;        //商家后台App客服电话

    @SerializedName(value = "MARKETER_QRCODE_IMG_IOS")
    public String MARKETER_QRCODE_IMG_IOS; //事业部App二维码地址IOS版
    @SerializedName(value = "MARKETER_QRCODE_IMG_IOS_URL")
    public String MARKETER_QRCODE_IMG_IOS_URL; //事业部App二维码地址IOS版url
    @SerializedName(value = "MARKETER_QRCODE_IMG_AZ")
    public String MARKETER_QRCODE_IMG_AZ;   //事业部App二维码地址安卓版
    @SerializedName(value = "MARKETER_QRCODE_IMG_AZ_URL")
    public String MARKETER_QRCODE_IMG_AZ_URL; //商城App二维码地址安卓版url
    @SerializedName(value = "MARKETER_FX")
    public String MARKETER_FX;   //事业部App分享图片地址
    @SerializedName(value = "MARKETER_FP")
    public String MARKETER_FP;  //事业部App首屏广告图
    @SerializedName(value = "MARKETER_SERVICE_ADMIN")
    public String MARKETER_SERVICE_ADMIN;  //事业部App管理员电话
    @SerializedName(value = "MARKETER_SERVICE")
    public String MARKETER_SERVICE;  //事业部App客服电话

    @SerializedName(value = "ACTIVITY_4")
    public String ACTIVITY_4;    //活动页4
    @SerializedName(value = "ACTIVITY_3")
    public String ACTIVITY_3;  //活动页3
    @SerializedName(value = "ACTIVITY_1")
    public String ACTIVITY_1; //活动页1
    @SerializedName(value = "ACTIVITY_5")
    public String ACTIVITY_5;  //安卓 给我评分
    @SerializedName(value = "ACTIVITY_2")
    public String ACTIVITY_2;  //活动页2
    @SerializedName(value = "STORE_PREVIEW")
    public String STORE_PREVIEW;  //商家商家预览
    @SerializedName(value = "CCQPACT")
    public String CCQ_PACT;  //餐餐抢商家协议
    @SerializedName(value = "STOREHELP")
    public String STOREHELP;              //商家帮助中心


}
