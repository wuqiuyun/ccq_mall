package clerk.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by meihuali on 2016/12/22.
 * 厨房相片集合
 */
public class KitchenListBean {
    @SerializedName(value = "kitchen_list_01")
    private String kitchenList_01;
    @SerializedName(value = "kitchen_list_01_id")
    private String kitchenListID_01;
    @SerializedName(value = "kitchen_list_02")
    private String kitchenList_02;
    @SerializedName(value = "kitchen_list_02_id")
    private String kitchenListID_02;
    @SerializedName(value = "kitchen_list_03")
    private String kitchenList_03;
    @SerializedName(value = "kitchen_list_03_id")
    private String kitchenListID_03;

    public String getKitchenList_01() {
        return kitchenList_01;
    }

    public void setKitchenList_01(String kitchenList_01) {
        this.kitchenList_01 = kitchenList_01;
    }

    public String getKitchenListID_01() {
        return kitchenListID_01;
    }

    public void setKitchenListID_01(String kitchenListID_01) {
        this.kitchenListID_01 = kitchenListID_01;
    }

    public String getKitchenList_02() {
        return kitchenList_02;
    }

    public void setKitchenList_02(String kitchenList_02) {
        this.kitchenList_02 = kitchenList_02;
    }

    public String getKitchenListID_02() {
        return kitchenListID_02;
    }

    public void setKitchenListID_02(String kitchenListID_02) {
        this.kitchenListID_02 = kitchenListID_02;
    }

    public String getKitchenList_03() {
        return kitchenList_03;
    }

    public void setKitchenList_03(String kitchenList_03) {
        this.kitchenList_03 = kitchenList_03;
    }

    public String getKitchenListID_03() {
        return kitchenListID_03;
    }

    public void setKitchenListID_03(String kitchenListID_03) {
        this.kitchenListID_03 = kitchenListID_03;
    }


}
