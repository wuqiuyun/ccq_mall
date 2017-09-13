package ccj.sz28yun.com.cache;

import java.util.ArrayList;
import java.util.HashMap;

import ccj.sz28yun.com.bean.GoodsCategoryBean;
import ccj.sz28yun.com.bean.MemberSysStatusBean;

/**
 * 全局缓存
 * Created by sue on 2017/1/3.
 */
public class GlobalDataCache {
    private static GlobalDataCache ourInstance = new GlobalDataCache();

    private MemberSysStatusBean memberSysStatusBean;
    public  int num;
    public double cost;
    public HashMap<String, ArrayList<GoodsCategoryBean>> goodsCategoryMap = new HashMap<>();
    public double count;

    public static GlobalDataCache getInstance() {
        return ourInstance;
    }

    private GlobalDataCache() {
    }

    public MemberSysStatusBean getMemberSysStatusBean() {
        return memberSysStatusBean;
    }

    public void setMemberSysStatusBean(MemberSysStatusBean memberSysStatusBean) {
        this.memberSysStatusBean = memberSysStatusBean;
    }
}
