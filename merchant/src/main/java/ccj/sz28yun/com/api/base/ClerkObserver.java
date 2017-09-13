package ccj.sz28yun.com.api.base;



import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ccj.sz28yun.com.bean.AccountBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.APIParamsCache;
import ccj.sz28yun.com.cache.GlobalDataCache;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import ccj.sz28yun.com.presenter.LoginPresenter;
import per.sue.gear2.exception.GearException;
import per.sue.gear2.net.APIObserver;
import per.sue.gear2.net.ApiConnection;
import per.sue.gear2.net.exception.ParseException;
import per.sue.gear2.net.parser.Parser;
import per.sue.gear2.ui.GearView;
import per.sue.gear2.utils.GearLog;
import rx.Subscriber;

/**
 * 请求响应处理类
 * Created by sue on 2016/11/15.
 */
public class ClerkObserver extends APIObserver {

    private static final String TAG = "ClerkObserver";

    public ClerkObserver( ApiConnection apiConnection, Parser parser) {
        super( apiConnection, parser);
    }

    /**
     *处理返回的json ,并且 解析最外层json
     * @param subscriber
     * @param json
     * @param parser
     * @throws IOException
     * @throws JSONException
     * @throws ParseException
     */
    @Override
    public void dealSubscriber(Subscriber subscriber, String json, Parser parser) throws IOException, JSONException, ParseException {
        GearLog.e(TAG, "  resultJson = "  +  json);
        int statusCode = new JSONObject(json).getInt("code");
        JSONObject objectJson = new JSONObject(json);
        if (statusCode == 200) {
            if(objectJson.has("token")){//最外层json 含有重要数据， 如果返回有token，则缓存之
                String token = objectJson.getString("token");
                GearLog.e(TAG,"prefixToken =" + token);
                APIParamsCache.getInstance().setPrefixToken(token);
            }
            if(objectJson.has("log_id")){//最外层json 含有重要数据，如果返回有log_id，则缓存之
                String logId = objectJson.getString("log_id");
                GearLog.e(TAG,"logId =" + logId);
                APIParamsCache.getInstance().setLogId(logId);
            }

            if(objectJson.has("last_day")){//最外层json 含有重要数据，last_day，则缓存之
                String lastDay = objectJson.getString("last_day");
                GearLog.e(TAG,"last_day =" + lastDay);
                APIParamsCache.getInstance().setLastDay(lastDay);
            }

            if(objectJson.has("num")){//最外层json 含有重要数据，num，则缓存之, 会员系统列表用到
                String num = objectJson.getString("num");
                GearLog.e(TAG,"num =" + num);
                if(!TextUtils.isEmpty(num)){
                    GlobalDataCache.getInstance().num = Integer.valueOf(num);
                }

            }

            if(objectJson.has("cost")){//最外层json 含有重要数据，cost，则缓存之, 会员系统列表没用到
                String cost = objectJson.getString("cost");
                GearLog.e(TAG,"cost =" + cost);
                if(!TextUtils.isEmpty(cost)){
                    GlobalDataCache.getInstance().cost = Integer.valueOf(cost);
                }
            }

            if(objectJson.has("count")){//最外层json 含有重要数据，count，则缓存之, 财务
                String count = objectJson.getString("count");
                GearLog.e(TAG,"count =" + count);
                if(!TextUtils.isEmpty(count)){
                    GlobalDataCache.getInstance().count = Integer.valueOf(count);
                }
            }

            boolean hasResult = objectJson.has("data");
            if (hasResult) {//有就取data的json
                String result = objectJson.getString("data");
                if (null == result || "null".equals(result)) {
                    result = "";
                }
                subscriber.onNext(parser.parse(result));
                subscriber.onCompleted();
            } else if(objectJson.has("data1") || objectJson.has("data2")) {//另外格式
                subscriber.onNext(parser.parse(json));
                subscriber.onCompleted();
            }else{
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        }else {
            AccountBean accountBean = GlobalDataStorageCache.getInstance().getAccountData();
            if(statusCode == 700 && null != accountBean){
                reLogin(accountBean);
            }else{
                GearException gearException = new GearException(statusCode,  objectJson.getString("message"));
                gearException.setJson(json);
                subscriber.onError(gearException);
                subscriber.onCompleted();
            }

        }
    }

    /**
     * 重登录 未测试
     * @param accountBean
     */
    private void reLogin(AccountBean accountBean){
        GearLog.e(TAG, " reset login");
       new LoginPresenter( new LoginResultForView(), context).login(accountBean.getAccount(), accountBean.getAccount());
    }

    /**
     * 用来处理登录成功后的响应
     */
    public class LoginResultForView extends GearView implements LoginPresenter.LoginView{

        @Override
        public void onSuccess(UserBean result) {
            observeOnMainThread().subscribe(currentSubscriber);
        }
    }


}
