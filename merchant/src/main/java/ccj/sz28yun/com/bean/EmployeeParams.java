package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2017/1/3.
 */
public class EmployeeParams {

    @SerializedName(value = "token")
    public String token;
    @SerializedName(value = "store_id")
    public String merchantId;
    @SerializedName(value = "type")
    public int type;
    @SerializedName(value = "phone")
    public String phone;
    @SerializedName(value = "seller_name")
    public String sellerName;
    @SerializedName(value = "name")
    public String name;
    @SerializedName(value = "password")
    public String password;

    @SerializedName(value = "seller_id")
    public String sellerId;


    public void setValueByDetails(EmployeeDetailsBean bean){
        this.type = bean.sellerGroupId;
        this.phone = bean.phone;
        this.sellerName = bean.sellerName;
        this.name = bean.name;
    }

}
