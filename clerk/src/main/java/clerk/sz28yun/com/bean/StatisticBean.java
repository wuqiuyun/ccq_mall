package clerk.sz28yun.com.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sue on 2016/12/21.
 */
public class StatisticBean {

    @SerializedName(value = "account")
    public FundsStatisticBean fundsStatisticBean ;

    @SerializedName(value = "member_num")
    public int memberCount;

    @SerializedName(value = "vip_member_num")
    public int memberVipCount;


    public static StatisticBean fromHomeBean(HomeBean homeBean){
        StatisticBean statisticBean = new StatisticBean();
        statisticBean.fundsStatisticBean = homeBean.getAccount();
        statisticBean.memberCount = homeBean.getMemberNum();
        statisticBean.memberVipCount = homeBean.getMemberVipNum();

        return statisticBean;

    }




}
