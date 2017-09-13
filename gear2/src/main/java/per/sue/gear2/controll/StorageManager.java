package per.sue.gear2.controll;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import per.sue.gear2.utils.StorageUtils;


/**
 * Created by langgu on 16/5/25.
 */
public class StorageManager {

    private static final String TAG = "StorageManager";
    private  String tmpImagesPath ;
    private final String TMP_IMAGE_PATH = "/tmpImages";


    private static StorageManager ourInstance = new StorageManager();

    public static StorageManager getInstance() {
        return ourInstance;
    }

    private StorageManager() {
    }


    public void setTmpImagesPath(String tmpImagesPath) {
        this.tmpImagesPath = tmpImagesPath;
    }

    private String getFileByUrl(String url){
        return  url.substring(url.lastIndexOf("/"));
    }


    public  File createImgFile(Context context) {
        String path = tmpImagesPath;
        if(null == path){
            path  = context.getExternalCacheDir().getAbsolutePath() + TMP_IMAGE_PATH;
            String storageState = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(storageState)) {
                path =  Environment.getExternalStorageDirectory().getAbsolutePath() + TMP_IMAGE_PATH;
            }
        }
        File file = new File(path);
        if (!file.exists())
        {
            file.mkdirs();
        }
        File ImageFile = new File(file, new StringBuilder(System.currentTimeMillis()+"").append(".jpg").toString() );
        return ImageFile;
    }




}
