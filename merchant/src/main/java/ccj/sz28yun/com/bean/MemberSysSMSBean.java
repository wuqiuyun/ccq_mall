package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/12/15.
 */
public class MemberSysSMSBean  {

    private String id;
    @SerializedName(value = "audit_time")
    private String date;
    @SerializedName(value = "sms_sign")
    private String signtrue;
    @SerializedName(value = "s_type")
    private String type;
    @SerializedName(value = "cost")
    private double payment;
    @SerializedName(value = "status")
    private int status;
    private int count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSigntrue() {
        return signtrue;
    }

    public void setSigntrue(String signtrue) {
        this.signtrue = signtrue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
