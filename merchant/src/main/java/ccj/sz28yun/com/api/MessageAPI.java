package ccj.sz28yun.com.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ccj.sz28yun.com.api.base.BaseURL;
import ccj.sz28yun.com.api.base.ClerkObserver;
import ccj.sz28yun.com.bean.MemberSysSMSBean;
import ccj.sz28yun.com.bean.MessageBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import per.sue.gear2.net.ApiConnectionFactory;
import per.sue.gear2.net.parser.GearParser;
import rx.Observable;

/**
 * Created by sue on 2017/1/3.
 */
public class MessageAPI {


    /**
     * 获取消息列表
     * @param token
     * @param merchantId
     * @param page
     * @return
     */
    public Observable<ArrayList<MessageBean>> getMessageList(String token, String merchantId, int page) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MESSAGE_LIST;
        Map<String, String> params = new HashMap<>();
        params.put("store_id", merchantId);
        params.put("token", token);
        params.put("page", page + "");
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<MessageBean>(MessageBean.class))
                .observeOnMainThread();
    }

    /**
     * 设置已读
     * @param token
     * @param messageId
     * @return
     */
    public Observable<String> setReaded(String token, String messageId) {
        String url = GlobalDataStorageCache.getInstance().getConfigData().API_DOMAIN + BaseURL.URL_MESSAGE_READ;
        Map<String, String> params = new HashMap<>();
        params.put("sm_id", messageId);
        params.put("token", token);
        return new ClerkObserver(ApiConnectionFactory.getInstance().createPOST(url, params), new GearParser<String>(String.class))
                .observeOnMainThread();
    }
}
