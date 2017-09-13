package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2017/1/3.
 */
public class EmployeeDetailsBean {

    @SerializedName(value = "seller_id")
    public String sellerId;
    @SerializedName(value = "phone")
    public String phone;
    @SerializedName(value = "name")
    public String name;
    @SerializedName(value = "seller_name")
    public String sellerName;
    @SerializedName(value = "seller_group_id")
    public int sellerGroupId;

}
