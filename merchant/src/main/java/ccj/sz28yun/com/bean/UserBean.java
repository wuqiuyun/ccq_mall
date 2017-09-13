package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 登录成功后的用户对象
 * Created by sue on 2016/11/15.
 */
public class UserBean implements Serializable{

    @SerializedName(value = "token")
    private String token;
    @SerializedName(value = "store_id")
    private String storeId;
    @SerializedName(value = "member_id")
    private String memberId;
    @SerializedName(value = "store_name")
    private String storeName;
    @SerializedName(value = "seller_group_id")
    private String sellerGroupId;
    @SerializedName(value = "member_truename")
    private String memberTruename;
    @SerializedName(value = "member_name")
    private String memberName;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getSellerGroupId() {
        return sellerGroupId;
    }

    public void setSellerGroupId(String sellerGroupId) {
        this.sellerGroupId = sellerGroupId;
    }

    public String getMemberTruename() {
        return memberTruename;
    }

    public void setMemberTruename(String memberTruename) {
        this.memberTruename = memberTruename;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
