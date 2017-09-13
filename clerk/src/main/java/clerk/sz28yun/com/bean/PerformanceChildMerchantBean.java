package clerk.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/12/22.
 */
public class PerformanceChildMerchantBean {

    @SerializedName(value = "invite_vip_count")
    private int inviteVipCount;
    @SerializedName(value = "seller_name")
    private String sellerName;
    @SerializedName(value = "invite_member_count")
    private int inviteMemberCount;
    @SerializedName(value = "addtime")
    private long addtime;
    @SerializedName(value = "store_name")
    private String storeName;
    @SerializedName(value = "id")
    private int id;
    @SerializedName(value = "addstore_image_02")
    private String addstoreImage_02;
    @SerializedName(value = "company_address_detail")
    private String companyAddressDetail;
    @SerializedName(value = "joinin_state")
    private int joininState;
    @SerializedName(value = "update_time")
    private String updateTime;
    @SerializedName(value = "joinin_message")
    private String joininMessage;
    @SerializedName(value = "c_time")
    private String cTime;

    public int getInviteVipCount() {
        return inviteVipCount;
    }

    public void setInviteVipCount(int inviteVipCount) {
        this.inviteVipCount = inviteVipCount;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public int getInviteMemberCount() {
        return inviteMemberCount;
    }

    public void setInviteMemberCount(int inviteMemberCount) {
        this.inviteMemberCount = inviteMemberCount;
    }

    public long getAddtime() {
        return addtime;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddstoreImage_02() {
        return addstoreImage_02;
    }

    public void setAddstoreImage_02(String addstoreImage_02) {
        this.addstoreImage_02 = addstoreImage_02;
    }

    public String getCompanyAddressDetail() {
        return companyAddressDetail;
    }

    public void setCompanyAddressDetail(String companyAddressDetail) {
        this.companyAddressDetail = companyAddressDetail;
    }

    public int getJoininState() {
        return joininState;
    }

    public void setJoininState(int joininState) {
        this.joininState = joininState;
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

    public String getcTime() {
        return cTime;
    }

    public void setcTime(String cTime) {
        this.cTime = cTime;
    }
}
