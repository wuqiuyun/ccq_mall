package clerk.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/11/18.
 */
public class PerformanceMonBean extends  PerformanceBean {

    @SerializedName(value = "type")
    private String type;
    @SerializedName(value = "member_type")
    private String memberType;
    @SerializedName(value = "addtime")
    private long addtime;
    @SerializedName(value = "value")
    private String value;
    @SerializedName(value = "member_id")
    private String memberId;
    @SerializedName(value = "id")
    private String id;
    @SerializedName(value = "store_id")
    private String storeId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public long getAddtime() {
        return addtime;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
