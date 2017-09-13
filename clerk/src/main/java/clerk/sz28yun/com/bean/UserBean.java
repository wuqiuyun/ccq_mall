package clerk.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 登录成功后的用户对象
 * Created by sue on 2016/11/15.
 */
public class UserBean implements Serializable{

    @SerializedName(value = "member_id")
    private String memberId; //会员id
    @SerializedName(value = "member_name")
    private String memberName; //用户名
    @SerializedName(value = "token")
    private String token; //token
    @SerializedName(value = "member_truename")
    private String memberTruename; //昵称
    @SerializedName(value = "member_type")
    private int memberType;
    @SerializedName(value = "status")
    private int status;

    public int getYunyingChild() {
        return yunyingChild;
    }

    public void setYunyingChild(int yunyingChild) {
        this.yunyingChild = yunyingChild;
    }

    @SerializedName(value = "yunying_child")
    private int yunyingChild; //1 是子账号

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMemberTruename() {
        return memberTruename;
    }

    public void setMemberTruename(String memberTruename) {
        this.memberTruename = memberTruename;
    }

    public int getMemberType() {
        return memberType;
    }

    public void setMemberType(int memberType) {
        this.memberType = memberType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
