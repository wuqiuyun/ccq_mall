package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2017/1/3.
 */
public class MerchantAdsImageBean {

    @SerializedName(value = "store_image_id")
    private String storeImageId;
    @SerializedName(value = "abs_images")
    private String absImages;

    public MerchantAdsImageBean(String storeImageId, String absImages) {
        this.storeImageId = storeImageId;
        this.absImages = absImages;
    }

    public String getStoreImageId() {
        return storeImageId;
    }

    public void setStoreImageId(String storeImageId) {
        this.storeImageId = storeImageId;
    }

    public String getAbsImages() {
        return absImages;
    }

    public void setAbsImages(String absImages) {
        this.absImages = absImages;
    }

    @Override
    public String toString() {
        return absImages ;
    }
}
