package clerk.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sue on 2016/11/23.
 */
public class ImageUploadResult implements Serializable{

    @SerializedName(value = "id")
    private String id;
    @SerializedName(value = "url")
    private String url;

    public ImageUploadResult(){}
    public ImageUploadResult(String id, String url){
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
