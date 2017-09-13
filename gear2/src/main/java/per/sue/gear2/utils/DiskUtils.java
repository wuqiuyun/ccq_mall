package per.sue.gear2.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/*
* 文件名：
* 描 述：获取存储路径
* 作 者：苏昭强
* 时 间：2016/3/31
*/
public class DiskUtils {

    public static String getDiskCacheDir(Context context) {

        return  getDiskCacheDir(context,  null);
    }

    public static String getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable() || null != context.getExternalCacheDir()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        if(null == uniqueName || "".equals(uniqueName)){
            return cachePath;
        }else{
            return (cachePath + File.separator + uniqueName);
        }
    }


}
