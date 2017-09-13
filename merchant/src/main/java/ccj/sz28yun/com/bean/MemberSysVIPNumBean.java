package ccj.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by meihuali on 2017/6/16.
 */

public class MemberSysVIPNumBean {

    @SerializedName(value = "a_invite_member_count")
    private String aInviteMemberCount;//会员数
    @SerializedName(value = "invite_vip_count")
    private String inviteVipCount;//vip会员数

    public String getaInviteMemberCount() {
        return aInviteMemberCount;
    }

    public void setaInviteMemberCount(String aInviteMemberCount) {
        this.aInviteMemberCount = aInviteMemberCount;
    }

    public String getInviteVipCount() {
        return inviteVipCount;
    }

    public void setInviteVipCount(String inviteVipCount) {
        this.inviteVipCount = inviteVipCount;
    }
}
