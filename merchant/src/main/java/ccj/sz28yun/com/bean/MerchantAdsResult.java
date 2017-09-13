package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sue on 2017/1/3.
 */
public class MerchantAdsResult {

    @SerializedName(value = "abs_images")
    private ArrayList<MerchantAdsImageBean> imageList;

    @SerializedName(value = "adv")
    private String adv;

    public ArrayList<MerchantAdsImageBean> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<MerchantAdsImageBean> imageList) {
        this.imageList = imageList;
    }

    public String getAdv() {
        return adv;
    }

    public void setAdv(String adv) {
        this.adv = adv;
    }
}
