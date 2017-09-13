package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sue on 2017/1/3.
 */
public class MessageBean implements Serializable{

    @SerializedName(value = "sm_addtime")
    private String smAddtime;
    @SerializedName(value = "sm_content")
    private String smContent;
    @SerializedName(value = "sm_id")
    private String smId;
    @SerializedName(value = "read")
    private int read;
    @SerializedName(value = "smt_code")
    private String smtCode;

    public String getSmAddtime() {
        return smAddtime;
    }

    public void setSmAddtime(String smAddtime) {
        this.smAddtime = smAddtime;
    }

    public String getSmContent() {
        return smContent;
    }

    public void setSmContent(String smContent) {
        this.smContent = smContent;
    }

    public String getSmId() {
        return smId;
    }

    public void setSmId(String smId) {
        this.smId = smId;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public String getSmtCode() {
        return smtCode;
    }

    public void setSmtCode(String smtCode) {
        this.smtCode = smtCode;
    }
}
