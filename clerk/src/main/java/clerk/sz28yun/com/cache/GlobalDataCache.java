package clerk.sz28yun.com.cache;

import clerk.sz28yun.com.bean.ShareConfigBean;
import clerk.sz28yun.com.bean.StatisticBean;

/**
 * Created by sue on 2016/12/22.
 */
public class GlobalDataCache {

    private static GlobalDataCache ourInstance = new GlobalDataCache();

    private ShareConfigBean shareConfigBean;
    private StatisticBean statisticBean;



    public static synchronized GlobalDataCache getInstance() {
        return ourInstance;
    }

    public ShareConfigBean getShareConfigData(){
        return shareConfigBean;
    }


    public void setShareConfigData( ShareConfigBean configBean ){
        this.shareConfigBean = configBean;

    }

    public StatisticBean getStatisticBean() {
        return statisticBean;
    }

    public void setStatisticBean(StatisticBean statisticBean) {
        this.statisticBean = statisticBean;
    }


}
