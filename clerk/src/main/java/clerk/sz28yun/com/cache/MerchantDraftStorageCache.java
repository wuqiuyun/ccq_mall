package clerk.sz28yun.com.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import clerk.sz28yun.com.bean.MerchantParams;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by sue on 2016/12/22.
 */
public class MerchantDraftStorageCache {

    private final String SP_NAME = "com.sz28yun.clerk.merchant_draft";
    private final String KEY_MERCHANT_LIST= "com.sz28yun.clerk.KEY_MERCHANT_LIST_1.0";


    private final int SAVE_MAX_COUNT = 10;//最多保存数量



    private static MerchantDraftStorageCache ourInstance = new MerchantDraftStorageCache();

    private ArrayList<MerchantParams> merchantParamsArrayList;

    public static MerchantDraftStorageCache getInstance() {
        return ourInstance;
    }

    private MerchantDraftStorageCache() {
    }


    public void deleteMarchantParams(Context context, MerchantParams bean){
        if(null == merchantParamsArrayList){
            merchantParamsArrayList = getMerchantListFromSP(context);
        }
        int deleteIndex = -1;
        for(int i =0; i < merchantParamsArrayList.size();  i++ ){
            MerchantParams merchantParams = merchantParamsArrayList.get(i);
            if(bean.localId.equals(merchantParams.localId)){
                deleteIndex = i;
            }
        }

        if(deleteIndex >= 0){
            merchantParamsArrayList.remove(deleteIndex);
            SharedPreferences.Editor editor = context.getSharedPreferences(SP_NAME, context.MODE_PRIVATE).edit();
            editor .putString(KEY_MERCHANT_LIST, new Gson().toJson(merchantParamsArrayList));
            editor.commit();
            merchantParamsArrayList = getMerchantListFromSP(context);

        }
    }

    /**
     * 保存商家草稿，
     * @param context
     * @param bean
     * @return
     */
    public boolean  storeMerchantParams(Context context, MerchantParams bean){

        if(null == merchantParamsArrayList){
            merchantParamsArrayList = getMerchantListFromSP(context);
        }

        boolean hased = false;//先遍历列表, 原先数据有没包含, 包含有替换掉
        for(int i =0; i < merchantParamsArrayList.size();  i++ ){
            MerchantParams merchantParams = merchantParamsArrayList.get(i);
            if(bean.localId.equals(merchantParams.localId)){
                merchantParamsArrayList.set(i, bean);
                hased = true;

            }
        }

        if(!hased){//原先列表没包含有, 替换掉新添加
            if(merchantParamsArrayList.size() < SAVE_MAX_COUNT){
                merchantParamsArrayList .add(bean);
            }else{//如果超过最大限制， 则覆盖最久的
                merchantParamsArrayList.set(merchantParamsArrayList.size() -1, bean);
            }
        }

        bean.localDate = System.currentTimeMillis();
        Collections.sort(merchantParamsArrayList, new Comparator<MerchantParams>() {
            @Override
            public int compare(MerchantParams lhs, MerchantParams rhs) {
                return (int)(rhs.localDate - lhs.localDate);//最新的在前面
            }
        });
        SharedPreferences.Editor editor = context.getSharedPreferences(SP_NAME, context.MODE_PRIVATE).edit();
        editor .putString(KEY_MERCHANT_LIST, new Gson().toJson(merchantParamsArrayList));
        editor.commit();
        merchantParamsArrayList = getMerchantListFromSP(context);
        return true;
    }

    public ArrayList<MerchantParams> getMerchantListFromSP(Context context){
        String json =  context.getSharedPreferences(SP_NAME, context.MODE_PRIVATE).getString(KEY_MERCHANT_LIST, null) ;
        Type type = new TypeToken<ArrayList<MerchantParams>>() {}.getType();
        merchantParamsArrayList = new Gson().fromJson(json,type);

        if(null == merchantParamsArrayList){
            merchantParamsArrayList = new ArrayList<>();
        }

        return merchantParamsArrayList;
    }

    public ArrayList<MerchantParams> getMerchantParamsArrayList(Context context) {
        if(null == merchantParamsArrayList){
            merchantParamsArrayList = getMerchantListFromSP(context);
        }
        return merchantParamsArrayList;
    }
}
