package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by meihuali on 2017/6/17.
 */

public class MemberVipDetailCCJResult {

    @SerializedName(value = "order_list")
    private ArrayList<MemberVipDetailCCJBean> list;
    @SerializedName(value = "order_num")
    private String orderNum;
    @SerializedName(value = "member_name")
    private String memberName;

    public void initialize() {
        if (null == list) {
            list = new ArrayList<>();
        }
    }

    public ArrayList<MemberVipDetailCCJBean> getList() {
        return list;
    }

    public void setList(ArrayList<MemberVipDetailCCJBean> list) {
        this.list = list;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
