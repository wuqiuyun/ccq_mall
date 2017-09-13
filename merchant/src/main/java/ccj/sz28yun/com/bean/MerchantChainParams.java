package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2017/1/3.
 */
public class MerchantChainParams {

    @SerializedName(value = "token")
    public String token;
    @SerializedName(value = "member_pwd")
    public String memberPwd;
    @SerializedName(value = "id")
    public String id;
    @SerializedName(value = "address")
    public String address;
    @SerializedName(value = "store_name")
    public String storeName;
    @SerializedName(value = "member_telephone")
    public String memberTelephone;
    @SerializedName(value = "member_name")
    public String memberName;
    @SerializedName(value = "contact_telephone")
    public String contactTelephone;
    @SerializedName(value = "store_id")
    public String storeId;


}
