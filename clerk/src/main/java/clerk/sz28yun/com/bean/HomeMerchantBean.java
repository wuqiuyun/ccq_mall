package clerk.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/11/16.
 */
public class HomeMerchantBean {

    @SerializedName(value = "id")
    private String id; //对应申请的id
    @SerializedName(value = "joinin_state")
    private int joininState; //商家状态 10-已提交申请 30-审核失败 40-审核通过开店
    @SerializedName(value = "store_name")
    private String storeName;  //商家名称


    public String getStatusName(){
        String name = "";
        switch(joininState){
            case 10: name = "审核中";
                break;
            case 30: name = "未通过";
                break;
            case 40: name = "已通过";
                break;
            default:
                name = "未知状态";
                break;
        }
        return name;
    }

    public int getJoininState() {
        return joininState;
    }

    public void setJoininState(int joininState) {
        this.joininState = joininState;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
