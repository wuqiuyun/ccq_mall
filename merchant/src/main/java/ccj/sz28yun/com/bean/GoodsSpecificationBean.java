package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/12/5.
 */
public class GoodsSpecificationBean {
    @SerializedName(value = "spec_name")
    private String specName;
    @SerializedName(value = "spec_id")
    private String specId;

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    @Override
    public String toString() {
        return specName;
    }
}
