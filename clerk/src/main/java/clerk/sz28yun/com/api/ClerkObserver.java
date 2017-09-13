package clerk.sz28yun.com.api;

import clerk.sz28yun.com.bean.AccountBean;
import clerk.sz28yun.com.bean.UserBean;
import clerk.sz28yun.com.cache.APIParamsCache;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;
import clerk.sz28yun.com.presenter.LoginPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
    private boolean isParserInRoot;

    public ClerkObserver( ApiConnection apiConnection, Parser parser) {
        super( apiConnection, parser);
    }
    public ClerkObserver( ApiConnection apiConnection, Parser parser, boolean isParserInRoot) {
        super( apiConnection, parser);
        this.isParserInRoot = isParserInRoot;
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
            if (isParserInRoot){
                subscriber.onNext(parser.parse(json));
                subscriber.onCompleted();
            }else{
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
            }


            boolean hasResult = objectJson.has("data");
            if (hasResult) {
                String result = objectJson.getString("data");
                if (null == result || "null".equals(result)) {
                    result = "";
                }
                subscriber.onNext(parser.parse(result));
                subscriber.onCompleted();
            } else {
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        }else {
            AccountBean accountBean = GlobalDataStorageCache.getInstance().getAccountData();
            if(statusCode == 700 && null != accountBean){
                reLogin(accountBean);
            }else{
                subscriber.onError(new GearException(statusCode,  objectJson.getString("message")));
                subscriber.onCompleted();
            }

        }
    }

    /**
     * 重登录 未测试
     * @param accountBean
     */
    public void reLogin(AccountBean accountBean){
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
