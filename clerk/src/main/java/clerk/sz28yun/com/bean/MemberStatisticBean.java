package clerk.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/11/21.
 */
public class MemberStatisticBean {

    @SerializedName(value = "member_name")
    private String memberName;
    @SerializedName(value = "sum_invite_vip_count")
    private int sumInviteVipCount; //VIP数量
    @SerializedName(value = "invite_member_count")
    private int inviteMemberCount; //注册人数数量
    @SerializedName(value = "invite_unvip_count")
    private int inviteUnvipCount;//普通会员

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getSumInviteVipCount() {
        return sumInviteVipCount;
    }

    public void setSumInviteVipCount(int sumInviteVipCount) {
        this.sumInviteVipCount = sumInviteVipCount;
    }

    public int getInviteMemberCount() {
        return inviteMemberCount;
    }

    public void setInviteMemberCount(int inviteMemberCount) {
        this.inviteMemberCount = inviteMemberCount;
    }

    public int getInviteUnvipCount() {
        return inviteUnvipCount;
    }

    public void setInviteUnvipCount(int inviteUnvipCount) {
        this.inviteUnvipCount = inviteUnvipCount;
    }
}
