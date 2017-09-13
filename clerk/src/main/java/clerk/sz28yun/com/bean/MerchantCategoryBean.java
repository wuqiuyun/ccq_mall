package clerk.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sue on 2016/11/25.
 */
public class MerchantCategoryBean implements Serializable{

    @SerializedName(value = "gc_name")
    private String gcName;
    @SerializedName(value = "gc_id")
    private String gcId;
    @SerializedName(value = "type_id")
    private String typeId;
    @SerializedName(value = "gc_sort")
    private int gcSort;
    @SerializedName(value = "gc_parent_id")
    private String gcParentId;
    @SerializedName(value = "gc_show")
    private int gcShow;
    @SerializedName(value = "class2")
    private ArrayList<MerchantCategoryBean> childList;

    public String getGcName() {
        return gcName;
    }

    public void setGcName(String gcName) {
        this.gcName = gcName;
    }

    public String getGcId() {
        return gcId;
    }

    public void setGcId(String gcId) {
        this.gcId = gcId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public int getGcSort() {
        return gcSort;
    }

    public void setGcSort(int gcSort) {
        this.gcSort = gcSort;
    }

    public String getGcParentId() {
        return gcParentId;
    }

    public void setGcParentId(String gcParentId) {
        this.gcParentId = gcParentId;
    }

    public int getGcShow() {
        return gcShow;
    }

    public void setGcShow(int gcShow) {
        this.gcShow = gcShow;
    }

    public ArrayList<MerchantCategoryBean> getChildList() {
        return childList;
    }

    public void setChildList(ArrayList<MerchantCategoryBean> childList) {
        this.childList = childList;
    }
}
