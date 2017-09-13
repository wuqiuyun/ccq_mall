package per.sue.gear2.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;

import per.sue.gear2.exception.GearException;
import per.sue.gear2.net.exception.GearThrowable;
import per.sue.gear2.net.exception.NetworkConnectionException;
import per.sue.gear2.net.exception.ParseException;
import per.sue.gear2.net.parser.Parser;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/*
* 文件名：
* 描 述：
* 作 者：苏昭强
* 时 间：2015/12/22
*/
public abstract class APIObserver<T> {

    protected final Context context;
    private ApiConnection apiConnection;
    private IResponseIntercept iResponseIntercept;
    private static final int BUFFER_SIZE = 10 * 1024; // 8k ~ 32K

    protected Observable<T> observable;
    protected Parser<T> currentparser;
    protected Subscriber<? super T> currentSubscriber;


    public APIObserver( ApiConnection apiConnection, Parser<T> parser ) {
        this.context = apiConnection.getContext();
        this.apiConnection = apiConnection;
        this.currentparser = parser;
    }

    public Observable<File> observeFileOnMainThread(final File file) {
        return Observable.create(new Observable.OnSubscribe<File>() {

            @Override
            public void call(Subscriber<? super File> subscriber) {
                InputStream in = null;
                FileOutputStream out = null;
                int byteread = 0;
                // File tmpFile = new File (context.getExternalCacheDir().getAbsoluteFile()  + File.separator + "biggar.apk") ;
                try {
                    out = new FileOutputStream(file);
                    in = apiConnection.requestCall().body().byteStream();
                    byte[] buffer = new byte[BUFFER_SIZE];
                    while ((byteread = in.read(buffer)) != -1) {
                        out.write(buffer, 0, byteread);
                    }
                    subscriber.onNext(file);
                    subscriber.onCompleted();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    subscriber.onError(new GearException("文件未发现。"));
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(new GearException("数据读取出错。"));
                    subscriber.onCompleted();
                } finally {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<T> observeOnMainThread() {
        return observe()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<T> observe() {
        observable = Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        String json = apiConnection.requestCall().body().string();
                        if (null != iResponseIntercept) {
                            json = iResponseIntercept.onRespone(json);
                        }
                        currentSubscriber = subscriber;
                        dealSubscriber(subscriber, json, currentparser);
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (e instanceof ParseException) {
                            subscriber.onError(new ParseException("解析异常"));
                            subscriber.onCompleted();
                        } else if (e instanceof SocketTimeoutException) {
                            subscriber.onError(new NetworkConnectionException("连接超时"));
                            subscriber.onCompleted();
                        } else {
                            subscriber.onError(new NetworkConnectionException(e.getMessage()));
                            subscriber.onCompleted();
                        }
                    }
                }else{
                    subscriber.onError(new NetworkConnectionException("请求失败, 请检查网络状态"));
                    subscriber.onCompleted();
                }
            }
        });
        return observable;
    }

/*    public void dealSubscriber( Subscriber<? super T> subscriber, String json, final Parser<T> parser) throws IOException, JSONException, ParseException {
                int status = new JSONObject(json).getInt("status");
                JSONObject objectJson = new JSONObject(json);
                if (status == 1) {
                    boolean hasResult = objectJson.has("result");
                    if (hasResult) {
                        String result = objectJson.getString("result");
                        if (null == result || "null".equals(result)) {
                            result = "";
                        }
                        subscriber.onNext(parser.parse(result));
                        subscriber.onCompleted();
                    } else {
                        subscriber.onNext(null);
                        subscriber.onCompleted();
                    }
                } else {
                    subscriber.onError(new Exception(objectJson.getString("msg")));
                    subscriber.onCompleted();
                }
    }*/
public abstract void dealSubscriber( Subscriber<? super T> subscriber, String json, final Parser<T> parser) throws IOException, JSONException, ParseException;
    /**
     * Checks if the device has any active internet connection.
     * @return true device with internet connection, otherwise false.
     */
    private boolean isThereInternetConnection() {
        boolean isConnected;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());
        return isConnected;
    }

    public void setResponseIntercept(IResponseIntercept iResponseIntercept) {
        this.iResponseIntercept = iResponseIntercept;
    }


}
