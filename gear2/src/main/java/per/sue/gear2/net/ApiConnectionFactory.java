package per.sue.gear2.net;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import per.sue.gear2.net.bean.InputFile;
import per.sue.gear2.net.progress.ProgressRequestListener;

/*
* 文件名：
* 描 述：
* 作 者：苏昭强
* 时 间：2016/4/29
*/
public class ApiConnectionFactory {

    public static  Context context;
    private static ApiConnectionFactory apiConnectionFactory = new ApiConnectionFactory();
    public Map<String, String> heads = new HashMap<>();

    public static ApiConnectionFactory getInstance() {
        return apiConnectionFactory;
    }

    public void  initialize(Context context){
        this.context = context.getApplicationContext();
    }

    public void  initialize(Context context,  Map<String, String> heads){
        this.context = context.getApplicationContext();
        this.heads = heads;
    }


    public  static ApiConnection createGET(String url){
        ApiConnection apiConnection =   new  ApiConnection.Builder().url(url).builder();
        return  apiConnection;

    }

    public static ApiConnection createPOST(String url, Map<String, String> params){
        return  create(url, params, null, null);
    }

    public static ApiConnection createPOSTFile(String url, Map<String, String> params, ArrayList<InputFile> files){
        return  create(url, params, files, null);
    }


    public static  ApiConnection createPOSTFile(String url, Map<String, String> params, ArrayList<InputFile> files, ProgressRequestListener progressRequestListener){
        return  create(url, params, files,  progressRequestListener);
    }

    public static ApiConnection create(String url, Map<String, String> params, ArrayList<InputFile> files, ProgressRequestListener progressRequestListener){
        ApiConnection apiConnection =  new  ApiConnection.Builder().url(url)
                .post(params)
                .files(files)
                .progressRequestListener(progressRequestListener)
                .builder(context);

        apiConnection.setHeads(getInstance().heads);
        return  apiConnection;
    }




}
