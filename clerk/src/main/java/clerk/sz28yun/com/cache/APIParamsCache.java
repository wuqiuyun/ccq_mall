package clerk.sz28yun.com.cache;

import com.amap.api.location.AMapLocation;

/**
 * 这个类用来缓存api 公共参数
 * Created by sue on 2016/11/15.
 */
public class APIParamsCache {
    private static APIParamsCache ourInstance = new APIParamsCache();

    public static APIParamsCache getInstance() {
        return ourInstance;
    }

    private APIParamsCache() {
    }

    private String prefixToken; //登录前置Token
    private String logId;   // 验证码id
    private String lastDay;

    private AMapLocation aMapLocation;

    public String getPrefixToken() {
        return prefixToken;
    }

    public void setPrefixToken(String prefixToken) {
        this.prefixToken = prefixToken;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getLastDay() {
        return lastDay;
    }

    public void setLastDay(String lastDay) {
        this.lastDay = lastDay;
    }

    public AMapLocation getaMapLocation() {
        return aMapLocation;
    }

    public void setaMapLocation(AMapLocation aMapLocation) {
        this.aMapLocation = aMapLocation;
    }
}
