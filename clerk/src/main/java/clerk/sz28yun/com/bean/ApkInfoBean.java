package clerk.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sue on 2017/1/20.
 */
public class ApkInfoBean implements Serializable{

    @SerializedName(value = "versions_3")
    public int versions_3;
    @SerializedName(value = "url")
    public String url;
    @SerializedName(value = "force")
    public int force;
    @SerializedName(value = "type")
    public int type;
    @SerializedName(value = "versions")
    public String versions;
    @SerializedName(value = "versions_1")
    public int versions_1;
    @SerializedName(value = "value")
    public int value;
    @SerializedName(value = "versions_info")
    public ArrayList<String> versionsInfo;
    @SerializedName(value = "versions_2")
    public int versions_2;
    @SerializedName(value = "id")
    public int id;
    @SerializedName(value = "app_versions")
    public int appVersions;
}
