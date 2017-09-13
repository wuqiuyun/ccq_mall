package ccj.sz28yun.com.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import ccj.sz28yun.com.bean.AccountBean;
import ccj.sz28yun.com.bean.ConfigBean;
import ccj.sz28yun.com.bean.UserBean;


/**
 * Created by sue on 2016/11/15.
 */
public class GlobalDataStorageCache {

    private static final String TAG = "GlobalDataCache";

    private final String SP_NAME = "ccj.sz28yun.com.app_info";
    private final String KEY_CONFIG = "ccj.sz28yun.com.KEY_CONFIG";
    private final String KEY_USER = "ccj.sz28yun.com.KEY_USER";
    private final String KEY_ACCOUNT = "ccj.sz28yun.com.KEY_ACCOUNT";

    private static GlobalDataStorageCache ourInstance = new GlobalDataStorageCache();
    private ConfigBean configBean;
    private AccountBean accountBean;
    private UserBean userBean;
    private  Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static synchronized GlobalDataStorageCache getInstance() {
        return ourInstance;
    }

    private GlobalDataStorageCache() {
    }

    public void  initialize(Context context){
        this.context = context.getApplicationContext();
        this.sharedPreferences = context.getSharedPreferences(SP_NAME, context.MODE_PRIVATE);
        this.editor = this.sharedPreferences.edit();
    }

    /**
     * 获取配置对象
     * @return
     */
    public ConfigBean getConfigData(){
        this.configBean = null;
        if(null ==  this.configBean){
            String json  = this.sharedPreferences.getString(KEY_CONFIG, null) ;
            this.configBean = new Gson().fromJson(json, ConfigBean.class);
        }
        return configBean;
    }


    /**
     * 存储配置对象
     * @param configBean
     */
    public void storeConfigData( ConfigBean configBean ){
        this.configBean = configBean;
        this.editor.putString(KEY_CONFIG, new Gson().toJson(configBean));
        this.editor.commit();
    }

    /**
     * 获取账号对象
     * @return
     */
    public AccountBean getAccountData(){
        this.configBean = null;
        if(null ==  this.accountBean){
            String json  = this.sharedPreferences.getString(KEY_ACCOUNT, null) ;
            this.accountBean = new Gson().fromJson(json, AccountBean.class);
        }
        return accountBean;
    }

    /**
     * 存储账号对象
     * @param bean
     */
    public void storeAccountData( AccountBean bean ){
        this.accountBean = bean;
        this.editor.putString(KEY_ACCOUNT, new Gson().toJson(bean));
        this.editor.commit();
    }

    /**
     * 获取用户对象
     * @return
     */
    public UserBean getUserData(){
        this.configBean = null;
        if(null ==  this.userBean){
            String json  = this.sharedPreferences.getString(KEY_USER, null) ;
            this.userBean = new Gson().fromJson(json, UserBean.class);
        }
        return userBean;
    }

    /**
     * 存储用户对象
     * @param bean
     */
    public void storeUserData( UserBean bean ){
        this.userBean = bean;
        this.editor.putString(KEY_USER, new Gson().toJson(bean));
        this.editor.commit();
    }


}
