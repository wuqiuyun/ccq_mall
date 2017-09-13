package clerk.sz28yun.com.help;

import android.content.Context;


import java.io.File;

import per.sue.gear2.controll.GearImageLoad;
import per.sue.gear2.controll.StorageManager;
import per.sue.gear2.utils.DiskUtils;


/*
* 文件名：
* 描 述：app文件存储管理
* 作 者：苏昭强
* 时 间：2016/3/29
*/
public class AppFileStoreHepler {

    private static final String APP_ROOT_PATH_NAME = "ccj_clerk";
    private static final String CACHE_ROOT_PATH_NAME = "cache";
    private static final String CACHE_IMAGE_ROOT_PATH_NAME = "images";
    private static final String CACHE_IMAGE_CUT_ROOT_PATH_NAME = "cut";
    private static final String CACHE_SCREEN_SHOT_ROOT_PATH_NAME = "screenshot";

    private String  rootPath = "";


    private static AppFileStoreHepler ourInstance = new AppFileStoreHepler();

    public static AppFileStoreHepler getInstance() {
        return ourInstance;
    }

    private AppFileStoreHepler() {
    }


    public void initialize(Context context){
        String path = "";
        path = DiskUtils.getDiskCacheDir(context.getApplicationContext());
        rootPath = path;
        AppFileStoreHepler.getInstance().setRootPath(path);
        String imageCacheDir = getCacheImagePath();
        GearImageLoad.getSingleton(context).initialize(imageCacheDir);
        StorageManager.getInstance().setTmpImagesPath(getCacheImageCutPath());//设置剪切图临时存储

    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getAppRootPath() {
        return  new StringBuilder(rootPath).append(File.separator)
                .append(APP_ROOT_PATH_NAME)
                .toString() ;
    }



    public String getAppCachePath(){
        return new StringBuilder(getAppRootPath())
                .append(File.separator)
                .append(CACHE_ROOT_PATH_NAME)
                .toString();
    }

    public String getCacheImagePath(){
        return new StringBuilder(getAppCachePath())
                .append(File.separator)
                .append(CACHE_IMAGE_ROOT_PATH_NAME)
                .toString();
    }


    public String getCacheImageCutPath(){
        return new StringBuilder(getCacheImagePath())
                .append(File.separator)
                .append(CACHE_IMAGE_CUT_ROOT_PATH_NAME)
                .toString();
    }

    public String getCacheScreenShot(){
        return new StringBuilder(getAppCachePath())
                .append(File.separator)
                .append(CACHE_SCREEN_SHOT_ROOT_PATH_NAME)
                .toString();
    }


}
