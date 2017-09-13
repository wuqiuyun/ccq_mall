package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sue on 2016/12/4.
 */
public class GoodsCategoryBean implements Serializable{
    @SerializedName(value = "gc_show")
    private int gcShow;
    @SerializedName(value = "gc_id")
    private String gcId;
    @SerializedName(value = "gc_name")
    private String gcName;
    @SerializedName(value = "gc_sort")
    private String gcSort;
    @SerializedName(value = "type_id")
    private String typeId;
    @SerializedName(value = "gc_parent_id")
    private String gcParentId;
    @SerializedName(value = "class2")
    private ArrayList<GoodsCategoryBean>  childList;

    public int getGcShow() {
        return gcShow;
    }

    public void setGcShow(int gcShow) {
        this.gcShow = gcShow;
    }

    public String getGcId() {
        return gcId;
    }

    public void setGcId(String gcId) {
        this.gcId = gcId;
    }

    public String getGcName() {
        return gcName;
    }

    public void setGcName(String gcName) {
        this.gcName = gcName;
    }

    public String getGcSort() {
        return gcSort;
    }

    public void setGcSort(String gcSort) {
        this.gcSort = gcSort;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getGcParentId() {
        return gcParentId;
    }

    public void setGcParentId(String gcParentId) {
        this.gcParentId = gcParentId;
    }

    public ArrayList<GoodsCategoryBean> getChildList() {
        return childList;
    }

    public void setChildList(ArrayList<GoodsCategoryBean> childList) {
        this.childList = childList;
    }


    @Override
    public String toString() {
        return gcName;
    }
}
