package clerk.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 商家业绩
 * Created by sue on 2016/11/21.
 */
public class PerformanceMerchantBean {

    @SerializedName(value = "union_img")
    private String unionImg; //商家图片
    @SerializedName(value = "store_name")
    private String storeName;  //商家名称
    @SerializedName(value = "sale_goods_num")
    private int saleGoodsNum; //餐餐抢上传
    @SerializedName(value = "invite_member_count")
    private int inviteMemberCount; //注册会员
    @SerializedName(value = "sale_check_goods_num")
    private int saleCheckGoodsNum; //已验证
    @SerializedName(value = "sum_invite_vip_count")
    private int sumInviteVipCount; //vip会员

    @SerializedName(value = "id")
    private String id; //id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnionImg() {
        return unionImg;
    }

    public void setUnionImg(String unionImg) {
        this.unionImg = unionImg;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getSaleGoodsNum() {
        return saleGoodsNum;
    }

    public void setSaleGoodsNum(int saleGoodsNum) {
        this.saleGoodsNum = saleGoodsNum;
    }

    public int getInviteMemberCount() {
        return inviteMemberCount;
    }

    public void setInviteMemberCount(int inviteMemberCount) {
        this.inviteMemberCount = inviteMemberCount;
    }

    public int getSaleCheckGoodsNum() {
        return saleCheckGoodsNum;
    }

    public void setSaleCheckGoodsNum(int saleCheckGoodsNum) {
        this.saleCheckGoodsNum = saleCheckGoodsNum;
    }

    public int getSumInviteVipCount() {
        return sumInviteVipCount;
    }

    public void setSumInviteVipCount(int sumInviteVipCount) {
        this.sumInviteVipCount = sumInviteVipCount;
    }
}
