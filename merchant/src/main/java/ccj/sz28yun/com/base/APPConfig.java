package ccj.sz28yun.com.base;

/**
 * Created by sue on 2016/11/15.
 */
public class APPConfig {
    private static APPConfig ourInstance = new APPConfig();

    public static APPConfig getInstance() {
        return ourInstance;
    }

    private APPConfig() {
    }
}
