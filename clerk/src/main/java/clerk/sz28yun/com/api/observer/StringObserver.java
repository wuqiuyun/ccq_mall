package clerk.sz28yun.com.api.observer;

import org.json.JSONException;

import java.io.IOException;

import per.sue.gear2.net.APIObserver;
import per.sue.gear2.net.ApiConnection;
import per.sue.gear2.net.exception.ParseException;
import per.sue.gear2.net.parser.Parser;
import per.sue.gear2.utils.GearLog;
import rx.Subscriber;

/**
 * Created by sue on 2016/12/21.
 */
public class StringObserver extends APIObserver {

    private static final String TAG = "StringObserver";

    public StringObserver(ApiConnection apiConnection, Parser parser) {
        super(apiConnection, parser);
    }

    @Override
    public void dealSubscriber(Subscriber subscriber, String json, Parser parser) throws IOException, JSONException, ParseException {
        GearLog.e(TAG, "  resultJson = "  +  json);
        subscriber.onNext(parser.parse(json));
        subscriber.onCompleted();
    }
}
