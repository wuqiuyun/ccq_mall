package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sue on 2016/12/6.
 */
public class EvaluateGoodsBean implements Serializable {

    @SerializedName(value = "geval_frommembername")
    private String gevalFrommembername;
    @SerializedName(value = "posi")
    private String posi;
    @SerializedName(value = "geval_content")
    private String gevalContent;
    @SerializedName(value = "eva")
    private int eva;
    @SerializedName(value = "geval_id")
    private String gevalId;
    @SerializedName(value = "is_posi")
    private int isPosi;
    @SerializedName(value = "image")
    private String image;
    @SerializedName(value = "geval_orderno")
    private String gevalOrderno;
    @SerializedName(value = "geval_addtime")
    private String gevalAddtime;

    public String getGevalFrommembername() {
        return gevalFrommembername;
    }

    public void setGevalFrommembername(String gevalFrommembername) {
        this.gevalFrommembername = gevalFrommembername;
    }

    public String getPosi() {
        return posi;
    }

    public void setPosi(String posi) {
        this.posi = posi;
    }

    public String getGevalContent() {
        return gevalContent;
    }

    public void setGevalContent(String gevalContent) {
        this.gevalContent = gevalContent;
    }

    public int getEva() {
        return eva;
    }

    public void setEva(int eva) {
        this.eva = eva;
    }

    public String getGevalId() {
        return gevalId;
    }

    public void setGevalId(String gevalId) {
        this.gevalId = gevalId;
    }

    public int getIsPosi() {
        return isPosi;
    }

    public void setIsPosi(int isPosi) {
        this.isPosi = isPosi;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGevalOrderno() {
        return gevalOrderno;
    }

    public void setGevalOrderno(String gevalOrderno) {
        this.gevalOrderno = gevalOrderno;
    }

    public String getGevalAddtime() {
        return gevalAddtime;
    }

    public void setGevalAddtime(String gevalAddtime) {
        this.gevalAddtime = gevalAddtime;
    }
}
