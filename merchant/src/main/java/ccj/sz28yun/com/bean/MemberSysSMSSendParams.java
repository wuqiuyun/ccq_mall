package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2017/1/3.
 */
public class MemberSysSMSSendParams {


    @SerializedName(value = "token")
    public String token;
    @SerializedName(value = "store_id")
    public String merchatId;   //商家id
    @SerializedName(value = "sms_content")
    public String smsContent;//短信内容
    @SerializedName(value = "sms_type")
    public int sysType;  //使用商家签名:0 使用餐餐抢签名:1
    @SerializedName(value = "type")
    public int sendType; //发送模式 立即发送:0 定时发送:1
    @SerializedName(value = "timing")
    public String sendTime; //默认为 ‘’ type = 1 定时发送时间(格式为时间戳)
    @SerializedName(value = "s_type")
    public int serviceType; //服务类型 全部会员:0 vip会员;1 指定会员:2
    @SerializedName(value = "designated_phone")
    public String designate; //默认为 ‘’ s_type = 2时为指定手机号
}
