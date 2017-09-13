package clerk.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/11/17.
 */
public class BusinessBean {

    @SerializedName(value = "id")
    private String id;  //申请id
    @SerializedName(value = "company_address_detail")
    private String companyAddressDetail; //地址
    @SerializedName(value = "update_time")
    private String updateTime;  //审核时间
    @SerializedName(value = "joinin_message")
    private String joininMessage; //管理员审核信息
    @SerializedName(value = "seller_name")
    private String sellerName;  //商家账号
    @SerializedName(value = "c_time")
    private String cTime;    //上架时间
    @SerializedName(value = "addstore_image_02")
    private String addstoreImage_02;  //门店照片
    @SerializedName(value = "store_name")
    private String storeName;   //商家名称
    @SerializedName(value = "addtime")
    private long addtime;  //添加时间

    @SerializedName(value = "invite_vip_count")
    private String inviteVipCount;  //vip会员
    @SerializedName(value = "invite_member_count")
    private String inviteMemberCount;  //注册会员
    @SerializedName(value = "joinin_state")
    private int joininState;   //申请状态 10-已提交申请 30-审核失败 40-审核通过开店


    public String getStatusName(){
        String name = "";
        switch(joininState){
            case 10: name = "已提交申请";
                break;
            case 30: name = "审核失败";
                break;
            case 40: name = "审核通过开店";
                break;
            default:
                name = "未知状态";
                break;
        }
        return name;
    }

    public String getTimeName(){
        String name = "";
        switch(joininState){
            case 10: name = "提交时间";
                break;
            case 30: name = "拒绝时间";
                break;
            case 40: name = "上架时间";
                break;
            default:
                name = "提交时间";
                break;
        }
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyAddressDetail() {
        return companyAddressDetail;
    }

    public void setCompanyAddressDetail(String companyAddressDetail) {
        this.companyAddressDetail = companyAddressDetail;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getJoininMessage() {
        return joininMessage;
    }

    public void setJoininMessage(String joininMessage) {
        this.joininMessage = joininMessage;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getcTime() {
        return cTime;
    }

    public void setcTime(String cTime) {
        this.cTime = cTime;
    }

    public String getAddstoreImage_02() {
        return addstoreImage_02;
    }

    public void setAddstoreImage_02(String addstoreImage_02) {
        this.addstoreImage_02 = addstoreImage_02;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public long getAddtime() {
        return addtime;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }

    public String getInviteVipCount() {
        return inviteVipCount;
    }

    public void setInviteVipCount(String inviteVipCount) {
        this.inviteVipCount = inviteVipCount;
    }

    public String getInviteMemberCount() {
        return inviteMemberCount;
    }

    public void setInviteMemberCount(String inviteMemberCount) {
        this.inviteMemberCount = inviteMemberCount;
    }

    public int getJoininState() {
        return joininState;
    }

    public void setJoininState(int joininState) {
        this.joininState = joininState;
    }
}
