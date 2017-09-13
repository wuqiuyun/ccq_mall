package clerk.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by meihuali on 2016/12/22.
 * 商家相片集合
 */
public class StoreListBean {
    @SerializedName(value = "store_list_01")
    private String storeList_01;
    @SerializedName(value = "store_list_01_id")
    private String storeListid_01;
    @SerializedName(value = "store_list_02")
    private String storeList_02;
    @SerializedName(value = "store_list_02_id")
    private String storeListid_02;
    @SerializedName(value = "store_list_03")
    private String storeList_03;
    @SerializedName(value = "store_list_03_id")
    private String storeListid_03;

    public String getStoreListid_03() {
        return storeListid_03;
    }

    public void setStoreListid_03(String storeListid_03) {
        this.storeListid_03 = storeListid_03;
    }

    public String getStoreList_01() {
        return storeList_01;
    }

    public void setStoreList_01(String storeList_01) {
        this.storeList_01 = storeList_01;
    }

    public String getStoreListid_01() {
        return storeListid_01;
    }

    public void setStoreListid_01(String storeListid_01) {
        this.storeListid_01 = storeListid_01;
    }

    public String getStoreList_02() {
        return storeList_02;
    }

    public void setStoreList_02(String storeList_02) {
        this.storeList_02 = storeList_02;
    }

    public String getStoreListid_02() {
        return storeListid_02;
    }

    public void setStoreListid_02(String storeListid_02) {
        this.storeListid_02 = storeListid_02;
    }

    public String getStoreList_03() {
        return storeList_03;
    }

    public void setStoreList_03(String storeList_03) {
        this.storeList_03 = storeList_03;
    }
}
