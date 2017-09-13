package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 商家评价
 * Created by sue on 2016/12/6.
 */
public class EvaluateMerchantBean  implements Serializable {

    @SerializedName(value = "is_posi")
    private int isPosi;
    @SerializedName(value = "eva")
    private int eva;
    @SerializedName(value = "seval_addtime")
    private String sevalAddtime;
    @SerializedName(value = "seval_membername")
    private String sevalMembername;
    @SerializedName(value = "image")
    private String image;
    @SerializedName(value = "seval_id")
    private String sevalId;
    @SerializedName(value = "seval_content")
    private String sevalContent;
    @SerializedName(value = "posi")
    private String posi;


    public int getIsPosi() {
        return isPosi;
    }

    public void setIsPosi(int isPosi) {
        this.isPosi = isPosi;
    }

    public int getEva() {
        return eva;
    }

    public void setEva(int eva) {
        this.eva = eva;
    }

    public String getSevalAddtime() {
        return sevalAddtime;
    }

    public void setSevalAddtime(String sevalAddtime) {
        this.sevalAddtime = sevalAddtime;
    }

    public String getSevalMembername() {
        return sevalMembername;
    }

    public void setSevalMembername(String sevalMembername) {
        this.sevalMembername = sevalMembername;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSevalId() {
        return sevalId;
    }

    public void setSevalId(String sevalId) {
        this.sevalId = sevalId;
    }

    public String getSevalContent() {
        return sevalContent;
    }

    public void setSevalContent(String sevalContent) {
        this.sevalContent = sevalContent;
    }

    public String getPosi() {
        return posi;
    }

    public void setPosi(String posi) {
        this.posi = posi;
    }
}
